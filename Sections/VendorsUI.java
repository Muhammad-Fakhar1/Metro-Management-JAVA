package com.metro.Sections;

import com.formdev.flatlaf.ui.FlatMarginBorder;
import com.metro.Components.Body;
import com.metro.Controller;
import com.metro.Models.Product;
import com.metro.Components.RoundedPanel;
import com.metro.ThemeManager;
import com.metro.Models.Vendor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class VendorsUI extends Body {

    private final int dashboardWidth;
    private final int dashboardHeight;
    private final Controller controller;
    private ArrayList<Vendor> vendors;
    private ArrayList<ImageIcon> icons;

    public VendorsUI(int width, int height) {
        this.dashboardWidth = width;
        this.dashboardHeight = 500;
        this.controller = Controller.getInstance();
        icons = new ArrayList<>();
        vendors = new ArrayList<>();

        getVendors();
        getIcons();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(ThemeManager.getBodyBackgroundColor());
        setBorder(new EmptyBorder(15, 15, 15, 15));

        add(addSection("Vendors"));
    }

    public JPanel addSection(String Title) {
        int size = vendors.size();

        int rows = (int) Math.ceil(size / 3.0);
        double multiplier = 1 + (rows - 1) * 0.6;//Too stretch or compress the cards

        int panelHeight = (int) (dashboardHeight * multiplier);

        RoundedPanel stats = new RoundedPanel(15);
        stats.setPreferredSize(new Dimension(0, panelHeight));
        stats.setBackground(ThemeManager.getBodyBackgroundColor());
        stats.setLayout(new BorderLayout());

        JLabel Label = new JLabel(Title);
        Label.setForeground(Color.GRAY);
        Label.setFont(new Font("Poppins", Font.BOLD, 20));
        Label.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel container = new JPanel();
        container.setLayout(new GridLayout(rows + 1, 4, 5, 5));
        container.setBackground(ThemeManager.getBodyBackgroundColor());
        container.setBorder(new FlatMarginBorder(new Insets(15, 0, 0, 0)));

        for (int i = 0; i < size; i++) {
            container.add(new VendorCard(vendors.get(i), icons));
        }

        int placeholders = (rows + 1) * 3 - size - 1;
        for (int i = 0; i < placeholders; i++) {
            container.add(new JLabel());
        }

        stats.add(Label, BorderLayout.NORTH);
        stats.add(container, BorderLayout.CENTER);

        return stats;
    }

    private void getVendors() {
        vendors = controller.getVendors();
    }

    private void getIcons() {
        String[] imagePaths = {"images/users.png", "images/cash.png","images/mail.png"};
        for (int i = 0; i < 3; i++) {
            icons.add(new ImageIcon(imagePaths[i]));
        }
    }
}
