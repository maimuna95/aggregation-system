import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        try {
            AggregatorInterface server = (AggregatorInterface) Naming.lookup("rmi://localhost/AggregationServer");

            // Retry mechanism for GET request
            boolean success = false;
            int retries = 3; // Number of retry attempts

            while (retries > 0 && !success) {
                try {
                    List<String> feed = server.getFeed();
                    System.out.println("Feed: " + feed);
                    success = true; // Set success to true if request is successful
                } catch (RemoteException e) {
                    System.out.println("Failed to GET feed, retrying... Retries left: " + retries);
                    retries--;
                    if (retries > 0) {
                        Thread.sleep(2000); // Wait 2 seconds before retrying
                    } else {
                        System.out.println("Failed to GET feed after multiple attempts.");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
