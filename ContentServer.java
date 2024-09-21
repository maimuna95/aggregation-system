import java.io.*;
import java.net.*;

public class ContentServer {
    private int port;
    private String content;

    public ContentServer(int port, String content) {
        this.port = port;
        this.content = content;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Content Server started at port: " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(content);
            clientSocket.close();
        }
    }

    public static void main(String[] args) throws IOException {
        ContentServer server1 = new ContentServer(8081, "Content from Server 1");
        ContentServer server2 = new ContentServer(8082, "Content from Server 2");

        new Thread(() -> {
            try {
                server1.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                server2.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
