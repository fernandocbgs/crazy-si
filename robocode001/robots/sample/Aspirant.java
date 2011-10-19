package sample;

import java.awt.Color;

import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

public class Aspirant extends Robot {
	private double global1 = 3.0;

	public void run() {
		setScanColor(Color.GREEN);
		setColors(Color.red, Color.blue, Color.green);
		
		setAdjustRadarForGunTurn(true);
		do {
			turnRadarLeft(360);
		} while (true);
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		double x = 2.0;
		double y = 1.0;
		{
			ahead(getRadarHeading());
			if ((getGunHeat() <= (getGunHeat() / ((-135d) + (-135d))))) {
				fire(e.getBearing());
			} else {
				turnGunRight(((-135d) + (-135d)));
			}
			back(getGunHeat());
			turnGunRight((e.getEnergy() / getRadarHeading()));
			turnGunLeft((15.656966209411621d));
			{
				if (((-135d) <= ((getHeading() * getY()) - ((62.581214904785156d) + getGunHeading())))) {
					back(e.getDistance());
				} else {
					ahead(((-135d) - (-135d)));
				}
				back((getX() + (getGunCoolingRate() * getGunCoolingRate())));
				turnGunRight((((getGunHeat() / ((-135d) + (-135d))) * e
						.getDistance()) + ((e.getDistance() + e.getBearing()) / e
						.getEnergy())));
				ahead(((getY() - (62.581214904785156d)) + e.getDistance()));
			}

			// atira
			for (int i = 0; i < 3; i++) {
				smartFire(e.getDistance());
			}

		}
	}

	public void smartFire(double robotDistance) {
		if (robotDistance > 200 || getEnergy() < 15) {
			fire(1);
		} else if (robotDistance > 50) {
			fire(2);
		} else {
			fire(3);
		}
	}

	public void onHitByBullet(HitByBulletEvent e) {
		double x = 2.0;
		{
			turnLeft((-45d));
			turnLeft((getVelocity() + (getHeading() * e.getBearing())));
		}
	}

	public void onHitWall(HitWallEvent e) {
		double x = 2.0;
		{
			if (((-180d) >= (10.710869789123535d))) {
				back((10.710869789123535d));
			} else {
				back(e.getBearing());
			}
			{
				turnLeft((-180d));
				{
					turnRight((45.0d));
					turnRight((10.710869789123535d));
				}

				turnLeft(e.getBearing());
			}

		}
	}

	public void onHitRobot(HitRobotEvent e) {
		double x = 2.0;
		fire(getGunHeading());
	}
}
