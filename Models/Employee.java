package com.metro.Models;

import com.metro.Models.Role;

public class Employee {

    private String EmployeeID;
    private String Name;
    private String Password;
    private String address;
    private String phoneNumber;
    private String branchCode;
    private float salary;
    private boolean active;

    private Role role;

    public Employee(String EmployeeID, String Name, String address, String phoneNumber, String branchCode, float salary, Role role) {
        this(EmployeeID, Name, "123456", address, phoneNumber, branchCode, salary, role);
    }

    public Employee(String EmployeeID, String Name, String Password, String address, String phoneNumber, String branchCode, float salary, Role role) {
        this.EmployeeID = EmployeeID;
        this.Name = Name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.branchCode = branchCode;
        this.salary = salary;
        this.role = role;
        this.active = true;
        this.Password = Password;
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

    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String EmployeeID) {
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

    @Override
    public String toString() {
        return "Employee{" + "EmployeeID=" + EmployeeID + ", Name=" + Name + ", Password=" + Password + ", address=" + address + ", phoneNumber=" + phoneNumber + ", branchCode=" + branchCode + ", salary=" + salary + ", active=" + active + ", role=" + role + '}';
    }
}
