package robos;

import robocode.HitByBulletEvent;
import robocode.HitWallEvent;
import robocode.RateControlRobot;
import robocode.ScannedRobotEvent;

public class MeuPrimeiroRobo extends RateControlRobot {
	
	int turnCounter;
	public void run() {

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
		fire(1);
	}

	public void onHitByBullet(HitByBulletEvent e) {
		// Turn to confuse the other robot
		setTurnRate(5);
	}
	
	public void onHitWall(HitWallEvent e) {
		// Move away from the wall
		setVelocityRate(-1 * getVelocityRate());
	}
    
}