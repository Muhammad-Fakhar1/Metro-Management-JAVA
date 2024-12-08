package com.metro.Forms;

import com.metro.Controller;
import com.metro.Models.Category;
import com.metro.ThemeManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class ProductAdditionForm extends Form {

    private Controller controller;
    private final JTextField titleField;
    private final JComboBox<String> categoryCombo;
    private final JSpinner originalPriceSpinner;
    private final JSpinner unitPriceSpinner;
    private final JSpinner cartonPriceSpinner;
    private final JSpinner quantitySpinner;
    private ArrayList<Category> categories;
    private String[] categoryNames;

    public ProductAdditionForm(String title, Consumer<Map<String, Object>> callback, int width, int height) {
        super("", callback, width, height);
        try {
            this.controller=Controller.getInstance();
        } catch (IOException ex) {
            Logger.getLogger(ProductAdditionForm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            categories = controller.getCategories();
            categoryNames = categories.stream()
                                       .map(Category::getTitle)
                                       .toArray(String[]::new);
        } catch (IOException ex) {
            Logger.getLogger(ProductAdditionForm.class.getName()).log(Level.SEVERE, null, ex);
        }

        JLabel headerLabel = new JLabel("Product Information", JLabel.CENTER);
        headerLabel.setFont(ThemeManager.getPoppinsFont(16, Font.BOLD));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel infoLabel = new JLabel("Please fill in the required information to add a new product.\n", JLabel.CENTER);
        infoLabel.setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));
        infoLabel.setForeground(Color.GRAY);
        infoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(headerLabel);
        mainPanel.add(infoLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        titlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titlePanel.setBackground(Color.WHITE);

        titlePanel.add(createFormField("Title ", titleField = new JTextField(), "box"));
        titlePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        titlePanel.add(createFormField("Category ", categoryCombo = new JComboBox<>(categoryNames), "category"));

        mainPanel.add(titlePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(createFormField("Original Price ", originalPriceSpinner = new JSpinner(new SpinnerNumberModel(10000, 1, Integer.MAX_VALUE, 10)), "cash"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.X_AXIS));
        pricePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        pricePanel.setBackground(Color.WHITE);

        pricePanel.add(createFormField("Unit Price ", unitPriceSpinner = new JSpinner(new SpinnerNumberModel(10000, 1, Integer.MAX_VALUE, 10)), "cash"));
        pricePanel.add(Box.createRigidArea(new Dimension(10, 0)));
        pricePanel.add(createFormField("Carton Price ", cartonPriceSpinner = new JSpinner(new SpinnerNumberModel(10000, 1, Integer.MAX_VALUE, 10)), "cash"));

        mainPanel.add(pricePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(createFormField("Quantity ", quantitySpinner = new JSpinner(new SpinnerNumberModel(100, 1, Integer.MAX_VALUE, 1)), "box"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        mainPanel.add(createButton("Add Product", 12, Font.BOLD, ThemeManager.getButtonColor(), ThemeManager.getButtonForeground()));

        add(mainPanel);
        setVisible(true);
    }

    @Override
    protected void submitForm() {
        if (titleField.getText().trim().isEmpty()) {
            showError("Please enter the product title.");
            return;
        }

        String title = titleField.getText().trim();
        String category = (String) categoryCombo.getSelectedItem();

        int originalPrice;
        int unitPrice;
        int cartonPrice;
        int quantity;

        try {
            originalPrice = (Integer) originalPriceSpinner.getValue();
            unitPrice = (Integer) unitPriceSpinner.getValue();
            cartonPrice = (Integer) cartonPriceSpinner.getValue();
            quantity = (Integer) quantitySpinner.getValue();
        } catch (NumberFormatException e) {
            showError("Please enter valid numeric values for the prices and quantity.");
            return;
        }

        Map<String, Object> productData = Map.of(
                "title", title,
                "category", category,
                "originalPrice", originalPrice,
                "unitPrice", unitPrice,
                "cartonPrice", cartonPrice,
                "quantity", quantity
        );

        if (callback != null) {
            callback.accept(productData);
        }

        titleField.setText("");
        categoryCombo.setSelectedIndex(0);
        originalPriceSpinner.setValue(10000);
        unitPriceSpinner.setValue(10000);
        cartonPriceSpinner.setValue(10000);
        quantitySpinner.setValue(100);
    }

}
