package com.mycompany.metroManagementJava;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Vendor {

    private String vendorID;
    private String name;
    private String contactInfo;
    private float amountSpent;
    boolean isActive;

    public Vendor(String vendorID, String name, String contactInfo, float amountSpent) {
        this(vendorID, name, contactInfo, amountSpent, true);
    }

    @JsonCreator
    public Vendor(
            @JsonProperty("vendorID") String vendorID,
            @JsonProperty("name") String name,
            @JsonProperty("contactInfo") String contactInfo,
            @JsonProperty("amountSpent") float amountSpent,
            @JsonProperty("active") boolean active
    ) {
        this.vendorID = vendorID;
        this.name = name;
        this.contactInfo = contactInfo;
        this.amountSpent = amountSpent;
        this.isActive = active;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
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
