package tcp.pacotes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import DadosRobos.DadosRobos;

public class AnalisePacotes {
	
	/**
	 * @return retorna o tipo do pacote
	 * */
	public static CriadorPacotes.TipoPacotes getTipo(byte[] pacote){
		ByteBuffer bb = ByteBuffer.wrap(pacote);
		bb.order(ByteOrder.BIG_ENDIAN);
		return new CriadorPacotes().getTipoPacote(bb.getInt());
	}
	
	/**
	 * @return a lista de ações do pacote
	 * */
	public static List<String> getLista(byte[] pacote) {
		ByteBuffer bb = ByteBuffer.wrap(pacote);
		bb.order(ByteOrder.BIG_ENDIAN);
		bb.getInt(); //tipo
		int tamLista = bb.getShort(); //a quantidade de Strings da mensagem
		List<String> rt = new ArrayList<String>(tamLista);
		for (int i = 0; i < tamLista; i++) {
			rt.add(readString(bb)); // dados da mensagem
		}
		return rt;
	}
	
	public static DadosRobos getDadosRobo(byte[] pacote) {
		ByteBuffer bb = ByteBuffer.wrap(pacote);
		bb.order(ByteOrder.BIG_ENDIAN);
		bb.getInt(); //tipo
		
		DadosRobos dados = new DadosRobos();
		dados.setIndiceRobo(bb.getInt());
		dados.setNomeRobo(readString(bb));
		dados.setEnergia(bb.getDouble());
		dados.setX(bb.getDouble());
		dados.setY(bb.getDouble());
		dados.setVelocidade(bb.getDouble());
		dados.setHeading(bb.getDouble());
		dados.setWidth(bb.getDouble());
		dados.setHeight(bb.getDouble());
		dados.setNumeroRound(bb.getInt());
		return dados;
	}
	
	//--------------------------------------------------------------------
	private static String readString(ByteBuffer bb){
		//----------------- string ----------
		short numero_caracteres = bb.getShort();
		byte[] bNome = new byte[numero_caracteres];
		for (int i = 0; i < numero_caracteres; i++) {
			bNome[i] = bb.get();
		}
		return new String(bNome);
	}
//	private static byte[] readVetorBytes(ByteBuffer bb){
//		short tam_vetor = bb.getShort();
//		byte[] rt = new byte[tam_vetor];
//		for (int i = 0; i < tam_vetor; i++) {
//			rt[i] = bb.get();
//		}
//		return rt;
//	}
	
}
