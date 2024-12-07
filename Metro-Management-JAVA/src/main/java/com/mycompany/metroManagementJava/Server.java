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
                    case "ADD":
                        handleAdd(in, out);
                    case "REMOVE":
                        handleRemove(in,out);
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
            case "CHECKOUT":
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
                    Vendor vendor = objectMapper.readValue(objectString,Vendor.class);
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
            case "CATEGORY":
                System.out.println("Handling ALL_CATEGORIES");
                break;
            case "CHECKOUT":
                System.out.println("Handling CHECKOUT");
                break;

            default:
                System.out.println("Invalid type received: " + type);
                out.println("Invalid type");
                break;
        }
    }

    private static void handleRemove(BufferedReader in, PrintWriter out) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
