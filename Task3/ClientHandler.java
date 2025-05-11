import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Set<ClientHandler> clients;

    public ClientHandler(Socket socket, Set<ClientHandler> clients) throws IOException {
        this.socket = socket;
        this.clients = clients;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public void run() {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Received: " + message);
                for (ClientHandler ch : clients) {
                    if (ch != this) {
                        ch.writer.println(message); // Send message to all other clients
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Client disconnected.");
        } finally {
            try {
                socket.close();
                clients.remove(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

