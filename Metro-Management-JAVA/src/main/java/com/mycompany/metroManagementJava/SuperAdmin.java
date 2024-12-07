package com.mycompany.metroManagementJava;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;


public class SuperAdmin {

    public static boolean addBranch(Branch branch) {
        String sql = "INSERT INTO branches (Name, City, Active, Address, ContactInfo, EmployeeCount, BranchManager, DateCreated) "
                + "VALUES ('" + branch.getName() + "', "
                + "'" + branch.getCity() + "', "
                + branch.isActive() + ", "
                + "'" + branch.getAddress() + "', "
                + "'" + branch.getPhone() + "', "
                + branch.getNumberOfEmployees() + ", "
                + "'" + branch.getBranchManager().getEmployeeID() + "', "
                + "'" + LocalDate.now().toString() + "')";

        if (DatabaseManager.add(sql)) {
            System.out.println("Branch added successfully.");
            return true;
        } else {
            System.out.println("Failed to add Branch.");
            return false;
        }

    }

    public static boolean removeBranch(String branchId) {
        String sql = "UPDATE Branches SET Active = FALSE WHERE BranchID = '" + branchId + "'";

        if (DatabaseManager.add(sql)) {
            System.out.println("Branch deactivated successfully.");
            return true;
        } else {
            System.out.println("Failed to deactivate Branch.");
            return false;
        }
    }

    public static boolean addBranchManager(Employee employee, String branchID) {
        BranchManager.addEmployee(employee);
        String getIdsql = "SELECT EmployeeID FROM employees where Name ='" + employee.getName() + "'";
        ResultSet id;
        int employeeID;
        try {
            id = DatabaseManager.get(getIdsql);
            if (id.next()) {
                employeeID = id.getInt("EmployeeID");
            } else {
                return false;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }

        String sql = "UPDATE Branches SET BranchManager ='" + employeeID + "' WHERE BranchID = '" + branchID + "'";

        if (DatabaseManager.update(sql)) {
            System.out.println("Branch Manager Appointed");
            return true;
        } else {
            System.out.println("Failed to appoint Branch manager");
            return false;
        }

    }

}
