import java.io.*;
import java.net.*;

public class Client {
    public static String getAggregatedContent() {
        StringBuilder content = new StringBuilder();
        try {
            Socket socket = new Socket("localhost", 8080);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                content.append(line).append("\n");
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString().trim();
    }

    public static void main(String[] args) {
        String content = getAggregatedContent();
        System.out.println("Aggregated Content:\n" + content);
    }
}
