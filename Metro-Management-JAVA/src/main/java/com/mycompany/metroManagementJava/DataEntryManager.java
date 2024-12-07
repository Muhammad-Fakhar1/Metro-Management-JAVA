package com.mycompany.metroManagementJava;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DataEntryManager {

    private static final String LOG_FILE = "DATA_ENTRY_LOG.txt";

    public static boolean addProduct(Product product, String EmployeeID, String VendorID) {
        String employeeCheckSql = "SELECT * FROM employees WHERE EmployeeID = '" + EmployeeID + "'";
        try {
            ResultSet employeeResultSet = DatabaseManager.get(employeeCheckSql);
            if (!employeeResultSet.next()) {
                employeeResultSet.close();
                System.out.println("Employee not found with ID: " + EmployeeID);
                return false;
            }
            employeeResultSet.close();

            String vendorCheckSql = "SELECT * FROM vendors WHERE VendorID = '" + VendorID + "'";
            ResultSet vendorResultSet = DatabaseManager.get(vendorCheckSql);
            if (!vendorResultSet.next()) {
                vendorResultSet.close();
                System.out.println("Vendor not found with ID: " + VendorID);
                return false;
            }
            vendorResultSet.close();

            String todayDate = LocalDate.now().toString();
            double purchaseAmount = product.getOriginalPrice() * product.getQuantity();

            String log = EmployeeID + "," + product.getProductID() + "," + product.getQuantity() + "," + todayDate + "," + purchaseAmount;

            String checkSql = "SELECT Quantity FROM products WHERE ProductID = '" + product.getProductID() + "'";
            ResultSet rs = DatabaseManager.get(checkSql);

            if (rs.next()) {
                int existingQuantity = rs.getInt("Quantity");
                int newQuantity = existingQuantity + product.getQuantity();
                String updateSql = "UPDATE products SET Quantity = " + newQuantity + " WHERE ProductID = '" + product.getProductID() + "'";
                rs.close();
                if (!DatabaseManager.add(updateSql)) {
                    System.out.println("Failed to update product quantity for ProductID: " + product.getProductID());
                    return false;
                }
            } else {
                rs.close();
                String insertSql = "INSERT INTO products (ProductID, Title, OriginalPrice, Category, UnitPrice, CartonPrice, Description, Quantity) VALUES ('"
                        + product.getProductID() + "', '"
                        + product.getTitle() + "', "
                        + product.getOriginalPrice() + ", '"
                        + product.getCategory() + "', "
                        + product.getUnitPrice() + ", "
                        + product.getCartonPrice() + ", '"
                        + product.getDescription() + "', "
                        + product.getQuantity() + ")";
                if (!DatabaseManager.add(insertSql)) {
                    System.out.println("Failed to insert new product with ProductID: " + product.getProductID());
                    return false;
                }
            }

            ResultSet rs2 = DatabaseManager.get("SELECT * FROM sales_purchase WHERE date = '" + todayDate + "'");
            if (rs2.next()) {
                double currPurchase = rs2.getFloat("purchase");
                double updatedPurchase = currPurchase + purchaseAmount;
                if (!DatabaseManager.add("UPDATE sales_purchase SET purchase = " + updatedPurchase + " WHERE date = '" + todayDate + "'")) {
                    System.out.println("Failed to update purchase amount for date: " + todayDate);
                    return false;
                }
            } else {
                if (!DatabaseManager.add("INSERT INTO sales_purchase (date, sale, purchase) VALUES ('" + todayDate + "', 0, " + purchaseAmount + ")")) {
                    System.out.println("Failed to insert new sales_purchase record for date: " + todayDate);
                    return false;
                }
            }
            rs2.close();

            String vendorUpdateSql = "UPDATE vendors SET AmountSpent = AmountSpent + " + purchaseAmount + " WHERE vendorID = '" + VendorID + "'";
            if (!DatabaseManager.add(vendorUpdateSql)) {
                System.out.println("Failed to update vendor total purchase for VendorID: " + VendorID);
                return false;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
                writer.write(log);
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return true;
    }

    public static boolean removeProduct(String productID) throws SQLException {
        String sql = "DELETE FROM products WHERE ProductID = '" + productID + "'";

        if (DatabaseManager.add(sql)) {
            System.out.println("Product removed successfully.");
            return true;
        } else {
            System.out.println("Failed to remove product.");
            return false;
        }
    }

    public static boolean addVendor(Vendor vendor) throws SQLException {
        String sql = "INSERT INTO vendors (VendorID, Name, ContactInfo, AmountSpent, Active) VALUES ('"
                + vendor.getVendorID() + "', '"
                + vendor.getName() + "', '"
                + vendor.getContactInfo() + "', "
                + vendor.getAmountSpent() + ", "
                + (vendor.isActive() ? "TRUE" : "FALSE") + ")";

        if (DatabaseManager.add(sql)) {
            System.out.println("Vendor added successfully.");
            return true;
        } else {
            System.out.println("Failed to add vendor.");
            return false;
        }
    }

    public static boolean removeVendor(String vendorID) throws SQLException {
        String sql = "UPDATE vendors SET Active = FALSE WHERE VendorID = '" + vendorID + "'";

        if (DatabaseManager.add(sql)) {
            System.out.println("Vendor deactivated successfully.");
            return true;
        } else {
            System.out.println("Failed to deactivate vendor.");
            return false;
        }
    }
}
