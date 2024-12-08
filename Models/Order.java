package com.metro.Models;

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

    public List<OrderItem> getItems() {
        return items;
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
