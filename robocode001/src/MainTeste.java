import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import controleambiente.BattleRunner;
import sample.RoboFazNada;
import servidor_tcp.gui.FrameServidor;

/**
 * Compila e copia os arquivos .class para a pasta correta e executa o ambiente robocode
 * -Djava.security.main -DJava.security.policy=policy -Xmx512M -DNOSECURITY=true
 * */
public class MainTeste {


	public static void main(String[] args) {
		testeTCP();
		roboBattle();
	}
	
	private static void testeTCP(){
		FrameServidor frm = new FrameServidor();
		frm.setVisible(true);
//		TCPServer sr = new TCPServer(7890);
//		sr.iniciarServidor();
//		
////		TCPServer srRobo = new TCPServer(7891);
////		srRobo.iniciarServidor();
//		
//		
//		TCPClient clienteRobo = new TCPClient(7890, "localhost");
//		clienteRobo.iniciarCliente();
		
	} 
	
	@SuppressWarnings("unused")
	private static void roboBattle(){
		//apenas para compilar
		@SuppressWarnings("unused")
		RoboFazNada rb;
		
		//copia os arquivos para o diretorio padrao do robocode
		String pastaOrigem = "D:\\Meus Documentos\\Emerson\\UTFPR\\6º Semestre\\Sistemas Inteligentes\\Parte 2\\TrabalhoFinal\\robocode001\\bin\\sample\\";
		String pastaDestino = "C:\\robocode\\robots\\sample\\";
		
		String pastaTCPOrigem = "D:\\Meus Documentos\\Emerson\\UTFPR\\6º Semestre\\Sistemas Inteligentes\\Parte 2\\TrabalhoFinal\\robocode001\\bin\\servidor_tcp\\";
		String pastaTCPDestino = "C:\\robocode\\robots\\servidor_tcp\\";
			
		String[] arqOr = new String[11];
		String[] arqDes = new String[arqOr.length];
		
		arqOr[0] = pastaOrigem + "RoboFazNada.class";
		arqDes[0] = pastaDestino + "RoboFazNada.class";
		arqOr[1] = pastaOrigem + "ControlRobot.class";
		arqDes[1] = pastaDestino + "ControlRobot.class";
		arqOr[2] = pastaOrigem + "RobosScaneados.class";
		arqDes[2] = pastaDestino + "RobosScaneados.class";
		arqOr[3] = pastaOrigem + "ControleTCPRobot.class";
		arqDes[3] = pastaDestino + "ControleTCPRobot.class";
		
		arqOr[4] = pastaTCPOrigem + "TCPClient.class";
		arqDes[4] = pastaTCPDestino + "TCPClient.class";
		arqOr[5] = pastaTCPOrigem + "TCPServer.class";
		arqDes[5] = pastaTCPDestino + "TCPServer.class";
		arqOr[6] = pastaTCPOrigem + "gui\\IAcoesTCP.class";
		arqDes[6] = pastaTCPDestino + "gui\\IAcoesTCP.class";
		arqOr[7] = pastaTCPOrigem + "gui\\ThreadIniciaServidor.class";
		arqDes[7] = pastaTCPDestino + "gui\\ThreadIniciaServidor.class";
		arqOr[8] = pastaTCPOrigem + "pacotes\\AnalisePacotes.class";
		arqDes[8] = pastaTCPDestino + "pacotes\\AnalisePacotes.class";
		arqOr[9] = pastaTCPOrigem + "pacotes\\CriadorPacotes$TipoPacotes.class";
		arqDes[9] = pastaTCPDestino + "pacotes\\CriadorPacotes$TipoPacotes.class";
		arqOr[10] = pastaTCPOrigem + "pacotes\\CriadorPacotes.class";
		arqDes[10] = pastaTCPDestino + "pacotes\\CriadorPacotes.class";
		
		for (int i = 0; i < arqOr.length; i++){
			if (!arqOr[i].equals("") && !arqDes[i].equals("")){
				try {
					copy(new File(arqOr[i]), new File(arqDes[i]));
				} catch (IOException e) { e.printStackTrace(); }
			}
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
