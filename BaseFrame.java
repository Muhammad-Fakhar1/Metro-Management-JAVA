package com.metro;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

abstract class BaseFrame extends JFrame {
    
    protected Sidebar sidebar;
    private final double WIDTH_RATIO;
    private final double HEIGHT_RATIO;
    private Dimension screenSize;

    public BaseFrame(String title, double WIDTH_RATIO, double HEIGHT_RATIO) {
        super(title);
        this.WIDTH_RATIO = WIDTH_RATIO;
        this.HEIGHT_RATIO = HEIGHT_RATIO;
        setSize(getScaledDimension());
        setLayout(new BorderLayout());

        setupFrame();
        sidebar = new Sidebar(getWidth(), getHeight());
        setSidebar();
        
        add(sidebar, BorderLayout.WEST);
        add(new Header("Muhammad Fakhar bin Rashid", "Lahore", getWidth(), getHeight()), BorderLayout.NORTH);
        setVisible(true);
    }

    private void setupFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //getContentPane().setBackground(Color.white);
        setResizable(false);
        setLocationRelativeTo(null);
    }

    private Dimension getScaledDimension() {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * WIDTH_RATIO);
        int height = (int) (screenSize.height * HEIGHT_RATIO);
        return new Dimension(width, height);
    }

    protected abstract void setSidebar();
    protected abstract JPanel createBody();
}
