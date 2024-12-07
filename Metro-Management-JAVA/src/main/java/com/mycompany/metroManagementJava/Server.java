package com.mycompany.metroManagementJava;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
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
        String identifier = in.readLine();

        switch (type) {
            case "PRODUCT":
                Product product = Assets.getProduct(identifier);
                if (product != null) {
                    out.println(objectMapper.writeValueAsString(product));
                } else {
                    out.println("null");
                }
                break;
            case "ALL_PRODUCTS":
                break;
            case "ALL_VENDORS":
                break;
            case "ALL_CATEGORIES":
                break;
            case "CHECKOUT":
                break;
            case "EMPLOYEES":

            default:
                out.println("Invalid type");
                break;
        }
    }
    

    private static void handleAdd(BufferedReader in, PrintWriter out) throws IOException {
        String type = in.readLine();
        System.out.println("Received type: " + type);
        String empID = in.readLine();
        System.out.println("Received empID: " + empID);
        String vendorID = in.readLine();
        System.out.println("Received vendorID: " + vendorID);

        String objectString = in.readLine();
        System.out.println("Received objectString: " + objectString);

        switch (type) {
            case "PRODUCT":
                System.out.println("Handling PRODUCT");
                try {
                    Product product = objectMapper.readValue(objectString, Product.class);
                    System.out.println("Parsed product: " + product);
                    if (DataEntryManager.addProduct(product, empID, vendorID)) {
                        System.out.println("Product added successfully");
                        out.println("Product added successfully");
                    } else {
                        System.out.println("Failed to add product");
                        out.println("Failed to add product");
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing product JSON: " + e.getMessage());
                    e.printStackTrace();
                    out.println("Error parsing product JSON");
                }
                break;
            case "ALL_PRODUCTS":
                System.out.println("Handling ALL_PRODUCTS");
                // Add logic for handling ALL_PRODUCTS
                break;
            case "ALL_VENDORS":
                System.out.println("Handling ALL_VENDORS");
                // Add logic for handling ALL_VENDORS
                break;
            case "ALL_CATEGORIES":
                System.out.println("Handling ALL_CATEGORIES");
                // Add logic for handling ALL_CATEGORIES
                break;
            case "CHECKOUT":
                System.out.println("Handling CHECKOUT");
                // Add logic for handling CHECKOUT
                break;
            case "EMPLOYEES":
                System.out.println("Handling EMPLOYEES");
                // Add logic for handling EMPLOYEES
                break;
            default:
                System.out.println("Invalid type received: " + type);
                out.println("Invalid type");
                break;
        }
    }

}

