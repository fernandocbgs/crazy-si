import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import controleambiente.BattleRunner;
import sample.RoboFazNada;

/**
 * Compila e copia os arquivos .class para a pasta correta e executa o ambiente robocode
 * */
public class MainTeste {


	public static void main(String[] args) {
		roboBattle();
	}
	
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
