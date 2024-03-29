package executor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import controleambiente.BattleRunner;
import sample.RoboFazNada;
import tcp.interfaces.IRoboTCP;

/**
 * Compila e copia os arquivos .class para a pasta correta e executa o ambiente robocode
 * -Ddebug=true -Djava.security.main -DJava.security.policy=policy -Xmx512M -DNOSECURITY=true
 * -Ddebug=true -DNOSECURITY=true
 * */
public class MainTeste {

	public static void main(String[] args) {
		new FrmTesteClienteJason().setVisible(true);
		roboBattle();
	}
	
	@SuppressWarnings("unused")
	public static void roboBattle(){
		ConstantesExecucao constantes = new ConstantesExecucao();
		//apenas para compilar
		RoboFazNada rb; IRoboTCP is;
		
		String posicoesIniciais = "0.0,0.0,180, 500.0,500.0,270";
		//posicoesIniciais = "0,0,180, 50,80,270";
		posicoesIniciais = "";
		
		//copia os arquivos para o diretorio padrao do robocode
		String pProjeto =  constantes.caminhoProjetoRobocode;
		String pJason = constantes.caminhoProjetoJason;
		String pRobocode = constantes.caminhoRobocode;
		
		String pastaOrigem = pProjeto + "/bin/sample/";
		String pastaDestino = pRobocode + "/robots/sample/";
		
		String pastaTCPOrigem = pJason + "/tcp/";
		String pastaTCPDestino = pRobocode + "/robots/tcp/";
		
		List<String> arqOr = new ArrayList<String>();
		List<String> arqDes = new ArrayList<String>();
		
		arqOr.add(pastaOrigem + "RoboFazNada.class");
		arqDes.add(pastaDestino + "RoboFazNada.class");
		arqOr.add(pastaOrigem + "AvisarJason.class");
		arqDes.add(pastaDestino + "AvisarJason.class");
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
		arqOr.add(pastaTCPOrigem + "interfaces/IRoboTCP.class");
		arqDes.add(pastaTCPDestino + "interfaces/IRoboTCP.class");
		
		arqOr.add(pastaTCPOrigem + "interfaces/IJason.class");
		arqDes.add(pastaTCPDestino + "interfaces/IJason.class");
		
		arqOr.add(pastaTCPOrigem + "pacotes/AnalisePacotes.class");
		arqDes.add(pastaTCPDestino + "pacotes/AnalisePacotes.class");
		arqOr.add(pastaTCPOrigem + "pacotes/CriadorPacotes$TipoPacotes.class");
		arqDes.add(pastaTCPDestino + "pacotes/CriadorPacotes$TipoPacotes.class");
		arqOr.add(pastaTCPOrigem + "pacotes/CriadorPacotes.class");
		arqDes.add(pastaTCPDestino + "pacotes/CriadorPacotes.class");

		for (int i = 0; i < arqOr.size(); i++){
			if (!arqOr.get(i).equals("") && !arqDes.get(i).equals("")){
				try {
					CriarPastas(arqDes.get(i));
					copy(new File(arqOr.get(i)), new File(arqDes.get(i)));
				} catch (IOException e) { e.printStackTrace(); }
			}
		}
		
		
		//ROBOS:
		ArrayList<String> robos = new ArrayList<String>();
		robos.add("sample.RoboFazNada");
		robos.add("sample.RoboFazNada");
		robos.add("sample.RoboFazNada");
		
		//C:/Robocode
		new BattleRunner(pRobocode, posicoesIniciais, robos).iniciar();
	}
	
	/**
	 * Remove a parte do arquivo e cria a pasta destino se ela n�o existir
	 * */
	private static void CriarPastas(String pastaDestino){
		pastaDestino = pastaDestino.replace("\\", "/");
		String pasta = pastaDestino;
		if (pastaDestino.lastIndexOf("/") > 0) {
			pasta = pastaDestino.substring(0, pastaDestino.lastIndexOf("/")) + "/";
		}
		funcoes.FuncoesGerais.VerificaECriaDiretorio(pasta);
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
