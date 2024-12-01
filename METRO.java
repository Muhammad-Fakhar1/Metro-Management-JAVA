package com.metro;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.metro.BranchManager.BranchManagerDashboard;
import com.metro.Cashier.CashierDashboard;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class METRO {

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatMacLightLaf());

        new BranchManagerDashboard();
        //new SuperAdminDashboard();
        //Employee superAdmin = new Employee("E001", "Muhammad Fakhar Bin Rashid", "123 Main Boulevard", "0333-1110001", "B001", 80000, Role.SUPER_ADMIN);
        //new SuperAdminHome(superAdmin);
        //new CashierDashboard();

    }
}
