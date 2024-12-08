package com.mycompany.metroManagementJava;

import java.sql.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

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

    public static double getTotalPurchase() throws SQLException {
        return getTotalAmount(null, null, "purchase");
    }

    public static double getTotalSale() throws SQLException {
        return getTotalAmount(null, null, "sale");
    }

    public static double getMonthlyRevenue(Month month) {
        int year = LocalDate.now().getYear();

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();

        double revenue = 0.0;
        try {
            revenue = getSale(firstDayOfMonth, lastDayOfMonth) - getPurchase(firstDayOfMonth, lastDayOfMonth) - Workforce.getSalaryExpenditure();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return revenue;
    }

    public static Report getReport() {
        double totalSale = 0.0;
        double totalPurchase = 0.0;
        double monthlyRevenue = 0.0;

        try {
            totalSale = getTotalSale();
            totalPurchase = getTotalPurchase();

            monthlyRevenue = getMonthlyRevenue(LocalDate.now().getMonth());  // Use current month

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Report(totalSale, totalPurchase, monthlyRevenue);
    }


}
