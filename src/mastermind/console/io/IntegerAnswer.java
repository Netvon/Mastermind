package mastermind.console.io;

import mastermind.dialog.Resources;

/**
 * An implementation of the Answer class using Integer as Answer type
 * @author Tom van Nimwegen
 * 
 */
public class IntegerAnswer extends Answer<Integer>
{
	/**
	 * Constructs a new IntegerAnswer with a minimum and maximum value.
	 * @param min the minimum value allowed
	 * @param max the maximum value allowed
	 * @see Answer
	 * @see Question
	 */
	public IntegerAnswer(Integer min, Integer max)
	{
		this.addValidInput("Min: " + min, "Max: " + max);
		this.setFunc(input ->
		{	
			//create new output var
			int output = -1;
			try 
			{
				//try to parse the int from the input string
				output = Integer.parseInt(input);
				
				//check if the max is null. If it's null there is no reason the check for max values
				if(max != null)
					//check if the value of output is larger then the max
					if(output > max)
						//if so, throw an exception
						throw new IllegalArgumentException(String.format(Resources.ERROR_NUMBER_MAX, max));
				
				//check if the min is null. If it's null there is no reason the check for min values
				if(min != null)
					//check if the value of output is smaller then the min
					if(output < min)
						//if so, throw an exception
						throw new IllegalArgumentException(String.format(Resources.ERROR_NUMBER_MIN, min));
			} 
			catch (NumberFormatException e) 
			{
				throw new IllegalArgumentException(Resources.ERROR_NUMBER);
			}
			
			return output;
		});
	}
	
	/**
	 * Constructs a new IntegerAnswer with maximum value.
	 * @param max the maximum value allowed
	 * @see Answer
	 * @see Question
	 */
	public IntegerAnswer(Integer max)
	{
		this(null, max);
	}
	
	/**
	 * Constructs a new IntegerAnswer without a minimum and maximum value.
	 * @see Answer
	 * @see Question
	 */
	public IntegerAnswer()
	{
		this(null, null);
	}
}
