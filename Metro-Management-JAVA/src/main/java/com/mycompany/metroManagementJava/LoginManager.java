package com.mycompany.metroManagementJava;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginManager {

    public static Employee login(String email, String password) {
        String query = "SELECT * FROM employees WHERE Email = '" + email + "'";

        try {
            ResultSet rs = DatabaseManager.get(query);

            if (rs != null && rs.next()) { 
                if (password.equals(rs.getString("Password"))) {
                    return new Employee(
                            rs.getInt("EmployeeID"),
                            rs.getString("Name"),
                            rs.getString("Password"),
                            rs.getString("Email"),
                            rs.getString("CNIC"),
                            rs.getString("Address"),
                            rs.getString("PhoneNumber"),
                            rs.getInt("BranchCode"),
                            rs.getFloat("Salary"),
                            rs.getBoolean("Active"),
                            Role.valueOf(rs.getString("Role"))
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; 
    }
}