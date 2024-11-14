/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.metroManagementJava;

/**
 *
 * @author saifj
 */

public class Employee {
     private String EmployeeID;
     private String Name;
     private String Password;
     private String address;
     private String phoneNumber;
     private String branchhCode;
     private float salary;
     private boolean active;
     
     private Role role;

    public Employee(String EmployeeID, String Name, String address, String phoneNumber, String branchhCode, float salary, Role role) {
        this.EmployeeID = EmployeeID;
        this.Name = Name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.branchhCode = branchhCode;
        this.salary = salary;
        this.role = role;
        this.active=true;
        this.Password="123456";
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

    public String getBranchhCode() {
        return branchhCode;
    }

    public void setBranchhCode(String branchhCode) {
        this.branchhCode = branchhCode;
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
    
   
     
}


