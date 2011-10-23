package sample;

import java.awt.Color;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import RMI.ClienteRobo;
import RMI.ServidorRobo;
import robocode.AdvancedRobot;
import robocode.BattleEndedEvent;
import robocode.DeathEvent;
import robocode.HitWallEvent;
import robocode.RobotDeathEvent;
import robocode.RoundEndedEvent;
import robocode.ScannedRobotEvent;
import robocode.WinEvent;

/**
 * Robo Faz Nada<br />
 * ideia é ser controlado via Jason 
 * */
public class RoboFazNada extends AdvancedRobot implements Serializable, Externalizable {
	private static final long serialVersionUID = 4417781459965392288L;
	private int _ultimaAcao, _numeroAcoesTomadas = 0;
	private List<String> _acaoExecutar;
	//private boolean executar = false;
	
	//private ControlRobot _controleRobo;
	private ServidorRobo _servidorRobo;
	
	public RoboFazNada() { }
	
	private static int getIndiceRobo(String nomeRobo){
		return 1;
		//return Integer.valueOf(nomeRobo.substring(nomeRobo.lastIndexOf('(')+1, nomeRobo.lastIndexOf(')')));
	}
	
	private void iniciarServidor(){
		if (_acaoExecutar!=null) return;
		try {
			int indice = getIndiceRobo(getName());
			System.out.println("indice: " + indice);
			
			_servidorRobo = new ServidorRobo(indice, this);
			_servidorRobo.iniciar(false);
			
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void ClienteRMI(){
		if (_acaoExecutar!=null) return;
		//try {Thread.sleep(100);} catch (InterruptedException e) { e.printStackTrace(); }
		try {
			ClienteRobo cli = new ClienteRobo();
			RoboFazNada r1 = cli.recuperarRobo(1);
			System.out.println("ROBO 1 " + r1);
			
			if (r1 == null) return;
			
			r1.Hello();
			
			ArrayList<String> acoes = new ArrayList<String>();
			acoes.add(2+"");
			acoes.add(3+"");
			r1.SetAcoes(acoes);
			//r1.notify();
						
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		if (_acaoExecutar==null) {
			iniciarServidor();
			ClienteRMI();
			//_controleRobo = new ControlRobot(getName().toString(),getBattleFieldWidth(), getBattleFieldHeight(),getX(),getY(),getOthers());
	
			setBodyColor(Color.WHITE);
			setGunColor(Color.BLACK);
			setRadarColor(Color.BLUE);
			setBulletColor(Color.green);
			setScanColor(Color.GREEN);
		}
		//arruma a posição inicial do robo
//		double vlr = getAnaliseValor(90, getHeading());
//		turnRight(vlr);
		
		//if (vlr < 0){turnRight(vlr);}else{turnLeft(vlr);}
		
		while(true){
			ahead(1);
		
			/*
			 * O ROBO SÓ VAI CHAMAR A PROXIMA AÇÃO ASSIM QUE TIVER TERMINAR A ÚLTIMA
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
			
			//if (_acaoExecutar != null && _acaoExecutar.size() >= 2) {
			System.out.println("_acaoExecutar: " + _acaoExecutar);
			if (_acaoExecutar != null) executar(_acaoExecutar);
			//}
			
		}
	}
	
	public void SetAcoes(List<String> acoes){
		System.out.println("1 ACOES " + acoes);
		//_acaoExecutar = acoes;
		_acaoExecutar = new ArrayList<String>();
		for (String str : acoes) {
			_acaoExecutar.add(str);
		}
		System.out.println("2 ACOES " + _acaoExecutar);
		//executar = true;
		//this.resume();
		//notify();
		//não posso chamar aqui por que ele ainda não chamou o run...
		run();
		//executar(_acaoExecutar);
	}
	
	public void Hello(){
		System.out.println("HELLO VIA RMI");
	}
	
	
	@Override
	public void onHitWall(HitWallEvent e) {
		//_controleRobo.Parede(e);
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		//_controleRobo.ScaneouRobo(e);
	}
	
	@Override
	public void onBattleEnded(BattleEndedEvent e) {
		//super.onBattleEnded(e);
	}
	
	@Override
	public void onDeath(DeathEvent e) {
		//super.onDeath(e);
	}
	
	@Override
	public void onRoundEnded(RoundEndedEvent e) {
		//super.onRoundEnded(e);
	}
	
	@Override
	public void onRobotDeath(RobotDeathEvent e) {
		//super.onRobotDeath(e);
	}
	
	@Override
	public void onWin(WinEvent e) {
		//super.onWin(e);
	}
	
	public void executar(List<String> params){
		//if (params.size() <= 1) return;

		_numeroAcoesTomadas++;
		_ultimaAcao = Integer.valueOf(params.get(0));
		
		System.out.println("Executar Acao + " + _ultimaAcao);
		
		if (_ultimaAcao <= 0) return;
		if (_ultimaAcao == 1) {
			turnRadarLeft(Double.valueOf(params.get(1)));
		} else if (_ultimaAcao == 2) {
			fire(Double.valueOf(params.get(1)));
		} else if (_ultimaAcao == 3) {
			ahead(Double.valueOf(params.get(1)));
		} else if (_ultimaAcao == 4) {
			turnRight(Double.valueOf(params.get(1)));
//		} else if (_ultimaAcao == 5) {
//			turnLeft(Double.valueOf(params.get(1)));
		}
		
		_acaoExecutar.clear();
		
	}
//	
//	private double getAnaliseValor(double valorEscolhido, double referencia){
//		double vlr;
//		vlr = valorEscolhido - referencia;
//		//informa o lado à ser rotacionado
//		//if (getHeading() < headingEscolhido) {if (vlr<0) vlr *=-1;}
//		return vlr;
//	}

	@Override
	public void readExternal(ObjectInput in) throws IOException,ClassNotFoundException {
		System.out.println(in.readInt());
	}

	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(1);
	}
	
}