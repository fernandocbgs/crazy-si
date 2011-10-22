package servidor_tcp;

import interfaces.IRobo;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import servidor_tcp.pacotes.CriadorPacotes;

public class TCPClient implements IRobo {
	private static int _portaServidor = 7896;
	//private static String mensagem = "Ola sou um cliente";
	private static String _ip = "localhost";
	private static int portaServidorRobo = 7890;
	
	public TCPClient(){
		_portaServidor = 7896;
		_ip = "localhost";
	}
	
	public TCPClient(int portaServidor, String ip){
		_portaServidor = portaServidor;
		_ip = ip;
	}
	
	public void iniciarCliente(){
		
		//cria o servidor do Robo
		new TCPServer(portaServidorRobo, this).start();
		
		Socket s = null;
		try {
			s = new Socket(_ip, _portaServidor);
			
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			out.write(new CriadorPacotes().getPacoteOqueDevoFazer());
			//out.write(new CriadorPacotes().getPacoteOi());	
			
			
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
	}

	@Override
	public void ExecutarAcao(int acao) {
		System.out.println("[c] ExecutarAcao acao " + acao);
	}
	
	
}
