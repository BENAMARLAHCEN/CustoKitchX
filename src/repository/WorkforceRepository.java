package repository;

import conn.DatabaseConnection;
import model.Workforce;
import repository.interf.IWorkforceRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public class WorkforceRepository implements IWorkforceRepository {
    private final Connection connection;

    public WorkforceRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public boolean addWorkforce(Workforce workforce) {
        String query = "INSERT INTO Materiaux (nom, type_composant, taux_horaire, heures_travail,productivite_ouvrier, projet_id) VALUES (?, 'Workforce', ?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, workforce.getNom());
            preparedStatement.setDouble(2, workforce.getTauxHoraire());
            preparedStatement.setDouble(3, workforce.getHeuresTravail());
            preparedStatement.setDouble(4, workforce.getProductiviteOuvrier());
            preparedStatement.setInt(5, workforce.getProjetId());
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateWorkforce(Workforce workforce) {
        String query = "UPDATE Materiaux SET nom = ?, taux_horaire = ?, heures_travail = ?, productivite_ouvrier = ?,taux_tva = ? WHERE id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, workforce.getNom());
            preparedStatement.setDouble(2, workforce.getTauxHoraire());
            preparedStatement.setDouble(3, workforce.getHeuresTravail());
            preparedStatement.setDouble(4, workforce.getProductiviteOuvrier());
            preparedStatement.setInt(5, workforce.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteWorkforce(int id) {
        String query = "DELETE FROM Materiaux WHERE id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Optional<Workforce> getWorkforce(int id) {
        String query = "SELECT * FROM Materiaux WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Workforce(
                        resultSet.getInt("id"),
                        resultSet.getString("nom"),
                        resultSet.getDouble("taux_tva"),
                        resultSet.getInt("projet_id"),
                        resultSet.getDouble("taux_horaire"),
                        resultSet.getDouble("heures_travail"),
                        resultSet.getDouble("productivite_ouvrier")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<List<Workforce>> getWorkforces() {
        String query = "SELECT * FROM Materiaux";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return Optional.ofNullable(Workforce.fromResultSet(resultSet));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<List<Workforce>> getWorkforcesByProject(int projectId) {
        String query = "SELECT * FROM Materiaux WHERE projet_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return Optional.ofNullable(Workforce.fromResultSet(resultSet));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }


}
