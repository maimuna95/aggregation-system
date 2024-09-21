import org.junit.Test;
import static org.junit.Assert.*;

public class ClientTest {
    
    @Test
    public void testAggregatedContent() {
        // Start the content servers and the aggregation server in separate threads
        new Thread(() -> {
            try {
                ContentServer server1 = new ContentServer(8081, "Content from Server 1");
                server1.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                ContentServer server2 = new ContentServer(8082, "Content from Server 2");
                server2.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                AggregationServer aggregationServer = new AggregationServer(8080, new String[]{"8081", "8082"});
                aggregationServer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Allow servers to start properly before sending requests
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Test client aggregated content
        String content = Client.getAggregatedContent();
        assertTrue(content.contains("Content from Server 1"));
        assertTrue(content.contains("Content from Server 2"));
    }
}
