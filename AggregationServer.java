import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AggregationServer {
    public static void main(String[] args) {
        try {
            // Connect to ContentServer on port 1100
            Registry registry = LocateRegistry.getRegistry("localhost", 1100);
            ContentServerInterface contentServer = (ContentServerInterface) registry.lookup("ContentServer");

            System.out.println("AggregationServer is running, and connected to ContentServer.");

            // This can be extended to handle multiple clients, expiration logic, etc.
        } catch (Exception e) {
            System.err.println("AggregationServer exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
