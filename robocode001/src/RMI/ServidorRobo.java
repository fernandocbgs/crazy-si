package RMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import sample.RoboFazNada;

public class ServidorRobo extends UnicastRemoteObject implements IRMIRobo {
	private static final long serialVersionUID = 1L;
	//private ThreadServidorRobo _servidorRobo;
	private RoboFazNada _ar;
	private int _idRobo;
	public ServidorRobo(int idRobo, RoboFazNada ar) throws RemoteException {_idRobo = idRobo; _ar = ar;}

	@Override
	public RoboFazNada getRobo() throws RemoteException {
		return _ar;
	}
	
	public void iniciar(){
		iniciar(true);
	}
	
	public void iniciar(boolean criar) {
		try {
			Registry registry;
			if (criar) {
				registry = LocateRegistry.createRegistry(3637); /* porta */
			} else {
				//ao invés de createRegistry, faz um getRegistry
				registry = LocateRegistry.getRegistry(3637); /* porta */
			}
			
			registry.bind("ServidorROBOT" + _idRobo, this);
			System.out.println("Servidor iniciado... - ServidorROBOT" + _idRobo);
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
	}

}