package mastermind;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


//import mastermind.Game.Pin;
import mastermind.console.io.IAnswer;
import mastermind.dialog.Resources;

public class PinRow implements IAnswer
{
	/**
	 * The list containing all the Pins in this PinRow
	 */
	private List<Pin> _pins;
	/**
	 * The Result from this PinRow
	 */
	private TurnResult _result;
	/**
	 * Valid input for this Answer
	 */
	private List<String> _validInput;
	
	public PinRow()
	{
		this._pins = new ArrayList<Pin>(Game.PIN_COUNT);
		
		//add all pins to validinput
		for (Pin pin : Pin.values()) 
		{
			this.addValidInput(pin.getName());
		}
	}
	
	/**
	 * Add a pin to the PinRow
	 * @param p The pin you want to add
	 */
	public void addPin(Pin p)
	{
		this._pins.add(p);
	}
	
	/**
	 * Clears the PinRow and Result
	 */
	public void clear()
	{
		this._pins.clear();
		this._result = null;
	}
	
	/**
	 * A method to return a copy of the PinsList within this PinRow
	 * @return Returns a copy of the PinsList within this PinRow
	 */
	public List<Pin> getPinList()
	{
		return new ArrayList<Pin>(this._pins);
	}
	
	/**
	 * A method to add a result to this PinRow. If the PinRow already has a TurnResult it will be overwritten
	 * @param tr The TurnResult you want to add to this PinRow
	 */
	public void addResult(TurnResult tr)
	{
		this._result = tr;
	}
	
	/**
	 * A method to return the TurnResult from this PinRow
	 * @return Returns the TurnResult from this PinRow. Returns NULL of the PinRow has no result
	 */
	public TurnResult getResult()
	{
		return this._result;
	}
	
	/**
	 * A method to return the length of this PinRow
	 * @return Return the length of this PinRow
	 */
	public int getLength()
	{
		return this._pins.size();
	}
	
	/**
	 * A method to create a new PinRow from a Pin array
	 * @param pins The Pin array you want to create a PinRow from
	 * @return Returns a new instance of the PinRow class with the Pins from the pins array
	 */
	public static PinRow fromArray(Pin[] pins)
	{
		PinRow p = new PinRow();
		for (int i = 0; i < pins.length; i++) 
		{
			Pin pin = pins[i];
			p.addPin(pin);
		}
		
		return p;
	}
	
	/**
	 * A method to create a new random PinRow code. The code has a length of Game.PIN_COUNT
	 * @return Returns a new random PinRow code.
	 */
	public static PinRow createRandom()
	{
		//create a new empty instance of the pinrow class
		PinRow pr = new PinRow();
		//create a new instance of the random class
		Random r = new Random();
		
		//get the number of available pins
		int aantalpins = Pin.values().length;
		
		//loop over all the available pins
		for (int i = 0; i < Game.PIN_COUNT; i++)
		{
			//create a new random pin from the array of pins with a random number between
			//0 and the max number of available pins
			Pin p = Pin.values()[r.nextInt(aantalpins)];
			
			//we don't want the pin to be EMPTY
			//so, loop until it's not EMPTY
			while(p.equals(Pin.EMPTY))
				p = Pin.values()[r.nextInt(aantalpins)];
			
			//add the new pin to the pinrow
			pr.addPin(p);
		}
		
		//return the new pinrow
		return pr;
	}
	
