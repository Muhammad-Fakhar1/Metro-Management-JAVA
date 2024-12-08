package com.metro.Cashier;

import com.metro.BranchManager.BranchManagerDashboard;
import com.metro.Components.BaseFrame;
import com.metro.Components.Body;
import com.metro.Controller;
import com.metro.Models.Branch;
import com.metro.Models.Employee;
import com.metro.Sections.ProductUI;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;

public class CashierDashboard extends BaseFrame {

    private Body cb;
    private Employee emp;
    private int branchCode;
    private Controller controller;
    private Branch b;

    public CashierDashboard(Employee e,Branch b) {
        super("Cashier", e.getName(), b.getName(), 0.80, 0.75);
        this.emp = e;
        this.b=b;
        cb = new WorkstationUI(emp.getEmployeeID(), body.getHeight(), body.getWidth());
        updateBody(cb, false);

        if ("123456".equals(e.getPassword())) {
            showPasswordChangeDialog(e);
        }
    }

    @Override
    protected void setSidebar() {
        sidebar.addButton("Workstation", new ImageIcon("images/home.png"), e -> updateBody(new WorkstationUI(emp.getEmployeeID(), body.getHeight(), body.getWidth()), false));
        //sidebar.addButton("Sales", new ImageIcon("images/users.png"), e -> System.out.println("vendors clicked"));
        sidebar.addButton("Products", new ImageIcon("images/box.png"), e -> updateBody(new ProductUI(branchCode, body.getWidth(), body.getHeight(), false), true));
    }

    private void updateBody(Body newPanel, boolean scrollable) {
        cb = newPanel;
        body.add(cb);
        if (scrollable) {
            body.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        } else {
            body.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        }
        body.setViewportView(cb);
        body.repaint();
        body.revalidate();
    }
}
