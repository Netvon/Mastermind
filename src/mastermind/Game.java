package mastermind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


//import javafx.scene.paint.Color;
import mastermind.console.io.BooleanAnswer;
import mastermind.console.io.Question;
import mastermind.dialog.Resources;

/**
 * A class representing a game of Mastermind. This class is a singleton, use {@code getInstance()} to get the current instance.
 * @author Tom van Nimwegen
 * @see Mastermind
 */
public class Game {
	
	/**
	 * The maximum number of Pins allowed in one turn
	 */
	public static int PIN_COUNT = 4;
	/**
	 * The number of turns in one Phase
	 */
	public static int TURN_COUNT = 9;
	
	private Queue<Phase> _phase;
	private Phase _currentPhase;
	
	private List<PinRow> _board;
	private PinRow _code;
	
	private int _playerPoints;
	private int _computerPoints;
	
	private static boolean _debugMode;
	
	private static Game _instance = null;
	
	private Game()
	{
		this._board = new ArrayList<PinRow>(TURN_COUNT);
		this._code = new PinRow();
		this._playerPoints = 0;
		this._computerPoints = 0;
	}
	
	/**
	 * Initializes a the instance of the Game class
	 */
	private void init()
	{
		this._phase = new LinkedList<Phase>(Arrays.asList(new PhaseHuman(), new PhaseComputer()));
	}
	
	/**
	 * A method to return the global instance of the Game class
	 * @return Returns the global instance of the Game class
	 */
	public static Game getInstance()
	{
		if(_instance == null)
		{
			_instance = new Game();
		}
		
		return _instance;
	}
	
	/**
	 * A method to set the current code that has to be guessed by the other player
	 * @param pins An Array of pins that represents the code
	 */
	public void setCode(Pin... pins)
	{
		if(pins.length > Game.PIN_COUNT)
			throw new IllegalArgumentException(Resources.ERROR_CODE_LONG);
		
		if(this._code != null)
			this._code.clear();
		else
			this._code = new PinRow();
		
		for (Pin p : pins) 
		{
			this._code.addPin(p);
		}
	}
	
	/**
	 * A method to set the current code that has to be guessed by the other player
	 * @param pr A PinRow that contains the code
	 * @throws IllegalArgumentException if the length of {@code pr} is greater then the maximum number of pins in a PinRow
	 */
	public void setCode(PinRow pr)
	{
		if(pr.getLength() > Game.PIN_COUNT)
			throw new IllegalArgumentException(Resources.ERROR_CODE_LONG);
		
		this._code = pr;
//		System.out.println(pr);
	}
	
	/**
	 * A method to return the current code
	 * @return Return the current code
	 */
	public PinRow getCode()
	{
		return this._code;
	}
	
	/**
	 * A method to return the current amount of points the human player has
	 * @return Returns the current amount of points the human player has
	 */
	public int getPlayerPoints()
	{
		return this._playerPoints;
	}
	
	/**
	 * A method to return the current amount of points the computer player has
	 * @return Returns the current amount of points the computer player has
	 */
	public int getComputerPoints()
	{
		return this._computerPoints;
	}
	
	
	/**
	 * A method to return a copy of the previous PinRows and their TurnResults
	 * @return Returns a copy of the previous PinRows and their TurnResults
	 */
	public List<PinRow> getBoard()
	{
		return new ArrayList<PinRow>(_board);
	}
	
	/**
	 * A method to check if the current game is in debug mode
	 * @return Returns a boolean that tells if the game is in debug mode. True if in debug mode.
	 */
	public static boolean isDebugMode()
	{
		return _debugMode;
	}
	
	/**
	 * A method to set the current game to debug mode
	 */
	public static void setDebugMode(boolean value)
	{
		_debugMode = value;
	}
	
