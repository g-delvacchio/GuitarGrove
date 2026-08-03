package model.dao;

import model.bean.Prodotto;

import java.sql.*;
import java.util.*;

public class ProdottoDAO {

    public void doSave(Prodotto p) throws SQLException {

        String sql = """
                INSERT INTO prodotto
                (nome, marca, modello, descrizione, prezzo, stock, categoria, attivo, immagine)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getNome());
            ps.setString(2, p.getMarca());
            ps.setString(3, p.getModello());
            ps.setString(4, p.getDescrizione());
            ps.setDouble(5, p.getPrezzo());
            ps.setInt(6, p.getStock());
            ps.setString(7, p.getCategoria());
            ps.setBoolean(8, p.isAttivo());
            ps.setString(9, p.getImmagine());

            ps.executeUpdate();
        }
    }

    public void doSaveOrUpdate(Prodotto p) throws SQLException {

        if (doRetrieveByKey(p.getProductId()) == null) {

            doSave(p);

        } else {

            String sql = """
                    UPDATE prodotto
                    SET nome=?,
                        prezzo=?,
                        stock=?,
                        attivo=?,
                        immagine=?
                    WHERE product_id=?
                    """;

            try (Connection con = ConPool.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, p.getNome());
                ps.setDouble(2, p.getPrezzo());
                ps.setInt(3, p.getStock());
                ps.setBoolean(4, p.isAttivo());
                ps.setString(5, p.getImmagine());
                ps.setInt(6, p.getProductId());

                ps.executeUpdate();
            }
        }
    }

    public boolean doDelete(int id) throws SQLException {

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "DELETE FROM prodotto WHERE product_id=?")) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        }
    }

    public Prodotto doRetrieveByKey(int id) throws SQLException {

        String sql = "SELECT * FROM prodotto WHERE product_id=?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next())
                return map(rs);
        }

        return null;
    }

    public List<Prodotto> doRetrieveByCond(String cond) throws SQLException {

        List<Prodotto> list = new ArrayList<>();

        String sql = "SELECT * FROM prodotto WHERE " + cond;

        try (Connection con = ConPool.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next())
                list.add(map(rs));
        }

        return list;
    }

    public synchronized Collection<Prodotto> doRetrieveByFilter(double prezzoMin,
                                                                double prezzoMax,
                                                                String marca,
                                                                String categoria)
            throws SQLException {

        Collection<Prodotto> prodotti = new LinkedList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT * FROM prodotto WHERE attivo = TRUE"
        );

        List<Object> parametri = new ArrayList<>();

        sql.append(" AND prezzo >= ?");
        parametri.add(prezzoMin);

        sql.append(" AND prezzo <= ?");
        parametri.add(prezzoMax);

        if (marca != null && !marca.isBlank()) {
            sql.append(" AND marca = ?");
            parametri.add(marca);
        }

        if (categoria != null && !categoria.isBlank()) {
            sql.append(" AND categoria = ?");
            parametri.add(categoria);
        }

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < parametri.size(); i++) {
                ps.setObject(i + 1, parametri.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                prodotti.add(map(rs));
            }
        }

        return prodotti;
    }

    public List<Prodotto> doRetrieveAll() throws SQLException {
        return doRetrieveByCond("1=1");
    }

    private Prodotto map(ResultSet rs) throws SQLException {

        Prodotto p = new Prodotto();

        p.setProductId(rs.getInt("product_id"));
        p.setNome(rs.getString("nome"));
        p.setMarca(rs.getString("marca"));
        p.setModello(rs.getString("modello"));
        p.setDescrizione(rs.getString("descrizione"));
        p.setPrezzo(rs.getDouble("prezzo"));
        p.setStock(rs.getInt("stock"));
        p.setCategoria(rs.getString("categoria"));
        p.setAttivo(rs.getBoolean("attivo"));
        p.setImmagine(rs.getString("immagine"));

        return p;
    }

    public List<Prodotto> doSearch(String query) throws SQLException {

        List<Prodotto> list = new ArrayList<>();

        String sql = """
                SELECT *
                FROM prodotto
                WHERE nome LIKE ?
                   OR marca LIKE ?
                   OR categoria LIKE ?
                """;

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String q = "%" + query + "%";

            ps.setString(1, q);
            ps.setString(2, q);
            ps.setString(3, q);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }
        }

        return list;
    }

    public List<Prodotto> searchByName(String q) throws SQLException {

        List<Prodotto> list = new ArrayList<>();

        String sql = "SELECT * FROM prodotto WHERE nome LIKE ? LIMIT 10";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + q + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }
        }

        return list;
    }
}