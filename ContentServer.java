import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class ContentServer implements ContentServerInterface {
    private HashMap<String, String> dataStore; // Using Hashma to store data in the server

    public ContentServer() {
        // Date stored in Content server
        dataStore = new HashMap<>();
        dataStore.put("id", "IDS60901");
        dataStore.put("name", "Adelaide (West Terrace / ngayirdapira)");
        dataStore.put("state", "SA");
        dataStore.put("time_zone", "CST");
        dataStore.put("lat", "-34.9");
        dataStore.put("lon", "138.6");
        dataStore.put("local_date_time", "15/04:00pm");
        dataStore.put("local_date_time_full", "20230715160000");
        dataStore.put("air_temp", "13.3");
        dataStore.put("apparent_t", "9.5");
        dataStore.put("cloud", "Partly cloudy");
        dataStore.put("dewpt", "5.7");
        dataStore.put("press", "1023.9");
        dataStore.put("rel_hum", "60");
        dataStore.put("wind_dir", "S");
        dataStore.put("wind_spd_kmh", "15");
        dataStore.put("wind_spd_kt", "8");
    }

    // This method will return the requested data from the contentserver
    @Override
    public String getData(String field) throws RemoteException {
        return dataStore.getOrDefault(field, "Field not found");
    }


    // This is the main function
    public static void main(String[] args) {
        try {
            ContentServer server = new ContentServer();
            ContentServerInterface stub = (ContentServerInterface) UnicastRemoteObject.exportObject(server, 0);

            // Use a different port (1100)
            Registry registry = LocateRegistry.createRegistry(1100);
            registry.bind("ContentServer", stub);

            System.out.println("ContentServer is running on port 1100");
        } catch (Exception e) {
            System.err.println("ContentServer exception: " + e.toString());
            e.printStackTrace();
        }
    }

}
