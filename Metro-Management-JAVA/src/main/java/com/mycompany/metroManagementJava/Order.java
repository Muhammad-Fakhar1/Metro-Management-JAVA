package com.mycompany.metroManagementJava;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<OrderItem> items;
    private String status;
    private double totalPrice; 

    public Order() {
        this.items = new ArrayList<>();
        this.status = "Pending";
        this.totalPrice = 0.0;
    }

    public void addProduct(Product product, int quantity) {
        for (OrderItem item : items) {
            if (item.getProduct().getProductID().equals(product.getProductID())) {
                item.setQuantity(item.getQuantity() + quantity);
                recalculateTotalPrice();
                return;
            }
        }
        items.add(new OrderItem(product, quantity));
        recalculateTotalPrice();
    }

    private void recalculateTotalPrice() {
        totalPrice = 0.0;
        for (OrderItem item : items) {
            totalPrice += item.getProduct().getOriginalPrice() * item.getQuantity();
        }
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public int existingItemQuantity(String productID) {
        for (OrderItem item : items) {
            if (item.getProduct().getProductID().equals(productID)) {
                return item.getQuantity();
            }
        }
        return 0; 
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
