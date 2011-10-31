package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Sem uso - representava o planejador Jason
 * */
public class ControleTCPRobot {
	private double _wCampo = 800.0, _hCampo = 600.0;
	private double _energia, _x, _y, _vel, _heading, _width, _height;
	private int _ultimaAcao, _numeroAcoesTomadas;
	private String _nomeRobo;
	
	public ControleTCPRobot(List<String> entrada){
		trataEntrada(entrada);
	}
	
//	public List<String> getAcao(){
//		if (getIndiceRobo(_nomeRobo) == 1) {
//			return getAcoesRobo1();	
//		}else if (getIndiceRobo(_nomeRobo) == 2) {
//			return getAcoesRobo2();	
//		}
//		
//		List<String> rt = new ArrayList<String>();
//		//cabeçalho (tipo pacote e tamanho)
//		CriadorPacotes cp = new CriadorPacotes();
//		rt.add(cp.getIntTipo(TipoPacotes.respostaAcao)+"");
//		rt.add((short)2+"");
//		//-----------
//		
//		int acao = rndInt(4);
//		
//		//ação 1: mover radar
//		//ação 2: atirar
//		//ação 3: mover ahead
//		//4: turnRight
//		//5: turnLeft
//		
//		//força uma ação
//		//acao = 4;
//		
//		rt.add("" + acao);
//		if (acao == 1) {
//			rt.add("" + rnd(360)); //angulo radar
//		} else if (acao == 2) {
//			rt.add("" + rndInt(3)); //forca tiro
//		} else if (acao == 3) {
//			double dis = rndInt(400);
//			if (rndB()) {dis = -dis;}
//			rt.add("" + dis);
//		} else if (acao == 4) {
//			double vlr = rndInt(360);
//			if (rndB()) {vlr = -vlr;}
//			rt.add("" + vlr);
//		}
//		return rt;
//	}
	
	private int rndInt(int vlr) {
		return new Random().nextInt(vlr) + 1;
	}
	private double rnd(int vlr) {
		return rndInt(vlr);
	}
	private boolean rndB(){return new Random().nextBoolean();}
		
//	/**
//	 * Ações do robo 1
//	 * */
//	public List<String> getAcoesRobo1(){
//		List<String> rt = new ArrayList<String>();
//		
//		//cabeçalho (tipo pacote e tamanho)
//		CriadorPacotes cp = new CriadorPacotes();
//		rt.add(cp.getIntTipo(TipoPacotes.respostaAcao)+"");
//		rt.add((short)2+"");
//		//-----------
//		
//		double posicaoRight = _wCampo - _width+10; //+10 = ajuste
//		double posicaotop = _hCampo - _height + 10; //-10 = ajuste
//		
//		if (_numeroAcoesTomadas == 0 && _x != posicaoRight) {
//			rt.add("" + 3); //ahead
//			rt.add("" + getAnaliseValor(_wCampo /*posicaoRight*/, _x));//move para a frente
//			return rt;
//		}
//		if (_numeroAcoesTomadas == 1 &&_heading != 0) {
//			rt.add("" + 4); //vira para cima
//			rt.add("" + getAnaliseValor(0, _heading)); //virar para cima
//			return rt;
//		}
//		if (_numeroAcoesTomadas == 2 &&_y != posicaotop){
//			rt.add("" + 3); //ahead
//			rt.add("" + getAnaliseValor(posicaotop, _y));//move para a frente
//			return rt;
//		}
//		if (_numeroAcoesTomadas == 3 &&_heading != 225.0) {
//			rt.add("" + 4); //virar
//			rt.add("" + getAnaliseValor(225, _heading)); //vira de volta ao campo
//			return rt;
//		}
//		
//		rt.add("" + 2); //tiro
//		rt.add("" + 3); //intensidade
//		
//		return rt;
//	}
//	
//	/**
//	 * Teste de controle específico do robo 2
//	 * */
//	public List<String> getAcoesRobo2(){
//		List<String> rt = new ArrayList<String>();
//		double posicaoBottom = _hCampo -60 ;//- _wRobot; //ajuste
//		
//		//cabeçalho (tipo pacote e tamanho)
//		CriadorPacotes cp = new CriadorPacotes();
//		rt.add(cp.getIntTipo(TipoPacotes.respostaAcao)+"");
//		rt.add((short)2+"");
//		//-----------
//		
//		if (_numeroAcoesTomadas == 0 &&_heading != 0) {
//			rt.add("" + 4); //virar
//			rt.add("" + getAnaliseValor(180, _heading)); //virar para baixo
//			return rt;
//		}
//		if (_numeroAcoesTomadas == 1 && _y != posicaoBottom) {
//			rt.add("" + 3); //ahead
//			rt.add("" + getAnaliseValor(posicaoBottom, _y));//move para a frente
//			return rt;
//		}
//		if (_numeroAcoesTomadas == 2 && _heading != 270) {
//			rt.add("" + 4); //virar
//			rt.add("" + getAnaliseValor(270, _heading)); //virar para cima
//			return rt;
//		}
//		if (_numeroAcoesTomadas == 3 && _x != 10){
//			rt.add("" + 3); //ahead
//			rt.add("" + -getAnaliseValor(10, _x));//move para a frente
//			return rt;
//		}
//		if (_numeroAcoesTomadas == 4 &&_heading != 45.0) {
//			rt.add("" + 4); //virar
//			rt.add("" + getAnaliseValor(45, _heading)); //vira de volta ao campo
//			return rt;
//		}
//		
//		rt.add("" + 2); //atira
//		rt.add("" + 3); //forca
//		
//		return rt;
//	}
	
	private void trataEntrada(List<String> entrada){
		//tipo: entrada.get(0)
		//tam: entrada.get(1);
		_energia = Double.parseDouble(entrada.get(2));
		_x = Double.parseDouble(entrada.get(3));
		_y = Double.parseDouble(entrada.get(4));
		_vel = Double.parseDouble(entrada.get(5));
		_heading = Double.parseDouble(entrada.get(6));
		_ultimaAcao = Integer.parseInt(entrada.get(7));
		_numeroAcoesTomadas = Integer.parseInt(entrada.get(8));
		_width = Double.parseDouble(entrada.get(9));
		_height = Double.parseDouble(entrada.get(10));
		_nomeRobo = entrada.get(11);
		//print();
	}

	private double getAnaliseValor(double valorEscolhido, double referencia){
		double vlr;
		vlr = valorEscolhido - referencia;
		//informa o lado à ser rotacionado
		//if (getHeading() < headingEscolhido) {if (vlr<0) vlr *=-1;}
		return vlr;
	}
	
	@Override
	public String toString() {
		return _nomeRobo + ","+ _wCampo + "," + _hCampo + 
			_energia + "," + _x + "," + _y + "," + _vel + "," + 
			_heading + "," + _width + "," +_height + "," + 
			_ultimaAcao + "," + _numeroAcoesTomadas;
	} 
	
	public void print(){System.out.println(toString());}
	
	private int getIndiceRobo(String nomeRobo){
		return Integer.valueOf(nomeRobo.substring(nomeRobo.lastIndexOf('(')+1, nomeRobo.lastIndexOf(')')));
	}	
	
}
