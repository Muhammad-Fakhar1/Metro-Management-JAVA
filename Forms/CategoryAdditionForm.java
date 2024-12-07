package com.metro.Forms;

import com.metro.ThemeManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class CategoryAdditionForm extends Form {
    private final JTextField nameField;
    private final JSpinner taxSpinner;

    public CategoryAdditionForm(Consumer<Map<String, Object>> callback, int width, int height) {
        super("", callback, width, height);
        
        JLabel headerLabel = new JLabel("Category information", JLabel.CENTER);
        headerLabel.setFont(ThemeManager.getPoppinsFont(16, Font.BOLD));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel infoLabel = new JLabel("Please fill in the required information to add a new category.\n",
                JLabel.CENTER);
        infoLabel.setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));
        infoLabel.setForeground(Color.GRAY);
        infoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        mainPanel.add(headerLabel);
        mainPanel.add(infoLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        
        mainPanel.add(createFormField("Category Title", nameField = new JTextField(), "users"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        // Create a spinner for tax with float values
        taxSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 100.0, 0.1));
        mainPanel.add(createFormField("GST Tax (%)", taxSpinner, "telephone"));
        
        mainPanel.add(Box.createRigidArea(new Dimension(0, 140)));
        mainPanel.add(createButton("Create Category", 12, Font.BOLD, 
                                   ThemeManager.getButtonColor(), 
                                   ThemeManager.getButtonForeground()));
        
        add(mainPanel);
        setVisible(true);
    }

    @Override
    protected void submitForm() {
        if (nameField.getText().trim().isEmpty()) {
            showError("Please enter the category Name.");
            return;
        }
        
        String name = nameField.getText().trim();
        double tax = (double) taxSpinner.getValue();
        
        Map<String, Object> categoryData = new HashMap<>();
        categoryData.put("name", name);
        categoryData.put("tax", tax);
        
        if (callback != null) {
            callback.accept(categoryData);
        }
        
        nameField.setText("");
        taxSpinner.setValue(0.0);
    }
}