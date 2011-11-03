package sample;

import java.awt.Color;
import java.util.List;
import DadosRobos.DadosRobos;
import robocode.AdvancedRobot;
import robocode.BattleEndedEvent;
import robocode.DeathEvent;
import robocode.RobotDeathEvent;
import robocode.StatusEvent;
import robocode.WinEvent;
import tcp.TCPServer;
import tcp.interfaces.IRoboTCP;

/**
 * Robo Faz Nada<br />
 * sera controlado via Jason 
 * @author Emerson Shigueo Sugimoto
 * @author Lucas Del Castanhel
 * */
public class RoboFazNada extends AdvancedRobot implements IRoboTCP {
	private boolean executar = true;
	private boolean _executandoAlgo = false;
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
		_executandoAlgo = false;
		if (_server == null) {
			_server = new TCPServer(getPortaServidorRobo(), this);
			_server.start();
		}
	}
	
	private void killServidorTCP(){
		_executandoAlgo = false;
		_server.parar(); //_server.stop();
		_server = null;
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
		
//		//arruma a posicao inicial do robo
//		turnRight(90 - getHeading());
		
		while(executar){
			while(pausar) {try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }}
			//ahead(1);
			if (_listaAcoes != null && _listaAcoes.size() >= 2) {
				_executandoAlgo = true;
				executar(); 
			} else {
				turnRadarLeft(1); //apenas para nao matar o processo
			}
		}
	}
	
	@Override
	public void onStatus(StatusEvent e) {
		//super.onStatus(e);
		if (getEnergy() <= 0) {
			//System.out.println("StatusEvent -- " + e.getStatus());
			executar = false;
			_executandoAlgo = false;
			stop();
		}
	}
	
	public void executar(){
		if (_listaAcoes == null && _listaAcoes.size() <= 1) { _executandoAlgo = false; return;}
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
		
		//System.out.println("_listaAcoes: " + _listaAcoes);
		if (_listaAcoes.size() >= 2) {
			_listaAcoes.remove(1);
			_listaAcoes.remove(0);
			_executandoAlgo = true;
		} else {
			_listaAcoes.clear();
			_executandoAlgo = false;
		}
		//_listaAcoes.clear(); //limpa as acoes executadas
//		if (_listaAcoes.size() >= 2) {
//			//aguarda um pouco antes de chamar novamente
//			try { Thread.sleep(70); } catch (InterruptedException e) { e.printStackTrace(); }
//			executar(); 
//		}
	}

	@Override
	public void print(String msg) {
		System.out.println("[robot] " + msg);
	}

	@Override
	public DadosRobos getDadosRobo() {
		DadosRobos d = new DadosRobos();
		d.setIndiceRobo(getIndiceRobo());
		d.setNomeRobo(getName());
		d.setEnergia(getEnergy());
		d.setX(getX());
		d.setY(getY());
		d.setVelocidade(getVelocity());
		d.setHeading(getHeading());
		d.setWidth(getWidth());
		d.setHeight(getHeight());
		d.setNumeroRound(getNumRounds()); //numero do round
		d.setExecutandoAlgo(_executandoAlgo);
		return d;
	}
	
//	private double Truncagem(double vlr){
//		return ((int)(vlr*1000))/1000.0; 
//	}
	
	@Override
	public void ExecutarAcoes(List<String> l) {
		if (l.size() <= 1) return;
		int tipo = Integer.valueOf(l.get(0)); //tipo acao
		switch(tipo){
			case 0: parar(); break; //parar
			case 1: reiniciar(); break; //reiniciar
			case 2: case 3: case 4: case 5:
				if (l == null || l.size() <= 0) {
					//se vier uma lista vazia.
					//if (_listaAcoes != null) {_listaAcoes.clear();}
					_executandoAlgo = false;
					break; 
				} 
				if (_listaAcoes==null || _listaAcoes.size() <= 0) {
					_listaAcoes = l;
					//System.out.println("_listaAcoes: " + _listaAcoes);
					
				}/* else {
					_listaAcoes.addAll(l);
				}*/
			break;
		}
	}
	
	private void parar(){ 
		//doNothing();
		_executandoAlgo = false;
		pausar = true; if (_listaAcoes!=null) _listaAcoes.clear(); 
	}
	private void reiniciar(){
		//execute();
		pausar = false; 
	}
	
	@Override public void onDeath(DeathEvent event) {
		killServidorTCP(); //super.onDeath(event);
	}
	@Override public void onWin(WinEvent event) { 
		killServidorTCP(); //super.onWin(event); 
	}
	@Override public void onBattleEnded(BattleEndedEvent event) {
		killServidorTCP(); //super.onBattleEnded(event);
	}
	@Override public void onRobotDeath(RobotDeathEvent event) {
		killServidorTCP(); //super.onRobotDeath(event);
	}
	
}

/**
 * 0 - parar
 * 1 - reiniciar robo
 * 2 - atirar
 * 3 - virar esquerda
 * 4 - virar direita
 * 5 - andar
 **/