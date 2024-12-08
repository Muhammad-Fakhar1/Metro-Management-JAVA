package com.metro.Sections;

import com.formdev.flatlaf.ui.FlatLineBorder;
import com.metro.Components.Card;
import com.metro.ImageProcessor;
import com.metro.Models.Product;
import com.metro.Components.RoundedPanel;
import com.metro.ThemeManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ProductCard extends Card {
    private Product p;

    public ProductCard(Product p, ArrayList<ImageIcon> icons) {
        super(p.getTitle());
        this.p=p;
        setBackground(Color.white);
        setBorder(new FlatLineBorder(new Insets(5, 5, 0, 5), new Color(238, 238, 238), 3, 15));
        setLayout(new BorderLayout());

        JPanel top = createTopPanel(p);
        JPanel center = createCenterPanel(p, icons);
        JPanel bottom = createBottomPanel();

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }

    private JPanel createTopPanel(Product p) {
        JPanel top = new JPanel(new BorderLayout());
        top.setBackground(new Color(0x133A6D));
        top.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(0x133A6D), 3, 10));

        JLabel label = createLabel(p.getTitle(), new Font("Poppins", Font.BOLD, 12), Color.white, new EmptyBorder(10, 15, 10, 0));
        JLabel status = createLabel(Integer.toString(p.getProductID()), new Font("Poppins", Font.BOLD, 11), Color.white, new EmptyBorder(15, 15, 15, 20));

        top.add(status, BorderLayout.EAST);
        top.add(label, BorderLayout.CENTER);
        return top;
    }

    private JPanel createCenterPanel(Product p, ArrayList<ImageIcon> icons) {
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(10, 15, 0, 15));

        center.add(createInfoPanel(icons.get(0), p.getCategory(), 12, Font.PLAIN));
        center.add(createInfoPanel(icons.get(1), p.getQuantity() + " Units", 12, Font.PLAIN));
        center.add(createInfoPanel(null, " Prices (Rs) ", 11, Font.PLAIN));

        RoundedPanel pricesPanel = new RoundedPanel(10);
        pricesPanel.setLayout(new GridLayout(2, 3, 5, 5));
        pricesPanel.setBorder(new EmptyBorder(5, 20, 5, 0));
        pricesPanel.setBackground(new Color(242, 242, 242));

        pricesPanel.add(new JLabel("Original"));
        pricesPanel.add(new JLabel("Unit"));
        pricesPanel.add(new JLabel("Carton"));

        // Values for prices
        pricesPanel.add(new JLabel(Float.toString(p.getOriginalPrice())));
        pricesPanel.add(new JLabel(Float.toString(p.getUnitPrice())));
        pricesPanel.add(new JLabel(Float.toString(p.getCartonPrice())));

        center.add(pricesPanel);

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

    private JPanel createBottomPanel() {
        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.setBorder(new EmptyBorder(15, 15, 0, 15));

        return bottom;
    }

    private JLabel createLabel(String text, Font font, Color color, EmptyBorder border) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setBorder(border);
        return label;
    }
    
    public Product getCardValue(){
        return p;
    }
}
