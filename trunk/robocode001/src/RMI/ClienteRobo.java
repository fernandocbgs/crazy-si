package RMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import sample.RoboFazNada;

/**
 * Cliente robos console
 * */
public class ClienteRobo extends UnicastRemoteObject implements INotify {
	private static final long serialVersionUID = 1L;
	//private IRMIRobo _msg;
	//private CheckerCallBack _checker;

//    public ClienteRobo() throws RemoteException, NotBoundException {
//        final Registry registry = LocateRegistry.getRegistry("localhost", 3637); /*ip, porta*/
//        _msg = (IRMIRobo) registry.lookup("ServidorROBOT1");  
//        //_checker = new CheckerCallBack(_msg);
//        
//        //espera o call back
//        //_msg.getRobo(_checker);
//    }
	
	private IRMIRobo _irmiRobot;
	public ClienteRobo() throws RemoteException, NotBoundException {}

	public void consultar(int idRobo) throws RemoteException, NotBoundException{
        final Registry registry = LocateRegistry.getRegistry("localhost", 3630); /*ip, porta*/
        _irmiRobot = (IRMIRobo) registry.lookup("ServidorROBOT"+idRobo);
        _irmiRobot.getRobo(this);
	}
	
	@Override
	public RoboFazNada doneIt() throws RemoteException {
		try {
			return _irmiRobot.getRobot();
		} catch (Exception e) {
			System.err.println("1 Erro CheckerCallBack " + e.getMessage());
		}
		return null;
	}

}