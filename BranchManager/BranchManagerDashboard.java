package com.metro.BranchManager;

import com.metro.Sections.VendorsUI;
import com.metro.Sections.ProductUI;
import com.metro.Components.BaseFrame;
import com.metro.Components.Body;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

public class BranchManagerDashboard extends BaseFrame {

    private Body bmd;

    public BranchManagerDashboard() {
        super("Branch Manager","Muhammad Fakhar bin Rashid","Lahore", 0.80, 0.75);
        bmd = new DashboardUI(body.getWidth(), body.getHeight());
        updateBody(bmd, false);
    }

    @Override
    protected void setSidebar() {
        sidebar.addButton("Dashboard", new ImageIcon("images/home.png"), e -> updateBody(new DashboardUI(body.getWidth(), body.getHeight()), false));
        sidebar.addButton("Employee", new ImageIcon("images/users.png"), e -> updateBody(new EmployeeUI(body.getWidth(), body.getHeight()), false));
        sidebar.addButton("Products", new ImageIcon("images/box.png"), e -> updateBody(new ProductUI(body.getWidth(), body.getHeight()), true));
        sidebar.addButton("Vendors", new ImageIcon("images/users.png"), e -> updateBody(new VendorsUI((int) body.getWidth(), (int) body.getHeight()), true));
        //sidebar.addButton("Settings", new ImageIcon("images/settings.png"), e -> System.out.println("settings clicked"));
    }

    private void updateBody(Body newPanel, boolean scrollable) {
        bmd = newPanel;
        body.add(bmd);
        if (scrollable) {
            body.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        } else {
            body.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        }
        body.setViewportView(bmd);
        body.repaint();
        body.revalidate();
    }
}
