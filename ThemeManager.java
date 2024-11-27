package com.metro;

import java.awt.Color;
import java.awt.Font;

public class ThemeManager {

    private static final Color sidebarBackgroundColor = Color.WHITE;
    private static final Color headerBackgroundColor = Color.WHITE;
    private static final Color bodyBackgroundColor = new Color(242, 242, 242);

    public static Color getSidebarBackgroundColor() {
        return sidebarBackgroundColor;
    }

    public static Color getHeaderBackgroundColor() {
        return headerBackgroundColor;
    }

    public static Color getBodyBackgroundColor() {
        return bodyBackgroundColor;
    }

    public static Font getPoppinsFont(int fontSize, int fontWeight) {
        return new Font("Poppins", fontWeight, fontSize);
    }

}
