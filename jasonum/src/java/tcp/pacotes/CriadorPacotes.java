package tcp.pacotes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

import DadosRobos.DadosRobos;

/**
 * Cria pacotes
 * @author Emerson
 * @author Lucas
 * */
public class CriadorPacotes {
	// int = 4, short = 2, long = 8
	
	public enum TipoPacotes {
		pedirDados,
		ordem,
		retornoDados,
	}
	public int getIntTipo(TipoPacotes tp){
		switch(tp){
			case pedirDados: return 0;
			case ordem: return 1;
			case retornoDados: return 2;
		}
		return 0;
	}
	public TipoPacotes getTipoPacote(int tp){
		if(tp==0){return TipoPacotes.pedirDados;}
		else if (tp==1) {return TipoPacotes.ordem;}
		else if (tp==2) {return TipoPacotes.retornoDados;}
		return null;
	}
	//---------------------------------------------------
	public byte[] pacotePedirDados(){
		int s = 4+2; //int+short
		byte[] conteudo = new byte[s];
		ByteBuffer bb = ByteBuffer.wrap(conteudo);
		bb.order(ByteOrder.BIG_ENDIAN);
		bb.putInt(getIntTipo(TipoPacotes.pedirDados));
		bb.putShort((short)0); //informa que não tem mais nada na msg além do tipo
		return conteudo;
	}
	public byte[] pacoteOrdem(List<String> params){
		return pacoteList(TipoPacotes.ordem, params);
	}
	
	public byte[] pacoteDadosRobos(DadosRobos dados){
		int s = 4 + 4 + (2 + dados.getNomeRobo().length()) + 
				8 + 8 + 8 + 8 + 8 + 8 + 8 + 4 + 2;
		byte[] conteudo = new byte[s];
		ByteBuffer bb = ByteBuffer.wrap(conteudo);
		bb.order(ByteOrder.BIG_ENDIAN);
		bb.putInt(getIntTipo(TipoPacotes.retornoDados)); //4
		
		bb.putInt(dados.getIndiceRobo()); //4
		writeString(bb, dados.getNomeRobo()); //2+size
		bb.putDouble(dados.getEnergia()); //8
		bb.putDouble(dados.getX()); //8
		bb.putDouble(dados.getY()); //8
		bb.putDouble(dados.getVelocidade()); //8
		bb.putDouble(dados.getHeading()); //8
		bb.putDouble(dados.getWidth()); //8
		bb.putDouble(dados.getHeight()); //8
		bb.putInt(dados.getNumeroRound()); //4
		bb.putShort((short)(dados.isExecutandoAlgo() ? 1 : 0)); //2
		
		return conteudo;
	}

	//--------------------------------------------------------
	
	/**
	 * generico
	 * */
	private byte[] pacoteList(TipoPacotes tp, List<String> params){
		int tam = 0; for (String s:params){tam += 2 + s.length();} //2 - tam da String
		int s = 4+2+tam;
		byte[] conteudo = new byte[s];
		ByteBuffer bb = ByteBuffer.wrap(conteudo);
		bb.order(ByteOrder.BIG_ENDIAN);
		bb.putInt(getIntTipo(tp));
		bb.putShort((short)params.size()); //numero de Strings que vem apos o tipo da mensagem 
		for (String str:params){writeString(bb,str);}
		return conteudo;
	}
	
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