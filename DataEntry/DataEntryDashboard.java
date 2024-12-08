package com.metro.DataEntry;

import com.metro.Components.BaseFrame;
import com.metro.Components.Body;
import com.metro.Models.Employee;
import com.metro.Sections.CategoryUI;
import com.metro.Sections.ProductUI;
import com.metro.Sections.VendorsUI;
import javax.swing.ImageIcon;

public class DataEntryDashboard extends BaseFrame {

    private Body deb;
    private Employee e;
    private int branchCode;

    public DataEntryDashboard(Employee e) {
        super("Data Entry", "Muhammad Fakhar bin Rashid", "Lahore", 0.80, 0.75);
        deb = new DashboardUI(body.getWidth(), body.getHeight());
        this.branchCode = e.getBranchCode();
        updateBody(deb);

        if ("123456".equals(e.getPassword())) {
            showPasswordChangeDialog(e);
        }
    }

    @Override
    protected void setSidebar() {
        sidebar.addButton("Dashboard", new ImageIcon("images/home.png"), e
                -> updateBody(new DashboardUI(body.getWidth(), body.getHeight())));

        sidebar.addButton("Vendors", new ImageIcon("images/users.png"), e
                -> updateBody(new VendorsUI(e.getID(), branchCode, body.getWidth(), 400, true)));

        sidebar.addButton("Products", new ImageIcon("images/box.png"), e -> updateBody(new ProductUI(branchCode, body.getWidth(), body.getHeight(), false)));

        sidebar.addButton("Categories", new ImageIcon("images/category.png"), e
                -> updateBody(new CategoryUI(branchCode, body.getWidth(), body.getHeight(), true)));

    }

    private void updateBody(Body newPanel) {
        deb = newPanel;
        body.add(deb);
        body.setViewportView(deb);
        body.repaint();
        body.revalidate();
    }
}
