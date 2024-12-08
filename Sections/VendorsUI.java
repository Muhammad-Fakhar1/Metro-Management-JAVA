package com.metro.Sections;

import com.metro.Components.Body;
import com.metro.Forms.VendorRegisterForm;
import com.metro.Models.Branch;
import com.metro.Models.Vendor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class VendorsUI extends Body {

    private ArrayList<Vendor> vendors;
    private final ArrayList<ImageIcon> icons;
    private int branchCode;
    private int empID;

    public VendorsUI(int empID,int branchCode, int width, int height, boolean showButton) {
        this.branchCode = branchCode;
        this.empID=empID;
        this.showButton = showButton;
        this.dashboardWidth = width;
        this.dashboardHeight = 400;
        this.title = "Vendors";
        icons = new ArrayList<>();
        vendors = new ArrayList<>();
        cards = new ArrayList<>();

        addButton = new JButton("+ Add Vendor");
        addButton.addActionListener(e -> {
            openVendorForm();
        });

        setupSearchField();
        loadVendors();
        setupUI();
    }

    private void loadVendors() {
        try {
            vendors = controller.getVendors(branchCode);
        } catch (IOException ex) {
            Logger.getLogger(VendorsUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadIcons();
        createVendorCards();
    }

    private void loadIcons() {
        String[] imagePaths = {"images/users.png", "images/cash.png", "images/mail.png"};
        for (String path : imagePaths) {
            icons.add(new ImageIcon(path));
        }
    }

    private void createVendorCards() {
        for (Vendor vendor : vendors) {
            cards.add(new VendorCard(empID,vendor, icons, showButton));
        }
    }

    private void openVendorForm() {
        if (form == null) {
            form = new VendorRegisterForm(vendorData -> {
                String name = (String) vendorData.get("name");
                String contactInfo = (String) vendorData.get("contactInfo");

                try {
                    if (controller.addVendor(name, contactInfo, branchCode)) {
                        Vendor v = new Vendor(name, contactInfo, 0, true, branchCode);
                        form.dispose();

                        cards.add(new VendorCard(empID,v, icons, showButton));
                        removeAll();
                        setupUI();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(VendorsUI.class.getName()).log(Level.SEVERE, null, ex);
                }

            }, 450, 450);
            form.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    form = null;
                }
            });
        } else {
            form.toFront();
            form.requestFocus();
        }
    }
}
