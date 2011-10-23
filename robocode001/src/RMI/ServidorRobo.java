package RMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import sample.RoboFazNada;

public class ServidorRobo extends UnicastRemoteObject implements IRMIRobo {
	private static final long serialVersionUID = 1L;
	private ThreadServidorRobo _servidorRobo;
	private RoboFazNada _ar;
	private int _idRobo;
	public ServidorRobo(int idRobo, RoboFazNada ar) throws RemoteException {_idRobo = idRobo; _ar = ar;}

	@Override
	public void getRobo(INotify n) throws RemoteException {
		_servidorRobo = new ThreadServidorRobo(n, _ar);
		_servidorRobo.start();
	}

	@Override
	public RoboFazNada getRobot() throws RemoteException {
		return _servidorRobo.getRobot();
	}

	public void iniciar() {
		try {
			//ao invés de CreatRegistry, fiz um getRegistry
			Registry registry = LocateRegistry.getRegistry(3630); /* porta */
			registry.bind("ServidorROBOT" + _idRobo, this);
			System.out.println("Servidor iniciado... - ServidorROBOT" + _idRobo);
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
	}

}

class ThreadServidorRobo extends Thread {
	private INotify _iNotify;
	private RoboFazNada _ar = null;
	
	public ThreadServidorRobo(INotify n, RoboFazNada ar) {
		_iNotify = n;
		_ar = ar;
	}

	public RoboFazNada getRobot() {
		return _ar;
	}

	public void run() {
		try {
			System.out.println("(servidor) ");
			//retorno do robo
			_iNotify.doneIt();
		} catch (Exception e) {
			System.err.println("Err thread servidor robo: " + e.getMessage());
		}
	}
}