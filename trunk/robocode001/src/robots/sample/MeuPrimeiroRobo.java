package robots.sample;

import java.awt.Color;

import robocode.HitByBulletEvent;
import robocode.HitWallEvent;
import robocode.RateControlRobot;
import robocode.ScannedRobotEvent;

public class MeuPrimeiroRobo extends RateControlRobot {
	
	int turnCounter;
	public void run() {
		setScanColor(Color.GREEN);
		
		turnCounter = 0;
		setGunRotationRate(15);
		
		while (true) {
			if (turnCounter % 64 == 0) {
				// Straighten out, if we were hit by a bullet and are turning
				setTurnRate(0);
				// Go forward with a velocity of 4
				setVelocityRate(4);
			}
			if (turnCounter % 64 == 32) {
				// Go backwards, faster
				setVelocityRate(-6);
			}
			turnCounter++;
			execute();
		}
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		//stop();
		//ahead(10);
		fire(10);
		//resume();
	}

	public void onHitByBullet(HitByBulletEvent e) {
		// Turn to confuse the other robot
		setTurnRate(5);
		turnRight(90);
		back(100);
	}
	
	public void onHitWall(HitWallEvent e) {
		// Move away from the wall
		setVelocityRate(-1 * getVelocityRate());
	}
    
}