package servidor_tcp.pacotes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

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
	
	public byte[] getPacoteOi(){
		String msgOi = "Ola sou um robo";
		int TamanhoVetorDados = 4 + (2 + msgOi.length());
		
		byte[] conteudo = new byte[TamanhoVetorDados];
		ByteBuffer bb = ByteBuffer.wrap(conteudo);
		bb.order(ByteOrder.BIG_ENDIAN);
		
		//tipo(int), msgOiCriptografada (2+length)
		bb.putInt(getIntTipo(TipoPacotes.oiMestre)); //size 4
		writeString(bb, msgOi); //2+length
		
		return conteudo;
	}
	
	public byte[] getPacoteOqueDevoFazer(){
		int TamanhoVetorDados = 4;
		byte[] conteudo = new byte[TamanhoVetorDados];
		ByteBuffer bb = ByteBuffer.wrap(conteudo);
		bb.order(ByteOrder.BIG_ENDIAN);
		bb.putInt(getIntTipo(TipoPacotes.oquedevoFazer)); //size 4
		return conteudo;
	}
	
	public byte[] getPacoteRespostaPerguntaMeuNumero(int numeroResposta){
		int TamanhoVetorDados = 4+4;
		byte[] conteudo = new byte[TamanhoVetorDados];
		ByteBuffer bb = ByteBuffer.wrap(conteudo);
		bb.order(ByteOrder.BIG_ENDIAN);
		bb.putInt(getIntTipo(TipoPacotes.respostaAcao)); //size 4
		bb.putInt(numeroResposta); //size 4
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