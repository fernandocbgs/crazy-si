package robos;

import robocode.AdvancedRobot;
import robocode.*;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.BulletHitEvent;
import robocode.BulletMissedEvent;

public class Candidate120 extends AdvancedRobot {
	private double enemyEnergy;
	private double enemyHeading;
	private double enemyDistance;
	private double wallBearing;
	private double bulletHeading;

	public void run() {
		execute();
		setTurnRight(87);
		setTurnGunRight(2);
		execute();
		setTurnGunLeft(6);
	}

	public void onScannedRobot(ScannedRobotEvent e) {
		//enemyBearing = e.getBearing();
		enemyEnergy = e.getEnergy();
		enemyDistance = e.getDistance();
		enemyHeading = e.getHeading();

		setFire((getHeadingRadians() + 55));
		setTurnGunRight(57);
		setMaxTurnRate((40 / getGunHeadingRadians()));
		setBack(enemyDistance);
		setTurnGunRight(87);
	}

	public void onHitWall(HitWallEvent e) {
		wallBearing = e.getBearing();

		setBack((getGunTurnRemainingRadians() - enemyDistance));
		setTurnGunLeft(bulletHeading);
	}

	public void onHitRobot(HitRobotEvent e) {
		//enemyBearing = e.getBearing();
		enemyEnergy = e.getEnergy();

		setTurnGunLeft((enemyHeading + wallBearing));
		setMaxTurnRate((54 * getGunHeadingRadians()));
		setMaxVelocity(44);
		setTurnGunLeft(enemyHeading);
	}

	public void onHitByBullet(HitByBulletEvent e) {
		//bulletBearing = e.getBearing();
		bulletHeading = e.getHeading();

		execute();
		setFire((getTurnRemainingRadians() + getGunTurnRemainingRadians()));
	}

	public void onBulletMissed(BulletMissedEvent e) {
		setBack(((enemyEnergy + 72) + enemyHeading));
		setTurnLeft((60 * bulletHeading));
	}

	public void onBulletHitBullet(BulletHitBulletEvent e) {
		setMaxTurnRate((enemyEnergy / getDistanceRemaining()));
		setTurnLeft(49);
		execute();
		setMaxTurnRate(2);
	}

	public void onBulletHit(BulletHitEvent e) {
		enemyEnergy = e.getEnergy();

		setAhead(((82 + 34) * 46));
		setTurnGunRight(getHeadingRadians());
		setTurnGunLeft(getDistanceRemaining());
		setAhead(enemyDistance);
	}

}