package sample;
import java.awt.Color;
import java.awt.Graphics2D;

import robocode.HitWallEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;

/**
 * my first example - Emerson
 * */
public class FnlBot extends Robot {
	
	//bool 
	
	int scannedX,scannedY;
	
	public void run(){
		setBodyColor(Color.green);
		setGunColor(Color.green);
		setRadarColor(Color.red);
		setBulletColor(Color.green);
		setScanColor(Color.GREEN);
		
		turnLeft(getHeading() % 90);
		turnGunRight(90);

		while(true){
			turnRadarRight(360);
			turnRadarLeft(360);
			ahead(1000);
			turnRight(90);
		}
	}
	
	@Override
	public void onHitWall(HitWallEvent event) {
		
	}
	
	public void onScannedRobot(ScannedRobotEvent e) {
		
		//testes Emerson
//		if (e.getDistance() <= 100) {
//			back(400);
//		} else {
//			turnRight(90);
//			back(30);
//		}
		
//	     // Calculate the angle to the scanned robot
//	    double angle = Math.toRadians((getHeading() + e.getBearing()) % 360);
//	 
//	     // Calculate the coordinates of the robot
//	    scannedX = (int)(getX() + Math.sin(angle) * e.getDistance());
//	    scannedY = (int)(getY() + Math.cos(angle) * e.getDistance());
//	    
//	    System.out.println("scannedX: " + scannedX + ", scannedY: " + scannedY);
		for (int i = 0; i < 3; i++) {
			smartFire(e.getDistance());
		}
		
		//fire(1);
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
	
	// Paint a transparent square on top of the last scanned robot
	 public void onPaint(Graphics2D g) {
	     // Set the paint color to a red half transparent color
	     g.setColor(new Color(0xff, 0x00, 0x00, 0x80));
	 
	     // Draw a line from our robot to the scanned robot
	     g.drawLine(scannedX, scannedY, (int)getX(), (int)getY());
	 
	     // Draw a filled square on top of the scanned robot that covers it
	     g.fillRect(scannedX - 20, scannedY - 20, 40, 40);
	 }
	

	 
}
