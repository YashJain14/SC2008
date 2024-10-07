import java.io.*;
import java.net.*;

/**
 * RFC 865 UDP Client Implementation for NTU
 * Name: dummyname
 * Group: dummy lab group
 * IP Address: [Will be determined at runtime]
 */
public class Rfc865UdpClient {
    private static final int QOTD_PORT = 17;
    private static final int MAX_PACKET_SIZE = 1024;
    private static final String SERVER_NAME = "swlab2-c.scse.ntu.edu.sg";

    public static void main(String[] argv) {
        DatagramSocket socket = null;

        try {
            // 1. Open UDP socket
            socket = new DatagramSocket();

            // 2. Send UDP request to server
            String clientIpAddress = InetAddress.getLocalHost().getHostAddress();
            String request = "dummyname, dummy lab group, " + clientIpAddress;
            byte[] requestData = request.getBytes();
            InetAddress address = InetAddress.getByName(SERVER_NAME);
            DatagramPacket requestPacket = new DatagramPacket(requestData, requestData.length, address, QOTD_PORT);
            socket.send(requestPacket);

            System.out.println("Request sent to server: " + SERVER_NAME);
            System.out.println("Request data: " + request);

            // 3. Receive UDP reply from server
            byte[] buffer = new byte[MAX_PACKET_SIZE];
            DatagramPacket replyPacket = new DatagramPacket(buffer, buffer.length);
            socket.receive(replyPacket);

            String quote = new String(replyPacket.getData(), 0, replyPacket.getLength());
            System.out.println("Quote received from server: " + quote);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}