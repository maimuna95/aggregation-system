import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ContentServerInterface extends Remote {
    String getFieldData(String field) throws RemoteException;
}
