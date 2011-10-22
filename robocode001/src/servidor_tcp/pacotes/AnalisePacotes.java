package servidor_tcp.pacotes;

import interfaces.IRobo;

import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.swing.JTextArea;

public class AnalisePacotes {
	private IRobo _irobo;
	private String _ipRobo = "localhost";
	private int _portaRobo = 7890;
	
	private JTextArea _jta;
	
	public AnalisePacotes(JTextArea jta, IRobo irobo){
		_irobo = irobo;
		_jta = jta;
	}
	
	public void Analisar(byte[] pacote) {
		CriadorPacotes.TipoPacotes tp = getTipo(pacote);
		print("[s] tp: " + tp.toString());
		switch (tp) {
		case oiMestre:AnaliseOi(pacote);break;
		case oquedevoFazer:
			respostaServidorRobo(AnaliseOQueDevoFazer(pacote));
			break;
		case respostaAcao:
			AnaliseRespostaAcao(pacote);
			break;
		}
	}
	
	/**
	 * resposta ao servidor TCP do robo
	 * */
	public void respostaServidorRobo(int numero){
		Socket s = null;
		try {
			print("[s] numero resposta: " + numero);
			s = new Socket(_ipRobo, _portaRobo); //servidor do robo
			
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			out.write(new CriadorPacotes().getPacoteRespostaPerguntaMeuNumero(numero));
			//out.writeUTF(mensagem); // UTF is a string encoding see Sn. 4.4
			
			//seria uma resposta do servidor
			//DataInputStream in = new DataInputStream(s.getInputStream());
			//String data = in.readUTF(); // read a line of data from the stream
			//System.out.println("[c] Received: " + data);
			
		} catch (UnknownHostException e) {
			System.err.println("[c resposta] Socket:" + e.getMessage());
		} catch (EOFException e) {
			System.err.println("[c resposta] EOF:" + e.getMessage());
		} catch (IOException e) {
			System.err.println("[c resposta] readline:" + e.getMessage());
		} finally {
			if (s != null)
				try {
					s.close();
				} catch (IOException e) {
					System.err.println("[c] close:" + e.getMessage());
				}
		}
		
	} 
	
	//--------------------------------------------------------------------
	public void AnaliseOi(byte[] pacote){
		ByteBuffer bb = ByteBuffer.wrap(pacote);
		bb.order(ByteOrder.BIG_ENDIAN);
		bb.getInt(); //tipo
		print("[s] oi: " + readString(bb));
	}
	public int AnaliseOQueDevoFazer(byte[] pacote){
		ByteBuffer bb = ByteBuffer.wrap(pacote);
		bb.order(ByteOrder.BIG_ENDIAN);
		bb.getInt(); //tipo
		return 3; //retorna o número
	}
	public void AnaliseRespostaAcao(byte[] pacote){
		if (_irobo == null) return;
		ByteBuffer bb = ByteBuffer.wrap(pacote);
		bb.order(ByteOrder.BIG_ENDIAN);
		bb.getInt(); //tipo
		_irobo.ExecutarAcao(bb.getInt());
	}
	//--------------------------------------------------------------------
	//retorna o tipo do pacote
	public CriadorPacotes.TipoPacotes getTipo(byte[] pacote){
		ByteBuffer bb = ByteBuffer.wrap(pacote);
		bb.order(ByteOrder.BIG_ENDIAN);
		return new CriadorPacotes().getTipoPacote(bb.getInt());
	}
	
	private String readString(ByteBuffer bb){
		//----------------- string ----------
		short numero_caracteres = bb.getShort();
		byte[] bNome = new byte[numero_caracteres];
		for (int i = 0; i < numero_caracteres; i++) {
			bNome[i] = bb.get();
		}
		return new String(bNome);
	}
//	private byte[] readVetorBytes(ByteBuffer bb){
//		short tam_vetor = bb.getShort();
//		byte[] rt = new byte[tam_vetor];
//		for (int i = 0; i < tam_vetor; i++) {
//			rt[i] = bb.get();
//		}
//		return rt;
//	}
	
	private void print(String msg){
		if (_jta == null) {
			System.out.println(msg);
		} else {
			_jta.setText(msg + "\n" + _jta.getText());
		}			
	}
	
}
