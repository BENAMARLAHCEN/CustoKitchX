package repository;

import conn.DatabaseConnection;
import repository.interf.IComponentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ComponentRepository implements IComponentRepository {
    private final Connection connection;

    public ComponentRepository() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public boolean applyTVA(int projectId, double tva) {
        String sql = "UPDATE components SET tva = ? WHERE projet_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, tva);
            statement.setInt(2, projectId);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
