package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import sample.RoboFazNada;

public interface IRMIRobo extends Remote {
	public RoboFazNada getRobo() throws RemoteException;
}