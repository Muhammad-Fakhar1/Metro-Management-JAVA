package com.mycompany.metroManagementJava;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {

    private int productID;
    private String title;
    private float originalPrice;
    private String category;
    private float unitPrice;
    private float cartonPrice;
    private String description;
    private int quantity;

    @JsonCreator
    public Product(
            @JsonProperty("title") String title,
            @JsonProperty("originalPrice") float originalPrice,
            @JsonProperty("category") String category,
            @JsonProperty("unitPrice") float unitPrice,
            @JsonProperty("cartonPrice") float cartonPrice,
            @JsonProperty("description") String description,
            @JsonProperty("quantity") int quantity) {
        this.title = title;
        this.originalPrice = originalPrice;
        this.category = category;
        this.unitPrice = unitPrice;
        this.cartonPrice = cartonPrice;
        this.description = description;
        this.quantity = quantity;
    }

    public Product(int productID,String title, float originalPrice, String category, float unitPrice, float cartonPrice, String description, int quantity) {
        this.productID=productID;
        this.title = title;
        this.originalPrice = originalPrice;
        this.category = category;
        this.unitPrice = unitPrice;
        this.cartonPrice = cartonPrice;
        this.description = description;
        this.quantity = quantity;
    }

    
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(float originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getCartonPrice() {
        return cartonPrice;
    }

    public void setCartonPrice(float cartonPrice) {
        this.cartonPrice = cartonPrice;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
