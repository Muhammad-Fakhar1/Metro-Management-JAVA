package com.mycompany.metroManagementJava;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ReportManager {

    private static double getTotalAmount(LocalDate startDate, LocalDate endDate, String columnName, int branchCode) throws SQLException {
        double totalAmount = 0.0;

        if (endDate != null && startDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date should be after start date");
        }

        String query = "SELECT SUM(AS total FROM sales_purchase WHERE branchCode=" + branchCode;

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

    public static ArrayList<Report> getReport(int branchCode) {
        ArrayList<Report> reportList = new ArrayList<>();
        String query = "SELECT * FROM sales_purchase WHERE branchCode=" + branchCode;

        ResultSet rs = null;
        try {
            rs = DatabaseManager.get(query);
            while (rs!=null&&rs.next()) {
                LocalDate date = rs.getDate("date").toLocalDate();
                double sale = rs.getDouble("sale");
                double purchase = rs.getDouble("purchase");
                Report report = new Report(date, sale, purchase);
                reportList.add(report);
            }
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return reportList;
    }
}
