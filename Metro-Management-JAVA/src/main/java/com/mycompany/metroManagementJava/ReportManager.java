package com.mycompany.metroManagementJava;

import java.sql.*;
import java.time.LocalDate;

public class ReportManager {

    private static double getTotalAmount(LocalDate startDate, LocalDate endDate, String columnName) throws SQLException {
        double totalAmount = 0.0;

        if (endDate != null && startDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date should be after start date");
        }

        String query = "SELECT SUM(" + columnName + ") AS total FROM sales_purchase";

        if (startDate != null && endDate != null) {
            query += " WHERE date BETWEEN '" + startDate.toString() + "' AND '" + endDate.toString() + "'";
        }

        ResultSet rs = null;
        try {
            rs = DatabaseManager.get(query);
            if (rs.next()) {
                totalAmount = rs.getDouble("total");
            }
        } finally {
            if (rs != null) {
                rs.close();
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

    public static double getAllTotalPurchase() throws SQLException {
        return getTotalAmount(null, null, "purchase");
    }

    public static double getAllTotalSale() throws SQLException {
        return getTotalAmount(null, null, "sale");
    }
    
    
}
