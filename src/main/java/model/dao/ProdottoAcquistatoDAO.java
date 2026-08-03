package model.dao;

import model.bean.ProdottoAcquistato;
import java.sql.*;
import java.util.*;

public class ProdottoAcquistatoDAO {

    public void doSave(ProdottoAcquistato p) throws SQLException {
        String sql = "INSERT INTO prodotto_acquistato VALUES (?,?,?,?)";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getOrderId());
            ps.setInt(2, p.getProductId());
            ps.setInt(3, p.getQuantita());
            ps.setDouble(4, p.getPrezzo());

            ps.executeUpdate();
        }
    }

    public void doSaveOrUpdate(ProdottoAcquistato p) throws SQLException {
        String sql = """
            INSERT INTO prodotto_acquistato(order_id,product_id,quantita,prezzo)
            VALUES (?,?,?,?)
            ON DUPLICATE KEY UPDATE quantita=?, prezzo=?
        """;

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getOrderId());
            ps.setInt(2, p.getProductId());
            ps.setInt(3, p.getQuantita());
            ps.setDouble(4, p.getPrezzo());

            ps.setInt(5, p.getQuantita());
            ps.setDouble(6, p.getPrezzo());

            ps.executeUpdate();
        }
    }

    public boolean doDelete(int orderId, int productId) throws SQLException {
        String sql = "DELETE FROM prodotto_acquistato WHERE order_id=? AND product_id=?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;
        }
    }

    public ProdottoAcquistato doRetrieveByKey(int orderId, int productId) throws SQLException {
        String sql = "SELECT * FROM prodotto_acquistato WHERE order_id=? AND product_id=?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ps.setInt(2, productId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ProdottoAcquistato p = new ProdottoAcquistato();
                p.setOrderId(rs.getInt("order_id"));
                p.setProductId(rs.getInt("product_id"));
                p.setQuantita(rs.getInt("quantita"));
                p.setPrezzo(rs.getDouble("prezzo"));
                return p;
            }
        }
        return null;
    }

    public List<ProdottoAcquistato> doRetrieveByCond(String cond) throws SQLException {
        List<ProdottoAcquistato> list = new ArrayList<>();
        String sql = "SELECT * FROM prodotto_acquistato WHERE " + cond;

        try (Connection con = ConPool.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                ProdottoAcquistato p = new ProdottoAcquistato();
                p.setOrderId(rs.getInt("order_id"));
                p.setProductId(rs.getInt("product_id"));
                p.setQuantita(rs.getInt("quantita"));
                p.setPrezzo(rs.getDouble("prezzo"));
                list.add(p);
            }
        }
        return list;
    }

    public List<ProdottoAcquistato> doRetrieveAll() throws SQLException {
        return doRetrieveByCond("1=1");
    }
}