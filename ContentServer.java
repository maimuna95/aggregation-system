import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import org.json.JSONObject;

public class ContentServer implements ContentServerInterface {

    // List of data stored in the content server
    private String weatherData = """
    {
        "id": "IDS60901",
        "name": "Adelaide (West Terrace / ngayirdapira)",
        "state": "SA",
        "time_zone": "CST",
        "lat": -34.9,
        "lon": 138.6,
        "local_date_time": "15/04:00pm",
        "local_date_time_full": "20230715160000",
        "air_temp": 13.3,
        "apparent_t": 9.5,
        "cloud": "Partly cloudy",
        "dewpt": 5.7,
        "press": 1023.9,
        "rel_hum": 60,
        "wind_dir": "S",
        "wind_spd_kmh": 15,
        "wind_spd_kt": 8
    }
    """;

    @Override
    public String getFieldData(String field) {
        try {
            // Convert the string to a JSON object
            JSONObject weatherJson = new JSONObject(weatherData);

            // Check if the requested field exists and return its value
            if (weatherJson.has(field)) {
                return weatherJson.get(field).toString();
            } else {
                return "Field not found";
            }
        } catch (Exception e) {
            System.err.println("Error in ContentServer: " + e.toString());
            return "Error retrieving the data.";
        }
    }

    public static void main(String[] args) {
        try {
            ContentServer server = new ContentServer();
            ContentServerInterface stub = (ContentServerInterface) UnicastRemoteObject.exportObject(server, 0);

            // Bind the Content Server to RMI registry
            Registry registry = LocateRegistry.createRegistry(2000);
            registry.rebind("ContentServerService", stub);

            System.out.println("Content Server is running...");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
