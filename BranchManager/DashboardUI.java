package com.metro.BranchManager;

import com.metro.ChartDisplay;
import com.metro.PieChartDisplay;
import com.metro.RoundedPanel;
import com.metro.ThemeManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class DashboardUI extends branchManagerBody {

    private final int dashboardWidth;
    private final int dashboardHeight;

    public DashboardUI(int width, int height) {
        this.dashboardWidth = width;
        this.dashboardHeight = height;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(ThemeManager.getBodyBackgroundColor());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(addStatistics());
        add(Box.createVerticalStrut(10));
        add(addCharts());
    }

    public JPanel addStatistics() {
        RoundedPanel stats = new RoundedPanel(0);
        stats.setLayout(new GridLayout(1, 2, 10, 10));
        stats.setBackground(ThemeManager.getBodyBackgroundColor());
        stats.setPreferredSize(new Dimension(0, (int) (dashboardHeight * 0.35)));

        Object[][] statsData = {
            {"/path/to/icon1.png", "Annual Sales", "Rs. 100,000"},
            {"/path/to/icon2.png", "Annual Profit", "Rs. 200,000"},
            {"/path/to/icon3.png", "Daily Sales", "Rs. 300,000"},
            {"/path/to/icon4.png", "Daily Profit", "Rs. 400,000"}
        };
        stats.add(createStatsPanel("Sales Overview", statsData));

        Object[][] statsData2 = {{"/path/to/icon1.png", "Annual Purchase", "10,000 Units"}, {"/path/to/icon2.png", "Annual Amount", "Rs. 5M"}, {"/path/to/icon3.png", "Daily Purchase", "500 Units"}, {"/path/to/icon4.png", "Daily Amount", "Rs. 200K"}};
        stats.add(createStatsPanel("Purchase Overview", statsData2));

        return stats;
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

//        ImageIcon icon = new ImageIcon(iconPath);
//        JLabel iconLabel = new JLabel(icon);
//        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        statPanel.add(iconLabel);
        RoundedPanel icon = new RoundedPanel(20);
        icon.setPreferredSize(new Dimension(50, 100));

        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(Color.white);
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));

        // Add the name
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(ThemeManager.getPoppinsFont(11, Font.PLAIN));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidePanel.add(nameLabel);

        // Add the value
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
        ChartDisplay chartDisplay = new ChartDisplay("Sales & Purchases", "Month", "Value", (int) (dashboardWidth * 0.70), (int) (dashboardHeight * 0.50));
        PieChartDisplay pieChart = new PieChartDisplay("Top Categories", (int) (dashboardWidth * 0.25), (int) (dashboardHeight * 0.50));

        chartDisplay.addSeries("Sales", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
                Arrays.asList(400, 300, 250, 400, 450, 500, 270, 400, 500, 350, 500, 700));
        chartDisplay.addSeries("Purchases", Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12),
                Arrays.asList(350, 220, 300, 350, 400, 420, 200, 300, 250, 400, 450, 500));

        Map<String, Double> data = new HashMap<>();
        data.put("Dairy", 10.0);
        data.put("Bakery", 30.0);
        data.put("Spices", 20.0);
        data.put("Sauces", 15.0);
        data.put("Vegetables", 25.0);

        pieChart.addData(data);

        RoundedPanel chartPanel = new RoundedPanel(20);
        chartPanel.setLayout(new BorderLayout());
        chartPanel.add(pieChart, BorderLayout.EAST);
        chartPanel.add(chartDisplay, BorderLayout.CENTER);

        return chartPanel;
    }
}
