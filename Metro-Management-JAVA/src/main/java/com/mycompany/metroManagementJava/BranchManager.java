package com.mycompany.metroManagementJava;


public class BranchManager {

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
