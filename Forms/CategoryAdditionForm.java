package com.metro.Forms;

import com.formdev.flatlaf.ui.FlatLineBorder;
import com.metro.ThemeManager;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class CategoryAdditionForm extends Form {

    private final JTextField nameField;
    private final JSpinner taxSpinner;
    private final JTextArea descriptionArea;

    public CategoryAdditionForm(Consumer<Map<String, Object>> callback, int width, int height) {
        super("", callback, width, height);

        JLabel headerLabel = new JLabel("Category information", JLabel.CENTER);
        headerLabel.setFont(ThemeManager.getPoppinsFont(16, Font.BOLD));
        headerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel infoLabel = new JLabel("Please fill in the required information to add a new category.\n",
                JLabel.CENTER);
        infoLabel.setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));
        infoLabel.setForeground(Color.GRAY);
        infoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(headerLabel);
        mainPanel.add(infoLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        mainPanel.add(createFormField("Category Title", nameField = new JTextField(), "users"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        taxSpinner = new JSpinner(new SpinnerNumberModel(0.0f, 0.0f, 100.0f, 0.1f));
        mainPanel.add(createFormField("GST Tax (%)", taxSpinner, "telephone"));
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel descriptionLabel = new JLabel("Description");
        descriptionLabel.setFont(ThemeManager.getPoppinsFont(12, Font.PLAIN));
        mainPanel.add(descriptionLabel);

        descriptionArea = new JTextArea();
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setRows(4);
        descriptionArea.setBorder(new FlatLineBorder(new Insets(10, 10, 10, 10), Color.lightGray, 1, 10));

        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        scrollPane.setBorder(new EmptyBorder(0, 10, 0, 0));
        scrollPane.setAlignmentX(LEFT_ALIGNMENT);
        scrollPane.setPreferredSize(new Dimension(300, 100));
        mainPanel.add(scrollPane);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(createButton("Create Category", 12, Font.BOLD,
                ThemeManager.getButtonColor(),
                ThemeManager.getButtonForeground()));

        add(mainPanel);
        setVisible(true);
    }

    @Override
    protected void submitForm() {
        if (nameField.getText().trim().isEmpty()) {
            showError("Please enter the category Name.");
            return;
        }

        String name = nameField.getText().trim();
        float tax = ((Number) taxSpinner.getValue()).floatValue();
        String description = descriptionArea.getText().trim();

        Map<String, Object> categoryData = new HashMap<>();
        categoryData.put("name", name);
        categoryData.put("tax", tax);
        categoryData.put("description", description);

        if (callback != null) {
            callback.accept(categoryData);
        }

        nameField.setText("");
        taxSpinner.setValue(0.0);
        descriptionArea.setText("");
    }
}
