package com.mycompany.metroManagementJava;

public class BranchManager {

    public static boolean addEmployee(Employee employee) {
        String sql = "INSERT INTO Employees (Name, Password,Email,CNIC,Address, PhoneNumber, BranchCode, Salary, Role, Active) VALUES ("
                + "'" + employee.getName() + "', "
                + "'" + employee.getPassword() + "', "
                + "'" + employee.getEmail() + "', "
                + "'" + employee.getCnic() + "', "
                + "'" + employee.getAddress() + "', "
                + "'" + employee.getPhoneNumber() + "', "
                + "'" + employee.getBranchCode() + "', "
                + employee.getSalary() + ", "
                + "'" + employee.getRole().toString() + "', "
                + employee.isActive() + ")";

        if (DatabaseManager.add(sql)) {
            String updateBranchSql = "UPDATE branches SET EmployeeCount = EmployeeCount + 1 WHERE branchID ='" + employee.getBranchCode() + "'";
            DatabaseManager.update(updateBranchSql);
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean removeEmployee(String employeeID) {
        String sql = "UPDATE Employees SET Active = false WHERE EmployeeID = '" + employeeID + "'";
        return DatabaseManager.delete(sql);
    }

    public static boolean changeRole(String employeeID, Role role) {
        String sql = "UPDATE Employees SET Role =" + role.toString() + "WHERE EmployeeID = '" + employeeID + "'";
        return DatabaseManager.delete(sql);
    }

}
