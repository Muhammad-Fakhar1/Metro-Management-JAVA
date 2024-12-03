package com.metro.Forms;

import com.formdev.flatlaf.ui.FlatLineBorder;
import com.metro.ThemeManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

public class ProductOrderForm extends JFrame {

    private final JTextField productIdField;
    private final JSpinner quantitySpinner;
    private JRadioButton unitRadio;
    private JRadioButton cartonRadio;
    private Consumer<Map<String, Object>> callback;

    public ProductOrderForm(Consumer<Map<String, Object>> callback) {
        this.callback = callback;
        setTitle("Add Product");
        setSize(300, 300);
        setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getRootPane().putClientProperty("JRootPane.titleBarBackground", Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.white);

        mainPanel.add(createFormField("Product ID ", productIdField = new JTextField()));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(createFormField("Quantity ", quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1))));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(createRadioButtonGroup());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JPanel panel = new JPanel();
        JButton submitButton = new JButton("Submit Product");
        submitButton.addActionListener(e -> submitOrder());
        panel.setLayout(new BorderLayout());
        panel.add(submitButton, BorderLayout.CENTER);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(panel);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createFormField(String label, JComponent field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBackground(Color.white);

        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldLabel.setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));
        fieldLabel.setBackground(Color.white);
        panel.add(fieldLabel);
        panel.add(Box.createVerticalStrut(2));

        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, field.getPreferredSize().height));
        field.setBorder(new FlatLineBorder(new Insets(5, 5, 5, 5), new Color(228, 228, 228), 1, 10));
        panel.add(field);

        return panel;
    }

    private JPanel createRadioButtonGroup() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBackground(Color.white);

        JLabel typeLabel = new JLabel("Type");
        typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        typeLabel.setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));

        unitRadio = new JRadioButton("Unit");
        unitRadio.setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));
        unitRadio.setBackground(Color.white);

        cartonRadio = new JRadioButton("Carton");
        cartonRadio.setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));
        cartonRadio.setBackground(Color.white);

        ButtonGroup group = new ButtonGroup();
        group.add(unitRadio);
        group.add(cartonRadio);

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.X_AXIS));
        radioPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        radioPanel.setBackground(Color.white);
        radioPanel.add(unitRadio);
        radioPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        radioPanel.add(cartonRadio);

        panel.add(typeLabel);
        panel.add(Box.createHorizontalGlue());
        panel.add(radioPanel);

        return panel;
    }

    private void submitOrder() {
        if (productIdField.getText().trim().isEmpty()) {
            showError("Please enter a Product ID.");
            return;
        }

        if (!unitRadio.isSelected() && !cartonRadio.isSelected()) {
            showError("Please select a type (Unit or Carton).");
            return;
        }

        String productId = productIdField.getText().trim();
        int quantity = (Integer) quantitySpinner.getValue();
        String type = unitRadio.isSelected() ? "Unit" : "Carton";

        Map<String, Object> data = new HashMap<>();
        data.put("productID", productId);
        data.put("quantity", quantity);
        data.put("type",type);
        
        if (callback != null) {
            callback.accept(data);
        }

        productIdField.setText("");
        quantitySpinner.setValue(1);
        unitRadio.setSelected(false);
        cartonRadio.setSelected(false);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }
}
