package lgennaOthello_v6;

import java.util.ArrayList;
import java.util.Random;

public class MakeAMove {
	
	public MakeAMove() { // i have no idea what to do with the constructor and at this point I'm too afraid to ask...
		
	}
	
	public int getSimpleMaxStrat(ArrayList<MoveReturnCode> mCurrentValidMoves) { // Simple Maximum Disk Strategy
		
		int maxPiecesFlips = 0;
		MoveReturnCode temp;
		ArrayList<Integer> flips;
		ArrayList<Integer> sameNumOfFlippablePieces = new ArrayList<Integer>();
		
		/**
		 * Get the MoveReturnCode, then take that and get the Flips from that return code. Then find the
		 *  maximum flips list size and use that as the maximum number of gained flips.
		 */
		for (int i = 0; i < mCurrentValidMoves.size(); i++) {
			
			temp = mCurrentValidMoves.get(i);
			flips = temp.getFlips();
			int piecesFlips = flips.size();
			
			if (piecesFlips >= maxPiecesFlips) {
				maxPiecesFlips = piecesFlips;
			}
		}
		
		/**
		 * Find out which lists have the same size as maxPiecesGained and add that to a list of moves that all 
		 *  have that same size length.
		 */
		for (int i = 0; i < mCurrentValidMoves.size(); i++) {
			
			temp = mCurrentValidMoves.get(i);
			flips = temp.getFlips();
			int piecesGained = flips.size();
			
			if (piecesGained == maxPiecesFlips) {
				sameNumOfFlippablePieces.add(i);
			}
		}
		
		//Then pick a random move from sameNumOfGainablePieces and add that to the board
		
		Random rand = new Random();
		int num = rand.nextInt(sameNumOfFlippablePieces.size());
			
		int mAMove = sameNumOfFlippablePieces.get(num);
		
		return mAMove;	
	}
	
	public int getComplexMaxStrat(ArrayList<MoveReturnCode> mCurrentValidMoves) { // Complex Maximum Disk Strategy
		
		int maxPiecesFlipped = 0, firstFinalCell, secondFinalCell;
		MoveReturnCode firstCode, secondCode;
		ArrayList<Integer> firstFlips, secondFlips;
		ArrayList<Integer> sameNumOfFlippablePieces = new ArrayList<Integer>();
		ArrayList<Integer> duplicateFinder = new ArrayList<Integer>();
		
		/**
		 * This loop finds the maximum number of Pieces gained through complex moves
		 *  Complex moves are moves that have multiple possible moves to a single spot. This comes up with 
		 *  a single value of the maximum number of pieces flips.
		 */
		
		for (int i = 0; i < mCurrentValidMoves.size(); i++) {

			firstCode = mCurrentValidMoves.get(i);
			firstFlips = firstCode.getFlips();
			firstFinalCell = firstCode.getFinalCell();
			int firstPiecesFlipped = firstFlips.size();
			int piecesFlipped = firstPiecesFlipped;
			
			for (int j = 0; j < mCurrentValidMoves.size(); j++) {

				secondCode = mCurrentValidMoves.get(j);
				secondFlips = secondCode.getFlips();
				secondFinalCell = secondCode.getFinalCell();
				int secondPiecesFlipped = secondFlips.size();
				
				if (firstFinalCell == secondFinalCell) {
					if (j != i) { // we don't want the comparison cell to be added to itself
						piecesFlipped += secondPiecesFlipped;
					}
				}
			}
			
			if (piecesFlipped > maxPiecesFlipped) {	
				maxPiecesFlipped = piecesFlipped;
				
			}
		}
		
		/**
		 * This loop goes through all of the available complex moves with how many pieces they gain to try and find
		 *  values that are equal to the maximum number of Pieces gained. It then adds these moves to a list.
		 */
		
		for (int i = 0; i < mCurrentValidMoves.size(); i++) {

			firstCode = mCurrentValidMoves.get(i);
			firstFlips = firstCode.getFlips();
			firstFinalCell = firstCode.getFinalCell();
			int firstPiecesFlipped = firstFlips.size();
			int piecesFlipped = firstPiecesFlipped;
			
			for (int j = 0; j < mCurrentValidMoves.size(); j++) {
				secondCode = mCurrentValidMoves.get(j);
				secondFlips = secondCode.getFlips();
				secondFinalCell = secondCode.getFinalCell();
				int secondPiecesFlips = secondFlips.size();
				
				if (firstFinalCell == secondFinalCell) {
					if (j != i) { // we don't want the comparison cell to be added to itself
						piecesFlipped += secondPiecesFlips;
					}
				}
			}
			
			if (piecesFlipped == maxPiecesFlipped) {	
				if (!duplicateFinder.contains(firstFinalCell)) {  // Value was not a duplicate, add it to the list
					sameNumOfFlippablePieces.add(i);
					duplicateFinder.add(firstFinalCell);
				}
			}
		}
		
		/**
		 * A final for loop to add any simple max disk moves that might be lying around to the list of maximum
		 * moves possible
		 */
		
		for (int i = 0; i < mCurrentValidMoves.size(); i++) {
			firstCode = mCurrentValidMoves.get(i);
			firstFlips = firstCode.getFlips();
			int piecesFlipped = firstFlips.size();
			
			if (piecesFlipped == maxPiecesFlipped) {
				sameNumOfFlippablePieces.add(i);
			}
		}

		/**
		 * Lastly pick a random cell from sameNumOfGainablePieces and return that move
		 * 
		 * There should  A L W A Y S  be at least  O N E  value in the sameNumOfGainablePieces
		 *  list, otherwise this  W I L L  die, crash, and burn killing itself along with the other
		 *  methods and thousands of print statements. We don't want that now do we?
		 * 
		 *  ... unless there's no moves, then it dies rather easily.
		 *  
		 *  Including a fail safe for this statement will cover up any bugs and it would only rely on the first
		 *   move rather than picking one at random. So it is out of the best interest to not include one even 
		 *   though this is likely to be going against standard coding conventions. Thats the way it has to be.
		 *   
		 *   Like how Edna Mode never said, NO FAILSAFES!
		 */
	
		int mAMove = -1;
		
		// if this statement can't come up with a random number, its taking the whole program with it
		if (sameNumOfFlippablePieces.size() != 0) { 
			Random rand = new Random();
			int num = rand.nextInt(sameNumOfFlippablePieces.size());
			mAMove = sameNumOfFlippablePieces.get(num);
		} else {
			// if true, die.
			System.out.println("C Uhhhh... haha... that wasn't supposed to happen... thats it for me folks.");
		}
		
		return mAMove;
	}
}
