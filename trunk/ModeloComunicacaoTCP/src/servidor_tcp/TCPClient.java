package servidor_tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import servidor_tcp.gui.IClienteTCP;
import servidor_tcp.pacotes.CriadorPacotes;
import servidor_tcp.pacotes.CriadorPacotes.TipoPacotes;

public class TCPClient {
	private static int _portaServidor = 7896;
	private static String _ip = "localhost";
	private Socket _s;
	private IClienteTCP _ictcp;
	
	public void setIctcp(IClienteTCP ictcp) {this._ictcp = ictcp;}
	public IClienteTCP getIctcp() {return _ictcp;}

	public TCPClient(IClienteTCP ictcp){setIctcp(ictcp);}
	public TCPClient(int portaServidor, String ip, IClienteTCP ictcp){
		_portaServidor = portaServidor;
		_ip = ip;
		setIctcp(ictcp);
	}
	
	public List<String> pedirDados(){
		try {
			if (_s==null) {
				_s = new Socket(_ip, _portaServidor);
				getIctcp().print("[c] iniciado " + _ip + ":" + _portaServidor);
			}
			
			DataOutputStream out = new DataOutputStream(_s.getOutputStream());
			out.write(new CriadorPacotes().getPacote(TipoPacotes.pedirDados));
			
			//resposta do servidor
			DataInputStream in = new DataInputStream(_s.getInputStream());
			List<String> l = new ArrayList<String>();
			int tamanho = Integer.valueOf(in.readInt()); //tamanho
			//System.out.println("tamanho: " + tamanho);
			for (int i = 0; i < tamanho; i++) {
				l.add(in.readUTF());
			}
			killSocket();
			return l;
		} catch (UnknownHostException e) {
			System.err.println("[c] Socket:" + e.getMessage());
		} catch (EOFException e) {
			System.err.println("[c] EOF:" + e.getMessage());
		} catch (IOException e) {
			System.err.println("[c] readline:" + e.getMessage());
		} finally {
			killSocket();
		}
		return null;
	}
	
	/**
	 * @ordens ordens para o robo do robocode
	 * */
	public void enviarOrdem(List<String> ordens){
		try {
			if (_s==null) {
				_s = new Socket(_ip, _portaServidor);
				getIctcp().print("[c] iniciado " + _ip + ":" + _portaServidor);
			}

			DataOutputStream out = new DataOutputStream(_s.getOutputStream());
			out.write(new CriadorPacotes().getPacote(TipoPacotes.ordem, ordens));

			//resposta do servidor
			DataInputStream in = new DataInputStream(_s.getInputStream());
			System.out.println("resposta servidor: " + in.readUTF()); //le a resposta do servidor
			
			killSocket();
		} catch (UnknownHostException e) {
			System.err.println("[c] Socket:" + e.getMessage());
		} catch (IOException e) {
			System.err.println("[c] readline:" + e.getMessage());
		} finally {
			killSocket();
		}
	}
	
	private void killSocket(){
		//System.out.println("killSocket");
		if (_s != null)
			try {
				_s.shutdownInput();
				_s.shutdownOutput();
				_s.close();
				_s = null;
			} catch (IOException e) { System.err.println("[c] close:" + e.getMessage());}
	}
	
	
}
