package com.metro.BranchManager;

import com.formdev.flatlaf.ui.FlatDropShadowBorder;
import com.metro.Sections.VendorsUI;
import com.metro.Sections.ProductUI;
import com.metro.Components.BaseFrame;
import com.metro.Components.Body;
import com.metro.Forms.EmployeeRegisterForm;
import com.metro.Models.Employee;
import com.metro.ThemeManager;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class BranchManagerDashboard extends BaseFrame {

    private Body bmd;
    private Employee e;
    private int branchcode;

    public BranchManagerDashboard(Employee e) {
        super("Branch Manager", "Muhammad Fakhar bin Rashid", "Lahore", 0.80, 0.75);
        bmd = new DashboardUI(body.getWidth(), body.getHeight());
        this.e=e;
        this.branchcode=e.getBranchCode();
        updateBody(bmd, false);
    }

    @Override
    protected void setSidebar() {
        sidebar.addButton("Dashboard", new ImageIcon("images/home.png"), e -> updateBody(new DashboardUI(body.getWidth(), body.getHeight()), false));
        sidebar.addButton("Employee", new ImageIcon("images/users.png"), e -> {
            updateBody(new EmployeeUI(branchcode,body.getWidth(), body.getHeight()), false);
        });
        sidebar.addButton("Products", new ImageIcon("images/box.png"), e -> updateBody(new ProductUI(branchcode,body.getWidth(), body.getHeight(), false), true));
        sidebar.addButton("Vendors", new ImageIcon("images/users.png"), e -> updateBody(new VendorsUI(e.getID(),branchcode,(int) body.getWidth(), (int) 350, false), true));
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
