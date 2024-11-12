package com.metro;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class BaseFrame extends JFrame {

    private final double WIDTH_RATIO;
    private final double HEIGHT_RATIO;

    public BaseFrame(String title, double WIDTH_RATIO, double HEIGHT_RATIO) {
        super(title);
        this.WIDTH_RATIO = WIDTH_RATIO;
        this.HEIGHT_RATIO = HEIGHT_RATIO;
        setupFrame();
    }

    private void setupFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.white);
        setResizable(false);
        setSize(getScaledDimension());
        setLocationRelativeTo(null);
    }

    private Dimension getScaledDimension() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * WIDTH_RATIO);
        int height = (int) (screenSize.height * HEIGHT_RATIO);
        return new Dimension(width, height);
    }
}
