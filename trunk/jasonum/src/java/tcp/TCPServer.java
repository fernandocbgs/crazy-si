package tcp;

import java.net.*;
import java.io.*;
import tcp.interfaces.IJason;
import tcp.interfaces.IRoboTCP;

/**
 * site base http://www.ase.md/~aursu/ClientServerThreads.html
 * Agora sim um servidor TCP multithread
 * */
public class TCPServer extends Thread {
	private int _portaServidor = 7896;
	private IRoboTCP _istcp;
	private IJason _iJason;
	private Socket _clientSocket = null;
    private ServerSocket _serverSocket = null;
    
	public void setIJason(IJason iJason) { this._iJason = iJason; }
	public IJason getIJason() { return _iJason; }
	public void setIstcp(IRoboTCP istcp) {this._istcp = istcp;}
	public IRoboTCP getIstcp() {return _istcp;}
	
	public TCPServer(int portaServidor){
		_portaServidor = portaServidor;
	}
	
	public void run(){
		iniciarServidor();
	}
	
	public void parar(){
		try {
			_clientSocket.close();
			_clientSocket = null;
			_serverSocket.close();
			_serverSocket = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void iniciarServidor() {
		//System.out.println("Usage: java MultiThreadChatServer \n" + "Now using port number=" + _portaServidor);

		try {
			_serverSocket = new ServerSocket(_portaServidor);
			//("[s] tcp:" + _portaServidor);
		} catch (IOException e) {
			System.out.println(e);
		}
		while (true) {
			try {
				_clientSocket = _serverSocket.accept();
				//("[s] requisicao " + _clientSocket);
				
				//cria uma thread para cuidar da requisição dos clientes
				ServerThread st = new ServerThread(_clientSocket);
				if (getIstcp() != null) { st.setIstcp(getIstcp()); }
				if (getIJason() != null) { st.setIJason(getIJason()); }
				st.start();
				
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
	
}

/**
 * Thread que cuida das requisições dos clientes
 * */
class ServerThread extends Thread {
	private DataInputStream _in = null;
	private DataOutputStream _out = null;
	private Socket _clientSocket = null;
	private IRoboTCP _irobotcp;
	private IJason _iJason;
	
	public void setIJason(IJason iJason) { this._iJason = iJason; }
	public IJason getIJason() { return _iJason; }
	public void setIstcp(IRoboTCP istcp) {this._irobotcp = istcp;}
	public IRoboTCP getIstcp() {return _irobotcp;}
	
	public ServerThread(Socket clientSocket) {
		this._clientSocket = clientSocket;
	}

	public void run() {
		try {
			_in = new DataInputStream(_clientSocket.getInputStream());
			_out = new DataOutputStream(_clientSocket.getOutputStream());
			
			byte[] pacote = new byte[1000];
			_in.read(pacote); //le o pacote
			
			if (_irobotcp!=null) _irobotcp.analisePacote(pacote, _out); //analise do pacote
			else if (_iJason!=null) _iJason.analisePacote(pacote, _out); //analise do pacote
					
			_in.close();
			_out.close();
			
			_clientSocket.close();
		} catch (IOException e) {}
	}
	
}