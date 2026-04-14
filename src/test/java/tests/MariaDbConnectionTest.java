package tests;

import one.MariaDbConnection;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MariaDbConnectionTest {

    @Test
    void testGetConnectionReturnsConnection() throws SQLException {
        Connection connection = MariaDbConnection.getConnection();

        assertNotNull(connection);
        assertFalse(connection.isClosed());
    }

    @Test
    void testGetConnectionReturnsSameInstance() throws SQLException {
        Connection connection1 = MariaDbConnection.getConnection();
        Connection connection2 = MariaDbConnection.getConnection();

        assertSame(connection1, connection2);
    }
}