package com.metro.Cashier;

import com.formdev.flatlaf.ui.FlatLineBorder;
import com.metro.Controller;
import com.metro.Components.RoundedPanel;
import com.metro.Forms.ProductOrderForm;
import com.metro.ImageProcessor;
import com.metro.Models.Order;
import com.metro.Models.Product;
import com.metro.ThemeManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class WorkstationUI extends CashierBody {

    private Order order;
    private Controller controller;
    private DefaultTableModel tableModel;
    private JTable table;
    private TableRowSorter<DefaultTableModel> rowSorter;
    private final int height;
    private final int width;
    private float totalPrice;
    private ProductOrderForm orderForm;
    private int empID;

    public WorkstationUI(int empID, int height, int width) {
        this.empID = empID;
        this.totalPrice = 0;
        this.height = height;
        this.width = width;
        this.order = new Order();

        try {
            controller = Controller.getInstance();
        } catch (IOException ex) {
            Logger.getLogger(WorkstationUI.class.getName()).log(Level.SEVERE, null, ex);
        }

        setLayout(new BorderLayout());
        setBackground(ThemeManager.getBodyBackgroundColor());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        add(createHeader(), BorderLayout.NORTH);
        add(createTable(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    public JPanel createHeader() {
        JLabel label = new JLabel("Workstation");
        label.setForeground(Color.GRAY);
        label.setFont(new Font("Poppins", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.LEFT);

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(0, 0, 15, 0));
        panel.setLayout(new BorderLayout());
        panel.setBackground(ThemeManager.getBodyBackgroundColor());

        panel.add(label, BorderLayout.WEST);
        panel.add(createFilterButtons(), BorderLayout.EAST);

        return panel;
    }

    public JPanel createFilterButtons() {
        RoundedPanel roundedPanel = new RoundedPanel(10);

        roundedPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        roundedPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        roundedPanel.setBackground(ThemeManager.getBodyBackgroundColor());

        JLabel label = new JLabel("Filters: ");
        label.setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));
        label.setForeground(Color.gray);

        JButton allButton = new JButton("All");
        JButton dataEntryButton = new JButton("Carton");
        JButton cashierButton = new JButton("Unit");

        allButton.setFont(ThemeManager.getPoppinsFont(10, Font.PLAIN));
        dataEntryButton.setFont(ThemeManager.getPoppinsFont(10, Font.PLAIN));
        cashierButton.setFont(ThemeManager.getPoppinsFont(10, Font.PLAIN));

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
        String[] columnNames = {"", "ID", "Name", "Quantity", "Price", "Type", "Action"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);

        rowSorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(rowSorter);

        styleTable();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(0, height - 150));
        scrollPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        return scrollPane;
    }

    private void styleTable() {
        table.setRowHeight(30);
        table.setBorder(new FlatLineBorder(new Insets(0, 0, 0, 0), Color.white, 0, 8));
        table.setFont(new Font("Poppins", Font.PLAIN, 12));
        table.getTableHeader().setFont(new Font("Poppins", Font.BOLD, 10));
        table.getTableHeader().setForeground(Color.lightGray);
        table.getTableHeader().setReorderingAllowed(true);
        table.setSelectionBackground(null);
        table.setSelectionForeground(Color.black);
        table.setFocusable(false);
        table.setEnabled(false);

        table.getColumnModel().getColumn(0).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (column == 0) {
                    JLabel label = new JLabel();
                    label.setIcon(ImageProcessor.resizeIcon((ImageIcon) value, 15, 15));
                    label.setHorizontalAlignment(JLabel.CENTER);
                    return label;
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

        table.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (column == 6) {
                    JLabel label = new JLabel();
                    label.setIcon(ImageProcessor.resizeIcon(new ImageIcon("images/delete.png"), 15, 15));
                    label.setHorizontalAlignment(JLabel.CENTER);
                    return label;
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        });

        table.getColumnModel().getColumn(0).setPreferredWidth(50); // First column (icon column)
        table.getColumnModel().getColumn(1).setPreferredWidth(100); // Name column
        table.getColumnModel().getColumn(2).setPreferredWidth(150); // Address column
        table.getColumnModel().getColumn(3).setPreferredWidth(100); // Salary column
        table.getColumnModel().getColumn(4).setPreferredWidth(100); // Role column
        table.getColumnModel().getColumn(5).setPreferredWidth(100); // Role column
        table.getColumnModel().getColumn(6).setPreferredWidth(100); // Role column
    }

    private void addItemInTable() {
        //logic to add item in table
    }

    private void applyFilter(String role) {
        if (role.equals("All")) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter(role, 4));
        }
    }

    private JPanel createBottomPanel() {
        JPanel bottom = new JPanel();
        bottom.setLayout(new BorderLayout());
        bottom.setBorder(new EmptyBorder(10, 10, 10, 10));
        bottom.setBackground(Color.white);

        JLabel totalPriceLabel = new JLabel("Total Price: " + Float.toString(this.totalPrice));
        totalPriceLabel.setFont(ThemeManager.getPoppinsFont(14, Font.BOLD));

        JButton addProductButton = new JButton("Add Product");
        JButton checkoutButton = new JButton("Checkout");

        checkoutButton.addActionListener(e -> {
            try {
                if (totalPrice != 0) {
                    Order checkedOut = controller.checkout(order, empID);
                    if (checkedOut != null) {
                        System.out.println("Checked out:"+checkedOut.getTotalPrice());
                        tableModel.setRowCount(0);
                        totalPriceLabel.setText("Total Price: 0.0");
                        order = null;
                        order = new Order();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to check out");
                    }
                }

            } catch (IOException ex) {
                Logger.getLogger(WorkstationUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        addProductButton.addActionListener(e -> {
            if (orderForm == null) {
                orderForm = new ProductOrderForm(data -> {
                    String productID = (String) data.get("productID");
                    int quantity = (int) data.get("quantity");
                    String type = (String) data.get("type");
                    String space = "          ";

                    try {
                        Order updatedOrder = controller.addProductToOrder(order, productID, quantity, type);

                        if (updatedOrder != null) {
                            this.order = updatedOrder;
                            System.out.println(order.getTotalPrice());
                            totalPrice = (float) order.getTotalPrice();
                            totalPriceLabel.setText("Total Price: " + Float.toString(totalPrice));

                            ImageIcon icon = new ImageIcon("images/box.png");
                            Product p = order.getItems().getLast().getProduct();
                            float cprice = p.getCartonPrice();
                            float uprice = p.getUnitPrice();
                            float finalprice = uprice * quantity;
                            if ("Carton".equals(type)) {
                                finalprice = cprice * quantity;
                            }

                            tableModel.addRow(new Object[]{icon, space + productID, space + p.getTitle(), space + quantity, space + "Rs. " + finalprice, space + type});
                        } else {
                            JOptionPane.showMessageDialog(null, "Product not available.");
                        }

                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Product not available.");
                    }

                });
                orderForm.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        orderForm = null;
                    }
                });
            } else {
                orderForm.toFront();
                orderForm.requestFocus();
            }
        });

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        sidePanel.setBackground(Color.white);

        sidePanel.add(addProductButton);
        sidePanel.add(checkoutButton);

        bottom.add(sidePanel, BorderLayout.EAST);
        bottom.add(totalPriceLabel, BorderLayout.WEST);

        return bottom;
    }
}
