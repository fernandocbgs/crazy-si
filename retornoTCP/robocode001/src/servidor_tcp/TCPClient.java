package servidor_tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import servidor_tcp.gui.IAcoesTCP;
import servidor_tcp.pacotes.CriadorPacotes;
import servidor_tcp.pacotes.CriadorPacotes.TipoPacotes;

public class TCPClient {
	private static int _portaServidor = 7896;
	private static String _ip = "localhost";
	private IAcoesTCP _iatcp;
	
	public TCPClient(int portaServidor, String ip, IAcoesTCP iatcp){
		_portaServidor = portaServidor;
		_ip = ip;
		_iatcp = iatcp;
	}
	
	public void iniciarCliente(TipoPacotes tp, List<String> params){
		Socket s = null;
		try {
			s = new Socket(_ip, _portaServidor);
			
			_iatcp.print("[c] iniciado " + _ip + ":" + _portaServidor);
			
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			//out.write(new CriadorPacotes().getPacoteOqueDevoFazer());
			out.write(new CriadorPacotes().getPacote(tp, params));
			//out.writeUTF(mensagem); // UTF is a string encoding see Sn. 4.4
			
			//resposta do servidor
			DataInputStream in = new DataInputStream(s.getInputStream());
			List<String> l = new ArrayList<String>();
			l.add(in.readUTF()); //tipo
			int tamanho = Integer.valueOf(in.readUTF());
			//System.out.println("tamanho: " + tamanho);
			for (int i = 0; i < tamanho; i++) {
				l.add(in.readUTF());
			}
			_iatcp.Trata(l);
							
		} catch (UnknownHostException e) {
			System.err.println("[c] Socket:" + e.getMessage());
		} catch (EOFException e) {
			System.err.println("[c] EOF:" + e.getMessage());
		} catch (IOException e) {
			System.err.println("[c] readline:" + e.getMessage());
		} finally {
			if (s != null)
				try {
					s.close();
				} catch (IOException e) {
					System.err.println("[c] close:" + e.getMessage());
				}
		}
	}
	
	
}
