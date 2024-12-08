package com.metro.BranchManager;

import com.metro.Sections.VendorsUI;
import com.metro.Sections.ProductUI;
import com.metro.Components.BaseFrame;
import com.metro.Components.Body;
import com.metro.Controller;
import com.metro.Models.Branch;
import com.metro.Models.Employee;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

public class BranchManagerDashboard extends BaseFrame {

    private Body bmd;
    private Employee e;
    private int branchcode;
    private Branch b;
    private Controller controller;

    public BranchManagerDashboard(Employee e,Branch b) {
        super("Branch Manager", e, b.getName(), 0.80, 0.75);
        this.e = e;
        this.b=b;
        branchcode=b.getBranchId();
        bmd = new DashboardUI(branchcode,body.getWidth(), body.getHeight());
        updateBody(bmd, false);

        if ("123456".equals(e.getPassword())) {
            showPasswordChangeDialog(e);
        }
    }

    @Override
    protected void setSidebar() {
        sidebar.addButton("Dashboard", new ImageIcon("images/home.png"), e -> updateBody(new DashboardUI(branchcode,body.getWidth(), body.getHeight()), false));
        sidebar.addButton("Employee", new ImageIcon("images/users.png"), e -> {
            updateBody(new EmployeeUI(branchcode, body.getWidth(), body.getHeight()), false);
        });
        sidebar.addButton("Reports", new ImageIcon("images/file.png"), e -> {
            updateBody(new ReportsUI(branchcode, body.getWidth(), body.getHeight()), false);
        });
        sidebar.addButton("Products", new ImageIcon("images/box.png"), e -> updateBody(new ProductUI(branchcode, body.getWidth(), body.getHeight(), false), true));
        sidebar.addButton("Vendors", new ImageIcon("images/users.png"), e -> updateBody(new VendorsUI(e.getID(), branchcode, (int) body.getWidth(), (int) 350, false), true));
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
