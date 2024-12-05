package com.metro.Forms;

import com.formdev.flatlaf.ui.FlatLineBorder;
import com.metro.ImageProcessor;
import com.metro.Models.Role;
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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

public class EmployeeRegisterForm extends JFrame {

    private final JTextField CNICField;
    private final JTextField EmailField;
    private final JTextField NameField;
    private final JTextField AddressField;
    private final JTextField PhoneField;
    private final JSpinner SalarySpinner;
    private JRadioButton cashierRadio;
    private JRadioButton dataEntryRadio;
    private final Consumer<Map<String, Object>> callback;

    public EmployeeRegisterForm(Consumer<Map<String, Object>> callback, int width, int height) {
        this.callback = callback;
        setSize(width, height);
        setFont(ThemeManager.getPoppinsFont(10, Font.PLAIN));
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getRootPane().putClientProperty("JRootPane.titleBarBackground", Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 25, 25, 25));
        mainPanel.setBackground(Color.white);

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

        mainPanel.add(createFormField("Salary ", SalarySpinner = new JSpinner(new SpinnerNumberModel(10000, 1, Integer.MAX_VALUE, 10)), "cash"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(createRadioButtonGroup());
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel panel = new JPanel();
        JButton submitButton = new JButton("Register Employee");
        submitButton.setFont(ThemeManager.getPoppinsFont(12, Font.BOLD));
        submitButton.setBackground(ThemeManager.getButtonColor());
        submitButton.setForeground(ThemeManager.getButtonForeground());
        submitButton.addActionListener(e -> registerEmployee());
        panel.setLayout(new BorderLayout());
        panel.add(submitButton, BorderLayout.CENTER);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(panel);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createFormField(String label, JComponent field, String icon) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBackground(Color.white);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.X_AXIS));
        labelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        labelPanel.setBackground(Color.white);

        JLabel iconLabel = new JLabel(ImageProcessor.resizeIcon(new ImageIcon("images/" + icon + ".png"), 13, 13));
        iconLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        iconLabel.setBorder(new EmptyBorder(0, 0, 5, 10));
        labelPanel.add(iconLabel);

        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
        fieldLabel.setFont(ThemeManager.getPoppinsFont(11, Font.PLAIN));
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

    private JPanel createRadioButtonGroup() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.setBackground(Color.white);

        JLabel typeLabel = new JLabel("Role");
        typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        typeLabel.setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));

        cashierRadio = new JRadioButton("Cashier");
        cashierRadio.setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));
        cashierRadio.setBackground(Color.white);

        dataEntryRadio = new JRadioButton("Data Entry");
        dataEntryRadio.setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));
        dataEntryRadio.setBackground(Color.white);

        ButtonGroup group = new ButtonGroup();
        group.add(cashierRadio);
        group.add(dataEntryRadio);

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.X_AXIS));
        radioPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        radioPanel.setBackground(Color.white);
        radioPanel.add(cashierRadio);
        radioPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        radioPanel.add(dataEntryRadio);

        panel.add(typeLabel);
        panel.add(Box.createHorizontalGlue());
        panel.add(radioPanel);

        return panel;
    }

    private void registerEmployee() {
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

        if (PhoneField.getText().trim().isEmpty()) {
            showError("Please enter the Phone number.");
            return;
        }

        String cnic = CNICField.getText().trim();
        String email = EmailField.getText().trim();
        String name = NameField.getText().trim();
        String address = AddressField.getText().trim();
        String phone = PhoneField.getText().trim();
        int Salary = (Integer) SalarySpinner.getValue();
        Role role = cashierRadio.isSelected() ? Role.CASHIER : Role.DATA_ENTRY;

        Map<String, Object> employeeData = new HashMap<>();
        employeeData.put("cnic", cnic);
        employeeData.put("emial", email);
        employeeData.put("name", name);
        employeeData.put("address", address);
        employeeData.put("phone", phone);
        employeeData.put("Salary", Salary);
        employeeData.put("role", role);

        if (callback != null) {
            callback.accept(employeeData);
        }

        CNICField.setText("");
        EmailField.setText("");
        NameField.setText("");
        AddressField.setText("");
        PhoneField.setText("");
        SalarySpinner.setValue(10000);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Validation Error", JOptionPane.ERROR_MESSAGE);
    }
}
