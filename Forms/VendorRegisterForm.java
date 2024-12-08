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

public class VendorRegisterForm extends Form {

    private final JTextField nameField;
    private final JTextField contactInfoField;

    public VendorRegisterForm(Consumer<Map<String, Object>> callback, int width, int height) {
        super("", callback, width, height);

        JLabel headerLabel = new JLabel("Vendor Registration", JLabel.CENTER);
        headerLabel.setFont(ThemeManager.getPoppinsFont(16, Font.BOLD));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel infoLabel = new JLabel("Please fill in the required information to register a new vendor.\n",
                JLabel.CENTER);
        infoLabel.setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));
        infoLabel.setForeground(Color.gray);
        infoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(headerLabel);
        mainPanel.add(infoLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        mainPanel.add(createFormField("Vendor Name", nameField = new JTextField(), "users"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(createFormField("Contact Information", contactInfoField = new JTextField(), "telephone"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 140)));

        mainPanel.add(createButton("Create Vednor", 12, Font.BOLD,ThemeManager.getButtonColor(),ThemeManager.getButtonForeground()));

        add(mainPanel);
        setVisible(true);
    }

    @Override
    protected void submitForm() {
        if (nameField.getText().trim().isEmpty()) {
            showError("Please enter the Vendor Name.");
            return;
        }

        if (contactInfoField.getText().trim().isEmpty()) {
            showError("Please enter the Contact Information.");
            return;
        }

        String name = nameField.getText().trim();
        String contactInfo = contactInfoField.getText().trim();

        Map<String, Object> vendorData = new HashMap<>();
        vendorData.put("name", name);
        vendorData.put("contactInfo", contactInfo);

        if (callback != null) {
            callback.accept(vendorData);
        }

        // Clear fields after registration
        nameField.setText("");
        contactInfoField.setText("");
    }
}
