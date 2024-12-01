package com.metro;

import com.metro.Models.Employee;
import com.metro.Models.Product;
import com.metro.Models.Role;
import com.metro.Models.Vendor;
import com.metro.Models.Branch;
import java.util.ArrayList;

public class Controller {

    private static Controller instance;

    private Controller() {

    }

    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public String[][] getEmployees() {
        return new String[][]{
            {"Fakhar", "St-123, Main, Lahore", "Rs. 100,000", "Data Entry"},
            {"Ali", "St-456, Main, Karachi", "Rs. 80,000", "Cashier"},
            {"Sara", "St-789, Main, Islamabad", "Rs. 90,000", "Data Entry"},
            {"John", "St-321, Gulshan, Karachi", "Rs. 70,000", "Cashier"},
            {"Ayesha", "St-654, F-10, Islamabad", "Rs. 85,000", "Cashier"},
            {"Bilal", "St-147, Model Town, Lahore", "Rs. 95,000", "Data Entry"},
            {"Zain", "St-258, Saddar, Karachi", "Rs. 60,000", "Cashier"},
            {"Fatima", "St-369, Blue Area, Islamabad", "Rs. 88,000", "Cashier"},
            {"Usman", "St-963, DHA, Lahore", "Rs. 110,000", "Data Entry"},
            {"Nida", "St-741, Clifton, Karachi", "Rs. 76,000", "Data Entry"},
            {"Hamza", "St-852, Bahria, Islamabad", "Rs. 92,000", "Cashier"},
            {"Mariam", "St-159, Cantonment, Lahore", "Rs. 97,000", "Data Entry"},
            {"Kamran", "St-753, Korangi, Karachi", "Rs. 68,000", "Cashier"},
            {"Hina", "St-864, I-8, Islamabad", "Rs. 89,000", "Cashier"},
            {"Tariq", "St-753, Johar Town, Lahore", "Rs. 102,000", "Data Entry"},
            {"Saad", "St-741, Malir, Karachi", "Rs. 65,000", "Cashier"},
            {"Aminah", "St-258, F-7, Islamabad", "Rs. 93,000", "Data Entry"},
            {"Waqas", "St-123, Garden Town, Lahore", "Rs. 98,000", "Cashier"},
            {"Naveed", "St-369, Defence, Karachi", "Rs. 77,000", "Data Entry"},
            {"Sana", "St-456, G-9, Islamabad", "Rs. 87,000", "Cashier"},
            {"Umer", "St-852, Canal View, Lahore", "Rs. 104,000", "Data Entry"},
            {"Zara", "St-159, Saddar, Karachi", "Rs. 66,000", "Cashier"},
            {"Adnan", "St-654, Margalla Town, Islamabad", "Rs. 90,000", "Data Entry"}
        };
    }

    public ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();

        products.add(new Product("P001", "Laptop", 50000.0f, "Electronics", 48000.0f, 47000.0f, "High-performance laptop"));
        products.add(new Product("P002", "Smartphone", 30000.0f, "Electronics", 29000.0f, 28000.0f, "Latest smartphone model"));
        products.add(new Product("P003", "Office Chair", 7000.0f, "Furniture", 6500.0f, 6300.0f, "Ergonomic office chair"));
        products.add(new Product("P004", "Desk Lamp", 1200.0f, "Home Accessories", 1100.0f, 1050.0f, "LED desk lamp"));
        products.add(new Product("P005", "Wireless Mouse", 1500.0f, "Computer Accessories", 1400.0f, 1350.0f, "Bluetooth mouse"));
        products.add(new Product("P006", "Air Conditioner", 45000.0f, "Appliances", 44000.0f, 43000.0f, "Energy-efficient air conditioner"));
        products.add(new Product("P007", "Coffee Maker", 6000.0f, "Kitchen Appliances", 5800.0f, 5600.0f, "Automatic coffee maker"));
        products.add(new Product("P008", "Gaming Headset", 4000.0f, "Gaming Accessories", 3900.0f, 3700.0f, "Noise-canceling headset"));
        products.add(new Product("P009", "Backpack", 2500.0f, "Accessories", 2400.0f, 2300.0f, "Water-resistant backpack"));
        products.add(new Product("P010", "Smartwatch", 15000.0f, "Wearable Tech", 14500.0f, 14000.0f, "Feature-rich smartwatch"));

