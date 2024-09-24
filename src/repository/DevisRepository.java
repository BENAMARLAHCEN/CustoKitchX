package repository;

import conn.DatabaseConnection;
import model.Devis;
import repository.interf.IDevisRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class DevisRepository implements IDevisRepository {
    private final Connection connection;

    public DevisRepository(){
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

// DevisRepository.java
public boolean createDevis(Devis devis) {
    try {
        String query = "INSERT INTO devis (montant_estime, date_emission, date_validite, accepte, projet_id) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setDouble(1, devis.getMontantEstime());
        statement.setDate(2, devis.getDateEmission() != null ? Date.valueOf(devis.getDateEmission()) : null);
        statement.setDate(3, devis.getDateValidite() != null ? Date.valueOf(devis.getDateValidite()) : null);
        statement.setBoolean(4, devis.isAccepte());
        statement.setInt(5, devis.getProjectId());
        return statement.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}

    public boolean updateDevis(Devis devis) {
        try {
            String query = "UPDATE devis SET montant_estime = ?, date_emission = ?, date_validite = ?, accepte = ?, projet_id = ? WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setDouble(1, devis.getMontantEstime());
            statement.setDate(2, Date.valueOf(devis.getDateEmission()));
            statement.setDate(3, Date.valueOf(devis.getDateValidite()));
            statement.setBoolean(4, devis.isAccepte());
            statement.setInt(5, devis.getProjectId());
            statement.setInt(6, devis.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteDevis(int id) {
        try {
            String query = "DELETE FROM devis WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Optional<Devis> getDevisById(int id) {
        String query = "SELECT * FROM devis WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            return Optional.of(Devis.fromResultSet(statement.executeQuery()).getFirst());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Devis> getDevisByProjectId(int projectId) {
        String query = "SELECT * FROM devis WHERE projet_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, projectId);
            return Optional.of(Devis.fromResultSet(statement.executeQuery()).getFirst());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
