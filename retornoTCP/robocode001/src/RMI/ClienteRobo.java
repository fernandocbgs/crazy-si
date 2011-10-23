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
public class ClienteRobo extends UnicastRemoteObject {
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
	
	public ClienteRobo() throws RemoteException, NotBoundException {}

	public RoboFazNada recuperarRobo(int idRobo) throws RemoteException, NotBoundException{
        final Registry registry = LocateRegistry.getRegistry("localhost", 3637); /*ip, porta*/
        IRMIRobo _irmiRobot = (IRMIRobo) registry.lookup("ServidorROBOT"+idRobo);
        return _irmiRobot.getRobo();
	}

}