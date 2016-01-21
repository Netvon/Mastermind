package mastermind.console.io;

import java.util.List;

/**
 * An Interface used for defining a common set of rules that apply to an answer.
 * All Answers must implement parseString(String)
 * @author Tom van Nimwegen
 *
 */
public interface IAnswer
{
	Object parseString(String input);
	void addValidInput(String...input);
	List<String> getValidInput();
	String getValidInputAsString();
}
