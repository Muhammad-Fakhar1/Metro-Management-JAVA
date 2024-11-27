package com.metro;

import BranchManager.BranchManagerDashboard;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import com.metro.DataEntry.DataEntryDashboard;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class METRO {
    public static void main(String[] args) throws UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(new FlatMacLightLaf());
        //new DataEntryDashboard();
        new BranchManagerDashboard();
    }
}
