package com.mycompany.metroManagementJava;

import java.io.*;
import java.sql.*;

public class DatabaseManager {

    private static final String URL = "jdbc:mysql://localhost:3306/MetroManagerDB";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection;

    //    static {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            connection = DriverManager.getConnection(URL, USER, PASSWORD);
//          initializeDatabase();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    public static void initializeDatabase() {
        String createEmployeeTableSQL = "CREATE TABLE IF NOT EXISTS employees ("
                + "EmployeeID VARCHAR(255) NOT NULL, "
                + "Name VARCHAR(255) NOT NULL UNIQUE, "
                + "Password VARCHAR(255) NOT NULL, "
                + "Email VARCHAR(255) NOT NULL, "
                + "CNIC VARCHAR(255) NOT NULL, "
                + "Address VARCHAR(255), "
                + "PhoneNumber VARCHAR(20), "
                + "BranchCode VARCHAR(50) NOT NULL foreign, "
                + "Salary FLOAT, "
                + "Active BOOLEAN, "
                + "Role VARCHAR(50), "
                + "PRIMARY KEY (EmployeeID)"
                + ")";

        String createProductsTableSQL = "CREATE TABLE IF NOT EXISTS products ("
                + "ProductID VARCHAR(255) NOT NULL, "
                + "Title VARCHAR(255) NOT NULL UNIQUE, "
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



        try (Statement stmt = connection.createStatement()) {
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
        if (!checkConnection()) {
            throw new SQLException("Failed to establish a database connection.");
        }
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(getStmt);
    }

    public static boolean add(String stmt) {
        if (!checkConnection()) {
            handleBrokenConnection(stmt);
            return false;
        }
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(stmt);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean delete(String stmt) {
        if (!checkConnection()) {
            handleBrokenConnection(stmt);
            return false;
        }
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(stmt);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean update(String updateStmt) {
        if (!checkConnection()) {
            return false;
        }
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(updateStmt);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean checkConnection() {
        try {
            if (connection == null || connection.isClosed() || !connection.isValid(2)) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Re-established the database connection.");
                return true;
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Connection broken");
            return false;
        }
    }

    private static void handleBrokenConnection(String query) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("backup.txt", true))) {
            writer.write("Query at " + new java.util.Date() + ": " + query);
            writer.newLine();
            System.out.println("Query Saved");
            waitConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static synchronized void waitConnection() {
        new Thread(new ReconnectTask()).start();
    }

    public static void updateDB() {
        try (BufferedReader reader = new BufferedReader(new FileReader("backup.txt"))) {
            String query;
            while ((query = reader.readLine()) != null) {
                int queryStartIndex = query.indexOf(":") + 2;
                if (queryStartIndex > 1 && queryStartIndex < query.length()) {
                    String actualQuery = query.substring(queryStartIndex);
                    try (Statement statement = connection.createStatement()) {
                        statement.executeUpdate(actualQuery);
                        System.out.println("Executed query from backup: " + actualQuery);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        System.out.println("Failed to execute query from backup: " + actualQuery);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("backup.txt"))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static class ReconnectTask implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    if (checkConnection()) {
                        updateDB();
                        break;
                    }
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
