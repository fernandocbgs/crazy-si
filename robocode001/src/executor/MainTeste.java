package executor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controleambiente.BattleRunner;
import sample.RoboFazNada;
import tcp.interfaces.IServidorTCP;

/**
 * Compila e copia os arquivos .class para a pasta correta e executa o ambiente robocode
 * -Ddebug=true -Djava.security.main -DJava.security.policy=policy -Xmx512M -DNOSECURITY=true
 * */
public class MainTeste {

	public static void main(String[] args) {
		new FrmTesteClienteJason().setVisible(true);
		roboBattle();
	}
	
	@SuppressWarnings("unused")
	public static void roboBattle(){
		//apenas para compilar
		RoboFazNada rb; IServidorTCP is;
		
		String posicoesIniciais = "0.0,0.0,180, 500.0,500.0,270";
		//posicoesIniciais = "0,0,180, 50,80,270";
		posicoesIniciais = "";
		
		//copia os arquivos para o diretorio padrao do robocode
		//linux
		//String p1 = "/home/todos/alunos/ct/a529818/Area de Trabalho/ProjetosSI/robocode001";
		//String pastaRobocode = "/home/todos/alunos/ct/a529818/Area de Trabalho/ProjetosSI/C_robocode";
		String p1 = "D:/Meus Documentos/Emerson/UTFPR/6� Semestre/Sistemas Inteligentes/Parte 2/TrabalhoFinal/robocode001";
		String pastaRobocode = "C:/robocode";

		
		String pastaOrigem = p1 + "/bin/sample/";
		String pastaDestino = pastaRobocode + "/robots/sample/";
		
		String pastaTCPOrigem = p1 + "/bin/tcp/";
		String pastaTCPDestino = pastaRobocode + "/robots/tcp/";
		
		List<String> arqOr = new ArrayList<String>();
		List<String> arqDes = new ArrayList<String>();
		
		arqOr.add(pastaOrigem + "RoboFazNada.class");
		arqDes.add(pastaDestino + "RoboFazNada.class");
		arqOr.add(pastaOrigem + "ControlRobot.class");
		arqDes.add(pastaDestino + "ControlRobot.class");
		arqOr.add(pastaOrigem + "RobosScaneados.class");
		arqDes.add(pastaDestino + "RobosScaneados.class");
		arqOr.add(pastaOrigem + "ControleTCPRobot.class");
		arqDes.add(pastaDestino + "ControleTCPRobot.class");
		
		//codigo pego na web
		arqOr.add(pastaOrigem + "Definitivo.class");
		arqDes.add(pastaDestino + "Definitivo.class");
		arqOr.add(pastaOrigem + "Enemigo.class");
		arqDes.add(pastaDestino + "Enemigo.class");
		
		arqOr.add(pastaTCPOrigem + "TCPServer.class");
		arqDes.add(pastaTCPDestino + "TCPServer.class");
		arqOr.add(pastaTCPOrigem + "ServerThread.class");
		arqDes.add(pastaTCPDestino + "ServerThread.class");
		arqOr.add(pastaTCPOrigem + "interfaces/IServidorTCP.class");
		arqDes.add(pastaTCPDestino + "interfaces/IServidorTCP.class");
		arqOr.add(pastaTCPOrigem + "pacotes/AnalisePacotes.class");
		arqDes.add(pastaTCPDestino + "pacotes/AnalisePacotes.class");
		arqOr.add(pastaTCPOrigem + "pacotes/CriadorPacotes$TipoPacotes.class");
		arqDes.add(pastaTCPDestino + "pacotes/CriadorPacotes$TipoPacotes.class");
		arqOr.add(pastaTCPOrigem + "pacotes/CriadorPacotes.class");
		arqDes.add(pastaTCPDestino + "pacotes/CriadorPacotes.class");

		for (int i = 0; i < arqOr.size(); i++){
			if (!arqOr.get(i).equals("") && !arqDes.get(i).equals("")){
				try {
					copy(new File(arqOr.get(i)), new File(arqDes.get(i)));
				} catch (IOException e) { e.printStackTrace(); }
			}
		}
		
		//C:/Robocode
		new BattleRunner(pastaRobocode, posicoesIniciais).iniciar();
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