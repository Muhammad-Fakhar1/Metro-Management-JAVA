package com.metro;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ImageProcessor {

    public static Icon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resized = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        return new ImageIcon(resized);
    }
}
