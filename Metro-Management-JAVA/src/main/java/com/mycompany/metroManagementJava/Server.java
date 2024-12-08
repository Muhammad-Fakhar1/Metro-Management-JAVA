package com.mycompany.metroManagementJava;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 12345;
    private static final int THREAD_POOL_SIZE = 10;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        objectMapper.registerModule(new JavaTimeModule());

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                threadPool.execute(() -> handleClient(socket));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleClient(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            String command;
            while ((command = in.readLine()) != null) {
                System.out.println("Received command: " + command);

                switch (command) {
                    case "GET":
                        handleGet(in, out);
                        break;
                    case "ADD":
                        handleAdd(in, out);
                        break;
                    case "REMOVE":
                        handleRemove(in, out);
                        break;
                    case "LOGIN":
                        handleLogin(in, out);
                        break;
                    case "UPDATE":
                        handleUpdate(in, out);
                        break;
                    case "CLOSE":
                        out.println("Connection closed by client");
                        return;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Client connection error: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }

    private static void handleGet(BufferedReader in, PrintWriter out) throws IOException {
        String type = in.readLine();
        String identifier;
        int branchCode;

        switch (type) {
            case "PRODUCT":
                identifier = in.readLine();
                Product product = Assets.getProduct(Integer.parseInt(identifier));
                if (product != null) {
                    out.println(objectMapper.writeValueAsString(product));
                } else {
                    out.println("null");
                }
                break;
            case "EMPLOYEE":
                identifier = in.readLine();
                Employee employee = Workforce.getEmployee(Integer.parseInt(identifier));
                if (employee != null) {
                    out.println(objectMapper.writeValueAsString(employee));
                } else {
                    out.println("null");
                }
                break;
            case "ALL_PRODUCTS":
                identifier = in.readLine();
                branchCode = Integer.parseInt(identifier);
                ArrayList<Product> products = Assets.getAllProducts(branchCode);
                if (products != null) {
                    out.println(objectMapper.writeValueAsString(products));
                } else {
                    out.println("null");
                }
                break;
            case "ALL_VENDORS":
                identifier = in.readLine();
                branchCode = Integer.parseInt(identifier);
                ArrayList<Vendor> vendors = Assets.getAllVendors(branchCode);
                if (vendors != null) {
                    out.println(objectMapper.writeValueAsString(vendors));
                } else {
                    out.println("null");
                }
                break;
            case "ALL_CATEGORIES":
                ArrayList<Category> categories = Assets.getAllCategories();
                if (categories != null) {
                    out.println(objectMapper.writeValueAsString(categories));
                } else {
                    out.println("null");
                }
                break;
            case "ALL_BRANCHES":
                ArrayList<Branch> branches = Assets.getAllBranches();
                if (branches != null) {
                    out.println(objectMapper.writeValueAsString(branches));
                } else {
                    out.println("null");
                }
                break;
            case "ALL_EMPLOYEES":
                identifier = in.readLine();
                branchCode = Integer.parseInt(identifier);
                ArrayList<Employee> branchEmployees = Workforce.getAllEmployees(branchCode);
                if (branchEmployees != null) {
                    out.println(objectMapper.writeValueAsString(branchEmployees));
                } else {
                    out.println("null");
                }
                break;
            case "REPORT":
                identifier = in.readLine();
                Report report = ReportManager.getReport(Integer.parseInt(identifier));
                if (report != null) {
                    out.println(objectMapper.writeValueAsString(report));
                } else {
                    out.println("null");
                }
                out.flush();
            default:
                out.println("Invalid type");
                break;
        }
        out.flush();
    }

    private static void handleAdd(BufferedReader in, PrintWriter out) throws IOException {
        String type = in.readLine();
        String id1;
        String id2;
        String objectString;

        switch (type) {
            case "PRODUCT":
                id1 = in.readLine();
                id2 = in.readLine();
                objectString = in.readLine();
                try {
                    Product product = objectMapper.readValue(objectString, Product.class);
                    if (DataEntryManager.addProduct(product, id1, id2)) {
                        out.println(Assets.getProduct("title", product.getTitle()));
                    } else {
                        out.println("Failed to add product");
                    }
                } catch (Exception e) {
                    out.println("Error parsing product JSON");
                    e.printStackTrace();
                }
                break;
            case "EMPLOYEE":
                objectString = in.readLine();
                try {
                    Employee employee = objectMapper.readValue(objectString, Employee.class);
                    if (BranchManager.addEmployee(employee)) {
                        out.println(Workforce.getEmployee("Name", employee.getName()));
                    } else {
                        out.println("Failed to add employee");
                    }
                } catch (Exception e) {
                    out.println("Error parsing employee JSON");
                    e.printStackTrace();
                }
                break;
            case "VENDOR":
                objectString = in.readLine();
                try {
                    Vendor vendor = objectMapper.readValue(objectString, Vendor.class);
                    if (DataEntryManager.addVendor(vendor)) {
                        out.println(Assets.getVendor("Name",vendor.getName()));
                    } else {
                        out.println("null");
                    }
                } catch (Exception e) {
                    out.println("Error parsing vendor JSON");
                    e.printStackTrace();
                }
                break;
            case "BRANCH_MANAGER":
                id1 = in.readLine();
                objectString = in.readLine();
                Employee manager = objectMapper.readValue(objectString, Employee.class);
                if (SuperAdmin.addBranchManager(manager, id1)) {
                    out.println("Branch Manager added successfully");
                } else {
                    out.println("Failed to add Branch Manager");
                }
                break;
            case "BRANCH":
                objectString = in.readLine();
                Branch branch = objectMapper.readValue(objectString, Branch.class);
                if (SuperAdmin.addBranch(branch)) {
                    out.println("Branch added successfully");
                } else {
                    out.println("Failed to add Branch");
                }
                break;
            case "CATEGORY":
                objectString = in.readLine();
                Category category = objectMapper.readValue(objectString, Category.class);
                if (DataEntryManager.addCategory(category)) {
                    out.println("Category added successfully");
                } else {
                    out.println("Failed to add Category");
                }
                out.flush();
                break;
            case "PRODUCT_TO_ORDER":
                String orderJson = in.readLine();
                String productID = in.readLine();
                int requiredQuantity = Integer.parseInt(in.readLine());

                Order order = objectMapper.readValue(orderJson, Order.class);
                Product product = Assets.getProduct(Integer.parseInt(productID));

                if (CashierManager.addProductToOrder(order, product, requiredQuantity)) {
                    out.println(objectMapper.writeValueAsString(order));
                } else {
                    out.println("Failed to update Order");
                }
                out.flush();
                break;
            default:
                out.println("Invalid type");
                break;
        }
        out.flush();
    }

    private static void handleRemove(BufferedReader in, PrintWriter out) throws IOException {
        String type = in.readLine();
        String identifier;

        switch (type) {
            case "PRODUCT":
                identifier = in.readLine();
                if (DataEntryManager.removeProduct(Integer.parseInt(identifier))) {
                    out.println("Product Removed");
                } else {
                    out.println("Failed to Remove Product");
                }
                break;
            case "VENDOR":
                identifier = in.readLine();
                if (DataEntryManager.removeVendor(Integer.parseInt(identifier))) {
                    out.println("Vendor Removed");
                } else {
                    out.println("Failed to Remove Vendor");
                }
                break;
            case "CATEGORY":
                identifier = in.readLine();
                if (DataEntryManager.removeCategory(identifier)) {
                    out.println("Category Removed");
                } else {
                    out.println("Failed to Remove Category");
                }
                break;
            case "BRANCH":
                identifier = in.readLine();
                if (SuperAdmin.removeBranch(identifier)) {
                    out.println("Branch Removed");
                } else {
                    out.println("Failed to Remove Branch");
                }
                break;
            default:
                out.println("Invalid type");
                break;
        }
        out.flush();
    }

    private static void handleLogin(BufferedReader in, PrintWriter out) throws IOException {
        String email = in.readLine();
        String password = in.readLine();

        Employee employee = LoginManager.login(email, password);

        if (employee != null) {
            out.println(objectMapper.writeValueAsString(employee));
        } else {
            out.println("null");
        }
        out.flush();
    }

    private static void handleUpdate(BufferedReader in, PrintWriter out) throws IOException {
        String type = in.readLine();

        switch (type) {
            case "CHECKOUT":
                String orderJson = in.readLine();
                int employeeID = Integer.parseInt(in.readLine());
                Order order = objectMapper.readValue(orderJson, Order.class);
                Employee emp = Workforce.getEmployee(employeeID);
                Order checkedOut = CashierManager.checkout(order, employeeID, emp.getBranchCode());
                if (checkedOut != null) {
                    out.println(objectMapper.writeValueAsString(checkedOut));
                } else {
                    out.println("Failed to checkOut");
                }
            default:
                out.println("Invalid type");
                break;
        }
        out.flush();

    }
}
