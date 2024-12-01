package com.metro.DataEntry;

import com.metro.Components.BaseFrame;
import javax.swing.ImageIcon;

public class DataEntryDashboard extends BaseFrame {

    private DataEntryBody deb;

    public DataEntryDashboard() {
        super("Data Entry","Muhammad Fakhar bin Rashid","Lahore", 0.80, 0.75);
        deb = new DashboardUI(body.getWidth(), body.getHeight());
        updateBody(deb);
    }

    @Override
    protected void setSidebar() {
        sidebar.addButton("Dashboard", new ImageIcon("images/home.png"), e
                -> updateBody(new DashboardUI(body.getWidth(), body.getHeight())));

        sidebar.addButton("Vendors", new ImageIcon("images/users.png"), e
                -> updateBody(new VendorsUI(body.getWidth(), body.getHeight())));

        sidebar.addButton("Products", new ImageIcon("images/box.png"), e
                -> System.out.println("Products clicked"));

        sidebar.addButton("Categories", new ImageIcon("images/category.png"), e
                -> updateBody(new CategoriesUI(body.getWidth(), body.getHeight())));

        sidebar.addButton("Settings", new ImageIcon("images/settings.png"), e
                -> System.out.println("Settings clicked"));
    }

    private void updateBody(DataEntryBody newPanel) {
        deb = newPanel;
        body.add(deb);
        body.setViewportView(deb);
        body.repaint();
        body.revalidate();
    }
}
