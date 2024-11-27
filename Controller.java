package com.metro;

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

}
