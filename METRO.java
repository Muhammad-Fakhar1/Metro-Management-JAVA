package com.metro;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.metro.BranchManager.BranchManagerDashboard;
import com.metro.Cashier.CashierDashboard;
import com.metro.DataEntry.DataEntryDashboard;
import com.metro.Forms.LoginForm;
import com.metro.Models.Employee;
import com.metro.Models.Role;
import com.metro.SuperAdmin.SuperAdminHome;
import java.awt.Color;
import java.io.IOException;
import java.net.Socket;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class METRO {

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatMacLightLaf());
        //Combobox
        UIManager.put("ComboBox.buttonBackground", Color.LIGHT_GRAY);
        UIManager.put("ComboBox.buttonHoverBackground", Color.GRAY);
        UIManager.put("ComboBox.popupBackground", Color.white);
        UIManager.put("ComboBox.buttonArrowColor", Color.WHITE);
        UIManager.put("ComboBox.selectionForeground", Color.white);
        UIManager.put("ComboBox.selectionBackground", ThemeManager.getButtonColor());
        UIManager.put("ComboBox.arrowType", "triangle");
        UIManager.put("CheckBox.icon.selectedBackground", ThemeManager.getButtonColor());

        FlatLaf.updateUI();
        
        try {
            Socket socket = new Socket("localhost", 12345);
            System.out.println("Connected to server.");
            Controller controller = Controller.getInstance();
            controller.setSocket(socket);
            controller.start();
            controller.closeConnections();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

        //new BranchManagerDashboard();
        //new SuperAdminDashboard();
//        Employee superAdmin = new Employee("E001", "", "", "Muhammad Fakhar Bin Rashid", "123 Main Boulevard", "0333-1110001", "B001", 80000, Role.SUPER_ADMIN);
//        new SuperAdminHome(superAdmin);
        //new CashierDashboard();
        //new DataEntryDashboard();

        //new LoginForm("", data->{}, 600, 450);
    }
}
