package mastermind.dialog;

import mastermind.Game;

/**
 * A class containing all the dialog and messages for this application
 * @author Tom
 *
 */
public class Resources {
	public static final String EMPTY = "";
	public static final String NL = "\n";
	public static final String T1 = "\t";
	public static final String T2 = "\t\t";
	public static final String BRACKET = "[%1$s]";
	
	public static final String SETUP_GOTO = "Do you want to edit any settings?";
	public static final String SETUP_DEBUGMODE = "Do you want to enable debug mode?";
	public static final String SETUP_RULES = "Do you want to change the rules of the game?";
	public static final String SETUP_RULES_PINCOUNT_1 = "What do you want the Pin count to be?"; 
	public static final String SETUP_RULES_PINCOUNT_2 = "Default: " + Game.PIN_COUNT + ", Min: 2, Max: 6";
	public static final String SETUP_RULES_TURNCOUNT_1 = "What do you want the Turn count to be?";
	public static final String SETUP_RULES_TURNCOUNT_2 = "Default: " + Game.TURN_COUNT + ", Min: 5, Max: " + Integer.MAX_VALUE;
	public static final String SETUP_RULES_ADDPIN = "Would you like to add a Pin?";
	public static final String SETEUP_RULES_NEWPIN = "Pin name";
	
	public static final String FLAVOR_OPENING_HELLO_1 = "Welcome to MasterMind!";
	public static final String FLAVOR_OPENING_HELLO_2 = "Type [h] or [help] to get information about input.";
	public static final String FLAVOR_OPENING_PHASE1_HELLO_1 = "I've got a secret code on my mind. Can you guess it?";
	public static final String FLAVOR_OPENING_PHASE2_HELLO_1 = "Now it's my turn.";
	public static final String FLAVOR_OPENING_PHASE2_HELLO_2 = "Think of a code and I'll try and guess it!";
	public static final String FLAVOR_OPENING_PHASE2_PREFIX = "Enter secret code";
	public static final String FLAVOR_GAME_WON = "You won!";
	public static final String FLAVOR_GAME_LOST = "You lose :(";
	
	public static final String TUTORIAL_GAME_QUIT = "Type [x] to quit";
	public static final String TUTORIAL_INPUT = "Valid input: %1$s";
	
	public static final String GAME_INPUT_PREFIX = "> ";
	public static final String GAME_PLAYAGAIN = "Would you like to play again?";
	public static final String GAME_DEBUGMODE_START = "******* Debug *******";
	public static final String GAME_DEBUGMODE_DEBUG = "Debug mode: ";
	public static final String GAME_DEBUGMODE_SCORE = "Score: ";
	public static final String GAME_DEBUGMODE_BOARD = "Board: ";
	public static final String GAME_DEBUGMODE_CODE = "Code: ";
	public static final String GAME_DEBUGMODE_PINS = "Pins: ";
	public static final String GAME_DEBUGMODE_END = "*********************";
	public static final String GAME_HELP_START = "******** Help *******";
	public static final String GAME_HELP_END = "*********************";
	public static final String GAME_BOARD_START = "***** Mastermind ****";
	public static final String GAME_BOARD_END = "*********************";
	public static final String GAME_POINTS_START = "****** Points *******";
	public static final String GAME_POINTS_END = "*********************";
	public static final String GAME_COMPUTER = "Computer";
	public static final String GAME_HUMAN = "Player";
	
	public static final String PHASE1_PREFIX = "Guess ";
	
	public static final String PHASE2_CODE = "The code:";
	public static final String PHASE2_CGUESS = "Computer guess:";
	public static final String PHASE2_BLACKPIN = "Black pins";
	public static final String PHASE2_WHITEPIN = "White pins";
	public static final String PHASE2_DEBUG_1 = "Result";
	public static final String PHASE2_DEBUG_2 = "Can win";
	public static final String PHASE2_DEBUG_3 = "Pair count";
	public static final String PHASE2_CALCULATING = "Calculating, please wait";
	
	public static final String ERROR_PREFIX = "Error: ";
	public static final String ERROR_CODE_SHORT = "Your code is too short. Please enter a code of " + Game.PIN_COUNT + " characters";
	public static final String ERROR_CODE_LONG = "Your code is too long. Please enter a code of " + Game.PIN_COUNT + " characters";
	public static final String ERROR_CODE_INVALID = "Your code contains one or more invalid characters";
	public static final String ERROR_MAX_TURNS= "You have excedded the maximum number of turns (" + Game.TURN_COUNT + ")";
	public static final String ERROR_TURNSET_1 = "is larger then the maximum number of pins (" + Game.PIN_COUNT + ")";
	public static final String ERROR_TURNSET_2 = "can not be lower than 0";
	public static final String ERROR_TURNFORMAT = "Input string was not in the right format: #,#";
	public static final String ERROR_CHAR_LONG = "You can only input one character";
	public static final String ERROR_CHAR_SHORT = "You have to input one character";
	public static final String ERROR_PIN_DUPLICATE = "Pin [%1$c] already exists";
	public static final String ERROR_NUMBER_MAX = "Number is larger than maximum value: [%1$d]";
	public static final String ERROR_NUMBER_MIN = "Number is lower than minimum value: [%1$d]";
	public static final String ERROR_NUMBER = "Input can only be a number";
	public static final String ERROR_INVALID = "Input invalid. " + TUTORIAL_INPUT;
}
