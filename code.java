import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserData {

    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password"; // Hardcoded password - Security Vulnerability

    public List<String> getUsernames(String role) {
        List<String> usernames = new ArrayList<>();
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            String query = "SELECT username FROM users WHERE role = '" + role + "'"; // SQL Injection Risk - Security Hotspot
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                usernames.add(rs.getString("username"));
            }
        } catch (Exception e) {
            System.out.println("Error retrieving usernames: " + e.getMessage());
        } finally {
            try {
                conn.close(); // Bug: Possible NullPointerException if connection failed
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return usernames;
    }
}
