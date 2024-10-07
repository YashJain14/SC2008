import java.io.*;
import java.net.*;

public class Rfc865TcpClient {
    private static final int QOTD_PORT = 1717;  // Updated to match the server

    public static void main(String[] argv) {
        if (argv.length < 1) {
            System.out.println("Usage: java Rfc865TcpClient <server_address>");
            return;
        }

        String serverAddress = argv[0];
        Socket socket = null;

        try {
            // 1. Establish TCP connection with server
            socket = new Socket(serverAddress, QOTD_PORT);

            // 2. Send TCP request to server
            OutputStream os = socket.getOutputStream();
            String request = "Request for Quote of the Day";
            os.write(request.getBytes());

            // 3. Receive TCP reply from server
            InputStream is = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String quote = reader.readLine();
            System.out.println("Quote received: " + quote);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null && !socket.isClosed()) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}