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
import javax.swing.JTextField;

public class BranchCreationForm extends Form {
    private final JTextField nameField;
    private final JTextField cityField;
    private final JTextField addressField;
    private final JTextField phoneField;

    public BranchCreationForm(String title, Consumer<Map<String, Object>> callback, int width, int height) {
        super(title, callback, width, height);
        
        JLabel headerLabel = new JLabel("Branch Information", JLabel.CENTER);
        headerLabel.setFont(ThemeManager.getPoppinsFont(16, Font.BOLD));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel infoLabel = new JLabel("Please fill in the required information to create a new branch.", JLabel.CENTER);
        infoLabel.setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));
        infoLabel.setForeground(Color.GRAY);
        infoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        mainPanel.add(headerLabel);
        mainPanel.add(infoLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));
       
        mainPanel.add(createFormField("Branch Name", nameField = new JTextField(), "users"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        mainPanel.add(createFormField("City", cityField = new JTextField(), "location"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        mainPanel.add(createFormField("Full Address", addressField = new JTextField(), "location"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        mainPanel.add(createFormField("Contact Number", phoneField = new JTextField(), "telephone"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 100)));
        
        mainPanel.add(createButton("Create Branch", 12, Font.BOLD, 
                                   ThemeManager.getButtonColor(), 
                                   ThemeManager.getButtonForeground()));
        
        add(mainPanel);
        setVisible(true);
    }
    
    @Override
    protected void submitForm() {
        if (nameField.getText().trim().isEmpty()) {
            showError("Please enter the branch name.");
            return;
        }
        
        if (cityField.getText().trim().isEmpty()) {
            showError("Please enter the city.");
            return;
        }
        
        if (addressField.getText().trim().isEmpty()) {
            showError("Please enter the full address.");
            return;
        }

        String phoneNumber = phoneField.getText().trim();
        if (!phoneNumber.matches("^[0-9]{10}$")) {
            showError("Please enter a valid 10-digit phone number.");
            return;
        }
        
        Map<String, Object> branchData = new HashMap<>();
        branchData.put("name", nameField.getText().trim());
        branchData.put("city", cityField.getText().trim());
        branchData.put("address", addressField.getText().trim());
        branchData.put("phone", phoneNumber);

        if (callback != null) {
            callback.accept(branchData);
        }
        
        nameField.setText("");
        cityField.setText("");
        addressField.setText("");
        phoneField.setText("");
    }
}