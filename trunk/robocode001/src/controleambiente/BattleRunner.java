package controleambiente;

import robocode.control.*;
import robocode.control.events.*;

//
// Application that demonstrates how to run two sample robots in Robocode using the
// RobocodeEngine from the robocode.control package.
//
// @author Flemming N. Larsen
//

//ver site: http://protefun.googlecode.com/svn/TestFilesDemoKia/src/DSE/Feb22/Robocode.java
//api pen drive: file:///F:/C_robocode/javadoc/index.html
//ver http://protefun.googlecode.com/svn/trunk/ - desenvolvedores robocode

/*
 * Para Rodar tem que passar os parametros:
 * 
 * -Djava.security.main -DJava.security.policy=policy -Xmx512M -DNOSECURITY=true
 * */

public class BattleRunner {
	
	private static String pastaRoboCode = "F:/C_robocode"; //D:/Robocode
	
    public static void main(String[] args) {

        // Create the RobocodeEngine
        //   RobocodeEngine engine = new RobocodeEngine(); // Run from current working directory
        RobocodeEngine engine = new RobocodeEngine(new java.io.File(pastaRoboCode)); // pasta padr�o C:/Robocode

        // Add our own battle listener to the RobocodeEngine 
        engine.addBattleListener(new BattleObserver());

        // Show the Robocode battle view
        engine.setVisible(true);

        // Setup the battle specification

        int numberOfRounds = 5;
        BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600); // 800x600
        RobotSpecification[] selectedRobots = engine.getLocalRepository("sample.RamFire,sample.Corners");

        BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);

        // Run our specified battle and let it run till it is over
        engine.runBattle(battleSpec, true); // waits till the battle finishes

        // Cleanup our RobocodeEngine
        engine.close();

        // Make sure that the Java VM is shut down properly
        System.exit(0);
    }
}

//
// Our private battle listener for handling the battle event we are interested in.
//
class BattleObserver extends BattleAdaptor {

    // Called when the battle is completed successfully with battle results
    public void onBattleCompleted(BattleCompletedEvent e) {
        System.out.println("-- Battle has completed --");
        
        // Print out the sorted results with the robot names
        System.out.println("Battle results:");
        for (robocode.BattleResults result : e.getSortedResults()) {
            System.out.println("  " + result.getTeamLeaderName() + ": " + result.getScore());
        }
    }

    // Called when the game sends out an information message during the battle
    public void onBattleMessage(BattleMessageEvent e) {
        System.out.println("Msg> " + e.getMessage());
    }

    // Called when the game sends out an error message during the battle
    public void onBattleError(BattleErrorEvent e) {
        System.out.println("Err> " + e.getError());
    }
}