package com.metro.Forms;

import com.formdev.flatlaf.ui.FlatLineBorder;
import com.metro.ImageProcessor;
import com.metro.ThemeManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

public abstract class Form extends JFrame {

    protected final Consumer<Map<String, Object>> callback;
    protected JPanel mainPanel;

    public Form(String title, Consumer<Map<String, Object>> callback, int width, int height) {
        this.callback = callback;

        setTitle(title);
        setSize(width, height);
        setFont(ThemeManager.getPoppinsFont(10, Font.PLAIN));
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getRootPane().putClientProperty("JRootPane.titleBarBackground", Color.WHITE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.white);
    }

    protected JPanel createFormField(String label, JComponent field, String icon) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBackground(Color.white);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
        labelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        labelPanel.setBackground(Color.white);

        JLabel iconLabel = new JLabel(ImageProcessor.resizeIcon(new ImageIcon("images/" + icon + ".png"), 14, 14));
        iconLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        iconLabel.setBorder(new EmptyBorder(0, 0, 5, 10));
        labelPanel.add(iconLabel);

        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        fieldLabel.setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));
        fieldLabel.setBackground(Color.white);
        labelPanel.add(fieldLabel);

        panel.add(labelPanel);
        panel.add(Box.createVerticalStrut(2));

        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, field.getPreferredSize().height * 2));
        field.setBorder(BorderFactory.createCompoundBorder(
                new FlatLineBorder(new Insets(3, 5, 3, 5), new Color(228, 228, 228), 1, 10),
                BorderFactory.createEmptyBorder(4, 5, 4, 5)
        ));
        field.setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));

        panel.add(field);

        return panel;
    }

    protected JPanel createRadioButtonGroup(String title, String[] labels, JRadioButton[] radios) {
        JPanel panel = new JPanel();
        if (labels.length >= 3) {
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        } else {
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        }
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBackground(Color.WHITE);

        JLabel typeLabel = new JLabel(title);
        typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        typeLabel.setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));

        ButtonGroup group = new ButtonGroup();
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.X_AXIS));
        radioPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        radioPanel.setBackground(Color.WHITE);

        for (int i = 0; i < radios.length; i++) {
            radios[i].setText(labels[i]);
            radios[i].setFont(ThemeManager.getPoppinsFont(11, Font.PLAIN));
            radios[i].setBackground(Color.WHITE);
            group.add(radios[i]);
            radioPanel.add(radios[i]);
            if (i < radios.length - 1) {
                radioPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            }
        }

        panel.add(typeLabel);
        panel.add(Box.createHorizontalGlue());
        panel.add(radioPanel);

        return panel;
    }

    protected JPanel createButton(String title, int fontSize, int fontWeight, Color bgColor, Color fgColor) {
        JPanel panel = new JPanel();
        JButton submitButton = new JButton(title);
        submitButton.setFont(ThemeManager.getPoppinsFont(fontSize, fontWeight));
        submitButton.setBackground(bgColor);
        submitButton.setForeground(fgColor);
        submitButton.addActionListener(e -> submitForm());
        panel.setLayout(new BorderLayout());
        panel.add(submitButton, BorderLayout.CENTER);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        return panel;
    }

    protected void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }

    protected abstract void submitForm();
}
