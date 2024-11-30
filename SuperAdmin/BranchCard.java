package com.metro.SuperAdmin;

import com.formdev.flatlaf.ui.FlatLineBorder;
import com.metro.Branch;
import com.metro.Card;
import com.metro.ImageProcessor;
import com.metro.ThemeManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class BranchCard extends Card {

    public BranchCard(Branch b, ArrayList<ImageIcon> icons) {

        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Branch clicked!");
            }
        });
        setBackground(Color.white);
        setBorder(new FlatLineBorder(new Insets(5, 5, 0, 5), new Color(238, 238, 238), (int) 3, 15));
        setLayout(new BorderLayout());

        JPanel top = new JPanel();
        top.setBackground(new Color(0x133A6D));
        top.setLayout(new BorderLayout());
        top.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(0x133A6D), (int) 3, 10));

        JLabel label = new JLabel(b.getName());
        label.setFont(new Font("Poppins", Font.BOLD, 12));
        label.setBorder(new EmptyBorder(10, 15, 10, 0));
        label.setForeground(Color.white);

        JLabel status = new JLabel(b.isActive() ? "Active" : "Inactive");
        status.setFont(new Font("Poppins", Font.PLAIN, 12));
        status.setForeground(Color.white);
        status.setBorder(new EmptyBorder(15, 15, 15, 20));

        top.add(status, BorderLayout.EAST);
        top.add(label, BorderLayout.CENTER);

        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setOpaque(false);
        center.setBorder(new EmptyBorder(10, 15, 0, 15));

        String[] labels = {
            b.getBranchId(),
            b.getAddress() + ", " + b.getCity(),
            b.getPhone(),
            Integer.toString(b.getNumberOfEmployees()) + " Employees",
            "Created on: " + new SimpleDateFormat("yyyy-MM-dd").format(b.getDateCreated())
        };

        for (int i = 0; i < labels.length; i++) {
            JPanel panel = new JPanel();
            panel.setBackground(Color.white);
            panel.setOpaque(false);
            panel.setLayout(new FlowLayout(FlowLayout.LEFT));

            JLabel iconLabel = new JLabel(ImageProcessor.resizeIcon(icons.get(i), 14, 14));
            iconLabel.setBorder(new EmptyBorder(0, 0, 0, 5));

            JLabel textLabel = new JLabel(labels[i]);
            textLabel.setFont(ThemeManager.getPoppinsFont(11, Font.PLAIN));

            panel.add(iconLabel);
            panel.add(textLabel);

            center.add(panel);
        }

        JPanel bottom = new JPanel();
        bottom.setOpaque(false);
        bottom.setLayout(new BorderLayout());
        bottom.setBorder(new EmptyBorder(15, 15, 15, 15));

        JButton btn = new JButton("Manager " + b.getBranchManager().getName(), ImageProcessor.resizeIcon(icons.get(5), 20, 20));
        btn.setIconTextGap(10);
        btn.setPreferredSize(new Dimension(0, 50));

        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Branch manager clicked!");
            }
        });

        bottom.add(btn);

        add(top, BorderLayout.NORTH);
        add(center, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }
}
