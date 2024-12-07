package com.metro.Sections;

import com.metro.Models.Branch;
import com.formdev.flatlaf.ui.FlatButtonBorder;
import com.formdev.flatlaf.ui.FlatLineBorder;
import com.formdev.flatlaf.ui.FlatMarginBorder;
import com.metro.Components.Body;
import com.metro.Controller;
import com.metro.Models.Employee;
import com.metro.Components.RoundedPanel;
import com.metro.ImageProcessor;
import com.metro.ThemeManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.util.ArrayList;

public class BranchesUI extends Body {

    private int dashboardWidth;
    private final int dashboardHeight;
    private final Controller controller;
    private final Employee superAdmin;
    private ArrayList<Branch> branches;
    private ArrayList<ImageIcon> icons;
    private ArrayList<BranchCard> branchCards;

    private static JTextField searchField;
    private JButton searchButton;

    public BranchesUI(Employee superAdmin, int width, int height) {
        this.superAdmin = superAdmin;
        this.dashboardWidth = width;
        this.dashboardHeight = height;
        this.controller = Controller.getInstance();
        icons = new ArrayList<>();
        branchCards = new ArrayList<>();

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 25));
        searchField.setBorder(new FlatLineBorder(new Insets(2, 10, 2, 10), new Color(200, 200, 200), 1, 10));

        getBranches();
        getIcons();
        createBranchCards();

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(ThemeManager.getBodyBackgroundColor());
        setBorder(new EmptyBorder(20, 55, 20, 55));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                dashboardWidth = getWidth();
                removeAll();
                add(addSection("Select A Branch", "Branch"));
                revalidate();
                repaint();
            }
        });

        add(addSection("Select A Branch", "Branch"));
    }

    public JPanel addSection(String Title, String ButtonTitle) {
        int size = branchCards.size();

        int rows = (int) Math.ceil(size / 3.0);
        double multiplier = 1 + (rows - 1) * 0.7;

        int panelHeight = (int) (dashboardHeight * multiplier);

//        if (size < 9) {
//            panelHeight = panelHeight + dashboardHeight - 100;
//            rows += 1;
//        }

        RoundedPanel stats = new RoundedPanel(15);
        stats.setPreferredSize(new Dimension(0, panelHeight));
        stats.setBackground(ThemeManager.getBodyBackgroundColor());
        stats.setLayout(new BorderLayout());

        JLabel Label = new JLabel(Title);
        Label.setForeground(Color.GRAY);
        Label.setFont(new Font("Poppins", Font.BOLD, 20));
        Label.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(ThemeManager.getBodyBackgroundColor());

        JPanel rightSide = new JPanel();
        rightSide.setLayout(new BoxLayout(rightSide, BoxLayout.X_AXIS));
        rightSide.setBackground(ThemeManager.getBodyBackgroundColor());

        JButton label = new JButton(ImageProcessor.resizeIcon(new ImageIcon("images/search.png"), 25, 25));
        label.addActionListener(e -> filterBranches());
        rightSide.add(label);
        rightSide.add(searchField);

        panel.add(Label, BorderLayout.WEST);
        panel.add(rightSide, BorderLayout.EAST);

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

        // Add the cards to the container in the correct order
        for (int i = 0; i < size; i++) {
            container.add(branchCards.get(i));  // Add the cards as they are sorted
        }
       

        int placeholders;
        if (dashboardWidth > 1100) {
            placeholders = (rows + 1) * 4 - size - 1;
        } else {
            placeholders = (rows + 1) * 3 - size - 1;
        }
        for (int i = 0; i < placeholders; i++) {
            container.add(new JLabel());
        }

        stats.add(panel, BorderLayout.NORTH);
        stats.add(container, BorderLayout.CENTER);

        return stats;
    }

    private void getBranches() {
        branches = controller.getBranches();
    }

    private void getIcons() {
        String[] imagePaths = {"images/building.png", "images/location.png", "images/telephone.png", "images/users.png", "images/calendar.png", "images/profile.png"};
        for (int i = 0; i < 6; i++) {
            icons.add(new ImageIcon(imagePaths[i]));
        }
    }

    private void createBranchCards() {
        for (Branch branch : branches) {
            BranchCard card = new BranchCard(superAdmin, branch, icons);
            branchCards.add(card);
        }
    }

    private void filterBranches() {
        String searchText = searchField.getText().toLowerCase();
        ArrayList<BranchCard> filteredCards = new ArrayList<>();
        ArrayList<BranchCard> nonFilteredCards = new ArrayList<>();

        for (BranchCard card : branchCards) {
            Branch branch = card.getBranch();
            if (branch.getName().toLowerCase().contains(searchText)) {
                filteredCards.add(card);
            } else {
                nonFilteredCards.add(card);
            }
        }

        filteredCards.addAll(nonFilteredCards);
        branchCards = filteredCards;

        removeAll();
        add(addSection("Select A Branch", "Branch"));
        revalidate();
        repaint();
    }
}
