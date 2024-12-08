package com.metro.Forms;

import com.metro.BranchManager.BranchManagerDashboard;
import com.metro.Cashier.CashierDashboard;
import com.metro.Controller;
import com.metro.DataEntry.DataEntryDashboard;
import com.metro.ImageProcessor;
import com.metro.Models.Branch;
import com.metro.Models.Employee;
import com.metro.Models.Role;
import com.metro.SuperAdmin.SuperAdminHome;
import com.metro.ThemeManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

public class LoginForm extends Form {

    private JTextField NameField;
    private JPasswordField PasswordField;
    private JRadioButton cashierRadio;
    private JRadioButton dataEntryRadio;
    private JRadioButton branchManagerRadio;

    private Controller controller;

    public LoginForm(String title, Consumer<Map<String, Object>> callback, int width, int height) {
        super(title, callback, width, height);

        try {
            controller = Controller.getInstance();
        } catch (IOException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        JLabel headerLabel = new JLabel("METRO Management", JLabel.CENTER);
        headerLabel.setFont(ThemeManager.getPoppinsFont(16, Font.BOLD));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel infoLabel = new JLabel("Online inventory management system.\n",
                JLabel.CENTER);
        infoLabel.setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));
        infoLabel.setForeground(Color.gray);
        infoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(headerLabel);
        mainPanel.add(infoLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 65)));

        mainPanel.add(createFormField("Email Address ", NameField = new JTextField(), "user"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(createFormField("Password ", PasswordField = new JPasswordField(), "lock"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(Box.createRigidArea(new Dimension(0, 65)));

        mainPanel.add(createButton("Login", 12, Font.BOLD, ThemeManager.getButtonColor(), ThemeManager.getButtonForeground()));

        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        setLayout(new GridLayout(1, 2));
        mainPanel.setBorder(new EmptyBorder(20, 50, 20, 0));
        add(mainPanel);

        JLabel imageLabel = new JLabel(ImageProcessor.resizeIcon(new ImageIcon("images/image.png"), 250, 250));
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.white);
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(10, 10, 15, 0));
        bottomPanel.add(imageLabel, BorderLayout.SOUTH);

        getContentPane().setBackground(Color.white);
        add(bottomPanel);
        setVisible(true);
    }

    @Override
    protected void submitForm() {
        String email = NameField.getText();
        String password = new String(PasswordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            System.out.println("Please fill in all fields.");
            return;
        }

        Employee e = null;
        try {
            e = controller.login(email, password);
        } catch (IOException ex) {
            Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (e != null) {
            Branch b=controller.getABranch(e.getBranchCode());
           
            switch (e.getRole()) {
                case Role.CASHIER:
                    new CashierDashboard(e,b);
                    dispose();
                    break;
                case Role.DATA_ENTRY:
                    new DataEntryDashboard(e,b);
                    dispose();
                    break;
                case Role.BRANCH_MANAGER:
                    new BranchManagerDashboard(e,b);
                    dispose();
                    break;
                case Role.SUPER_ADMIN:
                    new SuperAdminHome(e);
                    dispose();
                    break;
                default:
                    System.out.println("Unknown role");
                    break;
            }
        } else {
            System.out.println("Invalid login credentials.");
        }

        NameField.setText("");
        PasswordField.setText("");
    }

}
