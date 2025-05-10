package com.planmanagement.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Update these values according to your MySQL setup
    private static final String URL = "jdbc:mysql://localhost:3306/plan_management";
    private static final String USER = "root";  // Your MySQL username
    private static final String PASSWORD = "";   // Your MySQL password
    
    private static Connection connection = null;
    
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully!");
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL JDBC Driver not found.", e);
            } catch (SQLException e) {
                System.err.println("Database connection failed!");
                e.printStackTrace();
                throw e;
            }
        }
        return connection;
    }
    
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
} 