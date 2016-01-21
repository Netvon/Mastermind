package mastermind.console.io;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import mastermind.dialog.Resources;

/**
 * A generic implementation of the IAnswer interface.
 * @author Tom van Nimwegen
 *
 * @param <T>the type of this answer
 */
public class Answer<T> implements IAnswer
{
	/**
	 * The value of this Answer.
	 */
	private T _value;
	/**
	 * The function behind this Answer.
	 */
	private Function<String, T> _function;
	/**
	 * Valid input for this Answer
	 */
	private List<String> _validInput;
	
	/**
	 * Gets the return value of this Answer.
	 * @return the answer of this question of type T
	 */
	public T getValue()
	{
		return this._value;
	}
	
	/**
	 * Sets the value of this Answer
	 * @param value the value of this Answer
	 */
	public void setValue(T value)
	{
		this._value = value;
	}
	
	/**
	 * Sets the Function behind this Answer
	 * @param value an new Function
	 * @see Function
	 */
	public void setFunc(Function<String, T> value)
	{
		this._function = value;
	}
	
	/**
	 * Adds an array strings to validinput
	 * @param input a valid input string
	 */
	public void addValidInput(String...input)
	{
		if(this._validInput == null)
			this._validInput = new ArrayList<String>();
		
		for (String string : input) 
		{
			this._validInput.add(string);			
		}
	}
	
	/**
	 * Gets a copy of the valid input list
	 */
	public List<String> getValidInput()
	{
		return new ArrayList<String>(this._validInput);
	}
	
	/**
	 * Gets a list of valid input in string form
	 */
	public String getValidInputAsString()
	{
		String out = Resources.EMPTY;
		for (String string : _validInput) 
		{
			out += String.format(Resources.BRACKET, string);
		}
		
		return out;
	}
	
	@Override
	public T parseString(String input) 
	{		
		return this._function.apply(input);
	}
}
