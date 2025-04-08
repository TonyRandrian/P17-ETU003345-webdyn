package mg.itu.demoservlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DB {
    public static Connection connect() throws SQLException {
        String URL = "jdbc:mysql://172.80.237.53:3306/db_s2_ETU003345";
        Properties properties = new Properties();
        properties.setProperty("user", "ETU003345");
        properties.setProperty("password", "QRxCA3c3");

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, properties);
        } catch (SQLException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
            throw e;
        }

        return connection;
    }
}
