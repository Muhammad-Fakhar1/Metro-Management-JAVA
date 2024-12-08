package com.metro.Forms;

import com.metro.Models.Role;
import com.metro.ThemeManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class EmployeeRegisterForm extends Form {

    private final JTextField CNICField;
    private final JTextField EmailField;
    private final JTextField NameField;
    private final JTextField AddressField;
    private final JTextField PhoneField;
    private final JSpinner SalarySpinner;
    private JRadioButton cashierRadio;
    private JRadioButton dataEntryRadio;
    private final boolean radioFlag;

    public EmployeeRegisterForm(Consumer<Map<String, Object>> callback, int width, int height, boolean radioFlag) {
        super("", callback, width, height);

        this.radioFlag = radioFlag;

        JLabel headerLabel = new JLabel("Employee Registration", JLabel.CENTER);
        headerLabel.setFont(ThemeManager.getPoppinsFont(16, Font.BOLD));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel infoLabel = new JLabel("Please fill in the required information to register a new employee.\n",
                JLabel.CENTER);
        infoLabel.setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));
        infoLabel.setForeground(Color.gray);
        infoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(headerLabel);
        mainPanel.add(infoLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        JPanel cnicEmailPanel = new JPanel();
        cnicEmailPanel.setAlignmentX(LEFT_ALIGNMENT);
        cnicEmailPanel.setLayout(new BoxLayout(cnicEmailPanel, BoxLayout.X_AXIS));
        cnicEmailPanel.setBackground(Color.white);
        cnicEmailPanel.add(createFormField("CNIC ", CNICField = new JTextField(), "users"));
        cnicEmailPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        cnicEmailPanel.add(createFormField("Email ", EmailField = new JTextField(), "mail"));
        mainPanel.add(cnicEmailPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(createFormField("Full Name ", NameField = new JTextField(), "users"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(createFormField("Address ", AddressField = new JTextField(), "home"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(createFormField("Phone Number ", PhoneField = new JTextField(), "telephone"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(createFormField("Salary ", SalarySpinner = new JSpinner(new SpinnerNumberModel(10000.0, 1.0, Float.MAX_VALUE, 10.0)), "cash"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        if (radioFlag) {
            cashierRadio = new JRadioButton();
            dataEntryRadio = new JRadioButton();
            mainPanel.add(createRadioButtonGroup("Role",
                    new String[]{"Cashier", "Recorder"},
                    new JRadioButton[]{cashierRadio, dataEntryRadio})
            );
        }

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        mainPanel.add(createButton("Register Employee", 12, Font.BOLD, ThemeManager.getButtonColor(), ThemeManager.getButtonForeground()));

        add(mainPanel);
        setVisible(true);
    }

    @Override
    protected void submitForm() {
        if (CNICField.getText().trim().isEmpty()) {
            showError("Please enter the CNIC.");
            return;
        }

        if (NameField.getText().trim().isEmpty()) {
            showError("Please enter the Name.");
            return;
        }

        if (AddressField.getText().trim().isEmpty()) {
            showError("Please enter the Address.");
            return;
        }

        if (EmailField.getText().trim().isEmpty()) {
            showError("Please enter the email.");
            return;
        }

        if (PhoneField.getText().trim().isEmpty()) {
            showError("Please enter the Phone number.");
            return;
        }

        String cnic = CNICField.getText().trim();
        String email = EmailField.getText().trim();
        System.out.println("email: " + email);
        String name = NameField.getText().trim();
        String address = AddressField.getText().trim();
        String phone = PhoneField.getText().trim();

        float salary;
        try {
            salary = Float.parseFloat(SalarySpinner.getValue().toString());
        } catch (NumberFormatException e) {
            showError("Invalid salary value. Please enter a valid number.");
            return;
        }

        Role role = Role.BRANCH_MANAGER;
        if (radioFlag) {
            if (cashierRadio != null) {
                role = cashierRadio.isSelected() ? Role.CASHIER : Role.DATA_ENTRY;
            }
        }

        Map<String, Object> employeeData = new HashMap<>();
        employeeData.put("cnic", cnic);
        employeeData.put("email", email);
        employeeData.put("name", name);
        employeeData.put("address", address);
        employeeData.put("phone", phone);
        employeeData.put("Salary", salary);
        employeeData.put("role", role);

        if (callback != null) {
            callback.accept(employeeData);
        }

        CNICField.setText("");
        EmailField.setText("");
        NameField.setText("");
        AddressField.setText("");
        PhoneField.setText("");
        SalarySpinner.setValue(10000.0);
    }

}
