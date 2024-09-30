import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AggregationServer extends UnicastRemoteObject implements AggregatorInterface {
    private ConcurrentHashMap<String, Long> contentStore; // Stores content with timestamps
    private List<String> feed;
    private final long EXPIRATION_TIME_MS = 30000; // 30 seconds expiration

    public AggregationServer() throws RemoteException {
        super();
        feed = new ArrayList<>();
        contentStore = new ConcurrentHashMap<>();

        // Schedule periodic cleanup of expired content
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(this::removeExpiredContent, 10, 10, TimeUnit.SECONDS);
    }

    // GET request to retrieve feed
    @Override
    public synchronized List<String> getFeed() throws RemoteException {
        if (feed.isEmpty()) {
            throw new RemoteException("Feed is currently empty.");
        }
        System.out.println("GET Request Processed.");
        return new ArrayList<>(feed); // Return a copy of the feed
    }

    // PUT request to add content
    @Override
    public synchronized void putContent(String content) throws RemoteException {
        // Validate content (ensure id is present)
        if (!content.contains("id")) {
            throw new RemoteException("Content missing ID field, cannot be processed.");
        }

        // Add content with timestamp
        contentStore.put(content, System.currentTimeMillis());
        feed.add(content);

        System.out.println("PUT Request Processed: " + content);
    }

    // Remove expired content (older than 30 seconds)
    private void removeExpiredContent() {
        long currentTime = System.currentTimeMillis();
        contentStore.forEach((content, timestamp) -> {
            if (currentTime - timestamp > EXPIRATION_TIME_MS) {
                contentStore.remove(content);
                feed.remove(content);
                System.out.println("Expired Content Removed: " + content);
            }
        });
    }

    public static void main(String[] args) {
        try {
            AggregationServer server = new AggregationServer();
            Naming.rebind("AggregationServer", server);
            System.out.println("Aggregation Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
