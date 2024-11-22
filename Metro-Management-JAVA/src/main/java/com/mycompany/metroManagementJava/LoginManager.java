package com.mycompany.metroManagementJava;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginManager {

    public static Employee login(String employeeID, String password) {
        String query = "SELECT * FROM employees WHERE EmployeeID = '" + employeeID + "'";
        ResultSet rs =  DatabaseManager.get(query);
        
        try {
            if (rs != null && password.equals(rs.getString("Password"))) {
                return new Employee(
                        rs.getString("EmployeeID"),
                        rs.getString("Name"),
                        rs.getString("Password"),
                        rs.getString("Address"),
                        rs.getString("PhoneNumber"),
                        rs.getString("BranchCode"),
                        rs.getFloat("Salary"),
                        Role.valueOf(rs.getString("Role"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
