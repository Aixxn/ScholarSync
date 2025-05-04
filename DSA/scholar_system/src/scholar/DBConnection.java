package scholar;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
private static final String DB\_URL = "jdbc\:mysql://localhost:3306/scholarsyncdb";
private static final String USER = "root";
private static final String PASSWORD = ""; // default XAMPP password is empty

```
private static Connection connection;

public static Connection getConnection() throws SQLException {
    if (connection == null || connection.isClosed()) {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Create a connection to the database
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Database connected successfully");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found");
            e.printStackTrace();
            throw new SQLException("JDBC Driver not found", e);
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            e.printStackTrace();
            throw e;
        }
    }
    return connection;
}

public static void closeConnection() {
    try {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Database connection closed");
        }
    } catch (SQLException e) {
        System.err.println("Error closing connection");
        e.printStackTrace();
    }
}
```

}
