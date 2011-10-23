package RMI;
import java.rmi.Remote;
import java.rmi.RemoteException;
import sample.RoboFazNada;

public interface INotify extends Remote {
    public RoboFazNada doneIt() throws RemoteException;
}