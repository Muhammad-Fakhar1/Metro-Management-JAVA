package com.mycompany.metroManagementJava;

public class BranchManager {

    public static boolean addEmployee(Employee employee) {
        String sql = "INSERT INTO Employees (EmployeeID, Name, Password,Email,CNIC,Address, PhoneNumber, BranchCode, Salary, Role, Active) VALUES ("
                + "'" + employee.getEmployeeID() + "', "
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

        return DatabaseManager.add(sql);
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
