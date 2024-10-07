import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rfc865UdpServer {
    private static final int QOTD_PORT = 17;
    private static final int MAX_PACKET_SIZE = 1024;
    private static List<String> quotes;

    public static void main(String[] argv) {
        loadQuotes();
        DatagramSocket socket = null;

        try {
            // 1. Open UDP socket at well-known port
            socket = new DatagramSocket(QOTD_PORT);
            System.out.println("Server running on port " + QOTD_PORT);

            while (true) {
                // 2. Listen for UDP request from client
                byte[] buffer = new byte[MAX_PACKET_SIZE];
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request);

                String clientAddress = request.getAddress().getHostAddress();
                int clientPort = request.getPort();
                System.out.println("Request received from " + clientAddress + ":" + clientPort);

                // 3. Send UDP reply to client
                String quote = getRandomQuote();
                byte[] replyData = quote.getBytes();
                DatagramPacket reply = new DatagramPacket(replyData, replyData.length, request.getAddress(), request.getPort());
                socket.send(reply);

                System.out.println("Quote sent to " + clientAddress + ":" + clientPort);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }

    private static void loadQuotes() {
        quotes = new ArrayList<>();
        quotes.add("The only way to do great work is to love what you do. - Steve Jobs");
        quotes.add("Innovation distinguishes between a leader and a follower. - Steve Jobs");
        quotes.add("Stay hungry, stay foolish. - Steve Jobs");
        // Add more quotes as needed
    }

    private static String getRandomQuote() {
        Random random = new Random();
        return quotes.get(random.nextInt(quotes.size()));
    }
}