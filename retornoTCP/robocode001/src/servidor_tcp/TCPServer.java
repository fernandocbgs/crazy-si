package servidor_tcp;

import java.net.*;
import java.io.*;

import javax.swing.JTextArea;

import servidor_tcp.pacotes.AnalisePacotes;

public class TCPServer extends Thread {
	private static int _portaServidor = 7896;
	private JTextArea _jta;
	
	public TCPServer(int portaServidor){
		_portaServidor = portaServidor;
	}
	
	public TCPServer(JTextArea jta){
		_jta = jta;
	}
	
	public void run() {
		iniciarServidor();
	}
	
	public void iniciarServidor(){
		try {
			int serverPort = _portaServidor; // the server port
			ServerSocket listenSocket = new ServerSocket(serverPort);
			print("[s] iniciando servidor TCP");
			
			while(true) {
				Socket clientSocket = listenSocket.accept();
				
				DataInputStream in = new DataInputStream(clientSocket.getInputStream());
				
				byte[] pacote = new byte[1000];
				in.read(pacote);
				new AnalisePacotes(_jta).Analisar(pacote);
				
				//String data = in.readUTF(); // read a line of data from the stream
				//print("[s] recebi: " + data);
				//DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
				//out.writeUTF(data);
			}
			
		} catch (IOException e) {
			System.err.println("[s] Listen socket:" + e.getMessage());
		}
	}
	
	private void print(String msg){
		if (msg.equals("")) return;
		if (_jta != null) {
			_jta.setText(msg + "\n" + _jta.getText());
		} else {
			System.out.println(msg);
		}
	}
	
}