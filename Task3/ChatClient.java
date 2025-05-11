import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234); // Connect to server
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Ask for the user's name
            System.out.print("Enter your name: ");
            String name = userInput.readLine();

            // Thread to read messages from server
            new Thread(() -> {
                String msg;
                try {
                    while ((msg = in.readLine()) != null) {
                        System.out.println(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Main thread sends user input with name
            String input;
            while ((input = userInput.readLine()) != null) {
                out.println(name + ": " + input);  // Prefix with name
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
