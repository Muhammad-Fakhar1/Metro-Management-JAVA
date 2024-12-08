package com.mycompany.metroManagementJava;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderItem {
    private Product product;
    private int quantity;

    @JsonCreator
    public OrderItem(@JsonProperty("product") Product product, @JsonProperty("quantity") int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getAmount() {
        return product.getUnitPrice()*quantity;
    }
}
