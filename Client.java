import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Connect to the Aggregation Server
            Registry registry = LocateRegistry.getRegistry("localhost", 2000);
            AggregatorInterface aggregator = (AggregatorInterface) registry.lookup("AggregatorService");

            // Ask user for the field they want to retrieve
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the field you want to retrieve (e.g., local_date_time_full, air_temp, wind_spd_kmh): ");
            String field = scanner.nextLine();

            // Send request to Aggregation Server
            String result = aggregator.getContentField(field);

            // Print the result
            System.out.println("Requested Field: " + field);
            System.out.println("Result: " + result);
            
            scanner.close();
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
