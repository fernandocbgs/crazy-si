package tcp.interfaces;

import java.io.DataOutputStream;
import java.io.IOException;
//import DadosRobos.DadosRobos;

public interface IJason {
	//public void Continuar(DadosRobos dados); //comando para o Jason para que ele continue a sua execução
	public void analisePacote(byte[] pacote, DataOutputStream out) throws IOException;
}
