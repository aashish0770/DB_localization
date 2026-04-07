package one;

import java.sql.*;
import java.util.Map;

public class CartService {

    private MariaDbConnection dbConnection;

    public CartService(MariaDbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public void saveCart(ShoppingCart cart, String language) {
        try (Connection conn = dbConnection.getConnection()) {
            int cartRecordId = saveCart(conn, cart.getItems().size(), cart.getTotalBill(), language);

            for (Map.Entry<String, Item> entry : cart.getItems().entrySet()) {
                saveItem(conn, entry.getKey(), cartRecordId, entry.getValue());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Something went wrong");
        }
    }


    private int saveCart(Connection conn, int totalItems, double totalCost, String language) {
        String sql = "INSERT INTO cart_items (total_items, total_cost, language) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, totalItems);
            stmt.setDouble(2, totalCost);
            stmt.setString(3, language);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to persist data in the cart record table.");
        }
        return -1;
    }


    private void saveItem(Connection conn, String name, int cartRecordId, Item item) throws SQLException {
        String sql = "INSERT INTO items (cart_items_id, name, price, quantity, subtotal) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, cartRecordId);
            st.setString(2, name);
            st.setDouble(3, item.getPrice());
            st.setInt(4, item.getQuantity());
            st.setDouble(5, item.getTotal());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Could not persist data in the Item table");
        }
    }


}


