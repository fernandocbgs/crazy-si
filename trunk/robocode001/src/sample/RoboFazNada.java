package sample;

import java.awt.Color;

import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

/**
 * Robo Faz Nada<br />
 * ideia é ser controlado via Jason 
 * */
public class RoboFazNada extends Robot {
	public void run(){
		setBodyColor(Color.WHITE);
		setGunColor(Color.BLACK);
		setRadarColor(Color.BLUE);
		setBulletColor(Color.green);
		setScanColor(Color.GREEN);
		
		while(true){
			stop();
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
	
}
