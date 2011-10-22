package sample;

import java.awt.Color;
import robocode.BattleEndedEvent;
import robocode.DeathEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.RobotDeathEvent;
import robocode.RoundEndedEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

/**
 * Robo Faz Nada<br />
 * ideia é ser controlado via Jason 
 * */
public class RoboFazNadaBackup extends Robot /*implements IRobo*/ {
	//private int _portaServidor = 7896;
	//private int _portaServidorRobo = 7890;
	//private static String mensagem = "Ola sou um robo cliente TCP";
	//private String _ip = "localhost";
	//private int _acao = -1;
	//private TCPServer _tcpServer;
	//private ClienteRoboTCP _cliTCP;
	
	public RoboFazNadaBackup(){
		//inicia();
	}
	
//	private void inicia(){
//		if (_tcpServer != null) {_tcpServer.continuar=false; _tcpServer.stop();}
//		_tcpServer = null;
//		_tcpServer = new TCPServer(_portaServidorRobo, this);
//		if (_cliTCP != null) {_cliTCP.continuar = false; _cliTCP.stop();};
//		_cliTCP = null;
//		_cliTCP = new ClienteRoboTCP(_ip, _portaServidor);
//		_acao = -1;
//	}
//	private void iniciaTCP(){
//		//cria o servidor do Robo
//		_tcpServer.start();
//		//cria o cliente TCP
//		_cliTCP.start();
//	}
//	
//	@Override
//	public void stop() {
//		inicia();
//		super.stop();
//	}
	

	
	public void run(){
		setBodyColor(Color.WHITE);
		setGunColor(Color.BLACK);
		setRadarColor(Color.BLUE);
		setBulletColor(Color.green);
		setScanColor(Color.GREEN);
		//iniciaTCP();

		
		while(true){
			//stop();
			//turnRadarRight(360);
			//turnRadarLeft(360);
//			ahead(1000);
//			turnRight(90);
			
//			System.out.println("#_acao = " + _acao);
//			
//			if (_acao != -1) {
//				ahead(1000);
//				System.out.println("# Mover !!!!");
//				_acao = -1;
//			}
			
		}
	}
	
