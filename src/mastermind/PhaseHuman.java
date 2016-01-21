package mastermind;

import mastermind.console.io.Question;
import mastermind.dialog.Resources;

/**
 * The human phase in a Mastermind game
 * @author Tom van Nimwegen
 * @see Phase
 */
public class PhaseHuman extends Phase
{
	@Override
	public void play() 
	{
		try {
			System.out.println(Resources.EMPTY);
			System.out.println(Resources.FLAVOR_OPENING_PHASE1_HELLO_1);
			g.setCode(PinRow.createRandom());
			
			if(Game.isDebugMode())
			{
				System.out.println(Resources.GAME_DEBUGMODE_START);
				System.out.println(g.getCode());
				System.out.println(Resources.GAME_DEBUGMODE_END);
			}
			
			super.play();
			
			boolean exit = false;
			boolean won = false;
			boolean lost = false;
			int guesscount = 1;
				
			while(!exit)
			{
					
				if(guesscount > Game.TURN_COUNT)
				{
					exit = true;
					break;
				}
				
				//ask the user to make a guess
				Question<PinRow> testQ = new Question<PinRow>(Resources.PHASE1_PREFIX + guesscount);
				PinRow pinrowq = testQ.askQuestion(new PinRow(), PinRow.class);
				TurnResult tr = g.addTurn(pinrowq);
				
				if(tr.hasWon())
				{
					exit = true;
					won = true;
				}
				
				if(tr.hasLost())
				{
					exit = true;
					lost = true;
				}
				
				
				g.showBoard(true);
				guesscount++;
			}
			
			if(won)
				System.out.println(Resources.FLAVOR_GAME_WON);
			
			if(lost)
				System.out.println(Resources.FLAVOR_GAME_LOST);
					
		} 
		catch (Exception e) 
		{
			System.out.println(Resources.GAME_INPUT_PREFIX + Resources.ERROR_PREFIX + e.getMessage());
		} 
		
		
	}
}
