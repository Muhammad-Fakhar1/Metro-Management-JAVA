package com.mycompany.metroManagementJava;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;



public class Employee {

    private int EmployeeID;
    private String Name;
    private String email;
    private String cnic;
    private String Password;
    private String address;
    private String phoneNumber;
    private String branchCode;
    private float salary;
    private boolean active;
    private Role role;


    @JsonCreator
    public Employee(
            @JsonProperty("Name") String Name,
            @JsonProperty("email") String email,
            @JsonProperty("cnic") String cnic,
            @JsonProperty("address") String address,
            @JsonProperty("phoneNumber") String phoneNumber,
            @JsonProperty("branchCode") String branchCode,
            @JsonProperty("salary") float salary,
            @JsonProperty("role") Role role
    ) {
        this.Name = Name;
        this.Password = "123456";
        this.email = email;
        this.cnic = cnic;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.branchCode = branchCode;
        this.salary = salary;
        this.role = role;
        this.active = true;
    }

    public Employee(int EmployeeID, String Name, String Password, String email, String cnic, String address, String phoneNumber, String branchCode, float salary, boolean active, Role role) {
        this.EmployeeID = EmployeeID;
        this.Name = Name;
        this.Password = Password;
        this.email = email;
        this.cnic = cnic;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.branchCode = branchCode;
        this.salary = salary;
        this.active = active;
        this.role = role;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchhCode(String branchhCode) {
        this.branchCode = branchhCode;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public int getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(int EmployeeID) {
        this.EmployeeID = EmployeeID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    @Override
    public String toString() {
        return "Employee{" + "EmployeeID=" + EmployeeID + ", Name=" + Name + ", email=" + email + ", cnic=" + cnic + ", Password=" + Password + ", address=" + address + ", phoneNumber=" + phoneNumber + ", branchCode=" + branchCode + ", salary=" + salary + ", active=" + active + ", role=" + role + '}';
    }
}
