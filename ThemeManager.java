package com.metro;

import java.awt.Color;

public class ThemeManager {
    private static final Color sidebarBackgroundColor = Color.WHITE;
    private static final Color headerBackgroundColor = Color.WHITE;

    public static Color getSidebarBackgroundColor() {
        return sidebarBackgroundColor;
    }

    public static Color getHeaderBackgroundColor() {
        return headerBackgroundColor;
    }
}
