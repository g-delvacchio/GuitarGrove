package model.dao;

import model.bean.Indirizzo;
import java.sql.*;
import java.util.*;

public class IndirizzoDAO {

    public void doSave(Indirizzo i) throws SQLException {
        String sql = "INSERT INTO indirizzo VALUES (?,?,?,?,?,?)";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, i.getUserId());
            ps.setString(2, i.getPaese());
            ps.setString(3, i.getCitta());
            ps.setString(4, i.getCap());
            ps.setString(5, i.getVia());
            ps.setString(6, i.getCivico());

            ps.executeUpdate();
        }
    }

    public void doSaveOrUpdate(Indirizzo i) throws SQLException {
        String sql = """
            INSERT INTO indirizzo(user_id,paese,citta,cap,via,civico)
            VALUES (?,?,?,?,?,?)
            ON DUPLICATE KEY UPDATE
            paese=?, citta=?, cap=?, via=?, civico=?
        """;

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, i.getUserId());
            ps.setString(2, i.getPaese());
            ps.setString(3, i.getCitta());
            ps.setString(4, i.getCap());
            ps.setString(5, i.getVia());
            ps.setString(6, i.getCivico());

            ps.setString(7, i.getPaese());
            ps.setString(8, i.getCitta());
            ps.setString(9, i.getCap());
            ps.setString(10, i.getVia());
            ps.setString(11, i.getCivico());

            ps.executeUpdate();
        }
    }

    public boolean doDelete(int userId) throws SQLException {
        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM indirizzo WHERE user_id=?")) {

            ps.setInt(1, userId);
            return ps.executeUpdate() > 0;
        }
    }

    public Indirizzo doRetrieveByKey(int userId) throws SQLException {
        String sql = "SELECT * FROM indirizzo WHERE user_id=?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) return map(rs);
        }
        return null;
    }

    public List<Indirizzo> doRetrieveByCond(String cond) throws SQLException {
        List<Indirizzo> list = new ArrayList<>();
        String sql = "SELECT * FROM indirizzo WHERE " + cond;

        try (Connection con = ConPool.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    public List<Indirizzo> doRetrieveAll() throws SQLException {
        return doRetrieveByCond("1=1");
    }

    private Indirizzo map(ResultSet rs) throws SQLException {
        Indirizzo i = new Indirizzo();
        i.setUserId(rs.getInt("user_id"));
        i.setPaese(rs.getString("paese"));
        i.setCitta(rs.getString("citta"));
        i.setCap(rs.getString("cap"));
        i.setVia(rs.getString("via"));
        i.setCivico(rs.getString("civico"));
        return i;
    }
}