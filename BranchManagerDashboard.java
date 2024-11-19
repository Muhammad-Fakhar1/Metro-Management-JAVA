package com.metro;

import java.awt.ScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class BranchManagerDashboard extends BaseFrame {

    public BranchManagerDashboard() {
        super("Branch Manager", 0.80, 0.75);
    }

    @Override
    protected void setSidebar() {
        sidebar.addButton("Dashboard", new ImageIcon("images/home.png"), e -> System.out.println("home clicked"));
        sidebar.addButton("Reports", new ImageIcon("images/users.png"), e -> System.out.println("vendors clicked"));
        sidebar.addButton("Products", new ImageIcon("images/box.png"), e -> System.out.println("products clicked"));
        sidebar.addButton("Data Entriers", new ImageIcon("images/box.png"), e -> System.out.println("products clicked"));
        sidebar.addButton("Cashiers", new ImageIcon("images/box.png"), e -> System.out.println("products clicked"));
        sidebar.addButton("Settings", new ImageIcon("images/settings.png"), e -> System.out.println("settings clicked"));
    }
}