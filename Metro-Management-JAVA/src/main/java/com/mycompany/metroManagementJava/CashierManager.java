package com.mycompany.metroManagementJava;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CashierManager {

    private static final String LOG_FILE = "CASHIER_LOG_FILE.txt";

    private static boolean checkProductAvailability(Order order, int productID, int requiredQuantity) throws SQLException {
        int existingQuantityInOrder = order.existingItemQuantity(productID);
        requiredQuantity += existingQuantityInOrder;

        String sql = "SELECT Quantity FROM products WHERE ProductID = '" + productID + "'";
        ResultSet rs = DatabaseManager.get(sql);

        if (rs.next()) {
            int availableQuantity = rs.getInt("Quantity");
            rs.close();
            return availableQuantity >= requiredQuantity;
        } else {
            rs.close();
            return false;
        }
    }

    public static boolean addProductToOrder(Order order, Product product, int requiredQuantity) {
        try {
            if (checkProductAvailability(order, product.getProductID(), requiredQuantity)) {
                order.addProduct(product, requiredQuantity);
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static Order checkout(Order order, String EmployeeID) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE))) {
            for (OrderItem item : order.getItems()) {
                int productID = item.getProduct().getProductID();
                int quantity = item.getQuantity();
                String log = EmployeeID + "," + productID + "," + quantity + "," + item.getAmount();
                writer.write(log);
                writer.newLine();
                if (!updateProductQuantity(productID, -quantity)) {
                    return null;
                }
            }

            String todayDate = LocalDate.now().toString();

            ResultSet rs = DatabaseManager.get("SELECT * FROM sales_purchase WHERE date = '" + todayDate + "'");
            double orderTotalPrice = order.getTotalPrice();

            if (rs.next()) {
                double currSale = rs.getFloat("sale");
                double updatedSale = currSale + orderTotalPrice;
                DatabaseManager.add("UPDATE sales_purchase SET sale = " + updatedSale + " WHERE date = '" + todayDate + "'");
            } else {
                DatabaseManager.add("INSERT INTO sales_purchase (date, sale, purchase) VALUES ('" + todayDate + "', " + orderTotalPrice + ", 0)");
            }

            order.setStatus("Checked Out");
        } catch (IOException e) {
            e.printStackTrace();
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        return order;
    }

    private static boolean updateProductQuantity(int productID, int quantityChange) throws SQLException {
        String sql = "UPDATE products SET Quantity = Quantity + " + quantityChange + " WHERE ProductID = '" + productID + "'";
        return DatabaseManager.add(sql);
    }
}
