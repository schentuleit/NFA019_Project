package src.dao;

import src.model.Auteur;
import src.utils.DatabaseManager;

import java.sql.*;

public class AuteurDAO {

    public Auteur findByName(String nom) {
        String sql = "SELECT id, nom FROM auteur WHERE nom = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nom);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Auteur(rs.getInt("id"), rs.getString("nom"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Auteur insert(Auteur auteur) {
        String sql = "INSERT INTO auteur (nom) VALUES (?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, auteur.getNom());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    auteur.setId(rs.getInt(1));
                }
            }
            return auteur;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Auteur findOrCreate(String nom) {
        Auteur a = findByName(nom);
        return (a != null) ? a : insert(new Auteur(nom));
    }
}
