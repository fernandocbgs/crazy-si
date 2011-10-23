package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

import sample.RoboFazNada;

public interface IRMIRobo extends Remote {
	public void getRobo(INotify n) throws RemoteException;
	public RoboFazNada getRobot() throws RemoteException;
}