package util;

import jakarta.servlet.ServletException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/Users";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "123456";

    public static Connection getConnection() throws ServletException {
        try {
            return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        } catch (SQLException e) {
            throw new ServletException("Database connection error", e);
        }
    }
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // For newer versions of driver
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
