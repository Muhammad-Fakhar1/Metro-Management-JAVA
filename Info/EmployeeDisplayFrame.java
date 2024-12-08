package com.metro.Info;

import com.metro.Models.Employee;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;

public class EmployeeDisplayFrame extends JFrame {

    public EmployeeDisplayFrame(Employee employee) {
        setTitle("Employee Details");
        setSize(400, 500);
        setLocationRelativeTo(null);
        Font poppinsFont = new Font("Poppins", Font.PLAIN, 14);

        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        getContentPane().setBackground(Color.white);
        getRootPane().putClientProperty("JRootPane.titleBarBackground", Color.white);

        panel.setBorder(new EmptyBorder(40, 50, 40, 50));
        panel.setLayout(new GridLayout(0, 2, 10, 5));

        // Center-align the panel
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelEmployeeID = new JLabel("Employee ID: ");
        labelEmployeeID.setFont(poppinsFont);
        JLabel labelName = new JLabel("Name: ");
        labelName.setFont(poppinsFont);
        JLabel labelEmail = new JLabel("Email: ");
        labelEmail.setFont(poppinsFont);
        JLabel labelCnic = new JLabel("CNIC: ");
        labelCnic.setFont(poppinsFont);
        JLabel labelPassword = new JLabel("Password: ");
        labelPassword.setFont(poppinsFont);
        JLabel labelAddress = new JLabel("Address: ");
        labelAddress.setFont(poppinsFont);
        JLabel labelPhoneNumber = new JLabel("Phone Number: ");
        labelPhoneNumber.setFont(poppinsFont);
        JLabel labelBranchCode = new JLabel("Branch Code: ");
        labelBranchCode.setFont(poppinsFont);
        JLabel labelSalary = new JLabel("Salary: ");
        labelSalary.setFont(poppinsFont);
        JLabel labelRole = new JLabel("Role: ");
        labelRole.setFont(poppinsFont);
        JLabel labelActive = new JLabel("Active: ");
        labelActive.setFont(poppinsFont);

        JTextField fieldEmployeeID = new JTextField(String.valueOf(employee.getEmployeeID()));
        fieldEmployeeID.setEditable(false);
        fieldEmployeeID.setFont(poppinsFont);

        JTextField fieldName = new JTextField(employee.getName());
        fieldName.setEditable(false);
        fieldName.setFont(poppinsFont);

        JTextField fieldEmail = new JTextField(employee.getEmail());
        fieldEmail.setEditable(false);
        fieldEmail.setFont(poppinsFont);

        JTextField fieldCnic = new JTextField(employee.getCnic());
        fieldCnic.setEditable(false);
        fieldCnic.setFont(poppinsFont);

        JTextField fieldPassword = new JTextField(employee.getPassword());
        fieldPassword.setEditable(false);
        fieldPassword.setFont(poppinsFont);

        JTextField fieldAddress = new JTextField(employee.getAddress());
        fieldAddress.setEditable(false);
        fieldAddress.setFont(poppinsFont);

        JTextField fieldPhoneNumber = new JTextField(employee.getPhoneNumber());
        fieldPhoneNumber.setEditable(false);
        fieldPhoneNumber.setFont(poppinsFont);

        JTextField fieldBranchCode = new JTextField(String.valueOf(employee.getBranchCode()));
        fieldBranchCode.setEditable(false);
        fieldBranchCode.setFont(poppinsFont);

        JTextField fieldSalary = new JTextField(String.valueOf(employee.getSalary()));
        fieldSalary.setEditable(false);
        fieldSalary.setFont(poppinsFont);

        JTextField fieldRole = new JTextField(employee.getRole().toString());
        fieldRole.setEditable(false);
        fieldRole.setFont(poppinsFont);

        JTextField fieldActive = new JTextField(String.valueOf(employee.isActive()));
        fieldActive.setEditable(false);
        fieldActive.setFont(poppinsFont);

        panel.add(labelEmployeeID);
        panel.add(fieldEmployeeID);
        panel.add(labelName);
        panel.add(fieldName);
        panel.add(labelEmail);
        panel.add(fieldEmail);
        panel.add(labelCnic);
        panel.add(fieldCnic);
        panel.add(labelPassword);
        panel.add(fieldPassword);
        panel.add(labelAddress);
        panel.add(fieldAddress);
        panel.add(labelPhoneNumber);
        panel.add(fieldPhoneNumber);
        panel.add(labelBranchCode);
        panel.add(fieldBranchCode);
        panel.add(labelSalary);
        panel.add(fieldSalary);
        panel.add(labelRole);
        panel.add(fieldRole);
        panel.add(labelActive);
        panel.add(fieldActive);

        add(panel);
        setVisible(true);
    }
}
