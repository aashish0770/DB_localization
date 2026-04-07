package one;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LocalizationService {

    private final MariaDbConnection dbConnection;

    public LocalizationService(MariaDbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public Map<String,String> getUIMessages(String language){
        Map<String,String> messages = new HashMap<>();
        String sql = "SELECT `key`, value FROM localization_strings WHERE language = ?";
        try {
            Connection conn = MariaDbConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, language);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                messages.put(rs.getString("key"), rs.getString("value"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return messages;
    }

}
