package controleambiente.implementandointerfaces;

import robocode.control.BattleSpecification;
import robocode.control.IRobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.IBattleListener;

/**
 * esta classe já esta implementada na API, esta aqui apenas como referencia, nao deve ser usada
 * 
 * @see http://robocode.sourceforge.net/docs/robocode/robocode/control/IRobocodeEngine.html
 * */
public class RobocodeEngineNAOUSAR implements IRobocodeEngine {

	/**
	 * Aborts the current battle if it is running.
	 * */
	@Override
	public void abortCurrentBattle() {

	}

	/**
	 * Adds a battle listener that must receive events occurring in battles.
	 * @listener - the battle listener that must retrieve the event from the battles.
	 * */
	@Override
	public void addBattleListener(IBattleListener listener) {

	}

	/**
	 * Closes the RobocodeEngine and releases any allocated resources it holds. 
	 * You should call this when you have finished using the RobocodeEngine. 
	 * This method automatically disposes the Robocode window if it open.
	 * */
	@Override
	public void close() {

	}

	/**
	 * Returns all robots available from the local robot repository of Robocode. 
	 * These robots must exists in the /robocode/robots directory, and must be 
	 * compiled in advance, before these robot are returned with this method.
	 * 
	 * @return an array of all available robots from the local robot repository.
	 * */
	@Override
	public RobotSpecification[] getLocalRepository() {
		return null;
	}

	/**
	 * Returns a selection of robots available from the local robot repository of Robocode. 
	 * These robots must exists in the /robocode/robots directory, and must be compiled in 
	 * advance, before these robot are returned with this method.
	 * 
	 * Notice: If a specified robot cannot be found in the repository, it will not be returned 
	 * in the array of robots returned by this method.
	 * @selectedRobotList - a comma or space separated list of robots to return. The full class 
	 * name must be used for specifying the individual robot, e.g. "sample.Corners, sample.Crazy".
	 * @return an array containing the available robots from the local robot repository based on 
	 * the selected robots specified with the selectedRobotList parameter.
	 * */
	@Override
	public RobotSpecification[] getLocalRepository(String selectedRobotList) {
		return null;
	}

	
	/**
	 * Returns the installed version of Robocode controlled by this RobocodeEngine.
	 * */
	@Override
	public String getVersion() {
		return null;
	}

	/**
	 * Removes a battle listener that has previously been added to this object.
	 * @listener - the battle listener that must be removed.
	 * */
	@Override
	public void removeBattleListener(IBattleListener listener) {

	}

	/**
	 * Runs the specified battle.
	 * */
	@Override
	public void runBattle(BattleSpecification battleSpecification) {

	}

	/**
	 * Runs the specified battle.
	 * @battleSpecification - the specification of the battle to run including the participating robots.
	 * @waitTillOver - will block caller till end of battle if set
	 * */
	@Override
	public void runBattle(BattleSpecification battleSpecification, boolean waitTillOver) {

	}

	/**
	 * Runs the specified battle.
	 * @battleSpecification - the specification of the battle to run including the participating robots.
	 * @initialPositions - a comma or space separated list like: x1,y1,heading1, x2,y2,heading2, 
	 * which are the coordinates and heading of robot #1 and #2. So e.g. 0,0,180, 50,80,270 
	 * means that robot #1 has position (0,0) and heading 180, and robot #2 has position (50,80) 
	 * and heading 270.
	 * @waitTillOver - will block caller till end of battle if set
	 * */
	@Override
	public void runBattle(BattleSpecification battleSpecification,
			              String initialPositions,
			              boolean waitTillOver) {
		
	}

	/**
	 * Shows or hides the Robocode window.
	 * @visible - true if the Robocode window must be set visible; false otherwise.
	 * */
	@Override
	public void setVisible(boolean visible) {

	}

	/**
	 * Will block caller until current battle is over.
	 * */
	@Override
	public void waitTillBattleOver() {

	}

}