package model.dao;

import model.bean.ProdottoCarrello;
import java.sql.*;
import java.util.*;

public class ProdottoCarrelloDAO {

    public void doSave(ProdottoCarrello pc) throws SQLException {
        String sql = "INSERT INTO prodotto_carrello VALUES (?,?,?)";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pc.getUserId());
            ps.setInt(2, pc.getProductId());
            ps.setInt(3, pc.getQuantita());
            ps.executeUpdate();
        }
    }

    public void doSaveOrUpdate(ProdottoCarrello pc) throws SQLException {
        String sql = """
            INSERT INTO prodotto_carrello(user_id,product_id,quantita)
            VALUES (?,?,?)
            ON DUPLICATE KEY UPDATE quantita=?
        """;

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pc.getUserId());
            ps.setInt(2, pc.getProductId());
            ps.setInt(3, pc.getQuantita());
            ps.setInt(4, pc.getQuantita());

            ps.executeUpdate();
        }
    }

    public boolean doDelete(int userId, int productId) throws SQLException {
        String sql = "DELETE FROM prodotto_carrello WHERE user_id=? AND product_id=?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;
        }
    }

    public ProdottoCarrello doRetrieveByKey(int userId, int productId) throws SQLException {
        String sql = "SELECT * FROM prodotto_carrello WHERE user_id=? AND product_id=?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, productId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ProdottoCarrello pc = new ProdottoCarrello();
                pc.setUserId(rs.getInt("user_id"));
                pc.setProductId(rs.getInt("product_id"));
                pc.setQuantita(rs.getInt("quantita"));
                return pc;
            }
        }
        return null;
    }

    public List<ProdottoCarrello> doRetrieveByCond(String cond) throws SQLException {
        List<ProdottoCarrello> list = new ArrayList<>();
        String sql = "SELECT * FROM prodotto_carrello WHERE " + cond;

        try (Connection con = ConPool.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                ProdottoCarrello pc = new ProdottoCarrello();
                pc.setUserId(rs.getInt("user_id"));
                pc.setProductId(rs.getInt("product_id"));
                pc.setQuantita(rs.getInt("quantita"));
                list.add(pc);
            }
        }
        return list;
    }

    public List<ProdottoCarrello> doRetrieveAll() throws SQLException {
        return doRetrieveByCond("1=1");
    }
}