import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class AggregationServer implements AggregatorInterface {

    private ContentServerInterface contentServer;

    public AggregationServer() {
        try {
            // Connect to the Content Server
            Registry registry = LocateRegistry.getRegistry("localhost", 2000);
            contentServer = (ContentServerInterface) registry.lookup("ContentServerService");
        } catch (Exception e) {
            System.err.println("AggregationServer exception: " + e.toString());
            e.printStackTrace();
        }
    }

    // Method to get the requested field from ContentServer
    @Override
    public String getContentField(String field) {
        try {
            return contentServer.getFieldData(field);  // Forward the request to ContentServer
        } catch (Exception e) {
            System.err.println("Error in AggregationServer: " + e.toString());
            return "Error retrieving the data.";
        }
    }

    public static void main(String[] args) {
        try {
            AggregationServer server = new AggregationServer();
            AggregatorInterface stub = (AggregatorInterface) UnicastRemoteObject.exportObject(server, 0);

            // Bind the Aggregation Server to RMI registry
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("AggregatorService", stub);

            System.out.println("Aggregation Server is running...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
