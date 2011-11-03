package tcp;

import java.net.*;
import java.io.*;
import tcp.interfaces.IRoboTCP;
import tcp.pacotes.AnalisePacotes;
import tcp.pacotes.CriadorPacotes;

/**
 * site base http://www.ase.md/~aursu/ClientServerThreads.html
 * Agora sim um servidor TCP multithread
 * */
public class TCPServer extends Thread {
	private int _portaServidor = 7896;
	private IRoboTCP _istcp;
	private Socket _clientSocket = null;
    private ServerSocket _serverSocket = null;
    
	public void setIstcp(IRoboTCP istcp) {this._istcp = istcp;}
	public IRoboTCP getIstcp() {return _istcp;}
	
	public TCPServer(IRoboTCP istcp){setIstcp(istcp);}
	public TCPServer(int portaServidor, IRoboTCP istcp){
		_portaServidor = portaServidor;
		setIstcp(istcp);
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
	private IRoboTCP _irobotcp;
	
	public void setIRoboTcp(IRoboTCP istcp) {this._irobotcp = istcp;}
	public IRoboTCP getIroboTcp() {return _irobotcp;}
	
	public ServerThread(Socket clientSocket, IRoboTCP irobotcp) {
		this._clientSocket = clientSocket;
		setIRoboTcp(irobotcp);
	}

	public void run() {
		try {
			_in = new DataInputStream(_clientSocket.getInputStream());
			_out = new DataOutputStream(_clientSocket.getOutputStream());
			
			byte[] pacote = new byte[1000];
			_in.read(pacote); //le o pacote
		
			analisePacote(pacote, _out); //analise do pacote
			
			_in.close();
			_out.close();
			
			_clientSocket.close();
		} catch (IOException e) {}
	}
	
	/**
	 * Analise do pacote de dados recebido no servidor
	 * */
	private void analisePacote(byte[] pacote, DataOutputStream out) throws IOException{
		switch (AnalisePacotes.getTipo(pacote)) {
			case pedirDados:
				out.write(new CriadorPacotes().pacoteDadosRobos(getIroboTcp().getDadosRobo()));
				break;
			case ordem:
				getIroboTcp().ExecutarAcoes(AnalisePacotes.getLista(pacote)); //envia as ordens para o robo
				out.writeInt(0);
				break;
				
		}
	}
	
}