import java.io.*;
import java.net.*;

public class Rfc865TcpServer {
    private static final int QOTD_PORT = 1717;  // Using a higher port number for testing

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(QOTD_PORT)) {
            System.out.println("Server is listening on port " + QOTD_PORT);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    System.out.println("New client connected");
                    out.println("Here's your quote of the day: Life is what happens when you're busy making other plans.");
                }
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}