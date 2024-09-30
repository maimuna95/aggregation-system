import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AggregatorInterface extends Remote {
    // GET request - returns feed
    List<String> getFeed() throws RemoteException;

    // PUT request with content
    void putContent(String content) throws RemoteException;
}
