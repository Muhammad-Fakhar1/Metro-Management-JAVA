/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.metroManagementJava;

/**
 *
 * @author saifj
 */
public class Product {
    private String productID;
    private String title;
    private float originalPrice;
    private String category;
    private float unitPrice;
    private float cartonPrice;
    private String description;

    public Product(String productID, String title, float originalPrice, String category, float unitPrice, float cartonPrice, String description) {
        this.productID = productID;
        this.title = title;
        this.originalPrice = originalPrice;
        this.category = category;
        this.unitPrice = unitPrice;
        this.cartonPrice = cartonPrice;
        this.description = description;
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

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    
    
}
