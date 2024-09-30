import java.rmi.Naming;
import java.rmi.RemoteException;

public class ContentServer {
    public static void main(String[] args) {
        try {
            AggregatorInterface server = (AggregatorInterface) Naming.lookup("rmi://localhost/AggregationServer");

            // Simulate PUT operation with content
            String content = "{ \"id\": \"IDS60901\", \"name\": \"Adelaide\", \"state\": \"SA\", \"wind_spd_kt\": 8 }";

            // Retry mechanism for PUT request
            boolean success = false;
            int retries = 3; // Number of retry attempts

            while (retries > 0 && !success) {
                try {
                    server.putContent(content);
                    System.out.println("PUT Request Sent: " + content);
                    success = true; // Set success to true if request is successful
                } catch (RemoteException e) {
                    System.out.println("Failed to PUT content, retrying... Retries left: " + retries);
                    retries--;
                    if (retries > 0) {
                        Thread.sleep(2000); // Wait 2 seconds before retrying
                    } else {
                        System.out.println("Failed to PUT content after multiple attempts.");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
