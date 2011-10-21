package controleambiente.implementandointerfaces;

import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;
import robocode.control.events.BattleFinishedEvent;
import robocode.control.events.BattleMessageEvent;
import robocode.control.events.BattlePausedEvent;
import robocode.control.events.BattleResumedEvent;
import robocode.control.events.BattleStartedEvent;
import robocode.control.events.IBattleListener;
import robocode.control.events.RoundEndedEvent;
import robocode.control.events.RoundStartedEvent;
import robocode.control.events.TurnEndedEvent;
import robocode.control.events.TurnStartedEvent;

/**
 * @see http://robocode.sourceforge.net/docs/robocode/robocode/control/events/IBattleListener.html
 * */
public class BattleListener implements IBattleListener {
	
	/**
	 * This method is called when the battle has completed successfully and results are 
	 * available. This event will not occur if the battle is terminated or aborted by 
	 * the user before the battle is completed.
	 * */
	@Override
	public void onBattleCompleted(BattleCompletedEvent e) {

	}

	/**
	 * This method is called when the game has sent an error message.
	 * */
	@Override
	public void onBattleError(BattleErrorEvent e) {

	}

	/**
	 * This method is called when the battle has finished. This event is always sent as 
	 * the last battle event, both when the battle is completed successfully, terminated 
	 * due to an error, or aborted by the user. Hence, this events is well-suited for 
	 * cleanup after the battle.
	 * */
	@Override
	public void onBattleFinished(BattleFinishedEvent e) {

	}

	/**
	 * This method is called when the game has sent a new information message.
	 * */
	@Override
	public void onBattleMessage(BattleMessageEvent e) {

	}

	/**
	 * This method is called when the battle has been paused, either by the user or the game.
	 * */
	@Override
	public void onBattlePaused(BattlePausedEvent e) {

	}

	/**
	 * This method is called when the battle has been resumed (after having been paused).
	 * */
	@Override
	public void onBattleResumed(BattleResumedEvent e) {

	}

	/**
	 * This method is called when a new battle has started.
	 * */
	@Override
	public void onBattleStarted(BattleStartedEvent e) {

	}

	/**
	 * This method is called when the current round of a battle has ended.
	 * */
	@Override
	public void onRoundEnded(RoundEndedEvent e) {

	}
	
	/**
	 * 
	 * This method is called when a new round in a battle has started.
	 * */
	@Override
	public void onRoundStarted(RoundStartedEvent e) {

	}

	/**
	 * This method is called when the current turn in a battle round is ended.
	 * */
	@Override
	public void onTurnEnded(TurnEndedEvent e) {

	}

	/**
	 * This method is called when a new turn in a battle round has started.
	 * */
	@Override
	public void onTurnStarted(TurnStartedEvent e) {

	}

}