package mastermind;

//import mastermind.Game.Pin;
import mastermind.dialog.Resources;

/**
 * The base class for all Phases
 * @author Tom van Nimwegen
 *
 */
public class Phase {
	
	/**
	 * The game belonging to this Phase
	 */
	public Game g;
	
	/**
	 * Constructs a new instance of the Phase class. Also sets {@code g} to the current instance of the Game class
	 */
	public Phase()
	{
		g = Game.getInstance();
	}
		
	public void play()
	{
		System.out.println(Resources.GAME_HELP_START);		
		System.out.println(Resources.TUTORIAL_GAME_QUIT);
		System.out.println(Resources.GAME_HELP_END);
	}
}
