package sample;

import interfaces.IRobo;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import servidor_tcp.TCPServer;
import servidor_tcp.pacotes.CriadorPacotes;

/**
 * Robo Faz Nada<br />
 * ideia é ser controlado via Jason 
 * */
public class RoboFazNada extends Robot implements IRobo {
	private boolean _pausa = false;
	private boolean _executandoAcao = false;
	
	private static int portaServidor = 7896;
	private static int portaServidorRobo = 7890;
	//private static String mensagem = "Ola sou um robo cliente TCP";
	private static String ip = "localhost";
	
	public void run(){
		setBodyColor(Color.WHITE);
		setGunColor(Color.BLACK);
		setRadarColor(Color.BLUE);
		setBulletColor(Color.green);
		setScanColor(Color.GREEN);
		
		//cria o servidor do Robo
		new TCPServer(portaServidorRobo, this).start();
		
		while(!_pausa){
			if (!_executandoAcao) clienteTCP();
			
			//espera um pouco antes de enviar a próxima mensagem
			//try {Thread.sleep(1000); } catch (InterruptedException e) {e.printStackTrace();}
			
			//stop();
			//turnRadarRight(360);
			//turnRadarLeft(360);
//			ahead(1000);
//			turnRight(90);
		}
	}
	
	@Override
	public void onHitWall(HitWallEvent event) {
		
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {

	}
	
	public void iniciarServidor(){
		try {
			ServerSocket listenSocket = new ServerSocket(portaServidorRobo);
			while(true) {
				Socket clientSocket = listenSocket.accept();
				
				DataInputStream in = new DataInputStream(clientSocket.getInputStream());
				
				byte[] pacote = new byte[1000];
				in.read(pacote);
				Analisar(pacote);
				
				//String data = in.readUTF(); // read a line of data from the stream
				//print("[s] recebi: " + data);
				//DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
				//out.writeUTF(data);
			}
			
		} catch (IOException e) {
			System.err.println("[s] Listen socket:" + e.getMessage());
		}
	}
	
	public void Analisar(byte[] pacote) {
		CriadorPacotes.TipoPacotes tp = getTipo(pacote);
		switch (tp) {
		//case oiMestre:AnaliseOi(pacote);break;
		case oquedevoFazer:
			AnaliseOQueDevoFazer(pacote);
			break;
		case respostaAcao:
			AnaliseRespostaAcao(pacote);
			break;
		}
	}
	
	
	
	//--------------------------------------------------------------------
	public int AnaliseOQueDevoFazer(byte[] pacote){
		ByteBuffer bb = ByteBuffer.wrap(pacote);
		bb.order(ByteOrder.BIG_ENDIAN);
		bb.getInt(); //tipo
		return 3; //retorna o número
	}
	public void AnaliseRespostaAcao(byte[] pacote){
		ByteBuffer bb = ByteBuffer.wrap(pacote);
		bb.order(ByteOrder.BIG_ENDIAN);
		bb.getInt(); //tipo
		ExecutarAcao(bb.getInt());
	}
	//--------------------------------------------------------------------
	//retorna o tipo do pacote
	public CriadorPacotes.TipoPacotes getTipo(byte[] pacote){
		ByteBuffer bb = ByteBuffer.wrap(pacote);
		bb.order(ByteOrder.BIG_ENDIAN);
		return new CriadorPacotes().getTipoPacote(bb.getInt());
	}
	
	private String readString(ByteBuffer bb){
		//----------------- string ----------
		short numero_caracteres = bb.getShort();
		byte[] bNome = new byte[numero_caracteres];
		for (int i = 0; i < numero_caracteres; i++) {
			bNome[i] = bb.get();
		}
		return new String(bNome);
	}
//	private byte[] readVetorBytes(ByteBuffer bb){
//		short tam_vetor = bb.getShort();
//		byte[] rt = new byte[tam_vetor];
//		for (int i = 0; i < tam_vetor; i++) {
//			rt[i] = bb.get();
//		}
//		return rt;
//	}
	
	
	//-----------------------------------------------------------------------------
	//------------------------ CLIENTE --------------------------------------------
	//-----------------------------------------------------------------------------
	//cliente TCP
	private void clienteTCP(){
		Socket s = null;
		try {
			s = new Socket(ip, portaServidor);
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			
			//out.write(new CriadorPacotes().getPacoteOi());
			out.write(new CriadorPacotes().getPacoteOqueDevoFazer());
			
			//out.writeUTF(mensagem); // UTF is a string encoding see Sn. 4.4
			
			//seria uma resposta do servidor
			//DataInputStream in = new DataInputStream(s.getInputStream());
			//String data = in.readUTF(); // read a line of data from the stream
			//System.out.println("[c] Received: " + data);
			
		} catch (UnknownHostException e) {
			System.err.println("[c] Socket:" + e.getMessage());
		} catch (EOFException e) {
			System.err.println("[c] EOF:" + e.getMessage());
		} catch (IOException e) {
			System.err.println("[c] readline:" + e.getMessage());
		} finally {
			if (s != null)
				try {
					s.close();
				} catch (IOException e) {
					System.err.println("[c] close:" + e.getMessage());
				}
		}
		_pausa = true;
	}

	
	/**
	 * Ações Simples
	 * */
	@Override
	public void ExecutarAcao(int acao) {
		
//		switch (acao) {
//		case 1:
//			ahead(1000);
//			break;
//		case 2:
//			back(1000);
//			break;
//		case 3:
//			turnLeft(1000);
//			break;
//		}
		
		_pausa = false;
		_executandoAcao = true;
		ahead(1000);
	}


	
}