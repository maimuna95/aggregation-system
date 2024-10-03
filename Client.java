import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            // Connect to ContentServer on port 1100
            Registry registry = LocateRegistry.getRegistry("localhost", 1100);
            ContentServerInterface server = (ContentServerInterface) registry.lookup("ContentServer");

            // Asking client what information they want to get from the server
            Scanner scanner = new Scanner(System.in);
            System.out.println("What data you want to retrieve?\n id, name, state, time_zone, lat, lon,local_date_time, local_date_time_full, air_temp, apparent_t, cloud, dewpt, press, rel_hum, wind_dir, wind_spd_kmh, wind_spd_kt");
            String field = scanner.nextLine();

            // Request data from the server
            String data = server.getData(field);
            System.out.println("Data for '" + field + "': " + data);

            scanner.close();
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
