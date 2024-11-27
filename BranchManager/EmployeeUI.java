package BranchManager;

import com.metro.Controller;
import com.metro.RoundedPanel;
import com.metro.ThemeManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class EmployeeUI extends branchManagerBody {

    private Controller controller;
    private DefaultTableModel tableModel;
    private JTable table;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private int height;
    private int width;

    public EmployeeUI(int height, int width) {
        this.height = height;
        this.width = width;

        controller = Controller.getInstance();

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 20, 0, 20));

        add(createHeader(), BorderLayout.NORTH);
        add(createTable(), BorderLayout.CENTER);
    }

    public JPanel createHeader() {
        JLabel label = new JLabel("Employees");
        label.setForeground(Color.GRAY);
        label.setFont(new Font("Poppins", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(0, 0, 15, 0));
        panel.setLayout(new BorderLayout());

        panel.add(label, BorderLayout.WEST);
        panel.add(createFilterButtons(), BorderLayout.EAST);

        return panel;
    }

    public JPanel createFilterButtons() {
        RoundedPanel roundedPanel = new RoundedPanel(10);

        roundedPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        roundedPanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        JLabel label = new JLabel("Filters: ");
        label.setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));
        label.setForeground(Color.gray);

        JButton allButton = new JButton("All");
        JButton dataEntryButton = new JButton("Data Entry");
        JButton cashierButton = new JButton("Cashier");

        allButton.setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));
        dataEntryButton.setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));
        cashierButton.setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));

        allButton.addActionListener(e -> applyFilter("All"));
        dataEntryButton.addActionListener(e -> applyFilter("Data Entry"));
        cashierButton.addActionListener(e -> applyFilter("Cashier"));

        roundedPanel.add(label);
        roundedPanel.add(allButton);
        roundedPanel.add(dataEntryButton);
        roundedPanel.add(cashierButton);

        return roundedPanel;
    }

    public JScrollPane createTable() {
        String[] columnNames = {"Name", "Address", "Salary", "Role"};
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
        table.setBorder(new EmptyBorder(0, 0, 0, 0));
        table.setFont(new Font("Poppins", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 10));
        table.getTableHeader().setForeground(Color.lightGray);
        table.getTableHeader().setReorderingAllowed(true);
        table.setSelectionBackground(null);
        table.setSelectionForeground(Color.black);
        table.setFocusable(false);
        table.setEnabled(false);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
    }

    private void populateTable() {
        String[][] employees = controller.getEmployees();
        for (String[] employee : employees) {
            tableModel.addRow(employee);
        }
    }

    private void applyFilter(String role) {
        if (role.equals("All")) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter(role, 3));
        }
    }
}
