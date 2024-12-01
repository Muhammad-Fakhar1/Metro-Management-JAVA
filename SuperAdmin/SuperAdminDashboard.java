package com.metro.SuperAdmin;

import com.metro.Components.BaseFrame;
import com.metro.Components.Body;
import com.metro.Models.Branch;
import com.metro.BranchManager.DashboardUI;
import com.metro.BranchManager.EmployeeUI;
import com.metro.Sections.ProductUI;
import com.metro.Sections.VendorsUI;
import com.metro.Models.Employee;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

public class SuperAdminDashboard extends BaseFrame {

    private Employee superAdmin;
    private Branch branch;
    private Body sab;

    public SuperAdminDashboard(Employee superAdmin, Branch branch) {
        super("Super Admin", superAdmin.getName(), branch.getName(), 0.80, 0.75);

        this.branch = branch;
        this.superAdmin = superAdmin;
        sab = new DashboardUI(body.getWidth(), body.getHeight());
        updateBody(sab, false);
    }

    @Override
    protected void setSidebar() {
        sidebar.addButton("Dashboard", new ImageIcon("images/home.png"), e -> updateBody(new DashboardUI(body.getWidth(), body.getHeight()), false));
        sidebar.addButton("Employees", new ImageIcon("images/users.png"), e -> updateBody(new EmployeeUI(body.getWidth(), body.getHeight()), false));
        sidebar.addButton("Products", new ImageIcon("images/box.png"), e -> updateBody(new ProductUI(body.getWidth(), body.getHeight()), true));
        sidebar.addButton("Vendors", new ImageIcon("images/users.png"), e -> updateBody(new VendorsUI(body.getWidth(), body.getHeight()), true));
        //sidebar.addButton("Settings", new ImageIcon("images/settings.png"), e -> System.out.println("settings clicked"));
    }

    private void updateBody(Body newPanel, boolean scrollable) {
        sab = newPanel;
        body.add(sab);
        if (scrollable) {
            body.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        } else {
            body.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        }
        body.setViewportView(sab);
        body.repaint();
        body.revalidate();
    }
}
