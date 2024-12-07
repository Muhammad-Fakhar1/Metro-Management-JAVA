package com.mycompany.metroManagementJava;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Assets {

    public static List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categoryList";
        ResultSet rs;
        try {
            rs = DatabaseManager.get(sql);
            while (rs.next()) {
                String categoryTitle = rs.getString("categoryTitle");
                int productCount = rs.getInt("productCount");
                float GSTRate = rs.getFloat("GSTRate");
                boolean Active = rs.getBoolean("Active");

                categories.add(new Category(categoryTitle, productCount, GSTRate, Active));
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return categories;
    }

    public static List<Vendor> getAllVendors() {
        List<Vendor> vendors = new ArrayList<>();
        String sql = "SELECT * FROM vendors";
        try {
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return vendors;
    }

    public static Map<String, List<Product>> getAllProducts() throws SQLException {
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

    public static Product getProduct(String pID) {
        String sql = "SELECT * FROM products WHERE ProductID = '" + pID + "'";
        try {
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

                return new Product(productID, title, originalPrice, category, unitPrice, cartonPrice, description, quantity);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static List<Branch> getAllBranches() {
        String sql = "SELECT * FROM Branches";
        ResultSet resultSet;
        List<Branch> branches = new ArrayList<>();

        try {
            resultSet = DatabaseManager.get(sql);
            while (resultSet.next()) {
                String branchId = resultSet.getString("BranchID");
                String name = resultSet.getString("Name");
                String city = resultSet.getString("City");
                boolean active = resultSet.getBoolean("Active");
                String address = resultSet.getString("Address");
                String phone = resultSet.getString("ContactInfo");
                int numberOfEmployees = resultSet.getInt("EmployeeCount");
                String branchManager = resultSet.getString("BranchManager");
                LocalDate dateCreated = resultSet.getDate("DateCreated").toLocalDate();

                Branch branch = new Branch(branchId, name, city, active, address, phone, numberOfEmployees, branchManager, dateCreated);
                branches.add(branch);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
        }

        return branches;
    }

}
