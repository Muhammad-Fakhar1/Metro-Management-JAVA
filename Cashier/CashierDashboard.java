package com.metro.Cashier;

import com.metro.Components.BaseFrame;
import javax.swing.ImageIcon;

public class CashierDashboard extends BaseFrame {

    public CashierDashboard() {
        super("Cashier","Muhammad Fakhar bin Rashid","Lahore", 0.80, 0.75);
    }

    @Override
    protected void setSidebar() {
        sidebar.addButton("Dashboard", new ImageIcon("images/home.png"), e -> System.out.println("home clicked"));
        sidebar.addButton("Reports", new ImageIcon("images/users.png"), e -> System.out.println("vendors clicked"));
        sidebar.addButton("Products", new ImageIcon("images/box.png"), e -> System.out.println("products clicked"));
        sidebar.addButton("Settings", new ImageIcon("images/settings.png"), e -> System.out.println("settings clicked"));
    }
}
