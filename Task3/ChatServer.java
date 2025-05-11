import java.io.*;
import java.net.*;
import java.util.*;

public class ChatServer {
    private static Set<ClientHandler> clientHandlers = new HashSet<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Server started on port 1234...");

        while (true) {
            Socket clientSocket = serverSocket.accept(); // Wait for client
            System.out.println("New client connected");

            ClientHandler clientHandler = new ClientHandler(clientSocket, clientHandlers);
            clientHandlers.add(clientHandler); // Store client handler
            new Thread(clientHandler).start(); // Start new thread
        }
    }
}
