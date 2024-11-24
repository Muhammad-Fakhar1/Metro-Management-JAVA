package com.mycompany.metroManagementJava;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BranchManager {

    public List<Employee> getAllEmployees(String Attribute,String Value) {
        String sql = "SELECT * FROM Employees WHERE "+Attribute+" = '" + Value + "'";
        ResultSet rs = null;
        List<Employee> employees = new ArrayList<>();

        try {
            rs = DatabaseManager.get(sql);
            while (rs.next()) {
                String employeeID = rs.getString("EmployeeID");
                String name = rs.getString("Name");
                String address = rs.getString("Address");
                String phoneNumber = rs.getString("PhoneNumber");
                String branch = rs.getString("BranchCode");
                float salary = rs.getFloat("Salary");
                String roleString = rs.getString("Role");
                Role role = Role.valueOf(roleString.toUpperCase());

                Employee employee = new Employee(employeeID, name, address, phoneNumber, branch, salary, role);
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
    

    public Employee getEmployee(String employeeID) {
        String sql = "SELECT * FROM Employees WHERE EmployeeID = '" + employeeID + "'";
        ResultSet rs = null;
        Employee employee = null; // Initialize the employee variable

        try {
            rs = DatabaseManager.get(sql);
            if (rs.next()) {
                String id = rs.getString("EmployeeID");
                String name = rs.getString("Name");
                String address = rs.getString("Address");
                String phoneNumber = rs.getString("PhoneNumber");
                String branch = rs.getString("BranchCode");
                float salary = rs.getFloat("Salary");
                String roleString = rs.getString("Role");
                Role role = Role.valueOf(roleString.toUpperCase());

                employee = new Employee(id, name, address, phoneNumber, branch, salary, role);
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

    public boolean addEmployee(Employee employee) {
        String sql = "INSERT INTO Employees (EmployeeID, Name, Password, Address, PhoneNumber, BranchCode, Salary, Role, Active) VALUES ("
                + "'" + employee.getEmployeeID() + "', "
                + "'" + employee.getName() + "', "
                + "'" + employee.getPassword() + "', "
                + "'" + employee.getAddress() + "', "
                + "'" + employee.getPhoneNumber() + "', "
                + "'" + employee.getBranchCode() + "', "
                + employee.getSalary() + ", "
                + "'" + employee.getRole().toString() + "', "
                + employee.isActive() + ")";

        return DatabaseManager.add(sql);
    }

    public boolean removeEmployee(String employeeID) {
        String sql = "UPDATE Employees SET Active = false WHERE EmployeeID = '" + employeeID + "'";

        return DatabaseManager.delete(sql);
    }

}
