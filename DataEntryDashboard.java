package com.metro;

import javax.swing.ImageIcon;

public class DataEntryDashboard extends BaseFrame {

    public DataEntryDashboard() {
        super("Dashboard", 0.80, 0.75);
        init();
    }

    private void init() {
        setSidebar();
        setVisible(true);
    }

    private void setSidebar() {
        sidebar.addButton("Dashboard", new ImageIcon("images/home.png"), e -> System.out.println("Home clicked"));
        sidebar.addButton("Vendors", new ImageIcon("images/users.png"), e -> System.out.println("vendors clicked"));
        sidebar.addButton("Products", new ImageIcon("images/box.png"), e -> System.out.println("products clicked"));
        sidebar.addButton("Categories", new ImageIcon("images/category.png"), e -> System.out.println("Settings clicked"));
        sidebar.addButton("Settings", new ImageIcon("images/settings.png"), e -> System.out.println("Settings clicked"));

        SystemStatus bottomPanel = new SystemStatus();
        sidebar.addBottomPanel(bottomPanel);
    }
}
