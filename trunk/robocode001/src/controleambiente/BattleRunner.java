package controleambiente;

import robocode.control.*;
import robocode.control.events.*;
import servidor_tcp.gui.FrameServidorTCP;

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
 * Se n�o Rodar, passar os parametros:
 * -Djava.security.main -DJava.security.policy=policy -Xmx512M -DNOSECURITY=true
 * Para carregar os robos proprios, copiar os robos do package sample para 'C:\robocode\robots\sample' (pasta padr�o),
 * compilar usando o proprio robocode (Ctrl+E, Abre as classe, Ctrl+B), o package dos robos deve ser 'sample'
 * cria o arquivo .properties com as informa��es dos robos
 * */
public class BattleRunner {
	
	private static String _meusRobos = "sample.RoboFazNada, sample.FnlBot, sample.Aspirant, sample.Candidate120, sample.MeuPrimeiroRobo";
	
	private static String pastaRoboCode = "C:/Robocode"; //C:/Robocode
	private static int numberOfRounds = 5;
	
	
    public static void main(String[] args) {
    	
    	//FRAME Servidor TCP
    	new FrameServidorTCP().setVisible(true);
    	
    	//3 robos que n�o fazem nada
    	_meusRobos = "sample.RoboFazNada, sample.RoboFazNada, sample.RoboFazNada"; 
    	
        // Create the RobocodeEngine
        //   RobocodeEngine engine = new RobocodeEngine(); // Run from current working directory
        RobocodeEngine engine = new RobocodeEngine(new java.io.File(pastaRoboCode)); // pasta padr�o C:/Robocode

        // Add our own battle listener to the RobocodeEngine 
        engine.addBattleListener(new BattleObserver());
        // Show the Robocode battle view
        engine.setVisible(true);
        
        
        // Setup the battle specification

        BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 600); // 800x600
        
        //"sample.RamFire,sample.Corners,"
        RobotSpecification[] selectedRobots = engine.getLocalRepository(_meusRobos);
        //RobotSpecification[] selectedRobots = engine.getLocalRepository("sample.FnlBot, sample.MeuPrimeiroRobo");

        BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);
        
        //---------------- testes emerson -------------
//        TestesEmerson(battleSpec);
        //---------------- testes emerson -------------
        
       // Run our specified battle and let it run till it is over
        engine.runBattle(battleSpec, true); // waits till the battle finishes
        
        // Cleanup our RobocodeEngine
        engine.close();
        
        // Make sure that the Java VM is shut down properly
        System.exit(0);
    }
    
    @SuppressWarnings("unused")
	private static void TestesEmerson(BattleSpecification battleSpec){
        for (RobotSpecification r : battleSpec.getRobots()) {
        	System.out.print("r.toString(): " + r.toString() + " - ");
        	System.out.println(r.getJarFile());
        }
    }
    
}

//
// Our private battle listener for handling the battle event we are interested in.
//
class BattleObserver extends BattleAdaptor {
	boolean isReplay;

    // Called when the battle is completed successfully with battle results
    public void onBattleCompleted(BattleCompletedEvent e) {
        System.out.println("-- Battle has completed --");
        if (!isReplay) {
            //printResultsData(e);
        }
       
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
    
    @Override
    public void onBattleFinished(BattleFinishedEvent event) {
    	//super.onBattleFinished(event);
    	System.out.println("Battle Finished ");
    }
    
    @Override
    public void onBattleStarted(BattleStartedEvent event) {
    	isReplay = event.isReplay();
    }
    
}