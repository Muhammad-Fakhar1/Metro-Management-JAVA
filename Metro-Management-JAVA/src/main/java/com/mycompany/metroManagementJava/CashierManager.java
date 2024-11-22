package com.mycompany.metroManagementJava;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    public static void checkout(Order order) throws SQLException {
        for (OrderItem item : order.getItems()) {
            String productID = item.getProduct().getProductID();
            int quantity = item.getQuantity();
            updateProductQuantity(productID, -quantity);
        }
        order.setStatus("Checked Out");
    }

    private static void updateProductQuantity(String productID, int quantityChange) throws SQLException {
        String sql = "UPDATE products SET Quantity = Quantity + " + quantityChange + " WHERE ProductID = '" + productID + "'";
        DatabaseManager.add(sql);
    }
}
