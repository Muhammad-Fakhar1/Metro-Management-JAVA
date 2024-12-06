/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.metro.Sections;

import com.formdev.flatlaf.ui.FlatLineBorder;
import com.metro.Components.Card;
import com.metro.ImageProcessor;
import com.metro.ThemeManager;
import com.metro.Models.Vendor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class VendorCard extends Card {

    public VendorCard(Vendor v, ArrayList<ImageIcon> icons, boolean showButoon) {
        setBackground(Color.white);
        setBorder(new FlatLineBorder(new Insets(5, 5, 0, 5), new Color(238, 238, 238), 3, 15));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel top = createTopPanel(v);
        JPanel center = createCenterPanel(v, icons);
        JButton bottom = createBottomPanel();

        add(top);
        add(center);
        if (showButoon) {
            add(bottom);
        }else{
                    add(Box.createVerticalStrut(20));
        }
        add(Box.createVerticalStrut(5));
        
    }

    private JPanel createTopPanel(Vendor v) {
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(0x133A6D));
        top.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(0x133A6D), 3, 10));

        JLabel label = createLabel(v.getName(), new Font("Poppins", Font.BOLD, 14), Color.white, new EmptyBorder(10, 15, 10, 0));
        JLabel status = createLabel(v.isActive() ? "Active" : "Inactive", new Font("Poppins", Font.BOLD, 11), Color.white, new EmptyBorder(15, 15, 15, 20));

        top.add(status, BorderLayout.EAST);
        top.add(label, BorderLayout.CENTER);
        return top;
    }

    private JPanel createCenterPanel(Vendor v, ArrayList<ImageIcon> icons) {
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(10, 15, 0, 15));

        center.add(createInfoPanel(icons.get(0), v.getVendorID(), 12, Font.PLAIN));
        center.add(createInfoPanel(icons.get(1), "Rs. " + Float.toString(v.getAmountSpent()) + " spent", 12, Font.PLAIN));
        center.add(createInfoPanel(icons.get(2), v.getContactInfo(), 12, Font.PLAIN));
        center.add(Box.createVerticalStrut(10));
        return center;
    }

    private JPanel createInfoPanel(ImageIcon icon, String text, int fontSize, int fontWeight) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setOpaque(false);

        if (icon != null) {
            JLabel iconLabel = new JLabel(ImageProcessor.resizeIcon(icon, 15, 15));
            iconLabel.setBorder(new EmptyBorder(0, 0, 0, 5));
            panel.add(iconLabel);
        }

        JLabel textLabel = new JLabel(text);
        textLabel.setFont(ThemeManager.getPoppinsFont(fontSize, fontWeight));

        panel.add(textLabel);
        return panel;
    }

    private JButton createBottomPanel() {
        JButton btn = new JButton("Add Product");
 
        btn.setAlignmentX(CENTER_ALIGNMENT); // Ensure the button aligns centrally

        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50)); // Full width
        btn.setPreferredSize(new Dimension(0, 50)); // Preferred height
        btn.setFocusPainted(false);

        btn.addActionListener(e -> System.out.println("Product clicked!"));
        return btn;
    }

    private JLabel createLabel(String text, Font font, Color color, EmptyBorder border) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setBorder(border);
        return label;
    }
}
