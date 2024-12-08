package com.metro;

import java.awt.Color;
import java.awt.Font;

public class ThemeManager {

    private static final Color sidebarBackgroundColor = Color.WHITE;
    private static final Color headerBackgroundColor = Color.WHITE;
    private static final Color bodyBackgroundColor = new Color(242, 242, 242);
    private static final Color[] shadesOfBlue = {
        new Color(0x133A6D),
        new Color(0x4A90E2),
        new Color(0x357ABD),
        new Color(0x2565AE),
        new Color(0x1B4F8E),

    };

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

    public static Color[] getColorForPieChart() {
        return shadesOfBlue;
    }

    public static Color getButtonColor() {
        return new Color(0x133A6D);
    }

    public static Color getButtonForeground() {
        return Color.white;
    }

}
