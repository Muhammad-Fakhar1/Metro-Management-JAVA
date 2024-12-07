/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.metroManagementJava;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Workforce {

    public Employee getEmployee(String employeeID) {
        String sql = "SELECT * FROM Employees WHERE EmployeeID = '" + employeeID + "'";
        ResultSet rs = null;
        Employee employee = null;

        try {
            rs = DatabaseManager.get(sql);
            if (rs.next()) {
                String id = rs.getString("EmployeeID");
                String name = rs.getString("Name");
                String email = rs.getString("Email");
                String CNIC = rs.getString("CNIC");
                String address = rs.getString("Address");
                String phoneNumber = rs.getString("PhoneNumber");
                String branch = rs.getString("BranchCode");
                float salary = rs.getFloat("Salary");
                String roleString = rs.getString("Role");
                Role role = Role.valueOf(roleString.toUpperCase());

                employee = new Employee(id, name, email, CNIC, address, phoneNumber, branch, salary, role);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return employee;
    }

    public List<Employee> getAllEmployees(String Attribute, String Value) {
        String sql = "SELECT * FROM Employees WHERE " + Attribute + " = '" + Value + "'";
        ResultSet rs = null;
        List<Employee> employees = new ArrayList<>();

        try {
            rs = DatabaseManager.get(sql);
            while (rs.next()) {
                String employeeID = rs.getString("EmployeeID");
                String name = rs.getString("Name");
                String email = rs.getString("Email");
                String CNIC = rs.getString("CNIC");
                String address = rs.getString("Address");
                String phoneNumber = rs.getString("PhoneNumber");
                String branch = rs.getString("BranchCode");
                float salary = rs.getFloat("Salary");
                String roleString = rs.getString("Role");
                Role role = Role.valueOf(roleString.toUpperCase());

                Employee employee = new Employee(employeeID, name, email, CNIC, address, phoneNumber, branch, salary, role);
                employees.add(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return employees;
    }

    public List<Employee> getAllEmployees(String branchCode) {
        return this.getAllEmployees("BranchCode", branchCode);
    }

}
