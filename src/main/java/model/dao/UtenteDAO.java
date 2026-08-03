package model.dao;

import model.bean.Utente;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAO {


    public void doSave(Utente u) throws SQLException {
        String sql = "INSERT INTO utente" +
                "(username,email,password_hash,nome,cognome,telefono,created_at,admin) " +
                "VALUES (?,?,?,?,?,?,?,?)";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPasswordHash());
            ps.setString(4, u.getNome());
            ps.setString(5, u.getCognome());
            ps.setString(6, u.getTelefono());

            if (u.getCreatedAt() != null) {
                ps.setTimestamp(7, Timestamp.valueOf(u.getCreatedAt()));
            } else {
                ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            }

            ps.setBoolean(8, u.isAdmin());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {

                if (rs.next()) {
                    u.setUserId(rs.getInt(1));
                }
            }
        }
    }

    public void doSaveOrUpdate(Utente u) throws SQLException {
        if (doRetrieveByKey(u.getUserId()) == null) {
            doSave(u);
        } else {

            String sql = "UPDATE utente SET " +
                    "username=?, email=?, password_hash=?, nome=?, cognome=?, " +
                    "telefono=?, created_at=?, admin=? " +
                    "WHERE user_id=?";

            try (Connection con = ConPool.getConnection();
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, u.getUsername());
                ps.setString(2, u.getEmail());
                ps.setString(3, u.getPasswordHash());
                ps.setString(4, u.getNome());
                ps.setString(5, u.getCognome());
                ps.setString(6, u.getTelefono());

                if (u.getCreatedAt() != null) {
                    ps.setTimestamp(7, Timestamp.valueOf(u.getCreatedAt()));
                } else {
                    ps.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
                }

                ps.setBoolean(8, u.isAdmin());
                ps.setInt(9, u.getUserId());

                ps.executeUpdate();
            }
        }
    }

    public boolean doDelete(int id) throws SQLException {
        String sql = "DELETE FROM utente WHERE user_id=?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;
        }
    }

    public Utente doRetrieveByKey(int id) throws SQLException {
        String sql = "SELECT * FROM utente WHERE user_id=?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return map(rs);
                }
            }
        }

        return null;
    }

    public List<Utente> doRetrieveByCond(String condizione) throws SQLException {

        List<Utente> list = new ArrayList<>();
        String sql = "SELECT * FROM utente WHERE " + condizione;

        try (Connection con = ConPool.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                list.add(map(rs));
            }
        }

        return list;
    }

    public Utente doRetrieveByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM utente WHERE email=?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    public List<Utente> doRetrieveAll() throws SQLException {
        return doRetrieveByCond("1=1");
    }

    private Utente map(ResultSet rs) throws SQLException {
        Utente u = new Utente();

        u.setUserId(rs.getInt("user_id"));
        u.setUsername(rs.getString("username"));
        u.setEmail(rs.getString("email"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setNome(rs.getString("nome"));
        u.setCognome(rs.getString("cognome"));
        u.setTelefono(rs.getString("telefono"));

        Timestamp timestamp = rs.getTimestamp("created_at");
        if (timestamp != null) {
            u.setCreatedAt(timestamp.toLocalDateTime());
        }


        u.setAdmin(rs.getBoolean("admin"));


        return u;
    }

    public boolean existsEmail(String email) throws SQLException {
        String sql = "SELECT 1 FROM utente WHERE email=?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean existsUsername(String username) throws SQLException {
        String sql = "SELECT 1 FROM utente WHERE username=?";

        try (Connection con = ConPool.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

}