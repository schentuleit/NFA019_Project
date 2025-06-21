package src.dao;

import src.model.Auteur;
import src.model.Dessin;
import src.model.Pixel;
import src.utils.DatabaseManager;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DessinDAO {

    public void save(Dessin dessin) {
        String insertDessinSQL = "INSERT INTO dessin (nom, largeur, hauteur, auteur_id) VALUES (?, ?, ?, ?)";
        String insertPixelSQL = "INSERT INTO pixel (dessin_id, x, y, couleur_hex) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmtDessin = conn.prepareStatement(insertDessinSQL, Statement.RETURN_GENERATED_KEYS)) {

            stmtDessin.setString(1, dessin.getNom());
            stmtDessin.setInt(2, dessin.getLargeur());
            stmtDessin.setInt(3, dessin.getHauteur());
            stmtDessin.setInt(4, dessin.getAuteur().getId());

            stmtDessin.executeUpdate();

            ResultSet rs = stmtDessin.getGeneratedKeys();
            if (rs.next()) {
                int dessinId = rs.getInt(1);
                dessin.setId(dessinId);

                try (PreparedStatement stmtPixel = conn.prepareStatement(insertPixelSQL)) {
                    for (Pixel pixel : dessin.getPixels()) {
                        stmtPixel.setInt(1, dessinId);
                        stmtPixel.setInt(2, pixel.getX());
                        stmtPixel.setInt(3, pixel.getY());
                        stmtPixel.setString(4, pixel.getCouleurHex());
                        stmtPixel.addBatch();
                    }
                    stmtPixel.executeBatch();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Dessin dessin) {
        String deletePixelsSQL = "DELETE FROM pixel WHERE dessin_id = ?";
        String insertPixelSQL = "INSERT INTO pixel (dessin_id, x, y, couleur_hex) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement deleteStmt = conn.prepareStatement(deletePixelsSQL)) {

            deleteStmt.setInt(1, dessin.getId());
            deleteStmt.executeUpdate();

            try (PreparedStatement insertStmt = conn.prepareStatement(insertPixelSQL)) {
                for (Pixel pixel : dessin.getPixels()) {
                    insertStmt.setInt(1, dessin.getId());
                    insertStmt.setInt(2, pixel.getX());
                    insertStmt.setInt(3, pixel.getY());
                    insertStmt.setString(4, pixel.getCouleurHex());
                    insertStmt.addBatch();
                }
                insertStmt.executeBatch();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Dessin> findAll() {
        List<Dessin> dessins = new ArrayList<>();
        String sql = "SELECT d.id AS dessin_id, d.nom AS dessin_nom, d.largeur, d.hauteur, " +
                "a.id AS auteur_id, a.nom AS auteur_nom " +
                "FROM dessin d " +
                "JOIN auteur a ON d.auteur_id = a.id";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int dessinId = rs.getInt("dessin_id");
                String nom = rs.getString("dessin_nom");
                int largeur = rs.getInt("largeur");
                int hauteur = rs.getInt("hauteur");

                Auteur auteur = new Auteur(rs.getInt("auteur_id"), rs.getString("auteur_nom"));
                Dessin dessin = new Dessin(dessinId, nom, largeur, hauteur, auteur);

                // Chargement des pixels
                dessin.setPixels(loadPixelsForDessin(conn, dessinId));
                dessins.add(dessin);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dessins;
    }
    public void deleteById(int id) {
        String deletePixelsSQL = "DELETE FROM pixel WHERE dessin_id = ?";
        String deleteDessinSQL = "DELETE FROM dessin WHERE id = ?";

        try (Connection conn = DatabaseManager.getConnection()) {
            // Supprimer d'abord les pixels (clé étrangère)
            try (PreparedStatement stmtPixels = conn.prepareStatement(deletePixelsSQL)) {
                stmtPixels.setInt(1, id);
                stmtPixels.executeUpdate();
            }

            // Puis supprimer le dessin
            try (PreparedStatement stmtDessin = conn.prepareStatement(deleteDessinSQL)) {
                stmtDessin.setInt(1, id);
                stmtDessin.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Pixel> loadPixelsForDessin(Connection conn, int dessinId) throws SQLException {
        List<Pixel> pixels = new ArrayList<>();
        String sql = "SELECT x, y, couleur_hex FROM pixel WHERE dessin_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, dessinId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int x = rs.getInt("x");
                int y = rs.getInt("y");
                String couleur = rs.getString("couleur_hex");
                pixels.add(new Pixel(x, y, couleur));
            }
        }

        return pixels;
    }
}
