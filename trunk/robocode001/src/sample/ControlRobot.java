package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

/**
 * a ideia é fazer esta classe se comunicar com o Jason e controlar o robo
 * */
public class ControlRobot {
	private int _ultimaAcao;
	//private boolean bateuParede = false;
	private ArrayList<RobosScaneados> _robos = new ArrayList<RobosScaneados>();
	
	private double _xInicial, _yInicial;
	private double _wCampo, _hCampo, _energia, _x, _y, _velocidade;
	private String _nomeRobot;
	private int _outros;
	
	public ControlRobot(String nomeRobot, double w, double h, double xInicial, double yInicial, int outros){
		_nomeRobot = nomeRobot;
		_wCampo = w; _hCampo = h;
		_xInicial = xInicial; _yInicial = yInicial;
		_outros = outros;
		print();
	}
	
	public List<String> getAcaoRealizar(){
		List<String> rt = new ArrayList<String>();
		int acao = rndInt(5);
		
		//ação 1: mover radar
		//ação 2: atirar
		//ação 3: mover ahead
		//4: turnRight
		//5: turnLeft
		
		rt.add("" + acao);
		if (acao == 1) {
			rt.add("" + rnd(360)); //angulo radar
		} else if (acao == 2) {
			rt.add("" + rndInt(3)); //forca tiro
		} else if (acao == 3) {
			double dis = rndInt(400);
			if (rndB()) {dis = -dis;}
			rt.add("" + dis); //distancia
		} else if (acao == 4) {
			double dis = rndInt(360);
			if (rndB()) {dis = -dis;}
			rt.add("" + dis); //virar
		} else if (acao == 5) {
			double dis = rndInt(360);
			if (rndB()) {dis = -dis;}
			rt.add("" + dis); //virar
		}
		return rt;
	}
	
	public void Parede(HitWallEvent e){
		//bateuParede = true;
		print();
	}
	public void ScaneouRobo(ScannedRobotEvent e){
		addRobosScaneados(e);
	}
	
	
	public void setParams(double energia, double x, double y, double velocidade, int ultimaAcao){
		_energia = energia;
		_x = x;
		_y = y;
		_velocidade = velocidade;
		_ultimaAcao = ultimaAcao;
		print();
	}

	private int rndInt(int vlr) {
		return new Random().nextInt(vlr) + 1;
	}
	private double rnd(int vlr) {
		return rndInt(vlr);
	}
	private boolean rndB(){return new Random().nextBoolean();}
	
	@Override
	public String toString() {
		String rt = "r: " +_nomeRobot + ",e: " + _energia + ",o: " + _outros +
		   " size: ["+_wCampo+","+_hCampo+"]" +
		   //" ["+_xInicial+","+_yInicial+"]" +
		   " [" + _x + "," + _y + "] v: " + _velocidade + ", acao: " + getAcaoNome(_ultimaAcao);
		
		//para cada robo scaneado
		for (RobosScaneados rb : _robos) {
			rt += " {"+rb.toString()+"} ";
		}
		
		return rt;
	}
	public void print(){System.out.println(toString());}
	
	//robos scaneados
	private void addRobosScaneados(ScannedRobotEvent e){
		if (_robos.size() < _outros) { //_outros contem o número de outros robos do stage
			if (EstaNaLista(e.getName())) {
				AtualizarDados(e);
				return;
			}
			RobosScaneados add = new RobosScaneados();
			add.nome = e.getName();
			add.distancia = e.getDistance();
			add.energia = e.getEnergy();
			_robos.add(add);
		}
	}
	
	private boolean EstaNaLista(String nome){
		for (RobosScaneados rb : _robos) {
			if (rb.nome.equals(nome)) return true;
		}
		return false;
	}
	
	private void AtualizarDados(ScannedRobotEvent e){
		for (RobosScaneados rb : _robos) {
			if (rb.nome.equals(e.getName())) {
				rb.AtualizarDados(e.getDistance(), e.getEnergy());
				return;
			}
		}
	}
	
	private String getAcaoNome(int acao){
		String a = "nada";
		switch(acao){
			case 1: a= "radar"; break;
			case 2: a = "atirar"; break;
			case 3: a = "Ahead"; break;
			case 4: a = "right"; break;
			case 5: a = "left"; break;
		}
		return a;
	}
	
}
class RobosScaneados{
	String nome;
	double distancia;
	double energia;
	
	public void AtualizarDados(double dist, double ene){
		distancia = dist;
		energia = ene;
	}
	
	@Override
	public String toString() {
		return nome + " - " + distancia + " - " + energia;
	}
}