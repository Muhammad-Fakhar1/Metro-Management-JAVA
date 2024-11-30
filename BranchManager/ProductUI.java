package com.metro.BranchManager;

import com.formdev.flatlaf.ui.FlatButtonBorder;
import com.formdev.flatlaf.ui.FlatMarginBorder;
import com.metro.Controller;
import com.metro.Product;
import com.metro.RoundedPanel;
import com.metro.SuperAdmin.BranchCard;
import com.metro.ThemeManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ProductUI extends branchManagerBody {

    private final int dashboardWidth;
    private final int dashboardHeight;
    private final Controller controller;
    private ArrayList<Product> products;
    private ArrayList<ImageIcon> icons;

    public ProductUI(int width, int height) {
        this.dashboardWidth = width;
        this.dashboardHeight = 500;
        this.controller = Controller.getInstance();
        icons = new ArrayList<>();
        products = new ArrayList<>();

        getProducts();
        getIcons();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(ThemeManager.getBodyBackgroundColor());
        setBorder(new EmptyBorder(15, 15, 15, 15));

        add(addSection("Products"));
    }

    public JPanel addSection(String Title) {
        int size = products.size();

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
            container.add(new ProductCard(products.get(i), icons));
        }

        int placeholders = (rows + 1) * 3 - size - 1;
        for (int i = 0; i < placeholders; i++) {
            container.add(new JLabel());
        }

        stats.add(Label, BorderLayout.NORTH);
        stats.add(container, BorderLayout.CENTER);

        return stats;
    }

    private void getProducts() {
        products = controller.getProducts();
    }

    private void getIcons() {
        String[] imagePaths = {"images/category.png", "images/box.png"};
        for (int i = 0; i < 2; i++) {
            icons.add(new ImageIcon(imagePaths[i]));
        }
    }
}
