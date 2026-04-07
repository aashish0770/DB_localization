package one;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MariaDbConnection {

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(
                        "jdbc:mariadb://localhost:3306/shopping_cart_localization?user=root&password=dark");
            } catch (SQLException e) {
                System.out.println("Connection failed: " + e);
            }
        }
        return connection;
    }


}
