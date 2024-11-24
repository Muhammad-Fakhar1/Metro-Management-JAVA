package com.mycompany.metroManagementJava;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataEntryManager {

    public boolean addProduct(Product product) throws SQLException {
    String checkSql = "SELECT Quantity FROM products WHERE ProductID = '" + product.getProductID() + "'";
    ResultSet rs = DatabaseManager.get(checkSql);

    if (rs.next()) {
        int existingQuantity = rs.getInt("Quantity");
        int newQuantity = existingQuantity + product.getQuantity();
        String updateSql = "UPDATE products SET Quantity = " + newQuantity + " WHERE ProductID = '" + product.getProductID() + "'";
        rs.close();
        return DatabaseManager.add(updateSql);
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
        return DatabaseManager.add(insertSql);
    }
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
            rs.getString("Description")
        );

        productsByCategory.computeIfAbsent(product.getCategory(), k -> new ArrayList<>()).add(product);
    }

    rs.close();

    return productsByCategory;
}

    public List<Vendor> getAllVendors() throws SQLException {
        List<Vendor> vendors = new ArrayList<>();
        String sql = "SELECT * FROM vendors";
        ResultSet rs =DatabaseManager.get(sql);
    
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
