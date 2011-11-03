package tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import DadosRobos.DadosRobos;
import tcp.pacotes.AnalisePacotes;
import tcp.pacotes.CriadorPacotes;

/**
 * site base http://www.ase.md/~aursu/ClientServerThreads.html
 * */
public class TCPClient /*implements Runnable*/ {
	private int _portaServidor = 7896;
	private String _ip = "localhost";
	
	private Socket _clientSocket = null;
	private DataOutputStream _out;
	private DataInputStream _in;
	
	public TCPClient(int portaServidor){_portaServidor = portaServidor;}
	public TCPClient(int portaServidor, String ip){
		_portaServidor = portaServidor;
		_ip = ip;
	}
		
	public DadosRobos pedirDados() {
		try {
			_clientSocket = new Socket(_ip, _portaServidor);
			//_clientSocket.setSoTimeout(300);
			_out = new DataOutputStream(_clientSocket.getOutputStream());
			_in = new DataInputStream(_clientSocket.getInputStream());
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + _ip);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to the host " + _ip);
		}

		if (_clientSocket != null && _out != null && _in != null) {
			try {
				
				//new Thread(new TCPClient(_portaServidor, _ip, _ictpc)).start();
				_out.write(new CriadorPacotes().pacotePedirDados());
				
				System.out.println("resposta do servidor");
				
				// resposta do servidor
				byte[] pacote = new byte[1000];
				_in.read(pacote); //le o pacote
				DadosRobos dadosR = AnalisePacotes.getDadosRobo(pacote);
				
//				DadosRobos dadosR = new DadosRobos();
//				int tamanho = Integer.valueOf(_in.readInt()); // tamanho
//				if (tamanho > 0) {
//					dadosR.setIndiceRobo(_in.readInt());
//					dadosR.setNomeRobo(_in.readUTF());
//					dadosR.setEnergia(_in.readDouble());
//					dadosR.setX(_in.readDouble());
//					dadosR.setY(_in.readDouble());
//					dadosR.setVelocidade(_in.readDouble());
//					dadosR.setHeading(_in.readDouble());
//					dadosR.setWidth(_in.readDouble());
//					dadosR.setHeight(_in.readDouble());
//					dadosR.setNumeroRound(_in.readInt());
//					dadosR.setExecutandoAlgo(_in.readBoolean());
//				}
				
				_out.close();
				_in.close();
				
				_clientSocket.close();
				_clientSocket = null;
				
				return dadosR;
			} catch (IOException e) {
				System.err.println("[1] IOException:  " + e + ", mensagem: " + e.getMessage());
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @ordens ordens para o robo do robocode
	 * */
	public void enviarOrdem(List<String> ordens){
		try {
			_clientSocket = new Socket(_ip, _portaServidor);
			_out = new DataOutputStream(_clientSocket.getOutputStream());
			_in = new DataInputStream(_clientSocket.getInputStream());
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + _ip);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to the host " + _ip);
		}

		if (_clientSocket != null && _out != null && _in != null) {
			try {
				_out.write(new CriadorPacotes().pacoteOrdem(ordens));
				
				// resposta do servidor
				_in.readInt();

				_out.close();
				_in.close();
				_clientSocket.close();
				_clientSocket = null;
			} catch (IOException e) {
				System.err.println("[2] IOException:  " + e);
				e.printStackTrace();
			}
		}
	}

	
}
