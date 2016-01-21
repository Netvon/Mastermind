package mastermind.console.io;

import mastermind.dialog.Resources;

/**
 * An implementation of the Answer class using a Boolean as Answer type.
 * <p>Valid input for this class is: {@code y, yes, true, n, no, false}.
 * @author Tom van Nimwegen
 * 
 */
public class BooleanAnswer extends Answer<Boolean>
{
	/**
	 * Constructs a new instance of the BooleanAnswer class.
	 * @see Answer
	 */
	public BooleanAnswer() 
	{
		this.addValidInput("y", "yes", "true", "n", "no", "false");
		
		this.setFunc(input -> {
			boolean out = false;
			boolean correct = false;
			
			switch (input.toLowerCase()) {
			case "y":
			case "yes":
			case "true":
				out = true;
				correct = true;
				break;
				
			case "n":
			case "no":
			case "false":
				out = false;
				correct = true;
				break;
			}
			
			if(!correct)
				throw new IllegalArgumentException(String.format(Resources.ERROR_INVALID,this.getValidInputAsString()));
			
			return out;
		});
	}

}
