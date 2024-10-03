import java.rmi.Remote;
import java.rmi.RemoteException;

//Creating interface for contentServer
public interface ContentServerInterface extends Remote {
    // Declaring method so that it can get the data based on Client's request
    String getData(String field) throws RemoteException;
}
