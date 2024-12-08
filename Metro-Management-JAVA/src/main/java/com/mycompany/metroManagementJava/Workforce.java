package com.mycompany.metroManagementJava;

import java.sql.ResultSet;
import java.util.ArrayList;

public class Workforce {

    public static Employee getEmployee(int employeeID) {
        return getEmployee("EmployeeID", Integer.toString(employeeID));
    }

    public static Employee getEmployee(String attribute, String value) {
        String sql = "SELECT * FROM Employees WHERE " + attribute + " = '" + value + "'";
        ResultSet rs = null;
        Employee employee = null;

        try {
            rs = DatabaseManager.get(sql);
            if (rs.next()) {
                int empID = rs.getInt("EmployeeID");
                String name = rs.getString("Name");
                String password = rs.getString("Password");
                String email = rs.getString("Email");
                String CNIC = rs.getString("CNIC");
                String address = rs.getString("Address");
                String phoneNumber = rs.getString("PhoneNumber");
                int branch = rs.getInt("BranchCode");
                float salary = rs.getFloat("Salary");
                String roleString = rs.getString("Role");
                Role role = Role.valueOf(roleString.toUpperCase());
                boolean active = rs.getBoolean("Active");

                employee = new Employee(empID, name, password, email, CNIC, address, phoneNumber, branch, salary, active, role);
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

    public static ArrayList<Employee> getAllEmployees(String Attribute, String Value) {
        String sql = "SELECT * FROM Employees WHERE " + Attribute + " = '" + Value + "'";
        ResultSet rs = null;
        ArrayList<Employee> employees = new ArrayList<>();

        try {
            rs = DatabaseManager.get(sql);
            while (rs.next()) {
                int employeeID = rs.getInt("EmployeeID");
                String name = rs.getString("Name");
                String password = rs.getString("Password");
                String email = rs.getString("Email");
                String CNIC = rs.getString("CNIC");
                String address = rs.getString("Address");
                String phoneNumber = rs.getString("PhoneNumber");
                int branch = rs.getInt("BranchCode");
                float salary = rs.getFloat("Salary");
                String roleString = rs.getString("Role");
                Role role = Role.valueOf(roleString.toUpperCase());
                boolean active = rs.getBoolean("Active");

                Employee employee = new Employee(employeeID, name, password, email, CNIC, address, phoneNumber, branch, salary, active, role);
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

    public static ArrayList<Employee> getAllEmployees(int branchCode) {
        String bCode = Integer.toString(branchCode);
        return getAllEmployees("BranchCode", bCode);
    }

    public static double getSalaryExpenditure(int branchCode) {
        String sql = "SELECT SUM(Salary) AS Salary FROM Employees WHERE Active = TRUE AND branchCode = " + branchCode;
        ResultSet rs = null;
        double totalSalaryExpenditure = 0.0;

        try {
            rs = DatabaseManager.get(sql);
            if (rs.next()) {
                totalSalaryExpenditure = rs.getDouble("Salary");
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

        System.out.println(totalSalaryExpenditure);
        return totalSalaryExpenditure;
    }

    public static boolean updateEmployee(Employee employee) {
        if (employee != null) {
            return false;
        }
        String sql = "UPDATE Employees SET Name = '"
                + employee.getName() + "', Password = '"
                + employee.getPassword() + "', Email = '"
                + employee.getEmail() + "', CNIC = '"
                + employee.getCnic() + "', Address = '"
                + employee.getAddress() + "', PhoneNumber = '"
                + employee.getPhoneNumber() + "', BranchCode = '"
                + employee.getBranchCode() + "', Salary = "
                + employee.getSalary() + ", Role = '"
                + employee.getRole() + "', Active = "
                + employee.isActive() + " WHERE EmployeeID = "
                + employee.getEmployeeID() + ";";

        return DatabaseManager.update(sql);
    }

}
