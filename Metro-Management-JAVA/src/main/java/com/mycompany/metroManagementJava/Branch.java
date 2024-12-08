package com.mycompany.metroManagementJava;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class Branch {

    private int branchId;
    private String name;
    private String city;
    private boolean isActive;
    private String address;
    private String phone;
    private int numberOfEmployees;
    private Employee branchManager;
    private final LocalDate dateCreated;

    @JsonCreator
    public Branch(
            @JsonProperty("name") String name,
            @JsonProperty("city") String city,
            @JsonProperty("address") String address,
            @JsonProperty("phone") String phone,
            @JsonProperty("isActive") boolean isActive,
            @JsonProperty("numberOfEmployees") int numberOfEmployees,
            @JsonProperty("dateCreated") @JsonFormat(pattern = "yyyy-MM-dd") LocalDate dateCreated
    ) {
        this.name = name;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.isActive = isActive;
        this.numberOfEmployees = numberOfEmployees;
        this.dateCreated = dateCreated;
    }

    public Branch(int branchId, String name, String city, boolean isActive, String address, String phone, int numberOfEmployees, Employee branchManager, LocalDate date) {
        this.branchId = branchId;
        this.name = name;
        this.city = city;
        this.isActive = isActive;
        this.address = address;
        this.phone = phone;
        this.numberOfEmployees = numberOfEmployees;
        this.branchManager = branchManager;
        this.dateCreated = date;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
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

    public Employee getBranchManager() {
        return branchManager;
    }

    public void setBranchManager(Employee branchManager) {
        this.branchManager = branchManager;
    }

    public LocalDate getDateCreated() {
        return dateCreated;
    }

}
