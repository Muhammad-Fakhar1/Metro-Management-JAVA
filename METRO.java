package com.metro;

import com.formdev.flatlaf.themes.FlatMacLightLaf;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class METRO {
//  panelWithShadow.setBorder(new FlatDropShadowBorder(Color.BLACK, 20, 0.1f));

    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatMacLightLaf());
        new DataEntryDashboard();
    }
}
