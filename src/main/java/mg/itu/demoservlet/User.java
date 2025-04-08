package mg.itu.demoservlet;

import jakarta.servlet.ServletException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    int id;
    String name;
    String password;

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User login(Connection conn, String username, String password) throws ServletException {
        String request = "SELECT * FROM users WHERE name = ? AND password = ?";

        try(PreparedStatement ps = conn.prepareStatement(request)) {
            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("password")
                    );
                }
            }

        } catch (SQLException e) {
            throw new ServletException(e);
        }

        return null;
    }
}
