package com.metro.Sections;

import com.metro.Components.Body;
import com.metro.Forms.VendorRegisterForm;
import com.metro.Models.Vendor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class VendorsUI extends Body {

    private ArrayList<Vendor> vendors;
    private final ArrayList<ImageIcon> icons;

    public VendorsUI(int width, int height, boolean showButton) {
        this.showButton = true;
        this.dashboardWidth = width;
        this.dashboardHeight = 400;
        this.title = "Vendors";
        icons = new ArrayList<>();
        vendors = new ArrayList<>();
        cards = new ArrayList<>();

        setupSearchField();
        loadVendors();
        setupUI();
    }

    private void loadVendors() {
        vendors = controller.getVendors();
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
            cards.add(new VendorCard(vendor, icons, showButton));
        }
    }

    protected void openForm() {
        if (form == null) {
            form = new VendorRegisterForm(data -> {
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
