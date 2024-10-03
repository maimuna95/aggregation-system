import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AggregatorInterface extends Remote {
    String getContentField(String field) throws RemoteException;
}
