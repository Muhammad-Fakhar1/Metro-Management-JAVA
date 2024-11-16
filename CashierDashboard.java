package com.metro;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class CashierDashboard extends BaseFrame {

    public CashierDashboard() {
        super("Cashier", 0.80, 0.75);
    }

    @Override
    protected void setSidebar() {
        sidebar.addButton("Dashboard", new ImageIcon("images/home.png"), e -> System.out.println("home clicked"));
        sidebar.addButton("Reports", new ImageIcon("images/users.png"), e -> System.out.println("vendors clicked"));
        sidebar.addButton("Products", new ImageIcon("images/box.png"), e -> System.out.println("products clicked"));
        sidebar.addButton("Settings", new ImageIcon("images/settings.png"), e -> System.out.println("settings clicked"));
    }

    @Override
    protected JPanel createBody() {
        return new JPanel();
    }

}
