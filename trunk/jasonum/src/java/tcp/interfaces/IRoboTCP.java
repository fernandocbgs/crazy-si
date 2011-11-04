package tcp.interfaces;

import java.util.List;

import DadosRobos.DadosRobos;

public interface IRoboTCP {
	public DadosRobos getDadosRobo(); //lista com os dados do robo
	public void ExecutarAcoes(List<String> l); //robo executa as a��es
	public void JasonFoiAvisado();
}