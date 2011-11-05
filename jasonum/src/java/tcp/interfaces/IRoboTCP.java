package tcp.interfaces;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import robocode.DadosRobos.DadosRobos;

public interface IRoboTCP {
	public DadosRobos getDadosRobo(); //lista com os dados do robo
	public void ExecutarAcoes(List<String> l); //robo executa as ações
	//public void JasonFoiAvisado();
	public void analisePacote(byte[] pacote, DataOutputStream out) throws IOException;
}