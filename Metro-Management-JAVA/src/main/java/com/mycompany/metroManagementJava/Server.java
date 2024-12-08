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

        switch (type) {
            case "PRODUCT":
                identifier = in.readLine();
                Product product = Assets.getProduct(identifier);
                if (product != null) {
                    out.println(objectMapper.writeValueAsString(product));
                } else {
                    out.println("null");
                }
                out.flush();
                break;
            case "ALL_PRODUCTS":
                ArrayList<Product> products = Assets.getAllProducts();
                if (products != null) {
                    String productsJson = objectMapper.writeValueAsString(products);
                    out.println(productsJson);
                } else {
                    out.println("null");
                }
                out.flush();

                break;
            case "ALL_VENDORS":
                ArrayList<Vendor> vendors = Assets.getAllVendors();
                if (vendors != null) {
                    String vendorsJson = objectMapper.writeValueAsString(vendors);
                    out.println(vendorsJson);
                } else {
                    out.println("null");
                }
                out.flush();
                break;
            case "ALL_CATEGORIES":
                ArrayList<Category> categories = Assets.getAllCategories();
                if (categories != null) {
                    String categoriesJson = objectMapper.writeValueAsString(categories);
                    out.println(categories);
                } else {
                    out.println("null");
                }
                out.flush();
                break;
            case "ALL_BRANCHES":
                ArrayList<Branch> branches = Assets.getAllBranches();
                if (branches != null) {
                    String branchesJson = objectMapper.writeValueAsString(branches);
                    out.println(branchesJson);
                } else {
                    out.println("null");
                }
                out.flush();
                break;
            case "EMPLOYEES":
                identifier = in.readLine();
                ArrayList<Employee> employees = Workforce.getAllEmployees(identifier);
                if (employees != null) {
                    String employeesJson = objectMapper.writeValueAsString(employees);
                    out.println(employeesJson);
                } else {
                    out.println("null");
                }
                out.flush();
                break;
            default:
                out.println("Invalid type");
                break;
        }
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
                        out.println("Product added successfully");
                    } else {
                        out.println("Failed to add product");
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing product JSON: " + e.getMessage());
                    e.printStackTrace();
                    out.println("Error parsing product JSON");
                }
                break;
            case "EMPLOYEE":
                try {
                    objectString = in.readLine();
                    Employee employee = objectMapper.readValue(objectString, Employee.class);
                    if (BranchManager.addEmployee(employee)) {
                        out.println("Employee added successfully");
                    } else {
                        out.println("Failed to add employee");
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing product JSON: " + e.getMessage());
                    e.printStackTrace();
                    out.println("Error parsing product JSON");
                }
                break;
            case "VENDOR":
                try {
                    objectString = in.readLine();
                    Vendor vendor = objectMapper.readValue(objectString, Vendor.class);
                    if (DataEntryManager.addVendor(vendor)) {
                        out.println("Vendor added successfully");
                    } else {
                        out.println("Failed to add vendor");
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing product JSON: " + e.getMessage());
                    e.printStackTrace();
                    out.println("Error parsing product JSON");
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
                out.flush();
                break;
            case "BRANCH":
                objectString = in.readLine();
                Branch branch = objectMapper.readValue(objectString, Branch.class);
                if (SuperAdmin.addBranch(branch)) {
                    out.println("Branch added Succesfully");
                } else {
                    out.println("Failed to add Branch");
                }
                out.flush();
                break;

            default:
                System.out.println("Invalid type received: " + type);
                out.println("Invalid type");
                break;
        }

    }

    private static void handleRemove(BufferedReader in, PrintWriter out) throws IOException {
        String type = in.readLine();
        String identifier;

        switch (type) {
            case "PRODUCT":
                identifier = in.readLine();

                if (DataEntryManager.removeProduct(identifier)) {
                    out.println("Product Removed");
                } else {
                    out.println("Failed to Remove Product");
                }
                out.flush();
                break;
            case "VENDOR":
                identifier = in.readLine();

                if (DataEntryManager.removeVendor(identifier)) {
                    out.println("Vendor Removed");
                } else {
                    out.println("Failed to Remove Vendor");
                }
                out.flush();
                break;
            case "CATEGORY":
                identifier = in.readLine();

                if (DataEntryManager.removeCategory(identifier)) {
                    out.println("Category Removed");
                } else {
                    out.println("Failed to Remove Category");
                }
                out.flush();
                break;
            case "BRANCH":
                identifier = in.readLine();
                if (SuperAdmin.removeBranch(identifier)) {
                    out.println("Branch Removed");
                } else {
                    out.println("Failed to Remove Branch");
                }
                out.flush();
                break;

        }
    }

    private static void handleLogin(BufferedReader in, PrintWriter out) throws IOException {
        String email = in.readLine();
        String password = in.readLine();

        Employee employee = LoginManager.login(email, password);

        if (employee != null) {
            String employeeJson = objectMapper.writeValueAsString(employee);
            out.println(employeeJson);
        } else {
            out.println("null");
        }
        out.flush();
    }

}
