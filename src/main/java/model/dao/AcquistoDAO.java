package model.dao;

import model.bean.Acquisto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AcquistoDAO {

    public int doSave(Acquisto a) throws SQLException {

        String sql = "INSERT INTO acquisto " +
        		"(user_id, totale, spedizione, stato, data_acquisto, pagamento, " +
                "paese_spedizione, citta_spedizione, cap_spedizione, via_spedizione, civico_spedizione) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, a.getUserId());
            ps.setDouble(2, a.getTotale());
            ps.setDouble(3, a.getSpedizione());
            ps.setString(4, a.getStato());

            if (a.getDataAcquisto() != null) {
                ps.setTimestamp(5, Timestamp.valueOf(a.getDataAcquisto()));
            } else {
                ps.setTimestamp(5, Timestamp.valueOf(java.time.LocalDateTime.now()));
            }

            ps.setString(6, a.getPagamento());
            ps.setString(7, a.getPaeseSpedizione());
            ps.setString(8, a.getCittaSpedizione());
            ps.setString(9, a.getCapSpedizione());
            ps.setString(10, a.getViaSpedizione());
            ps.setString(11, a.getCivicoSpedizione());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return -1;
    }


    public void doSaveOrUpdate(Acquisto a) throws SQLException {

        String sql = "UPDATE acquisto SET stato=?, pagamento=? WHERE order_id=?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, a.getStato());
            ps.setString(2, a.getPagamento());
            ps.setInt(3, a.getOrderId());

            ps.executeUpdate();
        }
    }


    public boolean doDelete(int orderId) throws SQLException {

        String sql = "DELETE FROM acquisto WHERE order_id=?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            return ps.executeUpdate() > 0;
        }
    }


    public Acquisto doRetrieveByKey(int orderId) throws SQLException {

        String sql = "SELECT * FROM acquisto WHERE order_id=?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orderId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return map(rs);
                }
            }
        }

        return null;
    }


    public List<Acquisto> doRetrieveByCond(String cond) throws SQLException {

        List<Acquisto> list = new ArrayList<>();

        String sql = "SELECT * FROM acquisto WHERE " + cond;

        try (Connection con = ConPool.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(map(rs));
            }
        }

        return list;
    }


    public List<Acquisto> doRetrieveAll() throws SQLException {

        return doRetrieveByCond("1=1");
    }


    private Acquisto map(ResultSet rs) throws SQLException {

        Acquisto a = new Acquisto();

        a.setOrderId(rs.getInt("order_id"));
        a.setUserId(rs.getInt("user_id"));
        a.setTotale(rs.getDouble("totale"));
        a.setSpedizione(rs.getDouble("spedizione"));
        a.setStato(rs.getString("stato"));

        Timestamp timestamp = rs.getTimestamp("data_acquisto");

        if (timestamp != null) {
            a.setDataAcquisto(timestamp.toLocalDateTime());
        }

        a.setPagamento(rs.getString("pagamento"));
        a.setPaeseSpedizione(rs.getString("paese_spedizione"));
        a.setCittaSpedizione(rs.getString("citta_spedizione"));
        a.setCapSpedizione(rs.getString("cap_spedizione"));
        a.setViaSpedizione(rs.getString("via_spedizione"));
        a.setCivicoSpedizione(rs.getString("civico_spedizione"));

        return a;
    }
}