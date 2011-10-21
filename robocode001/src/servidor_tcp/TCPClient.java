package servidor_tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {
	
	private static int portaServidor = 7896;
	private static String mensagem = "Ola sou um cliente";
	private static String ip = "localhost";
	
	public void iniciarCliente(){
		// arguments supply message and hostname
		Socket s = null;
		try {
			int serverPort = portaServidor;
			s = new Socket(ip, serverPort);
			DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			out.writeUTF(mensagem); // UTF is a string encoding see Sn. 4.4
			String data = in.readUTF(); // read a line of data from the stream
			
			System.out.println("(cliente) Received: " + data);
			
		} catch (UnknownHostException e) {
			System.out.println("Socket:" + e.getMessage());
		} catch (EOFException e) {
			System.out.println("EOF:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("readline:" + e.getMessage());
		} finally {
			if (s != null)
				try {
					s.close();
				} catch (IOException e) {
					System.out.println("close:" + e.getMessage());
				}
		}
	}
	
	
}
