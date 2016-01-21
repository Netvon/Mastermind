package mastermind;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import mastermind.console.io.IntegerAnswer;
import mastermind.console.io.Question;
import mastermind.dialog.Resources;

/**
 * The computer phase in a Mastermind game
 * @author Tom van Nimwegen
 * @see Phase
 */
public class PhaseComputer extends Phase {

	/**
	 * All current correct pairs
	 */
	private List<PinRow> pairs;
	/**
	 * All possible pairs
	 */
	private static List<PinRow> allpairs = PinRow.getAllPairs();
	/**
	 * Pairs already played by the computer
	 */
	private List<PinRow> madePairs;
	
	private static final int MESSAGE_ITTERATION_COUNT = 5000;
	
	@Override
	public void play() 
	{
		try {
			//init pairs and madePairs				
			pairs = new LinkedList<PinRow>(allpairs);
			madePairs = new LinkedList<PinRow>();
			
			//init loop vars
			boolean exit = false;
			boolean won = false;
			boolean lost = false;
			
			//show some flavor text for this phase
			System.out.println(Resources.EMPTY);
			System.out.println(Resources.FLAVOR_OPENING_PHASE2_HELLO_1);
			System.out.println(Resources.FLAVOR_OPENING_PHASE2_HELLO_2);
			
			//run the super play method with input help
			super.play();
			
			//check if the game is in debug mode
			if(Game.isDebugMode())
			{
				//if the game is in debug mode let the user input his secret code
				Question<PinRow> scode = new Question<PinRow>(Resources.FLAVOR_OPENING_PHASE2_PREFIX);
				PinRow pinrowq = scode.askQuestion(new PinRow(), PinRow.class);
				g.setCode(pinrowq);
			}
			
			//create a new random for picking random pairs
			Random r = new Random();
							
			//pick an initial first guess
			PinRow pr = allpairs.get(r.nextInt(allpairs.size()));
			madePairs.add(pr);
			
			//while exit if false this loop will run
			while(!exit)
			{
				//setup two local vars to contain pin counts
				int blackPinCount = 0;
				int whitePinCount = 0;
				
				//if there is a code set by the user display it
				if(g.getCode() != null)
					System.out.println(Resources.PHASE2_CODE + Resources.T1 + g.getCode());
				
				//show the computers guess
				System.out.println(Resources.PHASE2_CGUESS + Resources.T1 + pr + Resources.NL);
				
				//check if the game is in debug mode
				if(Game.isDebugMode())
				{
					//if the game is in debug mode show some debug info
					//-Result: displays the correct answer
					//-Can win: displays if the correct code is in the pairs list, if it's in there the computer can guess it
					//-Pair count: the amount of remaining pairs
					System.out.println(Resources.GAME_DEBUGMODE_START);
					if(g.getCode() != null)
					{
						System.out.println(Resources.PHASE2_DEBUG_1 + Resources.T2 + g.getCode().compareTo(pr).toSmallString());
						boolean check3 = pairs.stream().anyMatch(x -> x.equals(g.getCode()));
						System.out.println(Resources.PHASE2_DEBUG_2 + Resources.T2  + check3);
					}
					System.out.println(Resources.PHASE2_DEBUG_3 + Resources.T1  + pairs.size());
					System.out.println(Resources.GAME_DEBUGMODE_END);
				}
				
				//ask the user for the amount of black pins
				Question<IntegerAnswer> blackc = new Question<IntegerAnswer>(Resources.PHASE2_BLACKPIN);
				blackPinCount = blackc.askQuestion(new IntegerAnswer(0, Game.PIN_COUNT), Integer.class);
				
				//ask the user for the amount of white pins
				Question<IntegerAnswer> whitec = new Question<IntegerAnswer>(Resources.PHASE2_WHITEPIN);
				whitePinCount = whitec.askQuestion(new IntegerAnswer(0, Game.PIN_COUNT - blackPinCount), Integer.class);
						
				//create a new turnresult with the numbers given by the user
				TurnResult tr = new TurnResult(blackPinCount, whitePinCount);
				//add the result to the computers guess
				pr.addResult(tr);
				
				//add the guess of the computer to the gameboard
				g.addTurnWithouthResult(pr);
				
				//if the game was won exit
				if(tr.hasWon())
				{
					won = true;
					exit = true;
					break;
				}
				
				//if the game was lost exit
				if(tr.hasLost())
				{
					exit = true;
					lost = true;
					break;
				}
				
				//create iterationcount for debug purposes
				int iterationcount = MESSAGE_ITTERATION_COUNT;
				
				//calculate all possible pairs
				for (PinRow pinRow : allpairs) 
				{
					//remove all pairs that do not have the same answer as the last turn
					//also remove the pair if it has already been guessed
					if(!pr.compareTo(pinRow).equals(tr) || madePairs.contains(pinRow))
					{
						pairs.remove(pinRow);
					}
					
					iterationcount--;
					
					if(iterationcount <= 0)
					{
						//display a message to show the user that the computer is 
						//still calculating
						iterationcount = MESSAGE_ITTERATION_COUNT;
						System.out.println(Resources.PHASE2_CALCULATING);
					}
				}
				
				if(pairs.size() <= 0)
				{
					pairs = new LinkedList<PinRow>(allpairs);
				}					
				
				pr = pairs.get(r.nextInt(pairs.size()));
				madePairs.add(pr);
				
				g.showBoard(false);
			}
			
			if(won)
				System.out.println(Resources.FLAVOR_GAME_LOST);
			
			if(lost)
				System.out.println(Resources.FLAVOR_GAME_WON);			
			
		} 
		catch (Exception e) {
			System.out.println(Resources.GAME_INPUT_PREFIX + Resources.ERROR_PREFIX + e.getMessage());
		} 
		
	}
	
	
	
	
}
