package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import robocode.HitWallEvent;
import robocode.ScannedRobotEvent;

/**
 * a ideia é fazer esta classe se comunicar com o Jason e controlar o robo
 * Não usar esta classe, usar o método com o TCP 
 * */
@Deprecated
public class ControlRobot {
	private int _ultimaAcao, _numeroAcoesTomadas = 0;
	//private boolean bateuParede = false;
	private ArrayList<RobosScaneados> _robos = new ArrayList<RobosScaneados>();
	
	@SuppressWarnings("unused")
	private double _xInicial, _yInicial;
	private double _wCampo = 800, _hCampo = 600, _energia, _x, _y, _velocidade,
				   _heading, _wRobot, _hRobot;
	private String _nomeRobot;
	private int _outros;
	
	public ControlRobot(String nomeRobot, double w, double h, double xInicial, double yInicial, int outros){
		_nomeRobot = nomeRobot;
		if (w > _wCampo)_wCampo = w;
		if (h > _hCampo) _hCampo = h;
		_xInicial = xInicial; _yInicial = yInicial;
		_outros = outros;
		print();
	}
	
	public List<String> getAcaoRealizar(){
		if (_nomeRobot.equals("sample.RoboFazNada (1)")) {
			return getAcoesRobo1();	
		} else if (_nomeRobot.equals("sample.RoboFazNada (2)")) {
			return getAcoesRobo2();	
		}
		
		List<String> rt = new ArrayList<String>();
		int acao = rndInt(4);
		
		//ação 1: mover radar
		//ação 2: atirar
		//ação 3: mover ahead
		//4: turnRight
		//5: turnLeft
		
		//força uma ação
		//acao = 4;
		
		rt.add("" + acao);
		if (acao == 1) {
			rt.add("" + rnd(360)); //angulo radar
		} else if (acao == 2) {
			rt.add("" + rndInt(3)); //forca tiro
		} else if (acao == 3) {
			double dis = rndInt(400);
			if (rndB()) {dis = -dis;}
			rt.add("" + dis);
		} else if (acao == 4) {
			double vlr = rndInt(360);
			if (rndB()) {vlr = -vlr;}
			rt.add("" + vlr);
		}
		return rt;
	}
	
	/**
	 * Teste de controle específico do robo 1
	 * */
	public List<String> getAcoesRobo1(){
		List<String> rt = new ArrayList<String>();
		double posicaoRight = _wCampo - _wRobot+10; //+10 = ajuste
		double posicaotop = _hCampo - _hRobot + 10; //-10 = ajuste
		
		if (_numeroAcoesTomadas == 0 && _x != posicaoRight) {
			rt.add("" + 3); //ahead
			rt.add("" + getAnaliseValor(posicaoRight, _x));//move para a frente
			return rt;
		}
		if (_numeroAcoesTomadas == 1 &&_heading != 0) {
			rt.add("" + 4); //vira para cima
			rt.add("" + getAnaliseValor(0, _heading)); //virar para cima
			return rt;
		}
		if (_numeroAcoesTomadas == 2 &&_y != posicaotop){
			rt.add("" + 3); //ahead
			rt.add("" + getAnaliseValor(posicaotop, _y));//move para a frente
			return rt;
		}
		if (_numeroAcoesTomadas == 3 &&_heading != 225.0) {
			rt.add("" + 4); //virar
			rt.add("" + getAnaliseValor(225, _heading)); //vira de volta ao campo
			return rt;
		}
		
		rt.add("" + 2); //nada
		rt.add("" + 3); //nada
		
		return rt;
	}
	
	/**
	 * Teste de controle específico do robo 2
	 * */
	public List<String> getAcoesRobo2(){
		List<String> rt = new ArrayList<String>();
		double posicaoBottom = _hCampo -60 ;//- _wRobot; //ajuste
		
		if (_numeroAcoesTomadas == 0 &&_heading != 0) {
			rt.add("" + 4); //virar
			rt.add("" + getAnaliseValor(180, _heading)); //virar para baixo
			return rt;
		}
		if (_numeroAcoesTomadas == 1 && _y != posicaoBottom) {
			rt.add("" + 3); //ahead
			rt.add("" + getAnaliseValor(posicaoBottom, _y));//move para a frente
			return rt;
		}
		if (_numeroAcoesTomadas == 2 && _heading != 270) {
			rt.add("" + 4); //virar
			rt.add("" + getAnaliseValor(270, _heading)); //virar para cima
			return rt;
		}
		if (_numeroAcoesTomadas == 3 && _x != 10){
			rt.add("" + 3); //ahead
			rt.add("" + -getAnaliseValor(10, _x));//move para a frente
			return rt;
		}
		if (_numeroAcoesTomadas == 4 &&_heading != 125.0) {
			rt.add("" + 4); //virar
			rt.add("" + getAnaliseValor(125, _heading)); //vira de volta ao campo
			return rt;
		}
		
		rt.add("" + 2); //atira
		rt.add("" + 3); //forca
		
		return rt;
	}
	
	public void Parede(HitWallEvent e){
		//bateuParede = true;
		print();
	}
	public void ScaneouRobo(ScannedRobotEvent e){
		addRobosScaneados(e);
	}
	
	private double getAnaliseValor(double valorEscolhido, double referencia){
		double vlr;
		vlr = valorEscolhido - referencia;
		//informa o lado à ser rotacionado
		//if (getHeading() < headingEscolhido) {if (vlr<0) vlr *=-1;}
		return vlr;
	}
	
	public void setParams(
			double energia, double x, double y, double velocidade, double heading, 
			int ultimaAcao, int numeroAcoesTomadas, double widthRobot, double heightRobot){
		_energia = energia;
		_x = x;
		_y = y;
		_velocidade = velocidade;
		_heading = heading;
		_ultimaAcao = ultimaAcao;
		_numeroAcoesTomadas = numeroAcoesTomadas;
		_wRobot = widthRobot;
		_hRobot = heightRobot;
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
		   " tam: ["+_wRobot+","+_hRobot+"]" +
		   ", heading: " + _heading +
		   //" ["+_xInicial+","+_yInicial+"]" +
		   " [" + _x + "," + _y + "] v: " + _velocidade + ", acao: " + getAcaoNome(_ultimaAcao) + 
		   " ["+ _wCampo +"," + _hCampo + "]"
		   ;
		
//		//para cada robo scaneado
//		for (RobosScaneados rb : _robos) {
//			rt += " {"+rb.toString()+"} ";
//		}
		
		return rt;
	}
	public void print(){System.out.println(toString());}
	
	//robos scaneados
	private void addRobosScaneados(ScannedRobotEvent e){
		if (EstaNaLista(e.getName())) {
			AtualizarDados(e);
			return;
		}
		if (_robos.size() < _outros) { //_outros contem o número de outros robos do stage
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