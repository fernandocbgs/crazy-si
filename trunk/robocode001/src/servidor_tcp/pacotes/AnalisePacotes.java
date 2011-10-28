package servidor_tcp.pacotes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import sample.ControleTCPRobot;

public class AnalisePacotes {
	public AnalisePacotes(){}
	
	public List<String> Analisar(byte[] pacote) {
		CriadorPacotes.TipoPacotes tp = getTipo(pacote);
		switch (tp) {
			case oiMestre: return AnaliseOi(pacote);
			case oquedevoFazer: return AnaliseOQueDevoFazer(pacote);
			case respostaAcao: return AnaliseRespostaAcao(pacote);
		}
		return null;
	}
	
	//--------------------------------------------------------------------
	private List<String> AnaliseOi(byte[] pacote){
		List<String> rt = new ArrayList<String>();
		ByteBuffer bb = ByteBuffer.wrap(pacote);
		bb.order(ByteOrder.BIG_ENDIAN);
		rt.add(bb.getInt()+""); //tipo
		rt.add(bb.getShort()+"");//size
		rt.add(readString(bb)); //mensagem
		return rt;
	}
	private List<String> AnaliseOQueDevoFazer(byte[] pacote){
		List<String> entrada = new ArrayList<String>();
		ByteBuffer bb = ByteBuffer.wrap(pacote);
		bb.order(ByteOrder.BIG_ENDIAN);
		entrada.add(bb.getInt()+""); //tipo
		int tam = bb.getShort();
		entrada.add(tam+"");//size
		for (int i = 0; i < tam; i++){
			entrada.add(readString(bb));
		}
		//return entrada;
		
		return new ControleTCPRobot(entrada).getAcao();
		
//		System.out.println("---------------------------");
//		for (String e:entrada){
//			System.out.println("e: " + e);
//		}
//		System.out.println("---------------------------");
		
//		List<String> l = new ArrayList<String>();
//		l.add("2");
//		l.add("1.5");
//		pacote = new CriadorPacotes().getPacote(TipoPacotes.respostaAcao, l);
//		l = new ArrayList<String>();
//		bb = ByteBuffer.wrap(pacote);
//		bb.order(ByteOrder.BIG_ENDIAN);
//		l.add(bb.getInt()+""); //tipo
//		l.add(bb.getShort()+"");//size
//		l.add(bb.getInt()+""); //acao
//		l.add(bb.getDouble()+""); //força, dist, etc
//		return l;
		
	}
	private List<String> AnaliseRespostaAcao(byte[] pacote){
		List<String> rt = new ArrayList<String>();
		ByteBuffer bb = ByteBuffer.wrap(pacote);
		bb.order(ByteOrder.BIG_ENDIAN);
		rt.add(bb.getInt()+""); //tipo
		rt.add(bb.getShort()+"");//size
		rt.add(bb.getInt()+""); //acao
		rt.add(bb.getDouble()+""); //força, dist, etc
		return rt;
	}
	//--------------------------------------------------------------------
	//retorna o tipo do pacote
	private CriadorPacotes.TipoPacotes getTipo(byte[] pacote){
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
	
}
