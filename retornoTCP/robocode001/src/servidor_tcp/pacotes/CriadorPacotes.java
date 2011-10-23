package servidor_tcp.pacotes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

//como o robo deve rodar no robocode, o metodo de cria o pacote deve 
//estar nele também (redundancia necessaria)

/**
 * Cria pacotes
 * */
public class CriadorPacotes {
	// int = 4, short = 2, long = 8
	
	public enum TipoPacotes {
		oiMestre,
		oquedevoFazer,
		respostaAcao
	}
	public int getIntTipo(TipoPacotes tp){
		switch(tp){
			case oiMestre: return 0;
			case oquedevoFazer: return 1;
			case respostaAcao: return 2;
		}
		return 0;
	}
	public TipoPacotes getTipoPacote(int tp){
		TipoPacotes rt = TipoPacotes.oiMestre;
		if(tp==0){rt = TipoPacotes.oiMestre;}
		else if (tp==1) {rt = TipoPacotes.oquedevoFazer;}
		else if (tp==2) {rt = TipoPacotes.respostaAcao;}
		return rt;
	}
	
	public byte[] getPacote(TipoPacotes tp, List<String> params){
		switch (tp) {
		case oiMestre: return getPacoteOi(Integer.valueOf(params.get(0)));
		case oquedevoFazer: return getPacoteOqueDevoFazer(params);
		case respostaAcao: 
			return getPacoteAcao(
				Integer.valueOf(params.get(0)), 
				Double.parseDouble(params.get(1)));
		}
		return null;
	}
	//--------------------------------------------------------
	private byte[] getPacoteOi(int id){
		String msgOi = "Ola sou um robo " + id;
		int TamanhoVetorDados = 4 + 2 +(2 + msgOi.length());
		
		byte[] conteudo = new byte[TamanhoVetorDados];
		ByteBuffer bb = ByteBuffer.wrap(conteudo);
		bb.order(ByteOrder.BIG_ENDIAN);
		//tipo(int), msgOi (2+length)
		bb.putInt(getIntTipo(TipoPacotes.oiMestre)); //size 4
		bb.putShort((short)1); //size 2
		writeString(bb, msgOi); //2+length
		return conteudo;
	}
	
	private byte[] getPacoteOqueDevoFazer(List<String> params){
		//tamanho
		int tam = 0;
		for (String s:params){tam += 2 + s.length();}

		int TamanhoVetorDados = 4+2 + tam;
		byte[] conteudo = new byte[TamanhoVetorDados];
		ByteBuffer bb = ByteBuffer.wrap(conteudo);
		bb.order(ByteOrder.BIG_ENDIAN);
		bb.putInt(getIntTipo(TipoPacotes.oquedevoFazer)); //size 4
		bb.putShort((short)params.size()); //size 2
		for (String s:params){
			writeString(bb,s);
		}
		return conteudo;
	}
	
	private byte[] getPacoteAcao(int acao, double valor){
		int TamanhoVetorDados = 4+2+4+8;
		byte[] conteudo = new byte[TamanhoVetorDados];
		ByteBuffer bb = ByteBuffer.wrap(conteudo);
		bb.order(ByteOrder.BIG_ENDIAN);
		bb.putInt(getIntTipo(TipoPacotes.respostaAcao)); //size 4
		bb.putShort((short)2); //size 2
		bb.putInt(acao); //size 4
		bb.putDouble(valor); //8
		return conteudo;
	}
	
	//----------------------------------------------------------
	private void writeString(ByteBuffer bb, String texto){
		bb.putShort((short)texto.length()); //size 2
		byte[] bMsg = texto.getBytes();
		for (int i = 0; i < bMsg.length; i++) {
			bb.put(bMsg[i]); //size 1
		}
	}
//	private void writeVetorBytes(ByteBuffer bb, byte[] vetor){
//		bb.putShort((short)vetor.length); //size 2
//		for (int i = 0; i < vetor.length; i++) {
//			bb.put(vetor[i]); //size 1
//		}
//	}
}