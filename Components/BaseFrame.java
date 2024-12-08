package com.metro.Components;

import com.metro.Models.Employee;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;

public abstract class BaseFrame extends JFrame {

    protected Sidebar sidebar;
    protected Header header;
    protected JScrollPane body;

    private final double WIDTH_RATIO;
    private final double HEIGHT_RATIO;
    private Dimension screenSize;

    public BaseFrame(String title, String username, String branchName, double WIDTH_RATIO, double HEIGHT_RATIO) {
        super(title);
        this.WIDTH_RATIO = WIDTH_RATIO;
        this.HEIGHT_RATIO = HEIGHT_RATIO;

        setupFrame();
        sidebar = new Sidebar(getWidth(), getHeight());
        header = new Header(username, branchName, getWidth(), getHeight());
        body = new JScrollPane();

        setSidebar();
        setBody();

        add(sidebar, BorderLayout.WEST);
        add(body, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);
        setVisible(true);
    }

    private void setupFrame() {
        setMinimumSize(getScaledDimension());
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.white);
        //setResizable(false);
        setLocationRelativeTo(null);
        getRootPane().putClientProperty("JRootPane.titleBarBackground", Color.white);//to change the title bar color
    }

    private Dimension getScaledDimension() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * WIDTH_RATIO);
        int height = (int) (screenSize.height * HEIGHT_RATIO);
        return new Dimension(width, height);
    }

    private void setBody() {
        body.setBorder(null);
        body.getVerticalScrollBar().setUnitIncrement(16);
    }

    protected abstract void setSidebar();

    protected void showPasswordChangeDialog(Employee e) {
        disableSidebarButtons();

        JPasswordField newPasswordField = new JPasswordField(20);
        int option = JOptionPane.showConfirmDialog(this, newPasswordField, "Change Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION && newPasswordField.getPassword().length > 0 && !Arrays.equals(newPasswordField.getPassword(), "123456".toCharArray())) {
            e.setPassword(new String(newPasswordField.getPassword()));
            enableSidebarButtons();
        } else {
            JOptionPane.showMessageDialog(null, "Choose a password other than '123456'", "Invalid Password", JOptionPane.WARNING_MESSAGE);
            showPasswordChangeDialog(e);
        }
    }

    protected void disableSidebarButtons() {
        sidebar.setEnabled(false);

    }

    protected void enableSidebarButtons() {
        sidebar.setEnabled(true);
    }
}
