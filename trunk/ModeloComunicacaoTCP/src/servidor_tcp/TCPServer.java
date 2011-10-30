package servidor_tcp;

import java.net.*;
import java.util.List;
import java.io.*;

import servidor_tcp.interfaces.IServidorTCP;
import servidor_tcp.pacotes.AnalisePacotes;

public class TCPServer extends Thread {
	private int _portaServidor = 7896;
	private IServidorTCP _istcp; 
	
	public TCPServer(IServidorTCP istcp){setIstcp(istcp);}
	public TCPServer(int portaServidor, IServidorTCP istcp){
		_portaServidor = portaServidor;
		setIstcp(istcp);
	}
	
	public void setIstcp(IServidorTCP istcp) {this._istcp = istcp;}
	public IServidorTCP getIstcp() {return _istcp;}
	
	public void run() {
		iniciarServidor();
	}
	
	public void iniciarServidor(){
		try {
			ServerSocket listenSocket = new ServerSocket(_portaServidor);
			getIstcp().print("[s] tcp:"+_portaServidor);
			
			while(true) {
				Socket clientSocket = listenSocket.accept();
				getIstcp().print("[s] requisicao " + clientSocket);
				
				DataInputStream in = new DataInputStream(clientSocket.getInputStream());
				byte[] pacote = new byte[1000];
				in.read(pacote); //le o pacote
				
				DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
				List<String> rt = analisePacote(pacote); //analise do pacote
				if (rt == null) {
					out.writeUTF("ok"); return;
				}
				out.writeInt(rt.size()); //tamanho da resposta
				for (String str : rt) {
					out.writeUTF(str);
				}
			}
			
		} catch (IOException e) {
			System.err.println("[s] Listen socket:" + e.getMessage());
		}
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