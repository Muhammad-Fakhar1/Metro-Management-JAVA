package com.mycompany.metroManagementJava;

public class Vendor {

    private String vendorID;
    private String name;
    private String contactInfo;
    private float amountSpent;
    boolean isActive;

    public Vendor(String vendorID, String name, String contactInfo, float amountSpent) {
       this(vendorID, name, contactInfo, amountSpent, true);
    }

    public Vendor(String vendorID, String name, String contactInfo, float amountSpent,boolean active) {
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
