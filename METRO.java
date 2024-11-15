package com.metro;

import javax.swing.SwingUtilities;

public class METRO {

//        panelWithShadow.setBorder(new FlatDropShadowBorder(Color.BLACK, 20, 0.1f));
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DataEntryDashboard();
        });
    }
}
