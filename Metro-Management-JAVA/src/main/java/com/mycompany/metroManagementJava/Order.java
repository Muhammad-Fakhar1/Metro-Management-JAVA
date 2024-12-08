package com.mycompany.metroManagementJava;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonCreator
    public Order(@JsonProperty("items") List<OrderItem> items,
                 @JsonProperty("status") String status,
                 @JsonProperty("totalPrice") double totalPrice) {
        this.items = items;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public void addProduct(Product product, int quantity) {
        for (OrderItem item : items) {
            if (item.getProduct().getProductID() == product.getProductID()) {
                item.setQuantity(item.getQuantity() + quantity);
                recalculateTotalPrice();
                return;
            }
        }
        items.add(new OrderItem(product, quantity));
        recalculateTotalPrice();
    }

    private void recalculateTotalPrice() {
        totalPrice = 0.0f;

        for (OrderItem orderItem : items) {
            Product product = orderItem.getProduct();

            if (product != null) {
                float unitPrice = product.getUnitPrice();
                float categoryTaxRate = Assets.getCategoryTax(product.getCategory());
                float taxAmount = unitPrice * categoryTaxRate;
                float unitRetailPrice = unitPrice + taxAmount;

                totalPrice += unitRetailPrice * orderItem.getQuantity();
            }
        }
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public int existingItemQuantity(int productID) {
        for (OrderItem item : items) {
            if (item.getProduct().getProductID() == productID) {
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
