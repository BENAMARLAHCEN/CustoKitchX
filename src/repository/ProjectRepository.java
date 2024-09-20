package repository;

import conn.DatabaseConnection;
import model.EtatProjet;
import model.Project;
import repository.interf.IProjectRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

public class ProjectRepository implements IProjectRepository {
    private final Connection connection;

    public ProjectRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public Project createProject(Project project) {
        String sql = "INSERT INTO projects (nom_projet,etat_projet, client_id) VALUES (?, ?::etat_projet, ?) RETURNING id";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, project.getNomProjet());
            statement.setString(2, project.getEtatProjet().toString());
            statement.setInt(3, project.getClientId());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                project.setId(resultSet.getInt("id"));
                return project;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Optional<Project> getProject(int id) {
        String sql = "SELECT * FROM projects WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Project project= new Project(
                    resultSet.getInt("id"),
                    resultSet.getString("nom_projet"),
                    resultSet.getDouble("marge_beneficiaire"),
                    resultSet.getDouble("cout_total"),
                    EtatProjet.valueOf(resultSet.getString("etat_projet")),
                    resultSet.getInt("client_id")
                );
                return Optional.of(project);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Project>  getProjects() {
        String sql = "SELECT * FROM projects";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            List<Project> projects = Project.fromResultSet(resultSet);
            return projects;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateProject(Project project) {
        String sql = "UPDATE projects SET nom_projet = ?, marge_beneficiaire = ?, cout_total = ?, etat_projet = ?::etat_projet, client_id = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, project.getNomProjet());
            statement.setDouble(2, project.getMargeBeneficiaire());
            statement.setDouble(3, project.getCoutTotal());
            statement.setString(4, project.getEtatProjet().toString());
            statement.setInt(5, project.getClientId());
            statement.setInt(6, project.getId());
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteProject(int id) {
        String sql = "DELETE FROM projects WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean applyMargeBeneficiaire(int id, double margeBeneficiaire){
        String sql = "UPDATE projects SET marge_beneficiaire = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, margeBeneficiaire);
            statement.setInt(2, id);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
