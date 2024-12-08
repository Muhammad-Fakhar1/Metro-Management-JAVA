package com.metro.BranchManager;

import com.formdev.flatlaf.ui.FlatLineBorder;
import com.metro.Components.RoundedPanel;
import com.metro.Controller;
import com.metro.ThemeManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public final class ReportsUI extends branchManagerBody {

    private Controller controller;
    private DefaultTableModel tableModel;
    private JTable table;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private final int dashboardWidth;
    private final int dashboardHeight;
    private final int branchCode;

    private JPanel allstats;
    private JLabel totalSales;
    private JLabel totalPurchase;
    private JLabel totalProfit;
    private JLabel totalSalesTitle;
    private JLabel totalPurchaseTitle;
    private JLabel totalProfitTitle;

    public ReportsUI(int branchCode, int width, int height) {
        totalSales = new JLabel();
        totalPurchase = new JLabel();
        totalProfit = new JLabel();
        this.branchCode = branchCode;
        this.dashboardWidth = width;
        this.dashboardHeight = height;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(ThemeManager.getBodyBackgroundColor());
        setBorder(new EmptyBorder(0, 15, 0, 15));

        allstats = addStatistics();

        add(createHeader());
        add(allstats);
        add(createTable());
    }

    public JPanel createHeader() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(15, 0, 15, 0));
        panel.setBackground(ThemeManager.getBodyBackgroundColor());

        JLabel label = new JLabel("Reports");
        label.setForeground(Color.GRAY);
        label.setFont(new Font("Poppins", Font.BOLD, 20));
        panel.add(label, BorderLayout.WEST);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        rightPanel.setBackground(ThemeManager.getBodyBackgroundColor());

        JLabel fromLabel = new JLabel("From:");
        JLabel toLabel = new JLabel("To:");
        JTextField fromField = new JTextField(10);
        JTextField toField = new JTextField(10);

        fromField.setBorder(new FlatLineBorder(new Insets(2, 6, 2, 10), Color.lightGray, 1, 10));
        toField.setBorder(new FlatLineBorder(new Insets(2, 6, 2, 10), Color.lightGray, 1, 10));

        fromField.setPreferredSize(new Dimension(fromField.getPreferredSize().width, 30));
        toField.setPreferredSize(new Dimension(toField.getPreferredSize().width, 30));

        fromField.setFont(ThemeManager.getPoppinsFont(10, Font.PLAIN));
        toField.setFont(ThemeManager.getPoppinsFont(10, Font.PLAIN));

        fromField.addActionListener(e -> filterTableByDateRange(fromField.getText(), toField.getText()));
        toField.addActionListener(e -> filterTableByDateRange(fromField.getText(), toField.getText()));

        rightPanel.add(fromLabel);
        rightPanel.add(fromField);
        rightPanel.add(toLabel);
        rightPanel.add(toField);

        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    private void filterTableByDateRange(String fromDate, String toDate) {
        try {
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
            java.util.Date from = fromDate.isEmpty() ? null : dateFormat.parse(fromDate);
            java.util.Date to = toDate.isEmpty() ? null : dateFormat.parse(toDate);

            rowSorter.setRowFilter(new RowFilter<DefaultTableModel, Integer>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                    try {
                        String dateValue = (String) entry.getValue(0);
                        java.util.Date rowDate = dateFormat.parse(dateValue);

                        if (from != null && rowDate.before(from)) {
                            return false;
                        }
                        if (to != null && rowDate.after(to)) {
                            return false;
                        }

                        return true;
                    } catch (Exception ex) {
                        return false; // Invalid date in table
                    }
                }
            });
        } catch (Exception e) {
            rowSorter.setRowFilter(null); // Reset filter if invalid input
        }
    }

    public JPanel addStatistics() {
        RoundedPanel stats = new RoundedPanel(0);
        stats.setLayout(new GridLayout(1, 2, 10, 10));
        stats.setBackground(ThemeManager.getBodyBackgroundColor());
        stats.setPreferredSize(new Dimension(0, (int) (dashboardHeight * 0.20)));

        stats.add(createStatsPanel("Sales Overview"));

        return stats;
    }

    public JPanel createStatsPanel(String title) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.white);

        JLabel titleLabel = new JLabel(" ");
        titleLabel.setFont(ThemeManager.getPoppinsFont(12, Font.BOLD));
        titleLabel.setBorder(new EmptyBorder(0, 20, 5, 10));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setForeground(Color.gray);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel statsPanel = new JPanel();
        statsPanel.setBackground(Color.white);
        statsPanel.setBorder(new EmptyBorder(0, 20, 20, 10));
        statsPanel.setLayout(new GridLayout(1, 3, 10, 10));

        statsPanel.add(createStatPanel(" ", "Total sales: ", totalSales));
        statsPanel.add(createStatPanel(" ", "Total purchase: ", totalPurchase));
        statsPanel.add(createStatPanel(" ", "Total profit: ", totalProfit));

        mainPanel.add(statsPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    private JPanel createStatPanel(String iconPath, String name, JLabel valueLabel) {
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

        valueLabel.setFont(ThemeManager.getPoppinsFont(12, Font.BOLD));
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidePanel.add(valueLabel);

        sidePanel.setBorder(new EmptyBorder(4, 10, 4, 10));

        statPanel.add(icon, BorderLayout.WEST);
        statPanel.add(sidePanel, BorderLayout.CENTER);

        return statPanel;
    }

    public JScrollPane createTable() {
        String[] columnNames = {"Date", "Sale", "Purchase", "Profit"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        rowSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(rowSorter);

        styleTable();
        populateTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        return scrollPane;
    }

    private void styleTable() {
        table.setRowHeight(40);
        table.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), Color.white, 0, 8));
        table.setFont(new Font("Poppins", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 10));
        table.getTableHeader().setForeground(Color.lightGray);
        table.getTableHeader().setReorderingAllowed(true);
        table.setSelectionBackground(null);
        table.setSelectionForeground(Color.black);

        table.setFocusable(false);
        table.setEnabled(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);

        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            table.getColumnModel().getColumn(i).setPreferredWidth(100);
        }

        rowSorter.addRowSorterListener(e -> updateStatistics());
    }

    private void populateTable() {
        String[][] dummyReports = {
            {"2024-12-01", "5000", "3000", "2000"},
            {"2024-12-02", "7000", "4000", "3000"},
            {"2024-12-03", "8000", "5000", "3000"},
            {"2024-12-04", "6000", "2000", "4000"},
            {"2024-12-05", "9000", "7000", "2000"},
            {"2024-12-06", "10000", "8000", "2000"},};

        for (String[] report : dummyReports) {
            tableModel.addRow(report);
        }

        updateStatistics();
    }

    private void updateStatistics() {
        int SALES = 0, PURCHASE = 0;

        for (int i = 0; i < table.getRowCount(); i++) {
            int modelRow = table.convertRowIndexToModel(i);
            SALES += Integer.parseInt((String) tableModel.getValueAt(modelRow, 1));
            PURCHASE += Integer.parseInt((String) tableModel.getValueAt(modelRow, 2));
        }

        int profit = SALES - PURCHASE;

        totalSales.setText(Float.toString(SALES));
        totalPurchase.setText(Float.toString(PURCHASE));
        totalProfit.setText(Float.toString(profit));
    }

}
