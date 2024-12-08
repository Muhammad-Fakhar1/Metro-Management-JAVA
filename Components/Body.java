package com.metro.Components;

import com.formdev.flatlaf.ui.FlatButtonBorder;
import com.formdev.flatlaf.ui.FlatLineBorder;
import com.formdev.flatlaf.ui.FlatMarginBorder;
import com.metro.Controller;
import com.metro.Forms.Form;
import com.metro.ImageProcessor;
import com.metro.ThemeManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public abstract class Body extends JPanel {

    protected Controller controller;

    protected Form form;
    protected int dashboardWidth;
    protected int dashboardHeight;
    protected JTextField searchField;
    protected ArrayList<Card> cards;
    protected boolean showButton;
    protected String title;
    protected String btnTitle;
    protected JButton addButton;

    public Body() {
        try {
            this.controller = Controller.getInstance();
        } catch (IOException e) {
        }

    }
    

    protected void setupSearchField() {
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 30));
        searchField.setBorder(new FlatLineBorder(new Insets(2, 10, 2, 10), new Color(200, 200, 200), 1, 10));
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (searchField.hasFocus() && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    filter();
                }
            }
        });
    }

    protected void setupUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(ThemeManager.getBodyBackgroundColor());
        setBorder(new EmptyBorder(15, 15, 15, 15));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                dashboardWidth = getWidth();
                refreshUI();
            }
        });

        refreshUI();
    }

    protected void refreshUI() {
        removeAll();
        add(createSection(title, cards));
        revalidate();
        repaint();
    }

    private JPanel createSection(String title, ArrayList<Card> Cards) {
        int size = Cards.size();
        int rows = (int) Math.ceil(size / 3.0);
        int panelHeight = (int) (dashboardHeight * (1 + (rows - 1) * 0.6));

        if (size < 9) {
            panelHeight += dashboardHeight - 100;
            rows++;
        }

        RoundedPanel section = new RoundedPanel(15);
        section.setPreferredSize(new Dimension(0, panelHeight));
        section.setBackground(ThemeManager.getBodyBackgroundColor());
        section.setLayout(new BorderLayout());

        section.add(createHeader(title), BorderLayout.NORTH);
        section.add(createCardsContainer(rows), BorderLayout.CENTER);

        return section;
    }

    private JPanel createHeader(String title) {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(ThemeManager.getBodyBackgroundColor());

        JLabel label = new JLabel(title);
        label.setFont(new Font("Poppins", Font.BOLD, 20));
        label.setForeground(Color.GRAY);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(ThemeManager.getBodyBackgroundColor());
        JButton searchButton = new JButton(ImageProcessor.resizeIcon(new ImageIcon("images/search.png"), 25, 25));
        searchButton.addActionListener(e -> filter());
        searchPanel.add(searchButton);
        searchPanel.add(searchField);

        header.add(label, BorderLayout.WEST);
        header.add(searchPanel, BorderLayout.EAST);

        return header;
    }

    private JPanel createCardsContainer(int rows) {
        JPanel container = new JPanel(new GridLayout(rows + 1, 4, 5, 5));
        container.setBackground(ThemeManager.getBodyBackgroundColor());
        container.setBorder(new FlatMarginBorder(new Insets(15, 0, 0, 0)));

        if (showButton) {
            addAddButton(container);
        }

        for (Card card : cards) {
            container.add(card);
        }

        fillPlaceholders(container, rows);

        return container;
    }

    private void addAddButton(JPanel container) {

        addButton.setFont(new Font("Poppins", Font.PLAIN, 12));
        addButton.setPreferredSize(new Dimension(150, 40));
        addButton.setFocusPainted(false);
        addButton.setBorder(new FlatButtonBorder());
        addButton.setBackground(Color.WHITE);
        addButton.setForeground(Color.LIGHT_GRAY);
        container.add(addButton);
    }

    private void fillPlaceholders(JPanel container, int rows) {
        int totalCells = (dashboardWidth > 1000 ? 4 : 3) * (rows + 1);
        int placeholders = totalCells - cards.size() - (showButton ? 1 : 0);

        for (int i = 0; i < placeholders; i++) {
            container.add(new JLabel());
        }
    }

    private void filter() {
        String searchText = searchField.getText().toLowerCase();
        ArrayList<Card> filteredCards = new ArrayList<>();

        for (Card card : cards) {
            if (card.getTitle().toLowerCase().contains(searchText)) {
                filteredCards.add(card);
            }
        }

        cards.removeAll(filteredCards);
        filteredCards.addAll(cards);
        cards = filteredCards;
        searchField.setText("");

        refreshUI();
    }
}
