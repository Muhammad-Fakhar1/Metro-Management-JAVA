package com.mycompany.metroManagementJava;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Assets {

    public static ArrayList<Category> getAllCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categoryList";
        ResultSet rs;
        try {
            rs = DatabaseManager.get(sql);
            while (rs.next()) {
                String categoryTitle = rs.getString("categoryTitle");
                int productCount = rs.getInt("productCount");
                float GSTRate = rs.getFloat("GSTRate");
                boolean Active = rs.getBoolean("Active");
                String description = rs.getString("description");

                categories.add(new Category(categoryTitle, productCount, GSTRate, description, Active));
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return categories;
    }

    public static ArrayList<Vendor> getAllVendors() {
        ArrayList<Vendor> vendors = new ArrayList<>();
        String sql = "SELECT * FROM vendors";
        try {
            ResultSet rs = DatabaseManager.get(sql);

            while (rs.next()) {
                Vendor vendor = new Vendor(
                        rs.getInt("VendorID"),
                        rs.getString("Name"),
                        rs.getString("ContactInfo"),
                        rs.getFloat("AmountSpent"),
                        rs.getBoolean("Active"),
                        rs.getInt("branchCode")
                );
                vendors.add(vendor);
            }

            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return vendors;
    }

    public static ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Products";
        try {
            ResultSet rs = DatabaseManager.get(sql);
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("Title"),
                        rs.getFloat("OriginalPrice"),
                        rs.getString("Category"),
                        rs.getFloat("UnitPrice"),
                        rs.getFloat("CartonPrice"),
                        rs.getString("Description"),
                        rs.getInt("quantity"),
                        rs.getInt("branchCode")
                );

                products.add(product);
            }

            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return products;
    }

    
    public static Product getProduct(String pID) {
        String sql = "SELECT * FROM products WHERE ProductID = '" + pID + "'";
        try {
            ResultSet rs = DatabaseManager.get(sql);
            if (rs.next()) {
                int productID = rs.getInt("ProductID");
                String title = rs.getString("Title");
                float originalPrice = rs.getFloat("OriginalPrice");
                String category = rs.getString("Category");
                float unitPrice = rs.getFloat("UnitPrice");
                float cartonPrice = rs.getFloat("CartonPrice");
                String description = rs.getString("Description");
                int quantity = rs.getInt("Quantity");
                int branchCode=rs.getInt("branchCode");

                return new Product(productID, title, originalPrice, category, unitPrice, cartonPrice, description, quantity,branchCode);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Branch> getAllBranches() {
        String sql = "SELECT * FROM Branches";
        ResultSet resultSet;
        ArrayList<Branch> branches = new ArrayList<>();

        try {
            resultSet = DatabaseManager.get(sql);
            while (resultSet.next()) {
                int branchId = resultSet.getInt("BranchID");
                String name = resultSet.getString("Name");
                String city = resultSet.getString("City");
                boolean active = resultSet.getBoolean("Active");
                String address = resultSet.getString("Address");
                String phone = resultSet.getString("ContactInfo");
                int numberOfEmployees = resultSet.getInt("EmployeeCount");
                int branchManager = resultSet.getInt("BranchManager");
                LocalDate dateCreated = resultSet.getDate("DateCreated").toLocalDate();
                
                Employee manager=Workforce.getEmployee(branchManager);

                Branch branch = new Branch(branchId, name, city, active, address, phone, numberOfEmployees,manager,dateCreated);
                branches.add(branch);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Assets.class.getName()).log(Level.SEVERE, null, ex);
        }

        return branches;
    }

}
