package conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private static final String URL = "jdbc:postgresql://localhost:5432/bati_cuisine_db";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";

    private DatabaseConnection() {
    }

    public Connection getConnection() {
        if(connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connection established successfully!");
            } catch (ClassNotFoundException e) {
                System.err.println("PostgreSQL JDBC Driver not found. Include it in your library path.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.err.println("Failed to establish a connection to the database.");
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static DatabaseConnection getInstance() {
        if(instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}
