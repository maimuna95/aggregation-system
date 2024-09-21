import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class AggregationServer {
    private int port;
    private String[] contentServers;

    public AggregationServer(int port, String[] contentServers) {
        this.port = port;
        this.contentServers = contentServers;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Aggregation Server started at port: " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String aggregatedContent = aggregateContent();
            out.println(aggregatedContent);

            clientSocket.close();
        }
    }

    private String aggregateContent() {
        ExecutorService executor = Executors.newCachedThreadPool();
        StringBuilder aggregatedData = new StringBuilder();

        for (String server : contentServers) {
            executor.submit(() -> {
                try {
                    Socket socket = new Socket("localhost", Integer.parseInt(server));
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    String content = in.readLine();
                    synchronized (aggregatedData) {
                        aggregatedData.append(content).append("\n");
                    }

                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return aggregatedData.toString();
    }

    public static void main(String[] args) throws IOException {
        String[] contentServers = {"8081", "8082"};
        AggregationServer aggregationServer = new AggregationServer(8080, contentServers);
        aggregationServer.start();
    }
}
