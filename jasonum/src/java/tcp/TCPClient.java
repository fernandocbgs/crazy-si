package tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import tcp.pacotes.CriadorPacotes;
import tcp.pacotes.CriadorPacotes.TipoPacotes;

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
	
//	@Override
//	public void run() {
//
//	}	
	
	public List<String> pedirDados() {
		//System.out.println("Usage: java TCPClient  \n" + "Now using host=" + _ip + ", port_number=" + _portaServidor);

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
				
				//new Thread(new TCPClient(_portaServidor, _ip, _ictpc)).start();
				
				_out.write(new CriadorPacotes().getPacote(TipoPacotes.pedirDados));
				
				// resposta do servidor
				List<String> l = new ArrayList<String>();
				int tamanho = Integer.valueOf(_in.readInt()); // tamanho
				// System.out.println("tamanho: " + tamanho);
				for (int i = 0; i < tamanho; i++) {
					l.add(_in.readUTF());
				}
				
				_out.close();
				_in.close();
				_clientSocket.close();
				_clientSocket = null;
				
				return l;
			} catch (IOException e) {
				System.err.println("IOException:  " + e);
			}
		}
		return null;
	}

	/**
	 * @ordens ordens para o robo do robocode
	 * */
	public void enviarOrdem(List<String> ordens){
		//System.out.println("Usage: java TCPClient  \n" + "Now using host=" + _ip + ", port_number=" + _portaServidor);

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
				//new Thread(new TCPClient()).start();
				_out.write(new CriadorPacotes().getPacote(TipoPacotes.ordem, ordens));
				
				// resposta do servidor
				_in.readInt();
				//System.out.println("resposta servidor: " + _in.readInt()); //le a resposta do servidor

				_out.close();
				_in.close();
				_clientSocket.close();
				_clientSocket = null;
			} catch (IOException e) {
				System.err.println("IOException:  " + e);
			}
		}
	}

	
}
