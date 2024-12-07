package com.metro.Sections;

import com.formdev.flatlaf.ui.FlatLineBorder;
import com.metro.Models.Branch;
import com.metro.Components.Card;
import com.metro.Models.Employee;
import com.metro.ImageProcessor;
import com.metro.SuperAdmin.SuperAdminDashboard;
import com.metro.ThemeManager;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class BranchCard extends Card {

    private Branch b;

    public BranchCard(Employee superAdmin, Branch b, ArrayList<ImageIcon> icons) {
        super(b.getName());
        this.b = b;
        setBackground(Color.white);
        setBorder(new FlatLineBorder(new Insets(5, 5, 0, 5), new Color(238, 238, 238), 3, 15));
        setLayout(new BorderLayout());
        addActionListener(e -> new SuperAdminDashboard(superAdmin, b));

        JPanel top = createTopPanel(b);
        JPanel center = createCenterPanel(b, icons);
        JPanel bottom = createBottomPanel(b, icons);

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    private JPanel createTopPanel(Branch b) {
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(0x133A6D));
        top.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(0x133A6D), 3, 10));

        JLabel label = createLabel(b.getName(), new Font("Poppins", Font.BOLD, 12), Color.white, new EmptyBorder(10, 15, 10, 0));
        JLabel status = createLabel(b.isActive() ? "Active" : "Inactive", new Font("Poppins", Font.PLAIN, 12), Color.white, new EmptyBorder(15, 15, 15, 20));

        top.add(status, BorderLayout.EAST);
        top.add(label, BorderLayout.CENTER);
        return top;
    }

    private JPanel createCenterPanel(Branch b, ArrayList<ImageIcon> icons) {
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(10, 15, 0, 15));

        String[] labels = {
            b.getBranchId(),
            b.getAddress() + ", " + b.getCity(),
            b.getPhone(),
            b.getNumberOfEmployees() + " Employees",
            "Created on: " + new SimpleDateFormat("yyyy-MM-dd").format(b.getDateCreated())
        };

        for (int i = 0; i < labels.length; i++) {
            center.add(createInfoPanel(icons.get(i), labels[i]));
        }
        return center;
    }

    private JPanel createInfoPanel(ImageIcon icon, String text) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false);

        JLabel iconLabel = new JLabel(ImageProcessor.resizeIcon(icon, 14, 14));
        iconLabel.setBorder(new EmptyBorder(0, 0, 0, 5));

        JLabel textLabel = new JLabel(text);
        textLabel.setFont(ThemeManager.getPoppinsFont(11, Font.PLAIN));

        panel.add(iconLabel);
        panel.add(textLabel);
        return panel;
    }

    private JPanel createBottomPanel(Branch b, ArrayList<ImageIcon> icons) {
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.setBorder(new EmptyBorder(15, 15, 15, 15));

        JButton btn = new JButton("Manager " + b.getBranchManager().getName(), ImageProcessor.resizeIcon(icons.get(5), 20, 20));
        btn.setIconTextGap(10);
        btn.setPreferredSize(new Dimension(0, 50));
        btn.addActionListener(e -> System.out.println("Branch manager clicked!"));

        bottom.add(btn);
        return bottom;
    }

    private JLabel createLabel(String text, Font font, Color color, EmptyBorder border) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setBorder(border);
        return label;
    }

    public Branch getBranch() {
        return b;
    }

}
