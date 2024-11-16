package com.metro;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;

public class SystemStatus extends JPanel {

    private JLabel databaseStatusLabel;
    private JLabel apiStatusLabel;
    private JLabel systemLoadLabel;
    private JProgressBar progressBar;

    public SystemStatus() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 15, 30, 25));
        setBackground(Color.WHITE);
        
        createProgressBar();

        add(createHeadingPanel());
        
        addStatusRow("Database", "Connected", "images/database.png");
        addStatusRow("API Status", "Operational", "images/server.png");
        addStatusRow("System Load", "75%", "images/load.png");

        add(Box.createRigidArea(new Dimension(0, 8)));
        add(progressBar);
    }

    private JPanel createHeadingPanel() {
        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headingPanel.setBackground(Color.white);

        JLabel headingLabel = new JLabel("System Status");
        headingLabel.setFont(new Font("Poppins", Font.BOLD, 12));
        headingLabel.setForeground(Color.DARK_GRAY);

        headingPanel.add(headingLabel);
        return headingPanel;
    }

    private void createProgressBar() {
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(75);
        progressBar.setForeground(Color.black);
        progressBar.setPreferredSize(new Dimension(190, 5));
        progressBar.setBackground(Color.WHITE);
        progressBar.setBorder(new EmptyBorder(0, 8, 0, 0));
    }

    private void addStatusRow(String labelText, String statusText, String iconPath) {
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new BorderLayout());
        rowPanel.setBackground(Color.WHITE);

        ImageIcon icon = new ImageIcon(iconPath);
        JLabel iconLabel = new JLabel(ImageProcessor.resizeIcon(icon, 15, 16));
        iconLabel.setBorder(new EmptyBorder(0, 5, 0, 10));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Poppins", Font.PLAIN, 10));
        label.setForeground(Color.GRAY);

        JLabel statusLabel = new JLabel(statusText);
        statusLabel.setFont(new Font("Poppins", Font.PLAIN, 9));
        statusLabel.setForeground(Color.gray);

        rowPanel.add(iconLabel, BorderLayout.WEST);
        rowPanel.add(label, BorderLayout.CENTER);
        rowPanel.add(statusLabel, BorderLayout.EAST);

        add(Box.createRigidArea(new Dimension(0, 4)));
        add(rowPanel);
    }

    public void updateDatabaseStatus(String status) {
        databaseStatusLabel.setText(status);
    }

    public void updateApiStatus(String status) {
        apiStatusLabel.setText(status);
    }

    public void updateSystemLoad(int percentage) {
        systemLoadLabel.setText(percentage + "%");
        progressBar.setValue(percentage);
    }
}
