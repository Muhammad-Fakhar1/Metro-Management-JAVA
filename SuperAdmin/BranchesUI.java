package com.metro.SuperAdmin;

import com.metro.Branch;
import com.formdev.flatlaf.ui.FlatButtonBorder;
import com.formdev.flatlaf.ui.FlatLineBorder;
import com.formdev.flatlaf.ui.FlatMarginBorder;
import com.metro.Controller;
import com.metro.RoundedPanel;
import com.metro.ThemeManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class BranchesUI extends SuperAdminBody {

    private final int dashboardWidth;
    private final int dashboardHeight;
    private final Controller controller;
    private ArrayList<Branch> branches;
    private ArrayList<ImageIcon> icons;

    public BranchesUI(int width, int height) {
        this.dashboardWidth = width;
        this.dashboardHeight = height;
        this.controller = Controller.getInstance();
        icons=new ArrayList<ImageIcon>();

        getBranches();
        getIcons();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(ThemeManager.getBodyBackgroundColor());
        setBorder(new EmptyBorder(20, 60, 20, 60));

        add(addSection("Select A Branch", "Branch"));
    }

    public JPanel addSection(String Title, String ButtonTitle) {
        int size = branches.size();

        int rows = (int) Math.ceil(size / 4.0);
        double multiplier = 1 + (rows - 1) * 0.7;//Too stretch or compress the cards

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

        JButton button = new JButton("Add " + ButtonTitle);
        button.setFont(new Font("Poppins", Font.PLAIN, 12));
        button.setPreferredSize(new Dimension(150, 40));
        button.setFocusPainted(false);
        button.setBorder(new FlatButtonBorder());
        button.setBackground(Color.WHITE);
        button.setForeground(Color.LIGHT_GRAY);

        container.add(button);

        for (int i = 0; i < size; i++) {
            container.add(new BranchCard(branches.get(i), icons));
        }

        int placeholders = (rows + 1) * 4 - size - 1;
        for (int i = 0; i < placeholders; i++) {
            container.add(new JLabel());
        }

        stats.add(Label, BorderLayout.NORTH);
        stats.add(container, BorderLayout.CENTER);

        return stats;
    }

    private void getBranches() {
        branches = controller.getBranches();
    }

    private void getIcons() {
        String[] imagePaths = {"images/building.png", "images/location.png", "images/telephone.png", "images/users.png", "images/calendar.png","images/profile.png"};
        for (int i = 0; i < 6; i++) {
            icons.add(new ImageIcon(imagePaths[i]));
        }
    }
}
