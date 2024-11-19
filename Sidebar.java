package com.metro;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import javax.swing.Box;
import javax.swing.border.EmptyBorder;

public class Sidebar extends JPanel {

    private final Font BUTTON_FONT = new Font("Poppins", Font.PLAIN, 14);
    private final int sidebarWidth;
    private JPanel buttonPanel;
    private SystemStatus bottomPanel;

    public Sidebar(int frameWidth, int frameHeight) {
        sidebarWidth = (int) (frameWidth * 0.225);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(sidebarWidth, frameHeight));
        setBackground(ThemeManager.getSidebarBackgroundColor());

        setButtonPanel();
        setSystemStatusPanel();

        add(buttonPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void addButton(String title, ImageIcon icon, ActionListener action) {
        JButton button = new JButton(title, ImageProcessor.resizeIcon(icon, 16, 16));
        button.setIconTextGap(15);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(sidebarWidth, 35));
        button.setBorder(new EmptyBorder(0, 25, 0, 0));
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBackground(ThemeManager.getSidebarBackgroundColor());
        button.addActionListener(action);

        buttonPanel.add(button);
    }

    private void setButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(ThemeManager.getSidebarBackgroundColor());
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    }

    private void setSystemStatusPanel() {
        bottomPanel = new SystemStatus();
    }
}