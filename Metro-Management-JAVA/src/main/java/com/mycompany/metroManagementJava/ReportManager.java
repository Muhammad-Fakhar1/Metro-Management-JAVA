package com.mycompany.metroManagementJava;

import java.sql.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;

public class ReportManager {

    private static double getTotalAmount(LocalDate startDate, LocalDate endDate, String columnName, int branchCode) throws SQLException {
        double totalAmount = 0.0;

        if (endDate != null && startDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date should be after start date");
        }

        String query = "SELECT SUM(" + columnName + ") AS total FROM sales_purchase WHERE branchCode=" + branchCode;

        if (startDate != null && endDate != null) {
            query += " AND date BETWEEN '" + startDate.toString() + "' AND '" + endDate.toString() + "'";
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

    public static double getSale(LocalDate startDate, LocalDate endDate, int branchCode) throws SQLException {
        return getTotalAmount(startDate, endDate, "sale", branchCode);
    }

    public static double getPurchase(LocalDate startDate, LocalDate endDate, int branchCode) throws SQLException {
        return getTotalAmount(startDate, endDate, "purchase", branchCode);
    }

    public static double getTotalPurchase(int branchCode) throws SQLException {
        return getTotalAmount(null, null, "purchase", branchCode);
    }

    public static double getTotalSale(int branchCode) throws SQLException {
        return getTotalAmount(null, null, "sale", branchCode);
    }

    public static double getMonthlyRevenue(Month month, int branchCode) {
        int year = LocalDate.now().getYear();

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();

        if (LocalDate.now().isBefore(lastDayOfMonth)) {
            return 0.0;
        }

        double revenue = 0.0;
        try {
            revenue = getSale(firstDayOfMonth, lastDayOfMonth, branchCode) - getPurchase(firstDayOfMonth, lastDayOfMonth, branchCode) - Workforce.getSalaryExpenditure(branchCode);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return revenue;
    }

    public static Report getReport(int branchCode) {
        double totalSale = 0.0;
        double totalPurchase = 0.0;
        double monthlyRevenue = 0.0;

        try {
            totalSale = getTotalSale(branchCode);
            totalPurchase = getTotalPurchase(branchCode);
            monthlyRevenue = getMonthlyRevenue(LocalDate.now().getMonth(), branchCode);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new Report(totalSale, totalPurchase, monthlyRevenue);
    }
}
