package com.metro.Components;

import javax.swing.JButton;

public class Card extends JButton {

    private String title;

    public Card(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
