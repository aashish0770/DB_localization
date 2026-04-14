package tests;

import one.CartService;
import one.Item;
import one.MariaDbConnection;
import one.ShoppingCart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock MariaDbConnection dbConnection;
    @Mock Connection connection;
    @Mock PreparedStatement cartStmt;
    @Mock PreparedStatement itemStmt;
    @Mock ResultSet generatedKeys;

    @Test
    void testSaveCart() throws Exception {
        // Use real ShoppingCart and add a real Item
        ShoppingCart cart = new ShoppingCart();
        cart.addItem("apple", new Item(5.0, 2));

        try (MockedStatic<MariaDbConnection> mockedStatic = mockStatic(MariaDbConnection.class)) {
            mockedStatic.when(MariaDbConnection::getConnection).thenReturn(connection);

            when(connection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                    .thenReturn(cartStmt);
            when(connection.prepareStatement(anyString()))
                    .thenReturn(itemStmt);

            when(cartStmt.executeUpdate()).thenReturn(1);
            when(cartStmt.getGeneratedKeys()).thenReturn(generatedKeys);
            when(generatedKeys.next()).thenReturn(true);
            when(generatedKeys.getInt(1)).thenReturn(1);

            CartService service = new CartService(dbConnection);
            service.saveCart(cart, "en");

            verify(cartStmt).executeUpdate();
            verify(itemStmt).executeUpdate();
        }
    }
}