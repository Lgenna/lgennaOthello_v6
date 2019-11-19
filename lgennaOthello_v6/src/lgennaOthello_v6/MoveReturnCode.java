package lgennaOthello_v6;

import java.util.ArrayList;

public class MoveReturnCode {

	private int initialCell;
	private int finalCell;
	private int origOffset;
	private ArrayList<Integer> flips;
	/**
	 * This is what each move is stored as so it can be returned across multiple methods.
	 * 
	 * @param initialCell This is the piece from which the move is generated
	 * @param finalCell This is the empty space that the move goes to
	 * @param origOffset The counter so the move can be replicated on the board (deprecated?)
	 * @param flips This is an array list of the flips that each move makes
	 */
	
	public MoveReturnCode(int initialCell, int finalCell, int origOffset, ArrayList<Integer> flips) {
		this.initialCell = initialCell;
		this.finalCell = finalCell;
		this.origOffset = origOffset;
		this.flips = flips;
		
	}

	public int getInitialCell() {
		return initialCell;
	}

	public int getFinalCell() {
		return finalCell;
	}
	
	public int getOrigOffset() {
		return origOffset;
	}
	
	public ArrayList<Integer> getFlips() {
		return flips;
	}

}
