package com.mycompany.metroManagementJava;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DataEntryManager {

    private static final String LOG_FILE = "DATA_ENTRY_LOG.txt";

    private static void logAction(String log) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(log);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean addProduct(Product product, String EmployeeID, String VendorID) {
        try {
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
                String insertSql = "INSERT INTO products (Title, OriginalPrice, Category, UnitPrice, CartonPrice, Description, Quantity) VALUES ('"
                        + product.getTitle() + "', "
                        + product.getOriginalPrice() + ", '"
                        + product.getCategory() + "', "
                        + product.getUnitPrice() + ", "
                        + product.getCartonPrice() + ", '"
                        + product.getDescription() + "', "
                        + product.getQuantity() + ")";
                if (!DatabaseManager.add(insertSql)) {
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
                    return false;
                }
            }
            rs2.close();

            String categoryUpdateSql = "UPDATE category SET productCount = productCount + 1 WHERE categoryTitle = '" + product.getCategory() + "'";
            if (!DatabaseManager.update(categoryUpdateSql)) {
                return false;
            }

            String vendorUpdateSql = "UPDATE vendors SET AmountSpent = AmountSpent + " + purchaseAmount + " WHERE vendorID = '" + VendorID + "'";
            if (!DatabaseManager.update(vendorUpdateSql)) {
                return false;
            }

            logAction(log);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return true;
    }

    public static boolean removeProduct(String productID) {
        String sql = "DELETE FROM products WHERE ProductID = '" + productID + "'";
        boolean result;
        result = DatabaseManager.delete(sql);
        if (result) {
            String log = "Removed product with ProductID: " + productID;
            logAction(log);
            System.out.println("Product removed successfully.");
        } else {
            System.out.println("Failed to remove product.");
        }

        return result;
    }

    public static boolean addVendor(Vendor vendor) {
        String sql = "INSERT INTO vendors (Name, ContactInfo, AmountSpent, Active) VALUES ('"
                + vendor.getName() + "', '"
                + vendor.getContactInfo() + "', "
                + vendor.getAmountSpent() + ", "
                + (vendor.isActive() ? "TRUE" : "FALSE") + ")";
        boolean result = DatabaseManager.add(sql);

        if (result) {
            String log = "Added vendor with VendorID: " + vendor.getVendorID();
            logAction(log);
            System.out.println("Vendor added successfully.");
        } else {
            System.out.println("Failed to add vendor.");
        }

        return result;
    }

    public static boolean removeVendor(String vendorID) {
        String sql = "UPDATE vendors SET Active = FALSE WHERE VendorID = '" + vendorID + "'";
        boolean result = DatabaseManager.add(sql);

        if (result) {
            String log = "Deactivated vendor with VendorID: " + vendorID;
            logAction(log);
            System.out.println("Vendor deactivated successfully.");
        } else {
            System.out.println("Failed to deactivate vendor.");
        }

        return result;
    }

    public static boolean addCategory(Category category) throws SQLException {
        String sql = "INSERT INTO category (categoryTitle, productCount, GSTRate) VALUES ('"
                + category.getTitle() + "', "
                + category.getProductCount() + ", "
                + category.getGSTRate() + ")";
        boolean result = DatabaseManager.add(sql);

        if (result) {
            String log = "Added category with Title: " + category.getTitle();
            logAction(log);
            System.out.println("Category added successfully.");
        } else {
            System.out.println("Failed to add category.");
        }

        return result;
    }

    public static boolean removeCategory(String categoryTitle) {
        String sql = "UPDATE category SET Active = FALSE WHERE categoryTitle = '" + categoryTitle + "'";
        boolean result = DatabaseManager.delete(sql);

        if (result) {
            String log = "Deactivated category with Title: " + categoryTitle;
            logAction(log);
            System.out.println("Category deactivated successfully.");
        } else {
            System.out.println("Failed to deactivate category.");
        }

        return result;
    }
}
