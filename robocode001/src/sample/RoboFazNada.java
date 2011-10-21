package sample;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

/**
 * Robo Faz Nada<br />
 * ideia � ser controlado via Jason 
 * */
public class RoboFazNada extends Robot {
	
	private static int portaServidor = 7896;
	private static String mensagem = "Ola sou um robo cliente TCP";
	private static String ip = "localhost";
	
	public void run(){
		setBodyColor(Color.WHITE);
		setGunColor(Color.BLACK);
		setRadarColor(Color.BLUE);
		setBulletColor(Color.green);
		setScanColor(Color.GREEN);
		
		while(true){
			clienteTCP();
			
			//espera um pouco antes de enviar a pr�xima mensagem
			try {Thread.sleep(1000); } catch (InterruptedException e) {e.printStackTrace();}
			
			//stop();
			//turnRadarRight(360);
			//turnRadarLeft(360);
//			ahead(1000);
//			turnRight(90);
		}
	}
	
	@Override
	public void onHitWall(HitWallEvent event) {
		
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {

	}
	
	
	//cliente TCP
	private void clienteTCP(){
		// arguments supply message and hostname
		Socket s = null;
		try {
			int serverPort = portaServidor;
			s = new Socket(ip, serverPort);
			DataInputStream in = new DataInputStream(s.getInputStream());
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			out.writeUTF(mensagem); // UTF is a string encoding see Sn. 4.4
			String data = in.readUTF(); // read a line of data from the stream
			
			System.out.println("(cliente) Received: " + data);
			
		} catch (UnknownHostException e) {
			System.out.println("Socket:" + e.getMessage());
		} catch (EOFException e) {
			System.out.println("EOF:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("readline:" + e.getMessage());
		} finally {
			if (s != null)
				try {
					s.close();
				} catch (IOException e) {
					System.out.println("close:" + e.getMessage());
				}
		}
	}
}