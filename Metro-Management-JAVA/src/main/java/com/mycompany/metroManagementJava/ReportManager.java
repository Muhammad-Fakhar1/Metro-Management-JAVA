package com.mycompany.metroManagementJava;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReportManager {

    private static double getTotalAmount(int daysRange, String columnName) throws SQLException {
        double totalAmount = 0.0;
        LocalDate currentDate = LocalDate.now();

        for (int i = 0; i < daysRange; i++) {
            LocalDate dateToCheck = currentDate.minusDays(i);
            String dateString = dateToCheck.format(DateTimeFormatter.ISO_DATE);

            String query = "SELECT " + columnName + " FROM sales_purchase WHERE date = '" + dateString + "'";
            ResultSet rs = DatabaseManager.get(query);
            
            try {
                if (rs.next()) {
                    totalAmount += rs.getDouble(columnName);
                }
            } finally {
                if (rs != null) {
                    rs.close();
                }
            }
        }
        return totalAmount;
    }

    public static double getSale(int daysRange) throws SQLException {
        return getTotalAmount(daysRange, "sale");
    }

    public static double getPurchase(int daysRange) throws SQLException {
        return getTotalAmount(daysRange, "purchase");
    }
}