        return products;
    }

    public ArrayList<Branch> getBranches() {
        ArrayList<Branch> branches = new ArrayList<>();

        Employee manager1 = new Employee("E001", "Ali Khan", "123 Main Boulevard", "0333-1110001", "B001", 80000, Role.BRANCH_MANAGER);
        Employee manager2 = new Employee("E002", "Sara Ahmed", "456 Y Block", "0333-1110002", "B002", 85000, Role.BRANCH_MANAGER);
        Employee manager3 = new Employee("E003", "Zain Malik", "789 Sea View Road", "0333-1110003", "B003", 75000, Role.BRANCH_MANAGER);
        Employee manager4 = new Employee("E004", "Nida Fatima", "321 Shadman Town", "0333-1110004", "B004", 70000, Role.BRANCH_MANAGER);
        Employee manager5 = new Employee("E005", "Hamza Sheikh", "654 Jinnah Avenue", "0333-1110005", "B005", 82000, Role.BRANCH_MANAGER);
        Employee manager6 = new Employee("E006", "Ayesha Siddiqui", "987 Markaz", "0333-1110006", "B006", 78000, Role.BRANCH_MANAGER);
        Employee manager7 = new Employee("E007", "Usman Javed", "159 Jamrud Road", "0333-1110007", "B007", 74000, Role.BRANCH_MANAGER);
        Employee manager8 = new Employee("E008", "Mariam Yousaf", "753 Mall Road", "0333-1110008", "B008", 76000, Role.BRANCH_MANAGER);
        Employee manager9 = new Employee("E009", "Fahad Riaz", "246 Zarghoon Road", "0333-1110009", "B009", 71000, Role.BRANCH_MANAGER);
        Employee manager10 = new Employee("E010", "Hira Qureshi", "135 Qasimabad", "0333-1110010", "B010", 78000, Role.BRANCH_MANAGER);

        branches.add(new Branch("B001", "Gulberg", "Lahore", "123 Main Boulevard", "042-111-0001", manager1));
        branches.add(new Branch("B002", "DHA Phase 5", "Lahore", "456 Y Block", "042-111-0002", manager2));
        branches.add(new Branch("B003", "Clifton", "Karachi", "789 Sea View Road", "021-111-0003", manager3));
        branches.add(new Branch("B004", "North Nazimabad", "Karachi", "321 Shadman Town", "021-111-0004", manager4));
        branches.add(new Branch("B005", "Blue Area", "Islamabad", "654 Jinnah Avenue", "051-111-0005", manager5));
        branches.add(new Branch("B006", "F-10", "Islamabad", "987 Markaz", "051-111-0006", manager6));
        branches.add(new Branch("B007", "University Town", "Peshawar", "159 Jamrud Road", "091-111-0007", manager7));
        branches.add(new Branch("B008", "Saddar", "Rawalpindi", "753 Mall Road", "051-111-0008", manager8));
        branches.add(new Branch("B009", "Satellite Town", "Quetta", "246 Zarghoon Road", "081-111-0009", manager9));
        branches.add(new Branch("B010", "Hyderabad Bypass", "Hyderabad", "135 Qasimabad", "022-111-0010", manager10));

        return branches;
    }

    public ArrayList<Vendor> getVendors() {
        ArrayList<Vendor> vendors = new ArrayList<>();
        vendors.add(new Vendor("V001", "John Doe", "johndoe@gmail.com", 1500.0f));
        vendors.add(new Vendor("V002", "Jane Smith", "janesmith@gmail.com", 2500.0f));
        vendors.add(new Vendor("V003", "Michael Brown", "michael.brown@gmail.com", 500.0f, false));
        vendors.add(new Vendor("V004", "Emily Davis", "emily.davis@gmail.com", 3000.0f, true));
        vendors.add(new Vendor("V005", "William Johnson", "william.johnson@gmail.com", 4500.0f));
        vendors.add(new Vendor("V006", "Sophia Wilson", "sophia.wilson@gmail.com", 1200.0f, true));
        vendors.add(new Vendor("V007", "James Lee", "james.lee@gmail.com", 800.0f, false));
        vendors.add(new Vendor("V008", "Olivia Martin", "olivia.martin@gmail.com", 2200.0f));
        vendors.add(new Vendor("V009", "Benjamin Garcia", "benjamin.garcia@gmail.com", 3500.0f));
        vendors.add(new Vendor("V010", "Charlotte Martinez", "charlotte.martinez@gmail.com", 1800.0f, true));
        return vendors;
    }
}
