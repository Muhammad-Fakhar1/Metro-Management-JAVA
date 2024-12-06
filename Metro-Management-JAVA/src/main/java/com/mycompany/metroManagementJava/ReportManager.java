package com.mycompany.metroManagementJava;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReportManager {

    private static double getTotalAmount(LocalDate startDate, LocalDate endDate, String columnName) throws SQLException {
        double totalAmount = 0.0;
        
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date.");
        }

        String query = "SELECT SUM(" + columnName + ") as totalAmount FROM sales_purchase WHERE date BETWEEN ? AND ?";
        
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             
            pstmt.setString(1, startDate.format(DateTimeFormatter.ISO_DATE));
            pstmt.setString(2, endDate.format(DateTimeFormatter.ISO_DATE));

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    totalAmount = rs.getDouble("totalAmount");
                }
            }
        }
        
        return totalAmount;
    }

    public static double getSale(LocalDate startDate, LocalDate endDate) throws SQLException {
        return getTotalAmount(startDate, endDate, "sale");
    }

    public static double getPurchase(LocalDate startDate, LocalDate endDate) throws SQLException {
        return getTotalAmount(startDate, endDate, "purchase");
    }
}
