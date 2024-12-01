package com.metro.DataEntry;

import com.formdev.flatlaf.ui.FlatBorder;
import com.formdev.flatlaf.ui.FlatButtonBorder;
import com.formdev.flatlaf.ui.FlatButtonUI;
import com.formdev.flatlaf.ui.FlatLineBorder;
import com.formdev.flatlaf.ui.FlatMarginBorder;
import com.metro.Components.RoundedPanel;
import com.metro.ThemeManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class VendorsUI extends DataEntryBody {

    private final int dashboardWidth;
    private final int dashboardHeight;

    public VendorsUI(int width, int height) {
        this.dashboardWidth = width;
        this.dashboardHeight = height;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(ThemeManager.getBodyBackgroundColor());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(addSection("Vendors", "Vendor"));
    }

    public JPanel addSection(String Title, String ButtonTitle) {
        RoundedPanel stats = new RoundedPanel(15);
        stats.setPreferredSize(new Dimension(0, (int) (dashboardHeight *2)));
        stats.setBackground(ThemeManager.getBodyBackgroundColor());
        stats.setLayout(new BorderLayout());

        JLabel vendorsLabel = new JLabel(Title);
        vendorsLabel.setForeground(Color.GRAY);
        vendorsLabel.setFont(new Font("Poppins", Font.BOLD, 20));
        vendorsLabel.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel container = new JPanel();
        container.setLayout(new GridLayout(5, 5, 5, 5));
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

        for (int i = 1; i <= 15; i++) {
            JPanel emptyPanel = new JPanel();
            emptyPanel.setBackground(Color.white);
            emptyPanel.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), new Color(238, 238, 238), (int) 3, 15));
            container.add(emptyPanel);
        }

        for (int i = 16; i < 25; i++) {
            container.add(Box.createHorizontalGlue());
        }

        stats.add(vendorsLabel, BorderLayout.NORTH);
        stats.add(container, BorderLayout.CENTER);

        return stats;
    }

}
