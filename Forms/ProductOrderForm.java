package com.metro.Forms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.Box;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class ProductOrderForm extends Form {

    private final JTextField productIdField;
    private final JSpinner quantitySpinner;
    private JRadioButton unitRadio;
    private JRadioButton cartonRadio;

    public ProductOrderForm(Consumer<Map<String, Object>> callback) {
        super("Add Product", callback, 300, 300);

        mainPanel.add(createFormField("Product ID ", productIdField = new JTextField(), ""));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(createFormField("Quantity ", quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1)), ""));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        unitRadio=new JRadioButton();
        cartonRadio=new JRadioButton();
        mainPanel.add(add(createRadioButtonGroup("Type",
                new String[]{"Unit", "Carton"},
                new JRadioButton[]{unitRadio, cartonRadio})
        ));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        mainPanel.add(createButton("Add Product", 12, Font.PLAIN, Color.white, Color.BLACK));

        add(mainPanel);
        setVisible(true);
    }

    @Override
    protected void submitForm() {
        if (productIdField.getText().trim().isEmpty()) {
            showError("Please enter a Product ID.");
            return;
        }

        if (!unitRadio.isSelected() && !cartonRadio.isSelected()) {
            showError("Please select a type (Unit or Carton).");
            return;
        }

        String productId = productIdField.getText().trim();
        int quantity = (Integer) quantitySpinner.getValue();
        String type = unitRadio.isSelected() ? "Unit" : "Carton";

        Map<String, Object> data = new HashMap<>();
        data.put("productID", productId);
        data.put("quantity", quantity);
        data.put("type", type);

        if (callback != null) {
            callback.accept(data);
        }

        productIdField.setText("");
        quantitySpinner.setValue(1);
        unitRadio.setSelected(false);
        cartonRadio.setSelected(false);
    }
}
