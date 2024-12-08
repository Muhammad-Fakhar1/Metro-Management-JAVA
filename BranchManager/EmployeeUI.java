package com.metro.BranchManager;

import com.formdev.flatlaf.ui.FlatButtonBorder;
import com.formdev.flatlaf.ui.FlatLineBorder;
import com.metro.Controller;
import com.metro.Forms.EmployeeRegisterForm;
import com.metro.Forms.ProductOrderForm;
import com.metro.ImageProcessor;
import com.metro.Models.Employee;
import com.metro.Models.Role;
import com.metro.Sections.BranchCard;
import com.metro.ThemeManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class EmployeeUI extends branchManagerBody {

    private EmployeeRegisterForm employeeForm;
    private Controller controller;
    private DefaultTableModel tableModel;
    private JTable table;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private final int height;
    private final int width;
    private int branchCode;

    public EmployeeUI(int branchCode, int height, int width) {
        this.height = height;
        this.width = width;
        this.branchCode = branchCode;

        try {
            controller = Controller.getInstance();
        } catch (IOException ex) {
            Logger.getLogger(EmployeeUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        setLayout(new BorderLayout());
        setBackground(ThemeManager.getBodyBackgroundColor());
        setBorder(new EmptyBorder(0, 15, 0, 15));

        add(createHeader(), BorderLayout.NORTH);
        add(createTable(), BorderLayout.CENTER);
    }

    public JPanel createHeader() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(15, 0, 15, 0));
        panel.setBackground(ThemeManager.getBodyBackgroundColor());

        JLabel label = new JLabel("Employees");
        label.setForeground(Color.GRAY);
        label.setFont(new Font("Poppins", Font.BOLD, 20));
        panel.add(label, BorderLayout.WEST);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        rightPanel.setBackground(ThemeManager.getBodyBackgroundColor());

        rightPanel.add(createFilterCombobox());

        rightPanel.add(addEmployeeButton());

        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    private JButton addEmployeeButton() {
        JButton btn = new JButton("+ New Employee");
        btn.setFont(ThemeManager.getPoppinsFont(11, Font.BOLD));
        btn.setForeground(Color.gray);
        btn.setFocusPainted(false);
        btn.setForeground(ThemeManager.getButtonForeground());
        btn.setBackground(ThemeManager.getButtonColor());
        btn.setBorder(new FlatButtonBorder());

        btn.addActionListener(e -> {
            if (employeeForm == null) {
                employeeForm = new EmployeeRegisterForm(data -> {
                    String cnic = (String) data.get("cnic");
                    String email = (String) data.get("email");
                    String name = (String) data.get("name");
                    String address = (String) data.get("address");
                    String phone = (String) data.get("phone");
                    float salary = (float) data.get("Salary");
                    Role role = (Role) data.get("role");

                    System.out.println(role);
                    Employee emp = new Employee(name, email, cnic, address, phone, branchCode, salary, role, true);
                    System.out.println(e);
                    try {
                        if (controller.addEmployee(emp) == true) {
                            employeeForm.dispose();
                            String space="          ";
                            tableModel.addRow(new Object[]{space+cnic, space+name,space+ phone,space+ address,space+ salary,space+ role});

                        }
                    } catch (IOException ex) {
                        Logger.getLogger(BranchCard.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }, 450, 550, true);
                employeeForm.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        employeeForm = null;
                    }
                });
            } else {
                employeeForm.toFront();
                employeeForm.requestFocus();
            }
        });
        return btn;
    }

    public JPanel createFilterCombobox() {
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        filterPanel.setBackground(ThemeManager.getBodyBackgroundColor());

        JLabel filterLabel = new JLabel("Filters:");
        filterLabel.setForeground(Color.GRAY);
        filterPanel.add(filterLabel);

        String[] filterOptions = {"All", "Data Entry", "Cashier"};
        JComboBox<String> filterComboBox = new JComboBox<>(filterOptions);
        filterComboBox.setPreferredSize(new Dimension(120, 28));
        filterComboBox.setFocusable(false);
        filterComboBox.setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));

        filterComboBox.addActionListener(e -> {
            String selectedOption = (String) filterComboBox.getSelectedItem();
            switch (selectedOption) {
                case "All" ->
                    applyFilter("All");
                case "Data Entry" ->
                    applyFilter("DATA_ENTRY");
                case "Cashier" ->
                    applyFilter("CASHIER");
            }
        });

        filterPanel.add(filterComboBox);
        return filterPanel;
    }

    public JScrollPane createTable() {
        String[] columnNames = {"CNIC", "Name", "Phone #", "Address", "Salary", "Role"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        rowSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(rowSorter);

        styleTable();
        populateTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        return scrollPane;
    }

    private void styleTable() {
        table.setRowHeight(40);
        table.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), Color.white, 0, 8));
        table.setFont(new Font("Poppins", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 10));
        table.getTableHeader().setForeground(Color.lightGray);
        table.getTableHeader().setReorderingAllowed(true);
        table.setSelectionBackground(null);
        table.setSelectionForeground(Color.black);
        table.setFocusable(false);
        table.setEnabled(false);

        table.getColumnModel().getColumn(0).setPreferredWidth(100); // Name column
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // Address column
        table.getColumnModel().getColumn(2).setPreferredWidth(100); // Salary column
        table.getColumnModel().getColumn(3).setPreferredWidth(100); // Salary column
        table.getColumnModel().getColumn(4).setPreferredWidth(90); // Role column
        table.getColumnModel().getColumn(5).setPreferredWidth(90); // Role column
    }

    private void populateTable() {
        String[][] employees = null;
        try {
            employees = controller.getEmployees(branchCode);
        } catch (IOException ex) {
            Logger.getLogger(EmployeeUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (String[] employee : employees) {
            for (int i = 0; i < employee.length; i++) {
                employee[i] = "          " + employee[i];
            }
            tableModel.addRow(employee);
        }
    }

    private void applyFilter(String role) {
        if (role.equals("All")) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter(role, 5));
        }
    }
}
