package model.dao;

import model.bean.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ChitarraDAO {
	
    private static final String URL = "jdbc:mysql://localhost:3306/guitargrove";
    private static final String USER = "root";
    private static final String PASSWORD = "jesuisgiuseppe99";

    public List<ChitarraBean> getAllChitarre() {
    	
        List<ChitarraBean> chitarre = new ArrayList<>();
        String query = "SELECT * FROM chitarre";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                ChitarraBean chitarra = new ChitarraBean();
                chitarra.setId(resultSet.getInt("id"));
                chitarra.setNome(resultSet.getString("nome"));
                chitarra.setDescrizione(resultSet.getString("descrizione"));
                chitarra.setPrezzo(resultSet.getBigDecimal("prezzo"));
                chitarra.setQuantitaDisponibile(resultSet.getInt("quantita_disponibile"));
                chitarra.setImmagine(resultSet.getString("immagine"));

                chitarre.add(chitarra);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chitarre;
    }

    public void addChitarra(ChitarraBean chitarra) {
        String query = "INSERT INTO chitarre (nome, descrizione, prezzo, quantita_disponibile, immagine) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, chitarra.getNome());
            preparedStatement.setString(2, chitarra.getDescrizione());
            preparedStatement.setBigDecimal(3, chitarra.getPrezzo());
            preparedStatement.setInt(4, chitarra.getQuantitaDisponibile());
            preparedStatement.setString(5, chitarra.getImmagine());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateChitarra(ChitarraBean chitarra) {
    	
        String query = "UPDATE chitarre SET nome = ?, descrizione = ?, prezzo = ?, quantita_disponibile = ?, immagine = ? WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, chitarra.getNome());
            preparedStatement.setString(2, chitarra.getDescrizione());
            preparedStatement.setBigDecimal(3, chitarra.getPrezzo());
            preparedStatement.setInt(4, chitarra.getQuantitaDisponibile());
            preparedStatement.setString(5, chitarra.getImmagine());
            preparedStatement.setInt(6, chitarra.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteChitarra(int id) {
    	
        String query = "DELETE FROM chitarre WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}