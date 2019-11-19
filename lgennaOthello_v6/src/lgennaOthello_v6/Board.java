package lgennaOthello_v6;

import java.util.ArrayList;

public class Board {

//	A clean board		   	a   b   c   d   e   f   g   h
	int[] gameBoard = { 2,  2,  2,  2,  2,  2,  2,  2,  2,  2, 
						2,  0,  0,  0,  0,  0,  0,  0,  0,  2,  // 1
						2,  0,  0,  0,  0,  0,  0,  0,  0,  2,  // 2
						2,  0,  0,  0,  0,  0,  0,  0,  0,  2,  // 3
						2,  0,  0,  0, -1,  1,  0,  0,  0,  2,  // 4
						2,  0,  0,  0,  1, -1,  0,  0,  0,  2,  // 5
						2,  0,  0,  0,  0,  0,  0,  0,  0,  2,  // 6
						2,  0,  0,  0,  0,  0,  0,  0,  0,  2,  // 7
						2,  0,  0,  0,  0,  0,  0,  0,  0,  2,  // 8
						2,  2,  2,  2,  2,  2,  2,  2,  2,  2 };
	
	private ArrayList<Integer> myPieceIds = new ArrayList<Integer>();
	private ArrayList<Integer> opponentPieceIds = new ArrayList<Integer>();
	private ArrayList<Integer> emptySpaces = new ArrayList<Integer>();
	private ArrayList<Integer> blackPieceIds = new ArrayList<Integer>();
	private ArrayList<Integer> whitePieceIds = new ArrayList<Integer>();
	private ArrayList<MoveReturnCode> validMoves = new ArrayList<MoveReturnCode>();
	private boolean isMyTurn, isMyColorBlack;
	
//	public Board(boolean isMyTurn, boolean isMyColorBlack) {
	public Board() {
//		this.gameBoard = gameBoard;
//		this.isMyTurn = isMyTurn;
//		this.isMyColorBlack = isMyColorBlack;
	}
	
	private void validMoveMaster() {
		
		turnBoardIntoPieces();

		int[] offsets = {-11, -10, -9, -1, 1, 9, 10, 11};
		
		if (isMyTurn) {
			for (int i = 0; i < emptySpaces.size(); i++) {
				for (int j = 0; j < offsets.length; j++) {
					checkEmptySpaces(emptySpaces.get(i), offsets[j], offsets[j]);
				}
			}
		} else {
			for (int i = 0; i < emptySpaces.size(); i++) {
				for (int j = 0; j < offsets.length; j++) {
					checkEmptySpacesForOpponent(emptySpaces.get(i), offsets[j], offsets[j]);
				}
			}
		}
	}
	
	private void turnBoardIntoPieces() {
		
		validMoves.clear();
		myPieceIds.clear();
		opponentPieceIds.clear();
		emptySpaces.clear();
		blackPieceIds.clear();
		whitePieceIds.clear();
		
		int[] currentBoard = getGameBoard();
		
		for (int i = 11; i <= 88; i++) {
			if (isMyColorBlack && currentBoard[i] == 1) {
				myPieceIds.add(i);
				blackPieceIds.add(i);
			} else if (!isMyColorBlack && currentBoard[i] == -1) {
				myPieceIds.add(i);
				whitePieceIds.add(i);
			} else if (!isMyColorBlack && currentBoard[i] == 1) {
				opponentPieceIds.add(i);
				blackPieceIds.add(i);
			} else if (isMyColorBlack && currentBoard[i] == -1) {
				opponentPieceIds.add(i);
				whitePieceIds.add(i);
			} else if (currentBoard[i] == 0) {
				emptySpaces.add(i);
			}
		}
	}
	
	private void checkEmptySpaces (int emptySpace, int offset, int origOffset) {
		if (opponentPieceIds.contains(emptySpace + offset)) {
			
			ArrayList<Integer> piecesToFlip = new ArrayList<Integer>();
			offset = offset + origOffset;
			int addedOffset = emptySpace + offset;
			
			if (!opponentPieceIds.contains(addedOffset) && myPieceIds.contains(addedOffset)) {
				addMoveReturnCodeToList(offset, addedOffset, emptySpace, origOffset, piecesToFlip);
			}
			checkEmptySpaces(emptySpace, offset, origOffset);
		}
	}
	
	private void checkEmptySpacesForOpponent (int emptySpace, int offset, int origOffset) {
		if (myPieceIds.contains(emptySpace + offset)) {

			ArrayList<Integer> piecesToFlip = new ArrayList<Integer>();
			offset = offset + origOffset;
			int addedOffset = emptySpace + offset;
			
			if (opponentPieceIds.contains(addedOffset) && !myPieceIds.contains(addedOffset)) {
				addMoveReturnCodeToList(offset, addedOffset, emptySpace, origOffset, piecesToFlip);
			}
			checkEmptySpacesForOpponent(emptySpace, offset, origOffset);  
		}
	}	
	
	private void addMoveReturnCodeToList (int offset, int addedOffset, int emptySpace, int origOffset, ArrayList<Integer> piecesToFlip) {
		
		int[] currentBoard = getGameBoard();
		
		if (currentBoard[addedOffset] != 2) {
			
			int piecesOffset = offset;
			
			if (piecesOffset > origOffset) {
				while (piecesOffset > origOffset) {	
					piecesToFlip.add(emptySpace + piecesOffset - origOffset);
					piecesOffset -= origOffset;
				}
			} else if (piecesOffset < origOffset) {
				while (piecesOffset < origOffset) {
					piecesToFlip.add(emptySpace + piecesOffset - origOffset);
					piecesOffset -= origOffset;
				}
			} 
			if (piecesOffset == origOffset) {
				if (piecesToFlip.isEmpty()) {
					piecesToFlip.add(emptySpace + piecesOffset - origOffset);
				}
			}
			
			MoveReturnCode validMove = new MoveReturnCode(addedOffset, emptySpace, origOffset, piecesToFlip);
			validMoves.add(validMove);
		}	
	}
	
	public int[] getGameBoard() {
		return gameBoard;
	}

	public void setGameBoard(int[] gameBoard) {
		this.gameBoard = gameBoard;
	}

	public ArrayList<MoveReturnCode> getValidMoves() {
		validMoveMaster();
		return validMoves;
	}

	public ArrayList<Integer> getBlackPieceIds() {
		return blackPieceIds;
	}

	public ArrayList<Integer> getWhitePieceIds() {
		return whitePieceIds;
	}
	
}
