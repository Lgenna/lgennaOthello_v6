package lgennaOthello_v6;

import java.util.ArrayList;

public class LetsGetPrinting {

//	private boolean pIsMyTurn, pIsMyColorBlack;
//	private int pPrintMode;
//	private ArrayList<MoveReturnCode> pCurrentValidMoves;
	private String pPrintBoardOutput, pPrintMovesOutput;
	
	private static String[] letterArray = { "a", "b", "c", "d", "e", "f", "g", "h" };

	public LetsGetPrinting() {
//		this.pIsMyTurn = isMyTurn;
//		this.pIsMyColorBlack = isMyColorBlack;
//		this.pPrintMode = printMode;
//		this.pCurrentValidMoves = currentValidMoves;
		
//		printMasterOutput = printMaster(printMode);
		
//		pPrintBoardOutput = printBoard(pPrintMode);
	}
	

//	public String getPrintBoardOutput() {
//		return pPrintBoardOutput;
//	}

//	public String getPrintMovesOutput() {
//		return pPrintMovesOutput;
//	}
	
	public String printBoard(Board currentBoard, int pPrintMode,boolean pIsMyTurn, boolean pIsMyColorBlack) {
		
		if (pPrintMode == 1 || pPrintMode == 3) {
			int[] theBoard = currentBoard.getGameBoard();
	
			int counter = 0;
			int columnNumbers = 1;
			
			pPrintBoardOutput = "C      a b c d e f g h \nC    *******************\n";
			for (int i = 10; i <= 88; i++) {
				if (counter == 0) {
					pPrintBoardOutput += "C  " + columnNumbers + " * ";
					columnNumbers++;
					counter++;
				} else if (counter == 8) {
					if (theBoard[i] == 1) {
						pPrintBoardOutput += "B *\n";
					} else if (theBoard[i] == -1)
						pPrintBoardOutput += "W *\n";
					else {
						pPrintBoardOutput += "- *\n";
					}
					counter = 0;
				} else if (theBoard[i] != 2) {
					if (theBoard[i] == 1) {
						pPrintBoardOutput += "B ";
					} else if (theBoard[i] == -1)
						pPrintBoardOutput += "W ";
					else {
						pPrintBoardOutput += "- ";
					}
					counter++;
				}
			}
			pPrintBoardOutput += "C    *******************";
			return pPrintBoardOutput;
		}
		return null;
	}

	public String printValidMoves(int pPrintMode, boolean pIsMyColorBlack, ArrayList<MoveReturnCode> pCurrentValidMoves) {
		
		if (pPrintMode == 2 || pPrintMode == 3) {
			for (int i = 0; i < pCurrentValidMoves.size(); i++) {
				
				String flipsList = "";
				
				String output;
				
				MoveReturnCode temp = pCurrentValidMoves.get(i);
			
				int initialCell = temp.getInitialCell();
				int finalCell = temp.getFinalCell();
	
				ArrayList<Integer> flips = temp.getFlips();
				
				for (int j = 0; j < flips.size(); j++) {
					if (j + 1 != flips.size()) {
						flipsList += "(" + getIntegerToRowColumn(flips.get(j)) + "), ";
					} else {
						flipsList += "(" + getIntegerToRowColumn(flips.get(j)) + ")";
					}
				}
				
				if (pIsMyColorBlack) {
					output = "C B ";
				} else {
					output = "C W ";
				}
				
				output += ": (" 
						+ getIntegerToRowColumn(initialCell) + ") to (" 
						+ getIntegerToRowColumn(finalCell) + ") : " 
						+ flips.size() + " Flipable disk(s) : " 
						+ flipsList;
					
				if (i + 1 < pCurrentValidMoves.size()) {
					output += "\n";
				}
				
				if (pPrintMovesOutput != null) {
					pPrintMovesOutput += output;
				} else {
					pPrintMovesOutput = output;
				}
				
			}
				
			return pPrintMovesOutput;
	
		}
		return null;
	}
	
	public String getIntegerToRowColumn(int cellId) {
		int cellIdTens = cellId / 10;
		int cellIdOnes = cellId % 10;

		return (letterArray[cellIdOnes - 1] + " " + cellIdTens);
	}
}
