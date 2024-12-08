package com.metro;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.ReferenceType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.metro.Forms.LoginForm;
import com.metro.Models.Employee;
import com.metro.Models.Product;
import com.metro.Models.Role;
import com.metro.Models.Vendor;
import com.metro.Models.Branch;
import com.metro.Models.Category;
import com.metro.Models.Order;
import com.metro.Models.Report;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {

    private static Controller instance;

    private Socket socket;
    private PrintWriter pw;
    private BufferedReader br;
    private ObjectMapper objectMapper;

    private Controller() {

    }

    public void setSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.pw = new PrintWriter(socket.getOutputStream(), true);
        this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        new Scanner(System.in);
    }

    public static Controller getInstance() throws IOException {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void start() {
        new LoginForm("", data -> {
        }, 600, 450);
        while (true);
    }

    public Employee login(String email, String password) throws IOException {
        pw.println("LOGIN");
        pw.println(email);
        pw.println(password);

        String response = br.readLine();

        if (response != null) {
            if (response.startsWith("{")) {

                Employee emp = objectMapper.readValue(response, Employee.class);
                System.out.println("Received employee: " + emp);
                return emp;
            } else {
                System.out.println("Server: " + response);
                return null;
            }
        } else {
            System.out.println("Invalid response from server.");
            return null;
        }
    }

    public String[][] getEmployees(int branchID) throws JsonProcessingException, IOException {
        pw.println("GET");
        pw.println("ALL_EMPLOYEES");
        pw.println(branchID);

        String response = br.readLine();
        System.out.println(response);
        ArrayList<Employee> employees = objectMapper.readValue(response, new TypeReference<ArrayList<Employee>>() {
        });

        ArrayList<String[]> validEmployees = new ArrayList<>();

        for (int i = 0; i < employees.size(); i++) {
            Employee emp = employees.get(i);
            if (emp.getRole() != Role.BRANCH_MANAGER && emp.getRole() != null) {
                String[] employeeRow = new String[6];
                employeeRow[0] = emp.getCnic();
                employeeRow[1] = emp.getName();
                employeeRow[2] = emp.getPhoneNumber();
                employeeRow[3] = emp.getAddress();
                employeeRow[4] = "Rs. " + emp.getSalary();
                employeeRow[5] = emp.getRole().toString();

                validEmployees.add(employeeRow);
            }
        }

        String[][] employeeArray = new String[validEmployees.size()][6];
        return validEmployees.toArray(employeeArray);
    }

    public ArrayList<Branch> getBranches() throws IOException {
        pw.println("GET");
        pw.println("ALL_BRANCHES");

        String response = br.readLine();
        System.out.println(response);
        ArrayList<Branch> branches = objectMapper.readValue(response, new TypeReference<ArrayList<Branch>>() {
        });

        return branches;
    }

    public ArrayList<Report> getReports(int branchCode) throws IOException {
        pw.println("GET");
        pw.println("REPORT");
        pw.println(Integer.toString(branchCode));

        String response = br.readLine();
        System.out.println(response);
        ArrayList<Report> reports = objectMapper.readValue(response, new TypeReference<ArrayList<Report>>() {
        });

        return reports;
    }

    public ArrayList<Vendor> getVendors(int branchCode) throws IOException {
        pw.println("GET");
        pw.println("ALL_VENDORS");
        pw.println(branchCode);

        String response = br.readLine();
        System.out.println(response);
        ArrayList<Vendor> vendors = objectMapper.readValue(response, new TypeReference<ArrayList<Vendor>>() {
        });

        return vendors;
    }

    public ArrayList<Product> getProducts(int branchCode) throws IOException {
        pw.println("GET");
        pw.println("ALL_PRODUCTS");
        pw.println(branchCode);

        String response = br.readLine();
        System.out.println(response);
        ArrayList<Product> products = objectMapper.readValue(response, new TypeReference<ArrayList<Product>>() {
        });

        return products;
    }

    public Product getAProduct(String productID) throws IOException {
        pw.println("GET");
        pw.println("PRODUCT");
        pw.println(productID);

        String response = br.readLine();
        if (response == null || response.isEmpty()) {
            return null;
        }
        Product product = objectMapper.readValue(response, Product.class);

        return product;
    }

    public Branch getABranch(int branchCode) {
        try {
            ArrayList<Branch> branches = getBranches();
            for (Branch branch : branches) {
                if (branch.getBranchId() == branchCode) {
                    return branch;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Category> getCategories() throws IOException {
        pw.println("GET");
        pw.println("ALL_CATEGORIES");

        String response = br.readLine();
        System.out.println(response);
        ArrayList<Category> categories = objectMapper.readValue(response, new TypeReference<ArrayList<Category>>() {
        });

        return categories;
    }

    public boolean addBranch(String name, String city, String address, String phone) throws IOException {
        pw.println("ADD");
        pw.println("BRANCH");

        Branch branch = new Branch(name, city, address, phone, true, 0, LocalDate.now());

        String branchJson = objectMapper.writeValueAsString(branch);

        pw.println(branchJson);
        System.out.println("Branch: " + branchJson);

        String response = br.readLine();
        if (response != null) {
            System.out.println(response);
            return true;
        }
        return false;
    }

    public void updatePassword(Employee e) throws IOException {
        pw.println("UPDATE");
        pw.println("EMPLOYEE");

        pw.println(objectMapper.writeValueAsString(e));

        String response = br.readLine();
        System.out.println(response);

        if ("Password Changed".equals(response)) {
            System.out.println("Password has been successfully updated.");
        } else if ("Failed to Change Password".equals(response)) {
            System.out.println("Password update failed.");
        } else {
            System.out.println("Unexpected server response: " + response);
        }
    }

    public boolean addVendor(String name, String phone, int branchCode) throws IOException {
        pw.println("ADD");
        pw.println("VENDOR");

        Vendor vendor = new Vendor(name, phone, 0, true, branchCode);

        String vendorJson = objectMapper.writeValueAsString(vendor);

        pw.println(vendorJson);
        System.out.println("Vendor: " + vendorJson);

        String response = br.readLine();
        if (response != null) {
            System.out.println(response);
            return true;
        }
        return false;
    }

    public boolean addProduct(String title, String category, int originalPrice, int unitPrice, int cartonPrice, int quantity, int branchCode, int empID, int vid) throws IOException {
        pw.println("ADD");
        pw.println("PRODUCT");
        pw.println(empID);
        pw.println(vid);

        Product product = new Product(title, originalPrice, category, unitPrice, cartonPrice, title, quantity, branchCode);

        String productJson = objectMapper.writeValueAsString(product);

        pw.println(productJson);
        System.out.println("Product: " + productJson);

        String response = br.readLine();
        if (response != null) {
            System.out.println(response);
            return true;
        }
        return false;
    }

    public Order addProductToOrder(Order order, String productID, int quantity, String type) throws IOException {
        if ("Carton".equals(type)) {
            Product product = getAProduct(productID);
            if (product != null) {
                int unitsInCarton = (int) (product.getCartonPrice() / product.getUnitPrice());
                quantity *= unitsInCarton;
            } else {
                return null; // Return null if product is not found
            }
        }

        pw.println("ADD");
        pw.println("PRODUCT_TO_ORDER");

        pw.println(objectMapper.writeValueAsString(order));
        pw.println(productID);
        pw.println(quantity);

        String orderJson = br.readLine();
        if (orderJson != null && !orderJson.isEmpty()) {
            return objectMapper.readValue(orderJson, Order.class);
        } else {
            return null; // Return null if no valid response
        }
    }

    public boolean addCategory(String name, float gst, String desc) throws IOException {
        pw.println("ADD");
        pw.println("CATEGORY");

        Category c = new Category(name, 0, gst, desc, true);

        String json = objectMapper.writeValueAsString(c);

        pw.println(json);
        System.out.println("cat: " + json);

        String response = br.readLine();
        if (response != null) {
            System.out.println(response);
            return true;
        }
        return false;
    }

    public boolean addEmployee(Employee e) throws IOException {
        pw.println("ADD");
        pw.println("EMPLOYEE");

        String empJson = objectMapper.writeValueAsString(e);

        pw.println(empJson);
        System.out.println("empJson: " + empJson);

        String response = br.readLine();
        if (response != null) {
            System.out.println(response);
            return true;
        }
        return false;
    }

    public boolean AssignManager(Employee e) throws JsonProcessingException, IOException {
        pw.println("ADD");
        pw.println("BRANCH_MANAGER");

        String empJson = objectMapper.writeValueAsString(e);

        pw.println(e.getBranchCode());
        pw.println(empJson);
        System.out.println("empJson: " + empJson);

        String response = br.readLine();
        if (response != null) {
            System.out.println(response);
            return true;
        }
        return false;
    }

    public Order checkout(Order order, int employeeID) throws IOException {
        pw.println("UPDATE");
        pw.println("CHECKOUT");
        String orderJson = objectMapper.writeValueAsString(order);
        pw.println(orderJson);
        pw.println(Integer.toString(employeeID));

        String response = br.readLine();
        if (response != null && response.equals("Failed to checkOut")) {
            System.out.println("Failed!");
            return null;
        } else {
            Order checkedOutOrder = objectMapper.readValue(response, Order.class);
            return checkedOutOrder;
        }
    }

    public void closeConnections() {
        pw.println("CLOSE");
        try {
            if (br != null) {
                br.close();
            }
            if (pw != null) {
                pw.close();
            }
            if (socket != null) {
                socket.close();
            }
            System.out.println("Connection closed.");
        } catch (IOException e) {
            System.err.println("Error closing connections: " + e.getMessage());
        }
    }

}
