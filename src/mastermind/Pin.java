package mastermind;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A class used to define a Pin in a PinRow
 * @author Tom
 * @see PinRow
 */
public class Pin 
{
	/**
	 * A static definition of an empty pin.
	 */
	public static final Pin EMPTY = new Pin("");
	/**
	 * A static list off all possible Pins
	 */
	private static List<Pin> _values;
	/**
	 * The name associated to this pin
	 */
	private String _name;

	/**
	 * Constructs a new instance of the Pin class
	 * @param name the name of the Pin
	 */
	public Pin(String name) 
	{
		this._name = name;
	}

	/**
	 * Gets the name of this pin
	 */
	public String getName() 
	{
		return this._name;
	}

	/**
	 * Checks if a Pin with name {@code s} exists in the list of all Pins
	 * @param s the name of the Pin you want to validate
	 * @return true if the Pin name is valid. False if not.
	 */
	public static boolean isValid(String s) 
	{
		//loop through all pin values
		for (Pin p : Pin.values())
		{
			//if a pin with name is is found return true
			if (s.equals(p.getName())) 
			{
				return true;
			}
		}

		//nothing was found, return false
		return false;
	}

	/**
	 * A method to return an array of all valid Pins
	 */
	public static Pin[] values() 
	{
		//create a new empty array with the same length as _values
		Pin[] parr = new Pin[_values.size()];

		//copy all the elements from _values to parr
		for (int i = 0; i < parr.length; i++) 
		{
			parr[i] = _values.get(i);
		}

		//return parr
		return parr;
	}

	/**
	 * Adds a new Pin to the list of valid Pins
	 * @param p the new Pin
	 */
	public static void addValue(Pin p) 
	{
		//if the list of _values is not initialized, initialize it
		if (_values == null)
			_values = new ArrayList<Pin>();

		if(_values.contains(p))
			throw new IllegalArgumentException();
		
		//add the new pin
		_values.add(p);
	}

	/**
	 * Adds multiple Pins to the list of valid Pins
	 * @param values
	 */
	public static void addAll(String... values) 
	{
		//loop through all params
		for (String value : values)
		{
			//add new pin
			addValue(new Pin(value));
		}
	}

	/**
	 * Gets the Pin with name s
	 * @param s the name of the desired Pin
	 */
	public static Pin valueOf(String s) 
	{
		//create empty pin
		Pin out = null;
		//use streams to get all pins with name s
		List<Pin> pl = _values.stream().filter(x -> x._name.equals(s)).collect(Collectors.toList());

		//are there any results?
		if (pl.size() > 0)
			//yes? get index 0
			out = pl.get(0);

		//return the pin
		return out;
	}

	@Override
	public boolean equals(Object p2) 
	{
		//if p2 is null return false
		if (p2 == null)
			return false;

		//check if p2 is of type Pin
		if(p2 instanceof Pin)
		{
			Pin p = (Pin)p2;
			return this.getName().equals(p.getName());
		}
		
		return false;
	}

	@Override
	public String toString() {
		return this.getName();
	}

}
