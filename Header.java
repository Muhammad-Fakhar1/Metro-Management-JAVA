package com.metro;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class Header extends JPanel {

    private final Font HEADER_FONT = new Font("Poppins", Font.BOLD, 22);
    private final int headerHeight;

    public Header(String name, int frameWidth, int frameHeight) {
        headerHeight = (int) (frameHeight * 0.10);
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(frameWidth, headerHeight));
        setBackground(Color.WHITE);

        add(createLeftPanel(frameWidth), BorderLayout.WEST);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createRightPanel(name), BorderLayout.EAST);
    }

    private JPanel createLeftPanel(int frameWidth) {
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setPreferredSize(new Dimension((int) (frameWidth * 0.225), headerHeight));
        leftPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("METRO");
        
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setForeground(Color.BLACK);
        leftPanel.add(titleLabel);

        return leftPanel;
    }

    private JPanel createCenterPanel() {
        JPanel spacerPanel = new JPanel();
        spacerPanel.setBackground(Color.WHITE);
        return spacerPanel;
    }

    private JPanel createRightPanel(String name) {
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(new EmptyBorder(0,0,0,15));

        JPanel rightContent = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightContent.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
        nameLabel.setForeground(Color.BLACK);

        RoundedPanel panel=new RoundedPanel(30);
        panel.setBackground(Color.GRAY);
        panel.setPreferredSize(new Dimension(30,30));
//        JButton actionButton = new JButton("Action");
//        actionButton.setFont(new Font("Poppins", Font.PLAIN, 12));
//        actionButton.setFocusPainted(false);

        rightContent.add(nameLabel);
        rightContent.add(panel);
        rightPanel.add(rightContent);

        return rightPanel;
    }
}
