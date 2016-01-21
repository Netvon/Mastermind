package mastermind.console.io;

import java.util.function.Function;

import mastermind.dialog.Resources;

/**
 * The Special class wraps a {@code Function<String, Boolean>} to run alongside other user input.
 * <p>
 * You can run the underlying {@code Function} by calling {@code accept(String)}.
 * @author Tom van Nimwegen
 * @see Function
 * 
 */
public class Special 
{
	/**
	 * The underlying function of this special
	 */
	private Function<String, Boolean> _func;
	
	private String[] _params;
	
	/**
	 * Creates a new instance of the special class
	 * @param function The function this special has to run
	 * @see Function
	 */
	public Special(Function<String, Boolean> function, String...params)
	{
		this._func = function;
		this._params = params;
	}
	
	/**
	 * A method to run the underlying function of the special io function
	 * @param input An input string
	 * @return Returns true if successful and false if not
	 * @see Function
	 */
	public Boolean accept(String input)
	{
		if(paramMatched(input))
			return this._func.apply(input);
		
		return false;
	}
	
	private boolean paramMatched(String input)
	{
		for (String string : _params) 
		{
			if(input.equals(string))
				return true;
		}
		
		return false;
	}
	
	@Override
	public String toString() 
	{
		String out = Resources.EMPTY;
		for (String string : _params) 
		{
			out += String.format(Resources.BRACKET, string);
		}
		return out;
	}
}
