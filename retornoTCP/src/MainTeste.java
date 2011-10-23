import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

import RMI.ClienteRobo;
import RMI.ServidorRobo;
import controleambiente.BattleRunner;
import sample.RoboFazNada;

/**
 * Compila e copia os arquivos .class para a pasta correta e executa o ambiente robocode
 * */
public class MainTeste {


	public static void main(String[] args) {
		//CriaRegistry();
		roboBattle();
		ClienteRMI();
	}
	
	@SuppressWarnings("unused")
	private static void testeRMI(){
		int idRobo = 1;
		
		try {
			RoboFazNada ar = new RoboFazNada();
			ServidorRobo serv = new ServidorRobo(idRobo, ar);
			serv.iniciar(true);
			
			//lookup
			ClienteRobo cli = new ClienteRobo();
			RoboFazNada r1 = cli.recuperarRobo(1);
			System.out.println("Resposta: " + r1);
			
//			 Registry registry = LocateRegistry.getRegistry("localhost", 3637); /*ip, porta*/
//			 IRMIRobo _msg = (IRMIRobo) registry.lookup("ServidorROBOT"+idRobo);  
//		     CheckerCallBack _checker = new CheckerCallBack(_msg);
			
		     //_msg.getRobo(_checker);
			
		} catch (RemoteException e) {
			System.err.println("1 err: " + e.getMessage()); 
		} catch (NotBoundException e) {
			System.err.println("2 err: " + e.getMessage());
		}
	}
	
	private static void ClienteRMI(){
		try {
			ClienteRobo cli = new ClienteRobo();
			RoboFazNada r1 = cli.recuperarRobo(1);
			System.out.println("ROBO 1 " + r1);
			
			//if (r1 == null) return;
			
			r1.Hello();
			
			ArrayList<String> acoes = new ArrayList<String>();
			acoes.add(2+"");
			acoes.add(3+"");
			r1.SetAcoes(acoes);
			//r1.notify();
						
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
//	private static void CriaRegistry(){
//		try {
//			LocateRegistry.createRegistry(3637); /* porta */
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//	}
	
	private static void roboBattle(){
		//apenas para compilar
		@SuppressWarnings("unused")
		RoboFazNada rb;
		
		//copia os arquivos para o diretorio padrao do robocode
		String pastaOrigem = "D:\\Meus Documentos\\Emerson\\UTFPR\\6º Semestre\\Sistemas Inteligentes\\Parte 2\\TrabalhoFinal\\robocode001\\bin\\sample\\";
		String pastaDestino = "C:\\robocode\\robots\\sample\\";
		
		String pastaRMIOrigem = "D:\\Meus Documentos\\Emerson\\UTFPR\\6º Semestre\\Sistemas Inteligentes\\Parte 2\\TrabalhoFinal\\robocode001\\bin\\RMI\\";
		String pastaRMIDestino = "C:\\robocode\\robots\\RMI\\";
			
		String[] arqOr = new String[6];
		String[] arqDes = new String[arqOr.length];
		
		arqOr[0] = pastaOrigem + "RoboFazNada.class";
		arqDes[0] = pastaDestino + "RoboFazNada.class";
		arqOr[1] = pastaOrigem + "ControlRobot.class";
		arqDes[1] = pastaDestino + "ControlRobot.class";
		arqOr[2] = pastaOrigem + "RobosScaneados.class";
		arqDes[2] = pastaDestino + "RobosScaneados.class";	
		
		arqOr[3] = pastaRMIOrigem + "ClienteRobo.class";
		arqDes[3] = pastaRMIDestino + "ClienteRobo.class";
		arqOr[4] = pastaRMIOrigem + "IRMIRobo.class";
		arqDes[4] = pastaRMIDestino + "IRMIRobo.class";
		arqOr[5] = pastaRMIOrigem + "ServidorRobo.class";
		arqDes[5] = pastaRMIDestino + "ServidorRobo.class";
		
		for (int i = 0; i < arqOr.length; i++){
			try {
				copy(new File(arqOr[i]), new File(arqDes[i]));
			} catch (IOException e) { e.printStackTrace(); }
		}
		
		BattleRunner.iniciar();
	}
	
	/**
	 * Copies a file into another file.
	 *
	 * @param srcFile  the input file to copy
	 * @param destFile the output file to copy to
	 * @throws IOException if an I/O exception occurs
	 */
	public static void copy(File srcFile, File destFile) throws IOException {
		if (srcFile.equals(destFile)) {
			throw new IOException("You cannot copy a file onto itself");
		}

		byte buf[] = new byte[4096];

		FileInputStream in = null;
		FileOutputStream out = null;

		try {
			in = new FileInputStream(srcFile);
			out = new FileOutputStream(destFile);

			while (in.available() > 0) {
				out.write(buf, 0, in.read(buf, 0, buf.length));
			}
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.close();
			}
		}
	}
	
}
