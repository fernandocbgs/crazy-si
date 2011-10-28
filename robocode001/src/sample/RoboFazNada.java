package sample;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import robocode.AdvancedRobot;
import robocode.StatusEvent;
import servidor_tcp.TCPClient;
import servidor_tcp.gui.IAcoesTCP;
import servidor_tcp.pacotes.CriadorPacotes;
//import robocode.BattleEndedEvent;
//import robocode.DeathEvent;
//import robocode.HitWallEvent;
//import robocode.RobotDeathEvent;
//import robocode.RoundEndedEvent;
//import robocode.ScannedRobotEvent;
//import robocode.WinEvent;
import servidor_tcp.pacotes.CriadorPacotes.TipoPacotes;

/**
 * Robo Faz Nada<br />
 * ideia é ser controlado via Jason 
 * @author Emerson Shigueo Sugimoto
 * @author Lucas Del Castanhel
 * */
public class RoboFazNada extends AdvancedRobot implements IAcoesTCP {
	//private static final long serialVersionUID = 4417781459965392288L;
	private int _ultimaAcao, _numeroAcoesTomadas = 0;
	private List<String> _acaoExecutar;
	private boolean executar = true;
	private int _porta = 7890;
	private String _ip = "localhost";
	
	public RoboFazNada() { }
	
//	private static int getIndiceRobo(String nomeRobo){
//		return 1;
//		//return Integer.valueOf(nomeRobo.substring(nomeRobo.lastIndexOf('(')+1, nomeRobo.lastIndexOf(')')));
//	}
	
	/**
	 * pergunta ao servidor TCP o que o robo deve fazer
	 * a resposta é a ação
	 * */
	private void iniciaClienteRobo(){
		_acaoExecutar = null;
		List<String> params = new ArrayList<String>();
		params.add(getEnergy()+"");
		params.add(getX()+"");
		params.add(getY()+"");
		params.add(getVelocity()+"");
		params.add(getHeading()+"");
		params.add(_ultimaAcao+"");
		params.add(_numeroAcoesTomadas+"");
		params.add(getWidth()+"");
		params.add(getHeight()+"");
		params.add(getName());
		
		new TCPClient(_porta, _ip, this).iniciarCliente(TipoPacotes.oquedevoFazer, params);
	}
	
	public void run(){
		iniciaClienteRobo();
		//iniciarServidor();
		//ClienteRMI();
		//_controleRobo = new ControlRobot(getName().toString(),getBattleFieldWidth(), getBattleFieldHeight(),getX(),getY(),getOthers());

		setBodyColor(Color.WHITE);
		setGunColor(Color.BLACK);
		setRadarColor(Color.BLUE);
		setBulletColor(Color.green);
		setScanColor(Color.GREEN);
		//arruma a posição inicial do robo
		double vlr = getAnaliseValor(90, getHeading());
		turnRight(vlr);
		
		//if (vlr < 0){turnRight(vlr);}else{turnLeft(vlr);}
		
		while(executar){
			//ahead(1);

			/*
			 * O ROBO SÓ VAI CHAMAR A PROXIMA AÇÃO ASSIM QUE TERMINAR A ÚLTIMA
			 */
			
			//try {Thread.sleep(2);} catch (InterruptedException e) { e.printStackTrace(); }
			
			//System.out.println("[" + getX() + "," + getY() + "]");
			//atualiza as informações do robo
//			_controleRobo.setParams(
//					getEnergy(),
//					getX(),
//					getY(),
//					getVelocity(),
//					getHeading(),
//					_ultimaAcao,
//					_numeroAcoesTomadas,
//					getWidth(),
//					getHeight());
			//executar(_controleRobo.getAcaoRealizar());
			
			if (_acaoExecutar != null && _acaoExecutar.size() >= 2) {
				//System.out.println("_acaoExecutar: " + _acaoExecutar);
				if (_acaoExecutar != null) executar(_acaoExecutar);
			}
			
			
			iniciaClienteRobo();
			
		}
	}
	
//	public void SetAcoes(List<String> acoes){
//		System.out.println("1 ACOES " + acoes);
//		//_acaoExecutar = acoes;
//		_acaoExecutar = new ArrayList<String>();
//		for (String str : acoes) {
//			_acaoExecutar.add(str);
//		}
//		System.out.println("2 ACOES " + _acaoExecutar);
//		//executar = true;
//		//this.resume();
//		//notify();
//		//não posso chamar aqui por que ele ainda não chamou o run...
//		run();
//		//executar(_acaoExecutar);
//	}

	
//	@Override
//	public void onHitWall(HitWallEvent e) {
//		//_controleRobo.Parede(e);
//	}
//	
//	public void onScannedRobot(ScannedRobotEvent e) {
//		//_controleRobo.ScaneouRobo(e);
//	}
//	
//	@Override
//	public void onBattleEnded(BattleEndedEvent e) {
//		//super.onBattleEnded(e);
//	}
//	
//	@Override
//	public void onDeath(DeathEvent e) {
//		//super.onDeath(e);
//		System.out.println("Morri ");
//		executar = false;
//		stop();
//	}
//	
//	@Override
//	public void onRoundEnded(RoundEndedEvent e) {
//		//super.onRoundEnded(e);
//	}
//	
//	@Override
//	public void onRobotDeath(RobotDeathEvent e) {
//		//super.onRobotDeath(e);
//		System.out.println("Morri ");
//		executar = false;
//		stop();
//	}
	
	@Override
	public void onStatus(StatusEvent e) {
		//super.onStatus(e);
		if (getEnergy() <= 0) {
			//System.out.println("StatusEvent -- " + e.getStatus());
			executar = false;
			stop();
		}
	}
	
	
	
//	
//	@Override
//	public void onWin(WinEvent e) {
//		//super.onWin(e);
//	}
	
	public void executar(List<String> params){
		//if (params.size() <= 1) return;
		//0 = tamanho
		//1 = acao
		//2 = valor
		//System.out.println(params.get(0) +","+params.get(1)+","+params.get(2));//+","+params.get(3));
		
		_numeroAcoesTomadas++;
		_ultimaAcao = Integer.valueOf(params.get(1));
		
		double vlr = Double.valueOf(params.get(2));
		
		if (_ultimaAcao <= 0) return;
		if (_ultimaAcao == 1) {
			turnRadarLeft(vlr);
		} else if (_ultimaAcao == 2) {
			fire(vlr);
		} else if (_ultimaAcao == 3) {
			ahead(vlr);
		} else if (_ultimaAcao == 4) {
			turnRight(vlr);
//		} else if (_ultimaAcao == 5) {
//			turnLeft(vlr);
		}
		_acaoExecutar.clear();
	}

	private double getAnaliseValor(double valorEscolhido, double referencia){
		double vlr;
		vlr = valorEscolhido - referencia;
		//informa o lado à ser rotacionado
		//if (getHeading() < headingEscolhido) {if (vlr<0) vlr *=-1;}
		return vlr;
	}

	@Override
	public void print(String msg) {
		System.out.println("[robot] " + msg);
	}

	@Override
	public void Trata(List<String> l) {
		if (l==null)return;
		TipoPacotes tipo = new CriadorPacotes().getTipoPacote(Integer.valueOf(l.get(0)));
		//print("Tipo Acao " + tipo.toString());
		switch (tipo) {
			case respostaAcao:
				_acaoExecutar = new ArrayList<String>();
				for (String s:l){
					_acaoExecutar.add(s);
				}
				break;
		}
		//remove o tipo
//		for (String s:_acaoExecutar) {
//			print(s);
//		}
	}
	
}