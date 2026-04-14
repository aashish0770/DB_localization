package tests;

import one.LocalizationService;
import one.MariaDbConnection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocalizationServiceTests {

    @Mock MariaDbConnection dbConnection;
    @Mock Connection connection;
    @Mock PreparedStatement preparedStatement;
    @Mock ResultSet resultSet;

    @Test
    void testGetUIMessagesReturnsMessages() throws Exception {
        try (MockedStatic<MariaDbConnection> mockedStatic = mockStatic(MariaDbConnection.class)) {
            mockedStatic.when(MariaDbConnection::getConnection).thenReturn(connection);
            when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
            when(preparedStatement.executeQuery()).thenReturn(resultSet);
            when(resultSet.next()).thenReturn(true, true, false);
            when(resultSet.getString("key")).thenReturn("hello", "bye");
            when(resultSet.getString("value")).thenReturn("Hello", "Goodbye");

            LocalizationService service = new LocalizationService(dbConnection);
            Map<String, String> result = service.getUIMessages("en");

            assertEquals(2, result.size());
            assertEquals("Hello", result.get("hello"));
            assertEquals("Goodbye", result.get("bye"));
            verify(preparedStatement).setString(1, "en");
        }
    }
}