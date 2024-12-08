package com.metro.BranchManager;

import com.metro.Components.Body;
import com.metro.Components.ChartDisplay;
import com.metro.Components.PieChartDisplay;
import com.metro.Components.RoundedPanel;
import com.metro.Controller;
import com.metro.Models.Report;
import com.metro.ThemeManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;

public class DashboardUI extends Body {

    private final int dashboardWidth;
    private final int dashboardHeight;
    private ArrayList<Report> reports;
    private Controller controller;
    private int branchCode;

    public DashboardUI(int branchCode, int width, int height) {
        this.dashboardWidth = width;
        this.dashboardHeight = height;
        try {
            this.controller = Controller.getInstance();
        } catch (IOException ex) {
            Logger.getLogger(DashboardUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        reports = new ArrayList<>();
        try {
            reports = this.controller.getReports(branchCode);
        } catch (IOException ex) {
            Logger.getLogger(DashboardUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(ThemeManager.getBodyBackgroundColor());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(addStatistics());
        add(Box.createVerticalStrut(10));
        add(addCharts());
    }

    public JPanel addStatistics() {
        RoundedPanel stats = new RoundedPanel(0);
        stats.setLayout(new BoxLayout(stats, BoxLayout.X_AXIS));
        stats.setBackground(ThemeManager.getBodyBackgroundColor());
        stats.setPreferredSize(new Dimension(0, (int) (dashboardHeight * 0.35)));

        double annualSales = calculateAnnualSales();
        double annualProfit = calculateAnnualProfit();
        double dailySales = calculateDailySales();
        double dailyProfit = calculateDailyProfit();

        Object[][] statsData = {
            {"/path/to/icon1.png", "Annual Sales", "Rs. " + annualSales},
            {"/path/to/icon2.png", "Annual Profit", "Rs. " + annualProfit},
            {"/path/to/icon3.png", "Daily Sales", "Rs. " + dailySales},
            {"/path/to/icon4.png", "Daily Profit", "Rs. " + dailyProfit}
        };
        stats.add(createStatsPanel("Sales Overview", statsData));
        stats.add(Box.createHorizontalStrut(10));

        double annualPurchase = calculateAnnualPurchase();
        double dailyPurchase = calculateDailyPurchase();

        Object[][] statsData2 = {
            {"/path/to/icon2.png", "Annual Purchase", "Rs. " + annualPurchase},
            {"/path/to/icon4.png", "Daily Purchase", "Rs. " + dailyPurchase}
        };
        stats.add(createStatsPanel("Purchase Overview", statsData2));

        Object[][] statsData3 = {{"/path/to/icon2.png", "Annual Salary", "Rs. 5M"}, {"/path/to/icon4.png", "Monthly Salary", "Rs. 200K"}};
        stats.add(Box.createHorizontalStrut(10));
        stats.add(createStatsPanel("Salary Expense", statsData3));

        return stats;
    }

    private double calculateAnnualPurchase() {
        return reports.stream()
                .filter(report -> report.getDate().getYear() == LocalDate.now().getYear())
                .mapToDouble(Report::getTotalPurchase)
                .sum();
    }

    private double calculateDailyPurchase() {
        return reports.stream()
                .filter(report -> report.getDate().equals(LocalDate.now()))
                .mapToDouble(Report::getTotalPurchase)
                .sum();
    }

    private double calculateAnnualSales() {
        return reports.stream()
                .filter(report -> report.getDate().getYear() == LocalDate.now().getYear())
                .mapToDouble(Report::getTotalSale)
                .sum();
    }

    private double calculateAnnualProfit() {
        return reports.stream()
                .filter(report -> report.getDate().getYear() == LocalDate.now().getYear())
                .mapToDouble(Report::getProfit)
                .sum();
    }

    // Calculate daily sales by filtering reports for today's date and summing up the sales
    private double calculateDailySales() {
        return reports.stream()
                .filter(report -> report.getDate().equals(LocalDate.now()))
                .mapToDouble(Report::getTotalSale)
                .sum();
    }

    // Calculate daily profit by filtering reports for today's date and summing up the profit
    private double calculateDailyProfit() {
        return reports.stream()
                .filter(report -> report.getDate().equals(LocalDate.now()))
                .mapToDouble(Report::getProfit)
                .sum();
    }

    public JPanel createStatsPanel(String title, Object[][] statsData) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.white);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(ThemeManager.getPoppinsFont(12, Font.BOLD));
        titleLabel.setBorder(new EmptyBorder(10, 20, 10, 10));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setForeground(Color.gray);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel statsPanel = new JPanel();
        statsPanel.setBackground(Color.white);
        statsPanel.setBorder(new EmptyBorder(0, 20, 20, 10));
        statsPanel.setLayout(new GridLayout(2, 2, 10, 10));

        for (Object[] stat : statsData) {
            String iconPath = (String) stat[0];
            String name = (String) stat[1];
            String value = (String) stat[2];

            statsPanel.add(createStatPanel(iconPath, name, value));
        }

        mainPanel.add(statsPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    private JPanel createStatPanel(String iconPath, String name, String value) {
        JPanel statPanel = new JPanel();
        statPanel.setBackground(Color.white);
        statPanel.setLayout(new BorderLayout());

        RoundedPanel icon = new RoundedPanel(20);
        icon.setPreferredSize(new Dimension(50, 50));

        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(Color.white);
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(ThemeManager.getPoppinsFont(11, Font.PLAIN));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidePanel.add(nameLabel);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(ThemeManager.getPoppinsFont(12, Font.BOLD));
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidePanel.add(valueLabel);

        sidePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        statPanel.add(icon, BorderLayout.WEST);
        statPanel.add(sidePanel, BorderLayout.CENTER);

        return statPanel;
    }

    public JPanel addCharts() {
        List<Double> monthlySales = new ArrayList<>(Collections.nCopies(12, 0.0));
        List<Double> monthlyPurchases = new ArrayList<>(Collections.nCopies(12, 0.0));
        
        for (Report report : reports) {
            int month = report.getDate().getMonthValue() - 1; // Get month (0-based index)
            monthlySales.set(month, monthlySales.get(month) + report.getTotalSale());
            monthlyPurchases.set(month, monthlyPurchases.get(month) + report.getTotalPurchase());
        }

        ChartDisplay chartDisplay = new ChartDisplay("Sales & Purchases", "Month", "Value", (int) (dashboardWidth * 0.70), (int) (dashboardHeight * 0.50));
        chartDisplay.addSeries("Sales",
                Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
                monthlySales.stream().mapToDouble(Double::doubleValue).boxed().collect(Collectors.toList()));

        chartDisplay.addSeries("Purchases",
                Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
                monthlyPurchases.stream().mapToDouble(Double::doubleValue).boxed().collect(Collectors.toList()));

        double totalPurchase = calculateAnnualPurchase();
        double totalSales = calculateAnnualSales();

        double soldPercentage = totalPurchase > 0 ? (totalSales / totalPurchase) * 100 : 0;
        double remainingPercentage = 100 - soldPercentage;

        Map<String, Double> data = new HashMap<>();
        data.put("Sold", soldPercentage);
        data.put("Remaining", remainingPercentage);

        PieChartDisplay pieChart = new PieChartDisplay("Stock Details", (int) (dashboardWidth * 0.25), (int) (dashboardHeight * 0.50));
        pieChart.addData(data);

        RoundedPanel chartPanel = new RoundedPanel(20);
        chartPanel.setLayout(new BorderLayout());
        chartPanel.add(pieChart, BorderLayout.EAST);
        chartPanel.add(chartDisplay, BorderLayout.CENTER);

        return chartPanel;
    }

}
