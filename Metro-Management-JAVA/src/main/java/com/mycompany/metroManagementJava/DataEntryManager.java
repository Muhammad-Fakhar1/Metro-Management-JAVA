package com.mycompany.metroManagementJava;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataEntryManager {

    private static final String LOG_FILE="DATA_ENTRY_LOG.txt";

    public boolean addProduct(Product product, String EmployeeID, String VendorID) throws SQLException {
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
                return false;
            }
        }

        ResultSet rs2 = DatabaseManager.get("SELECT * FROM sales_purchase WHERE date = '" + todayDate + "'");
        if (rs2.next()) {
            double currPurchase = rs2.getFloat("purchase");
            double updatedPurchase = currPurchase + purchaseAmount;
            DatabaseManager.add("UPDATE sales_purchase SET purchase = " + updatedPurchase + " WHERE date = '" + todayDate + "'");
        } else {
            DatabaseManager.add("INSERT INTO sales_purchase (date, sale, purchase) VALUES ('" + todayDate + "', 0, " + purchaseAmount + ")");
        }
        rs2.close();

        String vendorUpdateSql = "UPDATE vendors SET TotalPurchase = TotalPurchase + " + purchaseAmount + " WHERE ID = '" + VendorID + "'";
        if (!DatabaseManager.add(vendorUpdateSql)) {
            return false;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE))) {
            writer.write(log);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    public Product getProduct(String productTitle) throws SQLException {
        String sql="SELECT * FROM products WHERE ProductName = '" + productTitle + "'";
        ResultSet rs = DatabaseManager.get(sql);
        if (rs.next()) {
            String productID = rs.getString("ProductID");
            String title = rs.getString("Title");
            float originalPrice = rs.getFloat("OriginalPrice");
            String category = rs.getString("Category");
            float unitPrice = rs.getFloat("UnitPrice");
            float cartonPrice = rs.getFloat("CartonPrice");
            String description = rs.getString("Description");
            int quantity = rs.getInt("Quantity");

            return new Product(productID,productTitle,originalPrice,category,unitPrice,cartonPrice,description,quantity);
        }
        return null;
    }

    public boolean removeProduct(String productID) throws SQLException {
        String sql = "DELETE FROM products WHERE ProductID = '" + productID + "'";

        if (DatabaseManager.add(sql)) {
            System.out.println("Product removed successfully.");
            return true;
        } else {
            System.out.println("Failed to remove product.");
            return false;
        }
    }

    public boolean addVendor(Vendor vendor) throws SQLException {
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

    public boolean removeVendor(String vendorID) throws SQLException {
        String sql = "UPDATE vendors SET Active = FALSE WHERE VendorID = '" + vendorID + "'";

        if (DatabaseManager.add(sql)) {
            System.out.println("Vendor deactivated successfully.");
            return true;
        } else {
            System.out.println("Failed to deactivate vendor.");
            return false;
        }
    }

    public Map<String, List<Product>> getAllProducts() throws SQLException {
        Map<String, List<Product>> productsByCategory = new HashMap<>();
        String sql = "SELECT * FROM Products";
        ResultSet rs = DatabaseManager.get(sql);

        while (rs.next()) {
            Product product = new Product(
                    rs.getString("ProductID"),
                    rs.getString("Title"),
                    rs.getFloat("OriginalPrice"),
                    rs.getString("Category"),
                    rs.getFloat("UnitPrice"),
                    rs.getFloat("CartonPrice"),
                    rs.getString("Description"),
                    rs.getInt("quantity")
            );

            productsByCategory.computeIfAbsent(product.getCategory(), k -> new ArrayList<>()).add(product);
        }

        rs.close();

        return productsByCategory;
    }

    public List<Vendor> getAllVendors() throws SQLException {
        List<Vendor> vendors = new ArrayList<>();
        String sql = "SELECT * FROM vendors";
        ResultSet rs = DatabaseManager.get(sql);

        while (rs.next()) {
            Vendor vendor = new Vendor(
                    rs.getString("VendorID"),
                    rs.getString("Name"),
                    rs.getString("ContactInfo"),
                    rs.getFloat("AmountSpent"),
                    rs.getBoolean("Active")
            );
            vendors.add(vendor);
        }

        rs.close();

        return vendors;
    }

    public List<String> getCategories() throws SQLException {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT Category FROM products";
        ResultSet rs = DatabaseManager.get(sql);

        while (rs.next()) {
            categories.add(rs.getString("Category"));
        }

        rs.close();
        return categories;
    }

}
