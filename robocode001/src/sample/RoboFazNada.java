package sample;

import java.awt.Color;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import robocode.DadosRobos.DadosRobos;
import robocode.AdvancedRobot;
import robocode.BattleEndedEvent;
import robocode.DeathEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.RobotDeathEvent;
import robocode.StatusEvent;
import robocode.WinEvent;
//import tcp.TCPClient;
import tcp.TCPClient;
import tcp.TCPServer;
import tcp.interfaces.IRoboTCP;
import tcp.pacotes.AnalisePacotes;
import tcp.pacotes.CriadorPacotes;

/**
 * Robo Faz Nada<br />
 * sera controlado via Jason 
 * @author Emerson Shigueo Sugimoto
 * @author Lucas Del Castanhel
 * */
public class RoboFazNada extends AdvancedRobot implements IRoboTCP {
	private boolean executar = true;
	private boolean pausar = false;
	private int _portaServidorTCP = 7890;
	
	private boolean _pararAvisarJason = false;
	private AvisarJason _avJ;
	private int _portaServidorTCPJason = 7770;
	private String _ipTCPJason = "localhost";
	
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
			_server = new TCPServer(getPortaServidorRobo());
			_server.setIstcp(this);
			_server.start();
		}
	}
	
	private void killServidorTCP(){
		_server.parar(); //_server.stop();
		_server = null;
	}
	private int getPortaServidorRobo(){ return _portaServidorTCP + getIndiceRobo(); }
	private void AvisarJason(){
		if (_pararAvisarJason) return; //modo para avisar o jason foi desabilitado
		_avJ = new AvisarJason(_portaServidorTCPJason, _ipTCPJason, this); _avJ.start(); 
	}
	
	public void JasonFoiAvisado() {
		System.out.println("# ROBO - Jason FOI AVISADO ");
		_avJ.Parar();
		_avJ = null;
	}
	
	public void run(){
		iniciaServidorTCP();
		setBodyColor(Color.WHITE);
		setGunColor(Color.BLACK);
		setRadarColor(Color.BLUE);
		setBulletColor(Color.green);
		setScanColor(Color.GREEN);
		
		//turnRight(90 - getHeading()); //arruma a posicao inicial do robo
		
		while(executar){
			while(pausar) {try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }}
			//ahead(1);
			if (_listaAcoes != null && _listaAcoes.size() >= 2) {
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
			stop();
		}
	}
	
	public void executar(){
		if (_listaAcoes == null && _listaAcoes.size() <= 1) { 
			AvisarJason(); 
			return; 
		}
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
		} else {
			_listaAcoes.clear();
		}
		if (_listaAcoes.size() <= 1) {
			AvisarJason(); //avisa o Jason que ele deve voltar a funcionar
		}
		
	}

//	@Override
//	public void print(String msg) {
//		System.out.println("[robot] " + msg);
//	}

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
		return d;
	}
	
//	private double Truncagem(double vlr){
//		return ((int)(vlr*1000))/1000.0; 
//	}
	
	@Override
	public void ExecutarAcoes(List<String> l) {
		if (l.size() <= 1) {return;}
		int tipo = Integer.valueOf(l.get(0)); //tipo acao
		switch(tipo){
			case 0: parar(); break; //parar
			case 1: reiniciar(); break; //reiniciar
			case 2: case 3: case 4: case 5:
				if (l == null || l.size() <= 0) {
					//se vier uma lista vazia.
					//if (_listaAcoes != null) {_listaAcoes.clear();}
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
	
	private void parar(){ pausar = true; if (_listaAcoes!=null) _listaAcoes.clear(); }
	private void reiniciar(){ pausar = false; }
	
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
	
	@Override
	public void onHitRobot(HitRobotEvent e) {
		ahead(-10); //super.onHitRobot(e);
	}
	
	@Override
	public void onHitWall(HitWallEvent e) {
		//super.onHitWall(e);
		ahead(-10);
		turnLeft(20);
	}

	/**
     *analise de pacotes TCP 
     * */
	@Override
	public void analisePacote(byte[] pacote, DataOutputStream out) throws IOException {
		CriadorPacotes cp = new CriadorPacotes();
		switch (AnalisePacotes.getTipo(pacote)) {
			case pedirDadosToRobot:
				//servidor TCP do robo recebeu um pacote com a mensagem 'quero os seus dados'
				out.write(cp.pacoteDadosRobos(this.getDadosRobo())); //envia os dados ao Jason
				break;
			case ordemToRobot:
				//System.out.println("ENVIADO UM WAKEUP AO JASON");
				//robo recebendo ordens
				ExecutarAcoes(AnalisePacotes.getLista(pacote)); //recebe as ordens para o robô
				out.writeInt(0); //envia um int como resposta ao Jason
				break;
			case respostaJasonToRobot: //atualiza o robô
				System.out.println("RECEBI UMA RESPOSTA DO - JASON");
				//robô sendo avisado que deve parar de enviar mensagens ao Jason para ele acordar
				this.JasonFoiAvisado();
				break;
		}
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

class AvisarJason extends Thread {
	private int _portaServidorTCPJason;
	private String _ipTCPJason;
	private boolean _continuar = true;
	private RoboFazNada _rfn;
	
	public AvisarJason(int portaServidorTCPJason, String ipTCPJason, RoboFazNada rfn){
		_portaServidorTCPJason = portaServidorTCPJason;
		_ipTCPJason = ipTCPJason;
		_rfn = rfn;
		_continuar = true;
	}
	
	public void Parar(){
		_continuar = false;
	}
	
	public void run(){
		TCPClient cli = new TCPClient(_portaServidorTCPJason, _ipTCPJason);
	
		while (_continuar) {
			//System.out.println("# ROBO - AvisarJason() ");
			cli.avisarJason(_rfn.getDadosRobo());
			try { Thread.sleep(150); } catch (InterruptedException e) { e.printStackTrace(); } //aguarda um pouco
		}
		cli = null;
	}
}