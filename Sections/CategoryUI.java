package com.metro.Sections;

import com.metro.Components.Body;
import com.metro.Components.Card;
import com.metro.Models.Product;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class CategoryUI extends Body {

   // private ArrayList<Product> categories;
    private final ArrayList<ImageIcon> icons;

    public CategoryUI(int width, int height, boolean showButton) {
        this.showButton = showButton;
        this.dashboardWidth = width;
        this.dashboardHeight = 400;

        this.title = "Categories";
        this.showButton = false;
        icons = new ArrayList<>();
        //categories = new ArrayList<>();
        cards = new ArrayList<>();

        setupSearchField();
        getCategories();
        setupUI();
    }
    private void getCategories() {
       // categories = controller.getProducts();
        getIcons();
        //createProductCards();
    }

    private void getIcons() {
        String[] imagePaths = {"images/category.png", "images/box.png"};
        for (int i = 0; i < 2; i++) {
            icons.add(new ImageIcon(imagePaths[i]));
        }
    }

//    private void createCategoryCards() {
//        for (Category product : categories) {
//            Card card = new ProductCard(product, icons);
//            cards.add(card);
//        }
//    }
    
}
