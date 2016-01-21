package mastermind;

import java.util.ArrayList;
import java.util.List;

import mastermind.console.io.IAnswer;
import mastermind.dialog.Resources;

public class TurnResult implements IAnswer {
	
	/**
	 * The number of pins used in the result sets. This number is the same as the number of pins used on the game board
	 */
	private static final int RESULT_PIN_COUNT = Game.PIN_COUNT;
	
	/**
	 * The number of correctly placed Pins
	 */
	private int _blackPinCount;
	/**
	 * The number of Pins that have the right color but not the right place
	 */
	private int _whitePinCount;
	/**
	 * Was the phase lost this turn
	 */
	private boolean _hasLost;
	/**
	 * Was the phase won this turn
	 */
	private boolean _hasWon;
	
	/**
	 * Valid input for this Answer
	 */
	private List<String> _validInput;
	
	/**
	 * Constructs a new instance of the TurnResult class used
	 * @param blackPinCount The number of correct colors placed on the board on the right spot
	 * @param whitePinCount The number of correct colors placed on the board but not on the right spot
	 */
	public TurnResult(int blackPinCount, int whitePinCount)
	{
		this.setBlackPinCount(blackPinCount);
		this.setWhitePinCount(whitePinCount);
		
		String[] t = new String[Game.PIN_COUNT];
		
		for (int i = 0; i < Game.PIN_COUNT; i++) 
		{
			t[i] = Resources.EMPTY + (i+1);
		}
	}
	
	/**
	 * Constructs a new default instance of the TurnResult class
	 */
	public TurnResult()
	{
		this(0,0);
	}

	/**
	 * Sets the amount of correctly placed Pins.
	 * @param value number of correctly placed pins. Can not be larger then {@code Game.PIN_COUNT}.
	 */
	private void setBlackPinCount(int value)
	{
		if(checkSetValue(value, "input"))
			this._blackPinCount = value;
		
		if(this._blackPinCount >= Game.PIN_COUNT && !this.hasWon() && !this.hasLost())
			this.setWon();
	}
	

	/**
	 * Sets the amount of correctly colored Pins.
	 * @param value number of correctly colored Pins. Can not be larger then {@code Game.PIN_COUNT}.
	 */
	private void setWhitePinCount(int value)
	{
		if(checkSetValue(value, "input"))
			this._whitePinCount = value;
	}
	
	/**
	 * Sets that the game was lost during this turn
	 */
	public void setLost()
	{
		this._hasLost = true;
	}
	
	/**
	 * A method to check if the game was lost this turn
	 * @return Returns true if the game was lost during this turn. Returns false if it wasn't
	 */
	public boolean hasLost()
	{
		return this._hasLost;
	}
	
	/**
	 * Sets that the game was won during this turn
	 */
	public void setWon()
	{
		this._hasWon = true;
	}
	
	/**
	 * A method to check if the game was won this turn
	 * @return Returns true if the game was won during this turn. Returns false if it wasn't
	 */
	public boolean hasWon()
	{
		return this._hasWon;
	}
	
	/**
	 * A method to check if a set method works for a given method
	 * @param value The value you want to set
	 * @param name The name of the var you want to set
	 * @return Returns if the set was successful
	 */
	private boolean checkSetValue(int value, String name)
	{
		if(value > RESULT_PIN_COUNT)
		{
			throw new IllegalArgumentException(name + " (" + value + ") " + Resources.ERROR_TURNSET_1);
		}
		else if(value < 0)
		{
			throw new IllegalArgumentException(name + " (" + value + ") " + Resources.ERROR_TURNSET_2);
		}
		
		return true;
	}
	
	/**
	 * A method used to return he number of correct color placed on the board on the right spot
	 * @return Returns the number of correct color placed on the board on the right spot
	 */
	public int getBlackPinCount()
	{
		return _blackPinCount;
	}
	
	/**
	 * A method used to return the number of correct color placed on the board but not on the right spot
	 * @return Returns the number of correct color placed on the board but not on the right spot
	 */
	public int getWhitePinCount()
	{
		return _whitePinCount;
	}
	
	@Override
	public boolean equals(Object tr)
	{
		if(tr instanceof TurnResult)
		{
			TurnResult turn = (TurnResult)tr;
			return this.getBlackPinCount() == turn.getBlackPinCount() && this.getWhitePinCount() == turn.getWhitePinCount();
		}
		
		return false;
	}
	
	@Override
	public String toString() 
	{
		return Resources.PHASE2_BLACKPIN + ": " + getBlackPinCount() + 
				" - " + Resources.PHASE2_WHITEPIN + ": " + getWhitePinCount();
	}
	
	/**
	 * Gets a small string representation of this TurnResult
	 * @return a small string representation of this TurnResult. Example: 0,1.
	 */
	public String toSmallString() 
	{
		return getBlackPinCount() + "," + getWhitePinCount();
	}

	@Override
	public TurnResult parseString(String input)
	{
		if(!input.contains(","))
			throw new IllegalArgumentException(Resources.ERROR_TURNFORMAT);
		
		String[] numbers = input.split(",");
		
		if(numbers.length > 2 || numbers.length <= 1)
			throw new IllegalArgumentException(Resources.ERROR_TURNFORMAT);
		
		TurnResult out = null;
		
		try 
		{
			int correctplace = Integer.parseInt(numbers[0]);
			int correctcolor = Integer.parseInt(numbers[1]);
			
			out = new TurnResult(correctplace, correctcolor);
			
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(Resources.ERROR_TURNFORMAT);
		}
		
		return out;
	}

	@Override
	public void addValidInput(String... input) 
	{
		if(this._validInput == null)
			this._validInput = new ArrayList<String>();
		
		for (String string : input) 
		{
			this._validInput.add(string);			
		}	
	}

	@Override
	public List<String> getValidInput() 
	{
		return new ArrayList<String>(this._validInput);
	}

	@Override
	public String getValidInputAsString()
	{
		if(this._validInput == null)
			this._validInput = new ArrayList<String>();
		
		String out = Resources.EMPTY;
		for (String string : this._validInput) 
		{
			out += String.format(Resources.BRACKET, string);
		}
		
		return out;
	}
}
