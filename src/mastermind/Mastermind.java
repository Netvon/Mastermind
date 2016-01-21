package mastermind;

import mastermind.console.io.BooleanAnswer;
import mastermind.console.io.NewPinAnswer;
import mastermind.console.io.IntegerAnswer;
import mastermind.console.io.Question;
import mastermind.dialog.Resources;

/**
 * The Mastermind class contains everything to start a new game of mastermind
 * @author Tom van Nimwegen
 *
 */
public class Mastermind {

	private Game _game;
	
	public Mastermind()
	{
		//add the default pins
		Pin.addAll("A", "B", "C", "D", "E", "F");
		this._game = Game.getInstance();
	}
	
	public void startConsoleGame()
	{
		try 
		{				
			System.out.println(Resources.FLAVOR_OPENING_HELLO_1);
			System.out.println(Resources.FLAVOR_OPENING_HELLO_2);
			System.out.println(Resources.TUTORIAL_GAME_QUIT);
			System.out.println();
			
			//add all the special input params
			//every question will handle these
			Question.addSpecial(x -> 
			{
				System.exit(1);
				return false;
			}, "x", "exit");
			
			Question.addSpecial(x -> 
			{
				Game.setDebugMode(!Game.isDebugMode());
				System.out.println(Resources.GAME_DEBUGMODE_DEBUG + Game.isDebugMode());
				return true;
			}, "d", "debug");
			
			Question.addSpecial(x -> 
			{
				if(!Game.isDebugMode())
					return false;
				
				this._game.showScore();
				return true;
			}, "s", "score");
			
			Question.addSpecial(x -> 
			{
				if(!Game.isDebugMode())
					return false;
				
				this._game.showBoard(false);
				return true;
			}, "b", "board");
			
			Question.addSpecial(x -> 
			{
				if(!Game.isDebugMode())
					return false;
				
				String out = Resources.GAME_DEBUGMODE_PINS;
				for (Pin p : Pin.values()) 
				{
					out += "[" + p.getName() + "]";
				}
				System.out.println(out);
				return true;
			}, "p", "pins");
			
			Question.addSpecial(x -> 
			{
				if(!Game.isDebugMode())
					return false;
				
				String out = Resources.GAME_DEBUGMODE_CODE + Game.getInstance().getCode();
				System.out.println(out);
				return true;
			}, "c", "code");
			
			//ask the user if he wants to go to the settings
			Question<BooleanAnswer> gotoq = new Question<BooleanAnswer>(Resources.EMPTY, Resources.SETUP_GOTO);
			if(gotoq.askQuestion(new BooleanAnswer(), Boolean.class))
			{
				//ask if the user wants to enable debug mode
				Question<BooleanAnswer> debugq = new Question<BooleanAnswer>(Resources.EMPTY, Resources.SETUP_DEBUGMODE);
				
				if(debugq.askQuestion(new BooleanAnswer(), Boolean.class))
				{
					Game.setDebugMode(true);
				}
				
				//ask if the user wants to chance the game rules
				Question<BooleanAnswer> settingq = new Question<BooleanAnswer>(Resources.EMPTY, Resources.SETUP_RULES);
				
				if(settingq.askQuestion(new BooleanAnswer(), Boolean.class))
				{
					//ask the user user for the new pincount
					Question<IntegerAnswer> pincountq = new Question<IntegerAnswer>(Resources.EMPTY, Resources.SETUP_RULES_PINCOUNT_1, Resources.SETUP_RULES_PINCOUNT_2);
					Game.PIN_COUNT = pincountq.askQuestion(new IntegerAnswer(2, 6), Integer.class);
					
					//ask the user user for the new turncount
					Question<IntegerAnswer> turncountq = new Question<IntegerAnswer>(Resources.EMPTY, Resources.SETUP_RULES_TURNCOUNT_1, Resources.SETUP_RULES_TURNCOUNT_2);
					Game.TURN_COUNT = turncountq.askQuestion(new IntegerAnswer(5, Integer.MAX_VALUE), Integer.class);
					
					boolean addpins = true;
					
					//start addpin loop
					//will continue until the user says he doesn't want to add any more pins
					while(addpins)
					{
						Question<BooleanAnswer> addpin1 = new Question<BooleanAnswer>(Resources.EMPTY, Resources.SETUP_RULES_ADDPIN);
						addpins = addpin1.askQuestion(new BooleanAnswer(), Boolean.class);
						if(addpins)
						{
							Question<NewPinAnswer> pinq = new Question<NewPinAnswer>(Resources.SETEUP_RULES_NEWPIN);
							Pin.addValue(pinq.askQuestion(new NewPinAnswer(), Pin.class));
						}
					}
				}
			}			
					
			this._game.startConsoleLoop();
		}
		catch (Exception e) {
			System.out.println(Resources.GAME_INPUT_PREFIX + Resources.ERROR_PREFIX + e.getMessage());
		} 
	}
}
