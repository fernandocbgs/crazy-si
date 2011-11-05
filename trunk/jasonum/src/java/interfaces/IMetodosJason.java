package interfaces;

import robocode.DadosRobos.DadosRobos;

public interface IMetodosJason  {
	public void atualizarDadosRobos(DadosRobos r, int indice);
	public void atualizarDadosRobosViaTCP();
	public DadosRobos getR1();
	public DadosRobos getR2();
	public String getAgSave();
	public String getAgRefem();
	public double getXF();
	public double getYF();
	public int getPorta(int indice);
	public String getIp();
}
