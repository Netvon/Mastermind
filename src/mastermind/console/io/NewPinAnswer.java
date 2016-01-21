package mastermind.console.io;

import mastermind.Pin;import mastermind.dialog.Resources;


/**
 * An implementation of the Answer class using Pin as Answer type
 * @author Tom van Nimwegen
 * 
 */
public class NewPinAnswer extends Answer<Pin>
{
	/**
	 * Constructs a new instance of the NewPinAnswer class which only accepts Pin input
	 */
	public NewPinAnswer()
	{
		this.addValidInput("Single chars like: T, K, ~, !");
		this.setFunc(input -> 
		{		
			//if the input string is longer then one character it is not a single char
			if(input.length() > 1)
				throw new IllegalArgumentException(Resources.ERROR_CHAR_LONG);
			
			//if the input is empty it is not a char
			if(input.length() < 1)
				throw new IllegalArgumentException(Resources.ERROR_CHAR_SHORT);
			
			//get the first char of the input string 
			Character c = input.toUpperCase().charAt(0);
			
			//check if this char already has a pin attached to it
			if(Pin.isValid(c.toString()))
				throw new IllegalArgumentException(String.format(Resources.ERROR_PIN_DUPLICATE, c));
			
			return new Pin(c.toString());
		});
	}
}
