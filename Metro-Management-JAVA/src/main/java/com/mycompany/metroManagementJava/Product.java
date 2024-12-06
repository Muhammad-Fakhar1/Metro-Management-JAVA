package com.mycompany.metroManagementJava;

public class Product {
    private String productID;
    private String title;
    private float originalPrice;
    private String category;
    private float unitPrice;
    private float cartonPrice;
    private String description;
    private int quantity;

    public Product(String productID, String title, float originalPrice, String category, float unitPrice, float cartonPrice, String description,int quantity) {
        this.productID = productID;
        this.title = title;
        this.originalPrice = originalPrice;
        this.category = category;
        this.unitPrice = unitPrice;
        this.cartonPrice = cartonPrice;
        this.description = description;
        this.quantity = quantity;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
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
