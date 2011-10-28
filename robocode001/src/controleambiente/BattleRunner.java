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

/**
 * Se não Rodar, passar os parametros:
 * -Djava.security.main -DJava.security.policy=policy -Xmx512M -DNOSECURITY=true
 * Para carregar os robos proprios, copiar os robos do package sample para 'C:\robocode\robots\sample' (pasta padrão),
 * compilar usando o proprio robocode (Ctrl+E, Abre as classe, Ctrl+B), o package dos robos deve ser 'sample'
 * cria o arquivo .properties com as informações dos robos
 * */
public class BattleRunner {
	
	private static String _meusRobos = "sample.RoboFazNada, sample.FnlBot, sample.Aspirant, sample.Candidate120, sample.MeuPrimeiroRobo";
	private static String pastaRoboCode = "C:/Robocode"; //C:/Robocode
	private static int numberOfRounds = 5;
	
	/**
	 * initialPositions - a comma or space separated list like: 
	 * 		x1,y1,heading1, x2,y2,heading2
	 * which are the coordinates and heading of robot #1 and #2. 
	 * So e.g. 0,0,180, 50,80,270 means that robot #1 has position (0,0) and heading 180
	 * and robot #2 has position (50,80) and heading 270.
	 * 
	 * heading - face do robo
	 * */
	private static int _width = 800, _heigth = 600;
//	private static String posicaoRobo1  = (0-_tamRobo) + ","  +(0-_tamRobo) + ",90";
//	private static String posicaoRobo2  = "0.0,0.0,0";
//	//private static String posicaoRobo3  = "0.0,0.0,0";
//	private static String initialPositions = posicaoRobo1 ;//+ ", " + posicaoRobo2 ;//+ ", " +posicaoRobo3;
	
    public static void main(String[] args) {
    	iniciar();
    }
   
    public static void iniciar(){
    	//FRAME Servidor TCP
    	//new FrameServidorTCP().setVisible(true);
    	_meusRobos = "sample.RoboFazNada"
    			     +",sample.RoboFazNada"
    			     + ",sample.RoboFazNada" 
    			     + ",sample.RoboFazNada"
    				 ;
    	
        // Create the RobocodeEngine
        //   RobocodeEngine engine = new RobocodeEngine(); // Run from current working directory
        RobocodeEngine engine = new RobocodeEngine(new java.io.File(pastaRoboCode)); // pasta padrão C:/Robocode
        // Add our own battle listener to the RobocodeEngine 
        engine.addBattleListener(new BattleObserver());
        // Show the Robocode battle view
        engine.setVisible(true);
        
        // Setup the battle specification
        BattlefieldSpecification battlefield = new BattlefieldSpecification(_width, _heigth); // 800x600
        RobotSpecification[] selectedRobots = engine.getLocalRepository(_meusRobos);
        BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);
        
        //---------------- testes emerson -------------
//        TestesEmerson(battleSpec);
        //---------------- testes emerson -------------
        
       // Run our specified battle and let it run till it is over
        
        //initialPositions
        
        engine.runBattle(battleSpec, true); // waits till the battle finishes
        //engine.printRunningThreads();
        // Cleanup our RobocodeEngine
        engine.close();
        // Make sure that the Java VM is shut down properly
        System.exit(0);
    }
    
//    @SuppressWarnings("unused")
//	private static void TestesEmerson(BattleSpecification battleSpec){
//        for (RobotSpecification r : battleSpec.getRobots()) {
//        	System.out.print("r.toString(): " + r.toString() + " - ");
//        }
//    }
    
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