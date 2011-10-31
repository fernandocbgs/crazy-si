package sample;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import robocode.AdvancedRobot;
import robocode.StatusEvent;
import tcp.TCPServer;
import tcp.interfaces.IServidorTCP;

/**
 * Robo Faz Nada<br />
 * ideia é ser controlado via Jason 
 * @author Emerson Shigueo Sugimoto
 * @author Lucas Del Castanhel
 * */
public class RoboFazNada extends AdvancedRobot implements IServidorTCP {
	private boolean executar = true;
	private boolean pausar = false;
	private int _portaServidorTCP = 7890;
	private List<String> _listaAcoes = null;
	private TCPServer _server = null;
	
	public RoboFazNada() { }
	
	private int getIndiceRobo(){
		String nomeRobo = getName();
		if (nomeRobo.lastIndexOf('(') > 0)
			return Integer.valueOf(nomeRobo.substring(nomeRobo.lastIndexOf('(')+1, nomeRobo.lastIndexOf(')')));
		return 1;
	}

	/**
	 * Inicia o Servidor TCP do robo
	 * */
	private void iniciaServidorTCP(){
		if (_server == null) {
			_server = new TCPServer(getPortaServidorRobo(), this);
			_server.start();
		}
	}
	
	private int getPortaServidorRobo(){
		return _portaServidorTCP + getIndiceRobo();
	}
	
	public void run(){
		iniciaServidorTCP();
		setBodyColor(Color.WHITE);
		setGunColor(Color.BLACK);
		setRadarColor(Color.BLUE);
		setBulletColor(Color.green);
		setScanColor(Color.GREEN);
		
//		//arruma a posição inicial do robo
//		double vlr = getAnaliseValor(90, getHeading());
//		turnRight(vlr);
		
		while(executar){
			while(pausar) {try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }}
			//ahead(1);
			if (_listaAcoes != null && _listaAcoes.size() >= 2) { executar(); 
			} else {
				//faz um ahead, apenas para não matar o processo
				ahead(1);ahead(-1);
			}
		}
	}
	
	@Override
	public void onStatus(StatusEvent e) {
		//super.onStatus(e);
		if (getEnergy() <= 0) {
			//System.out.println("StatusEvent -- " + e.getStatus());
			executar = false;
			stop();
		}
	}
	
	public void executar(){
		if (_listaAcoes == null && _listaAcoes.size() <= 1) return;
		/**
		 * 0 - parar
		 * 1 - reiniciar robo
		 * 2 - atirar
		 * 3 - virar esquerda
		 * 4 - virar direita
		 * 5 - andar
		 **/
		int tipo = Integer.valueOf(_listaAcoes.get(0)); //tipo acao
		switch(tipo){
			case 2: 
				fire(Double.parseDouble(_listaAcoes.get(1))); break;
			case 3:
				turnLeft(Double.parseDouble(_listaAcoes.get(1))); break;
			case 4:
				turnRight(Double.parseDouble(_listaAcoes.get(1))); break;
			case 5: 
				ahead(Double.parseDouble(_listaAcoes.get(1))); break;
		}
		
		_listaAcoes.clear(); //limpa as ações executadas
	}

//	private double getAnaliseValor(double valorEscolhido, double referencia){
//		double vlr;
//		vlr = valorEscolhido - referencia;
//		//informa o lado à ser rotacionado
//		//if (getHeading() < headingEscolhido) {if (vlr<0) vlr *=-1;}
//		return vlr;
//	}

	@Override
	public void print(String msg) {
		System.out.println("[robot] " + msg);
	}

	@Override
	public List<String> getDadosRobo() {
		List<String> dadosRobo = new ArrayList<String>();
		dadosRobo.add(getIndiceRobo()+"");
		dadosRobo.add(getName());
		dadosRobo.add(getEnergy()+"");
		dadosRobo.add(getX()+"");
		dadosRobo.add(getY()+"");
		dadosRobo.add(getVelocity()+"");
		dadosRobo.add(getHeading()+"");
		dadosRobo.add(getWidth()+"");
		dadosRobo.add(getHeight()+"");
		return dadosRobo;
	}

	@Override
	public void ExecutarAcoes(List<String> l) {
		if (l.size() <= 1) return;
		/**
		 * 0 - parar
		 * 1 - reiniciar robo
		 * 2 - atirar
		 * 3 - virar esquerda
		 * 4 - virar direita
		 * 5 - andar
		 **/
		int tipo = Integer.valueOf(l.get(0)); //tipo acao
		switch(tipo){
			case 0: parar(); break; //parar
			case 1: reiniciar(); break; //reiniciar
			case 2: case 3: case 4: case 5: _listaAcoes = l; break;
		}
	}
	
	private void parar(){ pausar = true; }
	private void reiniciar(){ pausar = false; }
	
}