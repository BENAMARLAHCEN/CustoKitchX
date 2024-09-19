package repository;

import conn.DatabaseConnection;
import model.Material;
import repository.interf.IMaterialRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MaterialRepository implements IMaterialRepository {
    private final Connection connection;

    public MaterialRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    // Other methods

    public boolean addMaterial(Material material) {
        String query = "INSERT INTO Materiaux (nom, cout_unitaire, quantite, type_composant, projet_id, cout_transport, coefficient_qualite) VALUES (?, ?, ?, 'Matériel', ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, material.getNom());
            preparedStatement.setDouble(2, material.getCoutUnitaire());
            preparedStatement.setDouble(3, material.getQuantite());
            preparedStatement.setInt(4, material.getProjetId());
            preparedStatement.setDouble(5, material.getCoutTransport());
            preparedStatement.setDouble(6, material.getCoefficientQualite());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateMaterial(Material material) {
        String query = "UPDATE Materiaux SET nom = ?, cout_unitaire = ?, quantite = ?, cout_transport = ?, coefficient_qualite = ? ,taux_tva = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, material.getNom());
            preparedStatement.setDouble(2, material.getCoutUnitaire());
            preparedStatement.setDouble(3, material.getQuantite());
            preparedStatement.setDouble(4, material.getCoutTransport());
            preparedStatement.setDouble(5, material.getCoefficientQualite());
            preparedStatement.setInt(6, material.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteMaterial(int id) {
        String query = "DELETE FROM Materiaux WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Optional<Material> getMaterial(int id) {
        String query = "SELECT * FROM Materiaux WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Material material = new Material();
                material.setId(resultSet.getInt("id"));
                material.setNom(resultSet.getString("nom"));
                material.setTypeComposant(resultSet.getString("type_composant"));
                material.setTauxTVA(resultSet.getDouble("taux_tva"));
                material.setProjetId(resultSet.getInt("projet_id"));
                material.setCoutUnitaire(resultSet.getDouble("cout_unitaire"));
                material.setQuantite(resultSet.getDouble("quantite"));
                material.setCoutTransport(resultSet.getDouble("cout_transport"));
                material.setCoefficientQualite(resultSet.getDouble("coefficient_qualite"));
                return Optional.of(material);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<List<Material>> getMaterials() {
        String query = "SELECT * FROM Materiaux";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return Optional.ofNullable(Material.fromResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<List<Material>> getMaterialsByProject(int projectId) {
        String query = "SELECT * FROM Materiaux WHERE projet_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return Optional.ofNullable(Material.fromResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


}
