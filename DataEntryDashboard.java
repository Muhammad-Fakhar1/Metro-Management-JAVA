package com.metro;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.ScrollPaneUI;

public class DataEntryDashboard extends BaseFrame {

    public DataEntryDashboard() {
        super("Data Entry", 0.80, 0.75);
    }

    @Override
    protected void setSidebar() {
        sidebar.addButton("Dashboard", new ImageIcon("images/home.png"), e -> System.out.println("home clicked"));
        sidebar.addButton("Vendors", new ImageIcon("images/users.png"), e -> System.out.println("vendors clicked"));
        sidebar.addButton("Products", new ImageIcon("images/box.png"), e -> System.out.println("products clicked"));
        sidebar.addButton("Categories", new ImageIcon("images/category.png"), e -> System.out.println("categories clicked"));
        sidebar.addButton("Settings", new ImageIcon("images/settings.png"), e -> System.out.println("settings clicked"));
    }
}
