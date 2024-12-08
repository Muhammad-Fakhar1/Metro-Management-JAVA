package com.metro.Sections;

import com.formdev.flatlaf.ui.FlatLineBorder;
import com.metro.Components.Card;
import com.metro.Controller;
import com.metro.Forms.ProductAdditionForm;
import com.metro.ImageProcessor;
import com.metro.ThemeManager;
import com.metro.Models.Vendor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class VendorCard extends Card {

    private Vendor v;
    private int empID;
    private Controller controller;
    private ProductAdditionForm productForm;

    public VendorCard(int empID,Vendor v, ArrayList<ImageIcon> icons, boolean showButoon) {
        super(v.getName());
        this.v = v;
        this.empID=empID;
        try {
            this.controller=Controller.getInstance();
        } catch (IOException ex) {
            Logger.getLogger(VendorCard.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        } else {
            add(Box.createVerticalStrut(50));
        }
        add(Box.createVerticalStrut(5));

    }

    private JPanel createTopPanel(Vendor v) {
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(0x133A6D));
        top.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(0x133A6D), 3, 10));

        JLabel label = createLabel(v.getName(), new Font("Poppins", Font.BOLD, 12), Color.white, new EmptyBorder(10, 15, 10, 0));
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

        center.add(createInfoPanel(icons.get(0), Integer.toString(v.getVendorID()), 12, Font.PLAIN));
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
        btn.setAlignmentX(CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btn.setPreferredSize(new Dimension(0, 50));
        btn.setFocusPainted(false);

        btn.addActionListener(e -> {
            if (productForm == null) {
                productForm = new ProductAdditionForm("", data -> {
                    String title = (String) data.get("title");
                    String category = (String) data.get("category");
                    int originalPrice = (int) data.get("originalPrice");
                    int unitPrice = (int) data.get("unitPrice");
                    int cartonPrice = (int) data.get("cartonPrice");
                    int quantity = (int) data.get("quantity");
                    
                    try {                 
                        if(controller.addProduct(title, category, originalPrice, unitPrice, cartonPrice, quantity, v.getBranchCode(), empID, v.getVendorID())){
                            productForm.dispose();
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(VendorCard.class.getName()).log(Level.SEVERE, null, ex);
                    }
              
                }, 500, 510);
                productForm.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        productForm = null;
                    }
                });
            } else {
                productForm.toFront();
                productForm.requestFocus();
            }
        });
        return btn;
    }

    private JLabel createLabel(String text, Font font, Color color, EmptyBorder border) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setBorder(border);
        return label;
    }

    public Vendor getCardValue() {
        return v;
    }
}
