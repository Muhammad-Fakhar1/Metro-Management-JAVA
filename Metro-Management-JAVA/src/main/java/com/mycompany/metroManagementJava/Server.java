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


    private static final int PORT=12345;
    private static final int THREAD_POOL_SIZE=10;
    private static final ObjectMapper objectMapper=new ObjectMapper();

    public static void main(String[] args) {
        ExecutorService threadPool= Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        objectMapper.registerModule(new JavaTimeModule());

        try(ServerSocket serverSocket=new ServerSocket(PORT)){
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
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String command;
            while ((command = in.readLine()) != null) {
                System.out.println("Received command: " + command);

                switch (command) {
                 
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
}