	@Override
	public void onHitWall(HitWallEvent event) {
		
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {

	}
	
	@Override
	public void onBattleEnded(BattleEndedEvent event) {
		//inicia();
		super.onBattleEnded(event);
	}
	
	@Override
	public void onDeath(DeathEvent event) {
		//inicia();
		super.onDeath(event);
	}
	
	@Override
	public void onRoundEnded(RoundEndedEvent event) {
		//inicia();
		super.onRoundEnded(event);
	}
	
	@Override
	public void onRobotDeath(RobotDeathEvent event) {
		//inicia();
		super.onRobotDeath(event);
	}
	
	@Override
	public void onWin(WinEvent event) {
		//inicia();
		super.onWin(event);
	}
	
//	public void iniciarServidor(){
//		try {
//			ServerSocket listenSocket = new ServerSocket(_portaServidorRobo);
//			while(true) {
//				Socket clientSocket = listenSocket.accept();
//				
//				DataInputStream in = new DataInputStream(clientSocket.getInputStream());
//				
//				byte[] pacote = new byte[1000];
//				in.read(pacote);
//				Analisar(pacote);
//				
//				//String data = in.readUTF(); // read a line of data from the stream
//				//print("[s] recebi: " + data);
//				//DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
//				//out.writeUTF(data);
//			}
//			
//		} catch (IOException e) {
//			System.err.println("[s] Listen socket:" + e.getMessage());
//		}
//	}
//	
//	public void Analisar(byte[] pacote) {
//		CriadorPacotes.TipoPacotes tp = getTipo(pacote);
//		switch (tp) {
//		//case oiMestre:AnaliseOi(pacote);break;
//		case oquedevoFazer:
//			AnaliseOQueDevoFazer(pacote);
//			break;
//		case respostaAcao:
//			AnaliseRespostaAcao(pacote);
//			break;
//		}
//	}
	
	
	
	//--------------------------------------------------------------------
//	public int AnaliseOQueDevoFazer(byte[] pacote){
//		ByteBuffer bb = ByteBuffer.wrap(pacote);
//		bb.order(ByteOrder.BIG_ENDIAN);
//		bb.getInt(); //tipo
//		return 3; //retorna o número
//	}
//	public void AnaliseRespostaAcao(byte[] pacote){
//		ByteBuffer bb = ByteBuffer.wrap(pacote);
//		bb.order(ByteOrder.BIG_ENDIAN);
//		bb.getInt(); //tipo
//		ExecutarAcao(bb.getInt());
//	}
//	//--------------------------------------------------------------------
//	//retorna o tipo do pacote
//	public CriadorPacotes.TipoPacotes getTipo(byte[] pacote){
//		ByteBuffer bb = ByteBuffer.wrap(pacote);
//		bb.order(ByteOrder.BIG_ENDIAN);
//		return new CriadorPacotes().getTipoPacote(bb.getInt());
//	}
//	
//	private String readString(ByteBuffer bb){
//		//----------------- string ----------
//		short numero_caracteres = bb.getShort();
//		byte[] bNome = new byte[numero_caracteres];
//		for (int i = 0; i < numero_caracteres; i++) {
//			bNome[i] = bb.get();
//		}
//		return new String(bNome);
//	}
////	private byte[] readVetorBytes(ByteBuffer bb){
////		short tam_vetor = bb.getShort();
////		byte[] rt = new byte[tam_vetor];
////		for (int i = 0; i < tam_vetor; i++) {
////			rt[i] = bb.get();
////		}
////		return rt;
////	}
//	
//	
//	//-----------------------------------------------------------------------------
//	//------------------------ CLIENTE --------------------------------------------
//	//-----------------------------------------------------------------------------
//
//	/**
//	 * Ações Simples
//	 * */
//	@Override
//	public void ExecutarAcao(int acao) {
//		_acao = acao;
////		switch (acao) {
////		case 1:
////			ahead(1000);
////			break;
////		case 2:
////			back(1000);
////			break;
////		case 3:
////			turnLeft(1000);
////			break;
////		}
//		
//		
//	}
}

//class ClienteRoboTCP extends Thread {
//	private int _portaServidor = 7896;
//	private String _ip = "localhost";
//	public boolean continuar = true;
//	
//	public ClienteRoboTCP(String ip, int portaServidor){
//		_ip = ip;
//		_portaServidor = portaServidor;
//	}
//	
//	public void run(){
//		while(continuar){
//			iniciarCliente();
//			//espera um pouco antes de enviar a próxima mensagem
//			try {Thread.sleep(1000); } catch (InterruptedException e) {e.printStackTrace();}
//		}
//	}
//	
//	public void iniciarCliente(){
//		Socket s = null;
//		try {
//			s = new Socket(_ip, _portaServidor);
//			DataOutputStream out = new DataOutputStream(s.getOutputStream());
//			
//			//out.write(new CriadorPacotes().getPacoteOi());
//			out.write(new CriadorPacotes().getPacoteOqueDevoFazer());
//			
//			//out.writeUTF(mensagem); // UTF is a string encoding see Sn. 4.4
//			
//			//seria uma resposta do servidor
//			//DataInputStream in = new DataInputStream(s.getInputStream());
//			//String data = in.readUTF(); // read a line of data from the stream
//			//System.out.println("[c] Received: " + data);
//			
//		} catch (UnknownHostException e) {
//			System.err.println("[c] Socket:" + e.getMessage());
//		} catch (EOFException e) {
//			System.err.println("[c] EOF:" + e.getMessage());
//		} catch (IOException e) {
//			System.err.println("[c] readline:" + e.getMessage());
//		} finally {
//			if (s != null)
//				try {
//					s.close();
//				} catch (IOException e) {
//					System.err.println("[c] close:" + e.getMessage());
//				}
//		}
//	}	
//}