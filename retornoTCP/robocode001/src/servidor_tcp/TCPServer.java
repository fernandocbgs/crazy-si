package servidor_tcp;

import java.net.*;
import java.util.List;
import java.io.*;

import servidor_tcp.gui.IAcoesTCP;
import servidor_tcp.pacotes.AnalisePacotes;

public class TCPServer extends Thread {
	private int _portaServidor = 7896;
	private IAcoesTCP _iatcp; 
	public TCPServer(int portaServidor, IAcoesTCP iatcp){
		_portaServidor = portaServidor;
		_iatcp = iatcp;
	}
	
	public void run() {
		iniciarServidor();
	}
	
	public void iniciarServidor(){
		try {
			ServerSocket listenSocket = new ServerSocket(_portaServidor);
			_iatcp.print("[s] tcp:"+_portaServidor);
			
			while(true) {
				Socket clientSocket = listenSocket.accept();
				
				_iatcp.print("[s] requisicao " + clientSocket);
				
				DataInputStream in = new DataInputStream(clientSocket.getInputStream());
				
				byte[] pacote = new byte[1000];
				in.read(pacote); //le o pacote
				List<String> rt = new AnalisePacotes().Analisar(pacote);
				
				DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
				for (String str : rt) {
					out.writeUTF(str);
					//_iatcp.print("[s]" + str);
				}
				
			}
			
		} catch (IOException e) {
			System.err.println("[s] Listen socket:" + e.getMessage());
		}
	}

}