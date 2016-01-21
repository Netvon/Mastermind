package mastermind.console.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import mastermind.Game;
import mastermind.dialog.Resources;

/**
 * A class designed for easy questioning of the user through the console.
 * @author Tom van Nimwegen
 *
 * @param <T> the type of Question you want to ask. T must implement {@code IStringParseable}
 * @see IAnswer
 */
public class Question<T extends IAnswer> 
{
	/**
	 * The lines of text of this question
	 */
	private List<String> _lines;
	/**
	 * The input prefix of this question
	 */
	private String _inputPrefix;
	/**
	 * The static list of Specials used by all questions
	 */
	private static List<Special> _specials = new ArrayList<Special>();
	
	/**
	 * Constructs a new question with a prefix and lines. The lines and prefix can be left empty
	 * @param inputPrefix the input prefix that is displayed before the user input
	 * @param lines the lines of text that can be displayed before the user input
	 */
	public Question(String inputPrefix, String...lines)
	{
		this._lines = Arrays.asList(lines);
		this._inputPrefix = inputPrefix;
	}
	
	/**
	 * A method to return a copy of the {@code ArrayList} containing the text lines of this question.
	 * <p>
	 * This list is <b>read-only</b>.
	 */
	public List<String> getLines()
	{
		return new ArrayList<String>(Collections.unmodifiableList(this._lines));
	}
	
	/**
	 * Starts the question. 
	 * <p>The question will be continued to ask until the input required by T is correct.
	 * 
	 * @param out a new instance of T
	 * @param resultclass the class of the result type of this question
	 * @return a new instance of R with the result from this question
	 */
	public <R> R askQuestion(T out, Class<R> resultclass)
	{
		//setup two loop vars: correct and skip.
		//correct tells the system if the input was correct
		//skip tells the system if it should skip an input cycle
		boolean correct = false;
		boolean skip = false;
		
		//the output value of type R
		R output = null;
		
		//loop through all the lines of the question and display them
		for (String string : _lines) 
		{
			System.out.println(string);
		}		
		
		//setup a new bufferedreader for reading lines from the console
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		//setup an empty input string
		String input = Resources.EMPTY;
		
		//check if the input was correct (will always be false on the first run)
		while(!correct)
		{
			try 
			{
				boolean ignorespecial = false;
				
				//display the input prefix defines in strings
				System.out.print(this._inputPrefix + Resources.GAME_INPUT_PREFIX);
				//read the userinput
				input = br.readLine();
				
				if(input.startsWith("\\"))
				{
					ignorespecial = true;
					input = input.substring(1);
				}
				
				if(!ignorespecial)
				{
					Special help = new Special(x -> 
					{
						String ouputString = out.getValidInputAsString();
						
						if(Game.isDebugMode())
						{
							for (Special s : _specials) {
								ouputString += s;
							}
						}
						
						System.out.println(String.format(Resources.TUTORIAL_INPUT, ouputString));
						return true;
					}, "h", "help");
					
					skip = help.accept(input.toLowerCase());
					
					if(skip)
						continue;
					
					//loop through all the specials
					for (Special s : _specials) 
					{
						//run the special and store the result in skip
						skip = s.accept(input.toLowerCase());
						
						if(skip)
							break;
					}
				}
				
				//check if the skip flag was set
				if(!skip)
				{
					//if not, process the user input
					output = resultclass.cast(out.parseString(input));
					//if there was no exception at this point set correct to true
					correct = true;
				}
				
				System.out.println();
				
			} catch (Exception e) {
				System.out.println(Resources.GAME_INPUT_PREFIX + Resources.ERROR_PREFIX + e.getMessage());
			}
		}		
		
		//return the output
		return output;
	}
	
	/**
	 * A method to add a new special to the list of specials. Specials added will effect all questions asked.
	 * <p>The function should return true if successful and false if not.
	 * @param function the function that this special should run.
	 * @param params the input this special will be listening for 
	 * @see Special
	 * @see Function
	 */
	public static void addSpecial(Function<String, Boolean> function, String...params)
	{
		_specials.add(new Special(function, params));
	}
}
