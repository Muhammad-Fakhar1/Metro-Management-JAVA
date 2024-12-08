package com.metro.Sections;

import com.metro.Components.Body;
import com.metro.Components.Card;
import com.metro.Forms.CategoryAdditionForm;
import com.metro.Models.Category;
import com.metro.Models.Vendor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class CategoryUI extends Body {

    private ArrayList<Category> categories;
    private final ArrayList<ImageIcon> icons;
    private int branchCode;

    public CategoryUI(int branchCode, int width, int height, boolean showButton) {
        this.branchCode = branchCode;
        this.showButton = true;
        this.dashboardWidth = width;
        this.dashboardHeight = 400;

        this.title = "Categories";
        icons = new ArrayList<>();
        categories = new ArrayList<>();
        cards = new ArrayList<>();

        addButton = new JButton("+ Add Category");
        addButton.addActionListener(e -> {
            openCategoryForm();
        });

        setupSearchField();
        getCategories();
        setupUI();
    }

    private void getCategories() {
        try {
            categories = controller.getCategories();
        } catch (IOException ex) {
            Logger.getLogger(CategoryUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        getIcons();
        createCategoryCards();
    }

    private void getIcons() {
        String[] imagePaths = {"images/box.png", "images/cash.png"};
        for (int i = 0; i < 2; i++) {
            icons.add(new ImageIcon(imagePaths[i]));
        }
    }

    private void createCategoryCards() {
        for (Category category : categories) {
            Card card = new CategoryCard(category, icons);
            cards.add(card);
        }
    }

    private void openCategoryForm() {
        if (form == null) {
            form = new CategoryAdditionForm(categoryData -> {
                String name=(String) categoryData.get("name");
                String description=(String) categoryData.get("description");
                float tax=(float) categoryData.get("tax");
                
                try {
                    if (controller.addCategory(name, tax, description)) {
                        Category c = new Category(name, 0, tax, description, true);
                        form.dispose();

                        cards.add(new CategoryCard(c, icons));
                        removeAll();
                        setupUI();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(CategoryUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }, 450, 450);
            form.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    form = null;
                }
            });
        } else {
            form.toFront();
            form.requestFocus();
        }
    }

}
