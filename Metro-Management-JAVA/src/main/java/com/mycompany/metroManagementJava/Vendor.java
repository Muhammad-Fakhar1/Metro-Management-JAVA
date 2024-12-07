package com.mycompany.metroManagementJava;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Vendor {

    private int vendorID;
    private String name;
    private String contactInfo;
    private float amountSpent;
    boolean isActive;

    @JsonCreator
    public Vendor(
            @JsonProperty("name") String name,
            @JsonProperty("contactInfo") String contactInfo
    ) {
        this.name = name;
        this.contactInfo = contactInfo;
        this.amountSpent = 0;
        this.isActive = true;
    }

    public Vendor(int vendorID, String name, String contactInfo, float amountSpent, boolean isActive) {
        this.vendorID = vendorID;
        this.name = name;
        this.contactInfo = contactInfo;
        this.amountSpent = amountSpent;
        this.isActive = isActive;
    }

    public int getVendorID() {
        return vendorID;
    }

    public void setVendorID(int vendorID) {
        this.vendorID = vendorID;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public float getAmountSpent() {
        return amountSpent;
    }

    public void setAmountSpent(float amountSpent) {
        this.amountSpent = amountSpent;
    }
}
