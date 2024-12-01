package com.metro.DataEntry;

import com.formdev.flatlaf.ui.FlatButtonBorder;
import com.formdev.flatlaf.ui.FlatLineBorder;
import com.formdev.flatlaf.ui.FlatMarginBorder;
import com.metro.Components.RoundedPanel;
import com.metro.ThemeManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class DashboardUI extends DataEntryBody {

    private final int dashboardWidth;
    private final int dashboardHeight;

    public DashboardUI(int width, int height) {
        this.dashboardWidth = width;
        this.dashboardHeight = height;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(ThemeManager.getBodyBackgroundColor());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(addStatistics());
        add(Box.createVerticalStrut(20));
        add(addSection("Vendors", "Vendor"));
        add(Box.createVerticalStrut(20));
        add(addSection("Categories", "Category"));
    }

    public JPanel addStatistics() {
        RoundedPanel stats = new RoundedPanel(15);
        stats.setPreferredSize(new Dimension(0, (int) (dashboardHeight * 0.25)));
        stats.setBackground(Color.WHITE);
        return stats;
    }

    public JPanel addSection(String Title, String ButtonTitle) {
        RoundedPanel stats = new RoundedPanel(15);
        stats.setPreferredSize(new Dimension(0, (int) (dashboardHeight * 1.5)));
        stats.setBackground(ThemeManager.getBodyBackgroundColor());
        stats.setLayout(new BorderLayout());

        JLabel vendorsLabel = new JLabel(Title);
        vendorsLabel.setForeground(Color.GRAY);
        vendorsLabel.setFont(new Font("Poppins", Font.BOLD, 20));
        vendorsLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel container = new JPanel();
        container.setLayout(new GridLayout(3, 4, 10, 10));
        container.setBackground(ThemeManager.getBodyBackgroundColor());
        container.setBorder(new FlatMarginBorder(new Insets(15, 0, 0, 0)));

        JButton button = new JButton("Add " + ButtonTitle);
        button.setFont(new Font("Poppins", Font.PLAIN, 12));
        button.setPreferredSize(new Dimension(150, 40));
        button.setFocusPainted(false);

        button.setBorder(new FlatButtonBorder());
        button.setBackground(Color.WHITE);
        button.setForeground(Color.LIGHT_GRAY);

        container.add(button);

        for (int i = 1; i < 9; i++) {
            JPanel emptyPanel = new JPanel();
            emptyPanel.setBackground(Color.white);
            emptyPanel.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(238, 238, 238), (int) 3, 15));
            emptyPanel.setLayout(new BorderLayout());

            JPanel top = new JPanel();
            top.setOpaque(false);

            top.setLayout(new BorderLayout());

            JLabel label = new JLabel("Name");
            label.setFont(new Font("Poppins", Font.PLAIN, 16));
            label.setBorder(new EmptyBorder(15, 15, 15, 0));
            
            JLabel status = new JLabel("Active");
            status.setFont(new Font("Poppins", Font.PLAIN, 12));
            status.setForeground(new Color(94, 158, 41));
            status.setBorder(new EmptyBorder(15, 15, 15, 20));
            
            top.add(status,BorderLayout.EAST);
            top.add(label, BorderLayout.CENTER);
            
            JPanel bottom = new JPanel();
            bottom.setOpaque(false);
            bottom.setLayout(new BorderLayout());
            bottom.setBorder(new EmptyBorder(15,15,15,15));
            
            JButton btn=new JButton("Add Product");
            btn.setPreferredSize(new Dimension(0,40));
            
            bottom.add(btn);
            
            emptyPanel.add(top, BorderLayout.NORTH);
            emptyPanel.add(bottom,BorderLayout.SOUTH);

            container.add(emptyPanel);
        }

        for (int i = 1; i < 4; i++) {
            container.add(Box.createHorizontalGlue());
        }

        stats.add(vendorsLabel, BorderLayout.NORTH);
        stats.add(container, BorderLayout.CENTER);

        return stats;
    }

}
