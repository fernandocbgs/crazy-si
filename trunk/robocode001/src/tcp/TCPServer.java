package tcp;

import java.net.*;
import java.util.List;
import java.io.*;

import tcp.interfaces.IServidorTCP;
import tcp.pacotes.AnalisePacotes;

/**
 * site base http://www.ase.md/~aursu/ClientServerThreads.html
 * Agora sim um servidor TCP multithread
 * */
public class TCPServer extends Thread {
	private int _portaServidor = 7896;
	private IServidorTCP _istcp;
	private Socket _clientSocket = null;
    private ServerSocket _serverSocket = null;
    
	public void setIstcp(IServidorTCP istcp) {this._istcp = istcp;}
	public IServidorTCP getIstcp() {return _istcp;}
	
	public TCPServer(IServidorTCP istcp){setIstcp(istcp);}
	public TCPServer(int portaServidor, IServidorTCP istcp){
		_portaServidor = portaServidor;
		setIstcp(istcp);
	}
	
	public void run(){
		iniciarServidor();
	}
	
	private void iniciarServidor() {
		System.out.println("Usage: java MultiThreadChatServer \n" + "Now using port number=" + _portaServidor);

		try {
			_serverSocket = new ServerSocket(_portaServidor);
			getIstcp().print("[s] tcp:" + _portaServidor);
		} catch (IOException e) {
			System.out.println(e);
		}
		while (true) {
			try {
				_clientSocket = _serverSocket.accept();
				getIstcp().print("[s] requisicao " + _clientSocket);
				
				//cria uma thread para cuidar da requisição dos clientes
				new ServerThread(_clientSocket, _istcp).start();
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
	private IServidorTCP _istcp;
	
	public void setIstcp(IServidorTCP istcp) {this._istcp = istcp;}
	public IServidorTCP getIstcp() {return _istcp;}
	
	public ServerThread(Socket clientSocket, IServidorTCP istcp) {
		this._clientSocket = clientSocket;
		this._istcp = istcp;
	}

	public void run() {
		try {
			_in = new DataInputStream(_clientSocket.getInputStream());
			_out = new DataOutputStream(_clientSocket.getOutputStream());
			
			byte[] pacote = new byte[1000];
			_in.read(pacote); //le o pacote
		
			List<String> rt = analisePacote(pacote); //analise do pacote
			if (rt == null) {
				_out.writeInt(0); return;
			}
			_out.writeInt(rt.size()); //tamanho da resposta
			for (String str : rt) {
				_out.writeUTF(str);
			}

			_in.close();
			_out.close();
			_clientSocket.close();
		} catch (IOException e) {}
	}
	
	private List<String> analisePacote(byte[] pacote){
		switch (AnalisePacotes.getTipo(pacote)) {
		case pedirDados:
			return getIstcp().getDadosRobo();
		case ordem:
			getIstcp().ExecutarAcoes(AnalisePacotes.getLista(pacote));
			break;
		}
		return null;
	}
	
}