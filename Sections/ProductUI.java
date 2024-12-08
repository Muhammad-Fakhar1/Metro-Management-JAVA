package com.metro.Sections;

import com.metro.Components.Body;
import com.metro.Components.Card;
import com.metro.Models.Product;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

public class ProductUI extends Body {

    private ArrayList<Product> products;
    private final ArrayList<ImageIcon> icons;
    private int branchCode;
    
    public ProductUI(int branchCode,int width, int height, boolean showButton) {
        this.branchCode=branchCode;
        this.showButton = showButton;
        this.dashboardWidth = width;
        this.dashboardHeight = 400;
        this.title = "Products";
        this.showButton = false;
        icons = new ArrayList<>();
        products = new ArrayList<>();
        cards = new ArrayList<>();

        setupSearchField();
        getProducts();
        setupUI();
    }

    private void getProducts() {
        try {
            products = controller.getProducts(branchCode);
        } catch (IOException ex) {
            Logger.getLogger(ProductUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        getIcons();
        createProductCards();
    }

    private void getIcons() {
        String[] imagePaths = {"images/category.png", "images/box.png"};
        for (int i = 0; i < 2; i++) {
            icons.add(new ImageIcon(imagePaths[i]));
        }
    }

    private void createProductCards() {
        for (Product product : products) {
            Card card = new ProductCard(product, icons);
            cards.add(card);
        }
    }
}
