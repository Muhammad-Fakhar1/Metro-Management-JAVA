package com.metro.Sections;

import com.formdev.flatlaf.ui.FlatLineBorder;
import com.metro.Components.Card;
import com.metro.ImageProcessor;
import com.metro.Models.Category;
import com.metro.ThemeManager;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class CategoryCard extends Card {

    private Category c;

    public CategoryCard(Category c, ArrayList<ImageIcon> icons) {
        super(c.getTitle());
        this.c = c;
        setBackground(Color.white);
        setBorder(new FlatLineBorder(new Insets(5, 5, 0, 5), new Color(238, 238, 238), 3, 15));
        setLayout(new BorderLayout());

        JPanel top = createTopPanel(c);
        JPanel center = createCenterPanel(c, icons);
        JPanel bottom = createBottomPanel(c, icons);

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    private JPanel createTopPanel(Category c) {
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(0x133A6D));
        top.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(0x133A6D), 3, 10));

        JLabel label = createLabel(c.getTitle(), new Font("Poppins", Font.BOLD, 12), Color.white, new EmptyBorder(15, 15, 15, 0));

        top.add(label, BorderLayout.CENTER);
        return top;
    }

    private JPanel createCenterPanel(Category c, ArrayList<ImageIcon> icons) {
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(10, 15, 0, 15));

        String[] labels = {
            Integer.toString(c.getProductCount()) + " Total products",
            Float.toString(c.getGSTRate()) + "% GST tax"
        };

        for (int i = 0; i < labels.length; i++) {
            center.add(createInfoPanel(icons.get(i), labels[i]));
        }
        center.add(Box.createVerticalStrut(80));
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

    private JPanel createBottomPanel(Category c, ArrayList<ImageIcon> icons) {
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.setBorder(new EmptyBorder(0, 15, 15, 15));

//        JLabel descriptionTitleLabel = new JLabel("Description");
//        descriptionTitleLabel.setFont(ThemeManager.getPoppinsFont(11, Font.BOLD));
//        descriptionTitleLabel.setForeground(Color.gray);
//        bottom.add(descriptionTitleLabel, BorderLayout.NORTH);

        JTextArea descriptionTextArea = new JTextArea();
        descriptionTextArea.setText(c.getDescription());
        descriptionTextArea.setFont(ThemeManager.getPoppinsFont(10, Font.PLAIN));
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setWrapStyleWord(true);
        descriptionTextArea.setEditable(false);
        descriptionTextArea.setBackground(null);
        descriptionTextArea.setForeground(Color.DARK_GRAY);
        descriptionTextArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        descriptionTextArea.setPreferredSize(new Dimension(0, 70));

        bottom.add(descriptionTextArea, BorderLayout.CENTER);

        return bottom;
    }

    private JLabel createLabel(String text, Font font, Color color, EmptyBorder border) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setBorder(border);
        return label;
    }

    public Category getCategory() {
        return c;
    }
}