	/**
	 * A method to show the current playingboard in console form
	 * @param showLastResult A boolean to determine whether the method has to show the last turns result
	 */
	public void showBoard(boolean showLastResult)
	{
		if(showLastResult)
		{
			PinRow lpr = _board.get(_board.size() - 1);
			System.out.println(lpr.getResult());
		}
		
		System.out.println(Resources.GAME_BOARD_START);
		for (int i = 0; i < getBoard().size(); i++) 
		{
			PinRow pr = getBoard().get(i);
			System.out.println( i+1 + ":" + Resources.T1 + pr + Resources.T1 + pr.getResult().toSmallString());
		}
		System.out.println(Resources.GAME_BOARD_END);
	}
	
	/**
	 * A method to show the current score in console form. Shows the the score from the human and computer player
	 */
	public void showScore()
	{
		System.out.println(Resources.GAME_POINTS_START);
		System.out.println(Resources.GAME_COMPUTER + Resources.T1 + getComputerPoints());
		System.out.println(Resources.GAME_HUMAN + Resources.T2 + getPlayerPoints());
		System.out.println(Resources.GAME_POINTS_END);
	}
	
	/**
	 * Adds a turn to the current game.
	 * @param p the turn to be added
	 * @return a calculated TurnResult
	 * 
	 * @throws IllegalArgumentException if the maximum number of turns is reached
	 */
	public TurnResult addTurn(PinRow p)
	{
		if(_board.size() >= TURN_COUNT)
			throw new IllegalArgumentException(Resources.ERROR_MAX_TURNS);
		
		TurnResult tr = makeGuess(p);
		
		if(_board.size() + 1 >= TURN_COUNT && !tr.hasWon())
		{
			tr.setLost();
			
			if(this._currentPhase.getClass() == PhaseHuman.class)
			{
				this._computerPoints++;
			}
			else if(this._currentPhase.getClass() == PhaseHuman.class)
			{
				this._playerPoints++;
			}
		}
		
		if(tr.hasWon())
		{
			if(this._currentPhase.getClass() == PhaseHuman.class)
			{
				this._playerPoints++;
			}
			else if(this._currentPhase.getClass() == PhaseHuman.class)
			{
				this._computerPoints++;
			}
		}
		
		p.addResult(tr);
		this._board.add(p);
		
		return tr;
	}
	
	/**
	 * Adds a turn to the current game without calculating a TurnResult
	 * @param p the turn to be added
	 * 
	 * @throws IllegalArgumentException if the maximum number of turns is reached
	 */
	public void addTurnWithouthResult(PinRow p)
	{
		if(_board.size() >= TURN_COUNT)
			throw new IllegalArgumentException(Resources.ERROR_MAX_TURNS);
		
		TurnResult tr = p.getResult();
		
		if(_board.size() + 1 >= TURN_COUNT && !tr.hasWon())
		{
			tr.setLost();
			
			if(this._currentPhase.getClass() == PhaseHuman.class)
			{
				this._computerPoints++;
			}
			else if(this._currentPhase.getClass() == PhaseComputer.class)
			{
				this._playerPoints++;
			}
		}
		
		if(tr.hasWon())
		{
			if(this._currentPhase.getClass() == PhaseHuman.class)
			{
				this._playerPoints++;
			}
			else if(this._currentPhase.getClass() == PhaseComputer.class)
			{
				this._computerPoints++;
			}
		}
		
		this._board.add(p);
	}
	
	public TurnResult makeGuess(PinRow guess)
	{
		TurnResult t = this._code.compareTo(guess);
		return t;
	}
	
	public void startConsoleLoop()
	{	
		try {
			
			boolean exit = false;
		
			while(!exit)
			{			
				init();
				this._board.clear();
				this._code = null;
				
				for (Phase phase : _phase) 
				{
					this._currentPhase = phase;
					phase.play();
					
					this._board.clear();
					this._code = null;
					
					this.showScore();
					
				}
				
				Question<BooleanAnswer> playagain = new Question<BooleanAnswer>(Resources.EMPTY, Resources.GAME_PLAYAGAIN);
				
				if(!playagain.askQuestion(new BooleanAnswer(), Boolean.class))
				{
					exit = true;
				}
				
			}
		} 
		catch (Exception e) 
		{
			System.out.println(Resources.GAME_INPUT_PREFIX + Resources.ERROR_PREFIX + e.getMessage());
		}
	}
		
}
