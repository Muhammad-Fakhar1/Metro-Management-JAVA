package com.mycompany.metroManagementJava;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class CashierManager {

    public static boolean checkProductAvailability(Order order, String productID, int requiredQuantity) throws SQLException {
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

    public static boolean addProductToOrder(Order order, Product product, int requiredQuantity) throws SQLException {
        if (checkProductAvailability(order, product.getProductID(), requiredQuantity)) {
            order.addProduct(product, requiredQuantity);
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkout(Order order) throws SQLException {
        for (OrderItem item : order.getItems()) {
            String productID = item.getProduct().getProductID();
            int quantity = item.getQuantity();
            if (!updateProductQuantity(productID, -quantity)) {
                return false;
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
        return true;
    }

    private static boolean updateProductQuantity(String productID, int quantityChange) throws SQLException {
        String sql = "UPDATE products SET Quantity = Quantity + " + quantityChange + " WHERE ProductID = '" + productID + "'";
        return DatabaseManager.add(sql);
    }
}
