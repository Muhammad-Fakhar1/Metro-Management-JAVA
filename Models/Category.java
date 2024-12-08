package com.metro.Models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Category {

    private String title;
    private int productCount;
    private float GSTRate;
    private String description;
    private boolean Active;

    public Category(String title, float GSTRate) {
        this.title = title;
        this.productCount = 0;
        this.GSTRate = GSTRate;
        this.Active = true;
    }

    @JsonCreator
    public Category(@JsonProperty("title") String title,
            @JsonProperty("productCount") int productCount,
            @JsonProperty("GSTRate") float GSTRate,
            @JsonProperty("description") String description,
            @JsonProperty("Active") boolean Active
    ) {
        this.title = title;
        this.productCount = 0;
        this.GSTRate = GSTRate;
        this.description=description;
        this.Active = Active;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return Active;
    }

    public void setActive(boolean Active) {
        this.Active = Active;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public float getGSTRate() {
        return GSTRate;
    }

    public void setGSTRate(float GSTRate) {
        this.GSTRate = GSTRate;
    }

    @Override
    public String toString() {
        return "Category{" + "title=" + title + ", productCount=" + productCount + ", GSTRate=" + GSTRate + ", description=" + description + ", Active=" + Active + '}';
    }
    
}
