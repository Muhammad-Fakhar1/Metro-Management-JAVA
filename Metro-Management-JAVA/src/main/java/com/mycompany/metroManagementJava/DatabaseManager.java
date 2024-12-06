package com.mycompany.metroManagementJava;

import java.sql.*;

public class DatabaseManager {

    private static final String URL = "jdbc:mysql://localhost:3306/MetroManagerDB";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            initializeDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initializeDatabase() {
        String createEmployeeTableSQL = "CREATE TABLE IF NOT EXISTS employees ("
                + "EmployeeID VARCHAR(255) NOT NULL, "
                + "Name VARCHAR(255) NOT NULL, "
                + "Password VARCHAR(255) NOT NULL, "
                + "Address VARCHAR(255), "
                + "PhoneNumber VARCHAR(20), "
                + "BranchCode VARCHAR(50), "
                + "Salary FLOAT, "
                + "Active BOOLEAN, "
                + "Role VARCHAR(50), "
                + "PRIMARY KEY (EmployeeID)"
                + ")";

        String createProductsTableSQL = "CREATE TABLE IF NOT EXISTS products ("
                + "ProductID VARCHAR(255) NOT NULL, "
                + "Title VARCHAR(255) NOT NULL, "
                + "OriginalPrice FLOAT NOT NULL, "
                + "Category VARCHAR(255), "
                + "UnitPrice FLOAT NOT NULL, "
                + "CartonPrice FLOAT NOT NULL, "
                + "Description VARCHAR(255), "
                + "Quantity INT NOT NULL, "
                + "PRIMARY KEY (ProductID)"
                + ")";

        String createVendorsTableSQL = "CREATE TABLE IF NOT EXISTS vendors ("
                + "VendorID VARCHAR(255) NOT NULL, "
                + "Name VARCHAR(255) NOT NULL, "
                + "ContactInfo VARCHAR(255), "
                + "AmountSpent FLOAT NOT NULL, "
                + "Active BOOLEAN NOT NULL DEFAULT TRUE, "
                + "PRIMARY KEY (VendorID), "
                + "UNIQUE (Name)"
                + ")";

        String createBranchesTableSQL = "CREATE TABLE IF NOT EXISTS branches ("
                + "BranchID VARCHAR(255) NOT NULL, "
                + "Name VARCHAR(255) NOT NULL, "
                + "City VARCHAR(255) NOT NULL, "
                + "Active BOOLEAN NOT NULL DEFAULT TRUE, "
                + "Address VARCHAR(255) NOT NULL, "
                + "ContactInfo VARCHAR(255), "
                + "EmployeeCount INT NOT NULL, "
                + "BranchManager VARCHAR(255) NOT NULL, "
                + "DateCreated DATE NOT NULL, "
                + "PRIMARY KEY (BranchID), "
                + "UNIQUE (Name)"
                + ")";

        String createSalesPurchaseTableSQL = "CREATE TABLE IF NOT EXISTS sales_purchase ("
                + "date DATE NOT NULL, "
                + "sale FLOAT NOT NULL, "
                + "purchase FLOAT NOT NULL, "
                + "PRIMARY KEY (date)"
                + ")";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createEmployeeTableSQL);
            stmt.executeUpdate(createProductsTableSQL);
            stmt.executeUpdate(createVendorsTableSQL);
            stmt.executeUpdate(createBranchesTableSQL);
            stmt.executeUpdate(createSalesPurchaseTableSQL);

            System.out.println("Database initialized successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet get(String getStmt) throws SQLException {
        ResultSet resultSet = null;
        Statement stmt = connection.createStatement();
        resultSet = stmt.executeQuery(getStmt);
        return resultSet;
    }

    public static boolean add(String stmt) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(stmt);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean delete(String stmt) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(stmt);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean update(String updateStmt) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(updateStmt);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
