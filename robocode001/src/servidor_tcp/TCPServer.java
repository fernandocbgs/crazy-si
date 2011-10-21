package servidor_tcp;

import java.net.*;
import java.io.*;

import javax.swing.JTextArea;

public class TCPServer extends Thread {
	private static int portaServidor = 7896;
	private JTextArea _jta;
	
	public TCPServer(JTextArea jta){
		_jta = jta;
	}
	
	public void run() {
		iniciarServidor();
	}
	
	public void iniciarServidor(){
		try {
			int serverPort = portaServidor; // the server port
			ServerSocket listenSocket = new ServerSocket(serverPort);
			while (true) {
				
//				String msg = "[Servidor] iniciado servidor TCP";
//				if (_jta == null) {
//					System.out.println(msg);
//				} else {
//					_jta.setText(msg + "\n" + _jta.getText());
//				}
				
				Socket clientSocket = listenSocket.accept();
				new Connection(clientSocket, _jta);

				
			}
		} catch (IOException e) {
			System.out.println("Listen socket:" + e.getMessage());
		}
	}
	
}

class Connection extends Thread {
	private DataInputStream in;
	private DataOutputStream out;
	private Socket clientSocket;
	private JTextArea _jta;
	
	public Connection(Socket aClientSocket, JTextArea jta) {
		_jta = jta;
		try {
			clientSocket = aClientSocket;
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
			
			String msg = "[Servidor] recebi: " + in.readUTF();
			if (_jta == null) {
				System.out.println(msg);
			} else {
				_jta.setText(msg + "\n" + _jta.getText());
			}
			
			this.start();
		} catch (IOException e) {
			System.out.println("Connection:" + e.getMessage());
		}
	}

	public void run() {
		try { // an echo server
			String data = in.readUTF(); // read a line of data from the stream
			out.writeUTF(data);
		} catch (EOFException e) {
			System.out.println("EOF:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("readline:" + e.getMessage());
		} finally {
			try {
				clientSocket.close();
			} catch (IOException e) {/* close failed */
			}
		}

	}
}