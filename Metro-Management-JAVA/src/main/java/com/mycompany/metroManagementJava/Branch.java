package com.mycompany.metroManagementJava;

import java.util.Date;

public class Branch {

    private String branchId;
    private String name;
    private String city;
    private boolean isActive;
    private String address;
    private String phone;
    private int numberOfEmployees;
    private String branchManager;
    private final Date dateCreated;

    public Branch(String branchId, String name, String city, String address, String phone,String branchManagerId) {
        this.branchId = branchId;
        this.name = name;
        this.city = city;
        this.isActive = true;
        this.address = address;
        this.phone = phone;
        this.numberOfEmployees = 0;
        this.branchManager = branchManagerId;
        this.dateCreated = new Date();
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

    public String getBranchManager() {
        return branchManager;
    }

    public void setBranchManager(String branchManager) {
        this.branchManager = branchManager;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

}