	/**
	 * A method to compare two PinRow with each other
	 * @param p2 The PinRow you want to compare this PinRow to
	 * @return Returns a new instance from the TurnResult class containing the result of the compare
	 */
	public TurnResult compareTo(PinRow p2)
	{
		if(this._pins == null || p2.getPinList() == null)
			return new TurnResult(0,0);
		
		if(this._pins.size() < Game.PIN_COUNT || p2.getPinList().size() < Game.PIN_COUNT)
			return new TurnResult(0,0);
		
		//create local copy of the pins on the other players turn
		List<Pin> p2PinList = p2.getPinList();
		//create local copy of the pins from this players turn
		List<Pin> p1PinList = this.getPinList();
		
		//create a turf list for the other player
		List<Pin> p2adjustedPinList = new ArrayList<Pin>(p2PinList);
		//create a turf list for this player
		List<Pin> p1adjustedPinList = new ArrayList<Pin>(p1PinList);
		
		//set up the count of same pins for later
		int correctPlaceCount = 0;
		//set up the count of same color pins for later
		int rightColorCount = 0;
		
		//loop trough all pins of the other player
		for (int x = 0; x < p2PinList.size(); x++) 
		{
					
			//make a local copy of the current pin from the other player
			Pin p2Pin = p2PinList.get(x);
			//make a local copy of the current pin from this player
			Pin p1Pin = p1PinList.get(x);
			
			//check if the pins are equal
			if(p2Pin.equals(p1Pin))
			{
				//if so, add 1 to the number of identical pins
				correctPlaceCount++;
				
				//also set these pins to empty in the turf lists
				p2adjustedPinList.set(x, Pin.EMPTY);
				p1adjustedPinList.set(x, Pin.EMPTY);
			}
		}
		
		//after you checked all the pins that are the same you have to check all the leftover pins
		for (int p1Index = 0; p1Index < p1adjustedPinList.size(); p1Index++) 
		{				
			for (int p2Index = 0; p2Index < p2adjustedPinList.size(); p2Index++) 
			{
				Pin p1Pin = p1adjustedPinList.get(p1Index);
				Pin p2Pin = p2adjustedPinList.get(p2Index);
				
				//check if either of the players pins is empty
				if(p1Pin.equals(Pin.EMPTY) || p2Pin.equals(Pin.EMPTY))
					continue;
				
				//check if the pin from player1 equals the pin from player2
				if(p2Pin.equals(p1Pin))
				{
					//if so increase the count of right colored pins
					rightColorCount++;
					//set the pin from player1 in the adjusted list to empty
					p1adjustedPinList.set(p1Index, Pin.EMPTY);
					//set the pin from player2 in the adjusted list to empty
					p2adjustedPinList.set(p2Index, Pin.EMPTY);
				}
			}
		}
		
		//generate a new instance of the TurnResult class with the results from this compare
		TurnResult tr = new TurnResult(correctPlaceCount, rightColorCount);
		
		//check if the count of correctly placed pins is greater or equal to the 
		//Maximum number of allowed pins
		if(correctPlaceCount >= Game.PIN_COUNT)
			//if so, you can assume that the game was won during this turn
			tr.setWon();
		
		//return the TurnResult generated for this compare
		return tr;
	}
	
	/**
	 * A method to parse a String to a new instance of the PinRow class
	 * @param input The input String you want to parse. Example: "ABCD".
	 * @return Returns a new instance of the PinRow class with the Pins parse from the String
	 */
	public PinRow parseString(String input)
	{
		//create a char array with all the letters in the input string
		char[] letters = input.toCharArray();
		
		//check if there are more letters in the input string then allowed by Game.PIN_COUNT
		if(letters.length > Game.PIN_COUNT)
		{
			//if there are more letters in the string create a substring from the original string that
			//only includes the number of letters allowed
			letters = input.substring(0, Game.PIN_COUNT).toCharArray();
		}
		
		//check of the number of letters is to small
		if(letters.length < Game.PIN_COUNT)
			//if so, throw a new exception
			throw new IllegalArgumentException(Resources.ERROR_CODE_SHORT);
		
		//create an empty PinRow
		PinRow p = new PinRow();	
		
		//create an empty string to hold invalid characters
		String invalid = "";
		
		//loop over all the letters in the letters array
		for (char c : letters) 
		{
			String pstring = Character.toString(c).toUpperCase();
			
			//check if the current letter is valid
			if(!Pin.isValid(pstring))
			{
				invalid += String.format(Resources.BRACKET, pstring);
				continue;				
			}
			
			if(!invalid.equals(""))
				throw new IllegalArgumentException(Resources.ERROR_CODE_INVALID + ": " + invalid); 
			
			//add the pin to the empty PinRow
			p.addPin(Pin.valueOf(Character.toString(c).toUpperCase()));
		}
		
		if(!invalid.equals(""))
			throw new IllegalArgumentException(Resources.ERROR_CODE_INVALID + ": " + invalid); 
		
		//return the new PinRow
		return p;
	}
	
	/**
	 * Gets a list that contains all possible PinRow combinations
	 */
	public static List<PinRow> getAllPairs()
	{
		List<PinRow> out = new LinkedList<PinRow>();
		getAllPairs(Game.PIN_COUNT, out, new Pin[Game.PIN_COUNT]);
		return out;
	}
	
	/**
	 * A recursive method used to generate all possible PinRow combinations
	 * @param count the number of pins in a row. Will be counted down to 0
	 * @param pr a list that will contain all final combinations
	 * @param parr the current PinRow in construction
	 */
	private static void getAllPairs(int count, List<PinRow> pr, Pin[] parr)
	{
		//if count is zero then the row is ready to be added to pr
		if(count <= 0)
			pr.add(fromArray(parr));
		else
		{
			//loop over all possible Pin values
			for (Pin pin : Pin.values()) 
			{				
				//set index count - 1 in parr to the current pin
				parr[count-1] = pin;
				//call getAllPairs again for the next index
				getAllPairs(count-1, pr, parr);
			}
		}
	}
	
	@Override
	public boolean equals(Object obj) 
	{
		if(obj instanceof PinRow)
		{		
			PinRow pr = (PinRow)obj;
			return this.compareTo(pr).getBlackPinCount() == Game.PIN_COUNT;
		}
		return false;
	}
	
	@Override
	public String toString()
	{
		String out = "";
		for (Pin pin : _pins)
		{
			out += pin.getName();
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
