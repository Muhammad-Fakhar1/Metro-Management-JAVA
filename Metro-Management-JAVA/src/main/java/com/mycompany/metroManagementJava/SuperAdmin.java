package com.mycompany.metroManagementJava;

import java.time.LocalDate;

public class SuperAdmin {

    public boolean createBranch(Branch branch) {
        String sql = "INSERT INTO branches (BranchID, Name, City, Active, Address, ContactInfo, EmployeeCount, BranchManager, DateCreated) "
                + "VALUES ('" + branch.getBranchId() + "', "
                + "'" + branch.getName() + "', "
                + "'" + branch.getCity() + "', "
                + branch.isActive() + ", "
                + "'" + branch.getAddress() + "', "
                + "'" + branch.getPhone() + "', "
                + branch.getNumberOfEmployees() + ", "
                + "'" + branch.getBranchManager() + "', "
                + "'" + LocalDate.now().toString() + "')";

        if (DatabaseManager.add(sql)) {
            System.out.println("Branch added successfully.");
            return true;
        } else {
            System.out.println("Failed to add Branch.");
            return false;
        }

    }

    public boolean removeBranch(String branchId) {
        String sql = "UPDATE Branches SET Active = FALSE WHERE BranchID = '" + branchId + "'";

        if (DatabaseManager.add(sql)) {
            System.out.println("Branch deactivated successfully.");
            return true;
        } else {
            System.out.println("Failed to deactivate Branch.");
            return false;
        }
    }

}
