package com.metro.Components;

import com.formdev.flatlaf.ui.FlatButtonBorder;
import com.formdev.flatlaf.ui.FlatLineBorder;
import com.metro.ImageProcessor;
import com.metro.ThemeManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Header extends JPanel {

    private final Font HEADER_FONT = new Font("Poppins", Font.BOLD, 22);
    private final int headerHeight;
    private final String userName;
    private final String branchName;
    
    //private static JTextField searchField;

    public Header(String name, String branch, int frameWidth, int frameHeight) {
        headerHeight = (int) (frameHeight * 0.10);
        this.userName = name;
        this.branchName = branch;
        
//        searchField=new JTextField();
//        searchField.setPreferredSize(new Dimension(300,25));
//        searchField.setBorder(new FlatLineBorder(new Insets(2,10,2,10),new Color(200,200,200),1,10));

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(frameWidth, headerHeight));
        setBackground(ThemeManager.getHeaderBackgroundColor());

        add(createLeftPanel(frameWidth), BorderLayout.WEST);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createRightPanel(userName, branchName), BorderLayout.EAST);
    }

    private JPanel createLeftPanel(int frameWidth) {
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setPreferredSize(new Dimension((int) (frameWidth * 0.18), headerHeight));
        leftPanel.setBackground(ThemeManager.getHeaderBackgroundColor());

        JLabel titleLabel = new JLabel("METRO");
        titleLabel.setFont(HEADER_FONT);
        leftPanel.add(titleLabel);

        return leftPanel;
    }

    private JPanel createCenterPanel() {
        JPanel spacerPanel = new JPanel();
        spacerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        spacerPanel.setBackground(ThemeManager.getHeaderBackgroundColor());
        return spacerPanel;
    }

    private JPanel createRightPanel(String name, String branch) {
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(ThemeManager.getHeaderBackgroundColor());
        rightPanel.setBorder(new EmptyBorder(0, 0, 0, 15));

        JPanel rightContent = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightContent.setBackground(ThemeManager.getHeaderBackgroundColor());

        String branchString = "";
        if (branch != "") {
            branchString = " | <span style='color: #808080;font-size: 9px'>Branch: " + branch + "</span>";
        }
        JLabel nameLabel = new JLabel("<html>" + name + branchString + "</html>");
        nameLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
        nameLabel.setForeground(Color.BLACK);

        RoundedPanel panel = new RoundedPanel(30);
        panel.setBackground(Color.GRAY);
        panel.setPreferredSize(new Dimension(30, 30));

//        JButton actionButton = new JButton("Action");
//        actionButton.setFont(new Font("Poppins", Font.PLAIN, 12));
//        actionButton.setFocusPainted(false);
        rightContent.add(nameLabel);
        rightContent.add(panel);
        rightPanel.add(rightContent);

        return rightPanel;
    }
    
//    public static JTextField getSearchField(){
//        return searchField;
//    }
}
