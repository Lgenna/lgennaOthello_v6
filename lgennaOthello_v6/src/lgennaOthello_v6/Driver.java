package lgennaOthello_v6;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Driver {

	private static boolean choseColors, receivedValidMove, printedBoard, gameOver, isMyTurn, isMyColorBlack;
	private static int moveCounter = 0;

	static ArrayList<MoveReturnCode> programValidMoves;
	static ArrayList<MoveReturnCode> opponentValidMoves;
	
	private static String[] letterArray = { "a", "b", "c", "d", "e", "f", "g", "h" };
	
//	private static String currentPrintedBoard;
	
	
	public Driver() {
//		this.printMode = printMode;
//		this.playMode = playMode;
		
//		currentPrintedBoard = printBoard(printMode);
	}

	static int printMode = 0;
	/**
	 * 0 : Do not print board or moves (DEFAULT)
	 * 1 : Print board
	 * 2 : Print moves
	 * 3 : Print board & moves
	 */
	
	static int playMode = 0;
	/** 
	 * 0 : First move generated (DEFAULT)
	 * 1 : Random
	 * 2 : Simple maximum disk strategy
	 * 3 : Complex maximum disk strategy
	 * 4 : Weighted board
	 * 5 : Complex maximum disk strategy & weighted board
	 * 6 : Alpha/beta
	 */
		
	public static void userInput() {
		Scanner searchNFind = new Scanner(System.in);
		
		while (!choseColors) {
			System.out.println("C Enter StartColor: ");
			String enteredValue = searchNFind.nextLine();
//			String enteredValue = "I B";
//			System.out.println(enteredValue);
			if (enteredValue.contentEquals("I W")) {
//				searchNFind.close();
				isMyColorBlack = false;
				choseColors = true;
				isMyTurn = false;
				System.out.println("C Playing as \"I W\"\nR W");
			} else if (enteredValue.contentEquals("I B")) {
//				searchNFind.close();
				isMyColorBlack = true;
				choseColors = true;
				isMyTurn = true;
				System.out.println("C Playing as \"I B\" \nR B");
			} else if (enteredValue.startsWith("C")) {
				// do nothing because the input because was a comment
			} else {
				System.out.println("C Incorrect format! (Example of input: I W)");
			}
		}
		
		// Because I hate myself and do this check every time this method is re-run and I
		//  don't know where to put this print statement so it only does an initial print
		//  once players choose their colors
		
		Board currentBoard = new Board();
		
		// initial print of the board
		if (!printedBoard) {
			
			// example code of consulting other classes
//			ReportHandler rh = new ReportHandler(/* constructor args here */); 
//			rh.executeBatchInsert(); // Having fixed name to follow conventions
			
//			System.out.println("C Started printing the initial board.");
			
			LetsGetPrinting transporter = new LetsGetPrinting();
			
			System.out.println(transporter.printBoard(currentBoard, printMode, isMyTurn, isMyColorBlack));
			
			printedBoard = true;
//			System.out.println("C Finished printing the initial board.");
		}

		if (choseColors && !gameOver) {
			if (isMyTurn) {
				
				System.out.println("C The program thinks confidently...");
			
//				currentValidMoves = new Board(isMyTurn, isMyColorBlack).getValidMoves();
//				currentValidMoves = new Board().getValidMoves();
				
				programValidMoves = currentBoard.getValidMoves();
				
//				MakeAMove.getListOfValidMoves();
//				validMoveMaster();
				
				if (programValidMoves.size() > 0) {
					// for testing, print out all of the valid moves
					
					LetsGetPrinting transporter = new LetsGetPrinting();
					
					System.out.println(transporter.printValidMoves(printMode, isMyColorBlack, programValidMoves));
					
					MoveReturnCode temp = null; // if this continues being null, bad things will happen.
					
					MakeAMove zoomies = new MakeAMove();
					
					// lets modernize the method in which we play
					switch (playMode) {
						/** 
						 * 0 : First move generated (DEFAULT) 					(Working)
						 * 1 : Random 											(Working)
						 * 2 : Simple maximum disk strategy						(Working)
						 * 3 : Complex maximum disk strategy					(Working)
						 * 4 : Weighted board									(Not Implemented)
						 * 5 : Complex maximum disk strategy & weighted board	(Not Implemented) (Time?)
						 * 6 : Alpha/beta										(Not Implemented)
						 */
					
						default :
							temp = programValidMoves.get(0);
							break;
						case 1 :
							Random rand = new Random();
							int randy = rand.nextInt(programValidMoves.size());
							temp = programValidMoves.get(randy);
							break;
						case 2 :
							temp = programValidMoves.get(zoomies.getSimpleMaxStrat(programValidMoves));
							break;
						case 3 :
							temp = programValidMoves.get(zoomies.getComplexMaxStrat(programValidMoves));
							break;
						case 4 :  
							System.out.println("This strategy is not currently implemented yet, using default.");
							playMode = 0;
							temp = programValidMoves.get(0);
							break;
						case 5 :  
							System.out.println("This strategy is not currently implemented yet, using default.");
							playMode = 0;
							temp = programValidMoves.get(0);
							break;
						case 6 :  
							System.out.println("This strategy is not currently implemented yet, using default.");
							playMode = 0;
							temp = programValidMoves.get(0);
							break;
					}
					
					// get the values from the MoveReturnCode type
					int initialCell = temp.getInitialCell();
					int finalCell = temp.getFinalCell();
					int origOffset = temp.getOrigOffset();
					
					
					// check to see if there are multiple moves for an empty space
					if (programValidMoves.size() >= 1) { // there's at least two or more valid moves
						for (int i = 0; i < programValidMoves.size(); i++) {
							MoveReturnCode temp2 = programValidMoves.get(i);
							
							// get new versions of the values, because I don't know a better way to implement this
							int initialCell2 = temp2.getInitialCell();
							int finalCell2 = temp2.getFinalCell();
							int origOffset2 = temp2.getOrigOffset();
							
							if (finalCell2 == finalCell) { //the two final cells originated at the same cell
								updateBoard(currentBoard, initialCell2, finalCell2, origOffset2, isMyColorBlack);
							}
						}
					}
	
					// turn the integer location code to a "lower_case_letter space number" format
					
					
					String humanReadableFormat = transporter.getIntegerToRowColumn(finalCell);
					
					updateBoard(currentBoard, initialCell, finalCell, origOffset, isMyColorBlack);
			
					// state where the program made a move
					if (isMyColorBlack) {
						System.out.println("B " + humanReadableFormat);
					} else {
						System.out.println("W " + humanReadableFormat);
					}
					
					// state that the program logic is finished making a move
					System.out.println("C The program has made their move.");
					
					LetsGetPrinting theVan = new LetsGetPrinting();
					
					String theNewPrintedBoard = theVan.printBoard(currentBoard ,printMode, isMyTurn, isMyColorBlack);
					
					System.out.println(theNewPrintedBoard);
//					System.out.println(transporter2.printBoard(printMode, isMyTurn, isMyColorBlack));
					
//					System.out.println(transported.printBoard(printMode, isMyTurn, isMyColorBlack));
					
//					);
					
//					printBoard();
//					
				} else {
//					
//					// Turns out I have no moves, lets check the opponent...
//					myTurn = !myTurn;
//					validMoveMaster();
//					if (validMoves.size() == 0) {
//						// Wait they have no moves either? Activate the alarms
//						//  because this is the end game!
//						turnBoardIntoPieces();
//						int numOfBlackPieces = (blackPieceIds.size() - whitePieceIds.size());
//						System.out.println(numOfBlackPieces);
////						System.out.println(myPieceIds.size() - opponentPieceIds.size());
//						myTurn = !myTurn;
//						gameOver = true;
//					} else {
//						// Turns out they have more moves than us, but that will
//						//  only be for this round, throw a pass, for now.
//						if (myColor == 1) {
//							System.out.println("B");
//						} else {
//							System.out.println("W");
//						}
//						myTurn = !myTurn;
//					}
//					
				}
				
				// state what move number it is and after that, increment the moveCounter
				
				System.out.println("C Move #" + moveCounter);
				moveCounter++;
				
				isMyTurn = !isMyTurn;
				
			}
			if (!isMyTurn && !gameOver) {
				System.out.println("C The opponent should think about their options...");
//				System.exit(0);
				
				Board opponentBoard = new Board();
				
				opponentValidMoves = opponentBoard.getValidMoves();

				LetsGetPrinting opponentTransporter = new LetsGetPrinting();
				
				System.out.println(opponentTransporter.printValidMoves(printMode, isMyColorBlack, opponentValidMoves));
				
				while (!receivedValidMove) {

					System.out.println("C Enter a Move:");
					String enteredValue = searchNFind.nextLine();

					String numOfBlackPieces = "^(?:100|[1-9]?[0-9])$";

					if (!enteredValue.startsWith("C")) {
						if (!enteredValue.matches(numOfBlackPieces)) {
							determineValidUserInput(opponentBoard, enteredValue);
						} else {
						
							/**
							 * This check is terribly written
							 * 
							 * I agree, but I also refuse to fix it.
							 */

//							if (opponentValidMoves.size() > 0 && !isMyTurn) {
//								System.out.println("C There are still moves you can make.");
//								isMyTurn = !isMyTurn;
//								opponentBoard.getValidMoves();
//								iMyTurn = !isMyTurn;
//								opponentValidMoveMaster();
//							}
//							else if (opponentValidMoves.size() > 0 && isMyTurn) {
//								System.out.println("C There are still moves the program can make.");	
//							} else {
//								System.out.println(opponentBoard.getBlackPieceIds().size() - opponentBoard.getWhitePieceIds().size());
//								gameOver = true;
//								break;
//							}
						}
					}

					if (receivedValidMove) {
//						searchNFind.close();
						isMyTurn = !isMyTurn;
						receivedValidMove = false;
						System.out.println("C Recieved : " + enteredValue);
						System.out.println("C The opponent finally finished making up their mind.");
						
						LetsGetPrinting theVan = new LetsGetPrinting();
						
						String theNewPrintedBoard = theVan.printBoard(currentBoard, printMode, isMyTurn, isMyColorBlack);
						
						System.out.println(theNewPrintedBoard);
						
						System.out.println("C Move #" + moveCounter);
						moveCounter++;
						break;
					}
				}
			}
		}
	}
	
	public static void determineValidUserInput(Board opponentBoard, String enteredValue) {

		String regexValue = "[BW]\\s[a-h]\\s[1-9]";

		if (enteredValue.matches(regexValue)) {

			String[] splitEnteredValue = enteredValue.split("\\s+");

			String moveLetter = splitEnteredValue[1];
			int moveNumber = Integer.parseInt(splitEnteredValue[2]);

			if (opponentBoard.getValidMoves().size() > 0) {
				for (int i = 0; i < opponentBoard.getValidMoves().size(); i++) {
					MoveReturnCode temp = opponentBoard.getValidMoves().get(i);
	
					int initialCell = temp.getInitialCell();
					int finalCell = temp.getFinalCell();
					int origOffset = temp.getOrigOffset();
	
					int returnValue = rowColumnToInteger(moveLetter, moveNumber);

					if (finalCell == returnValue) {
						
						receivedValidMove = true;
						updateBoard(opponentBoard, initialCell, finalCell, origOffset, !isMyColorBlack);
					}
				}
			}
			if (!receivedValidMove) {
				System.out.println("C Not a valid move!");
			}
		} else {
			
			if (enteredValue.equals("B")) {
				// They think they are smart, lets see if there are really no moves on the board
				opponentBoard.getValidMoves();
				if (opponentBoard.getValidMoves().size() == 0) {
					// well they were smart, lets break this method
					receivedValidMove = true;
				}
			} else if (enteredValue.equals("W")) {
				// They think they are smart, lets see if there are really no moves on the board
				opponentBoard.getValidMoves();
				if (opponentBoard.getValidMoves().size() == 0) {
					// well they were smart, lets break this method
					receivedValidMove = true;
				}
			} else {
				System.out.println("C Invalid input! (Example of input: W a 2)");
			}
		}
	}
	
	private static int rowColumnToInteger(String rawRow, int rawColumn) {
		int cellValue = 0;

		for (int i = 0; i < 8; i++) {
			if (letterArray[i].contentEquals(rawRow)) {
				cellValue = Integer.parseInt(rawColumn + "" + (i + 1));
			}
		}

		return cellValue;
	}
	
	public static void updateBoard(Board currentBoard, int initialCell, int finalCell, int origOffset, boolean whosColor) {

		// put that thing where moveToMake says or so help me codd.

//		Board aBoard = new Board();
		
		int[] theBoard = currentBoard.getGameBoard();
		
		if (finalCell > initialCell) {
			while (finalCell >= initialCell) {
				if (whosColor) { // they are playing as black
					theBoard[initialCell] = 1;
//					System.out.println("updated");
				} else { // they are playing as white
					theBoard[initialCell] = -1;
//					System.out.println("updated");
				}
				initialCell -= origOffset;
			}
		} else {
			while (initialCell >= finalCell) {
				if (whosColor) { // they are playing as black
					theBoard[initialCell] = 1;
//					System.out.println("updated");
				} else { // they are playing as white
					theBoard[initialCell] = -1;
//					System.out.println("updated");
				}
				finalCell += origOffset;
			}
		}
		
		currentBoard.setGameBoard(theBoard);
	}

	/**
	 * Well this is where it all starts.
	 * 
	 * Regarding the comments, they may be extremely formal or literal screaming, there is no in-between.
	 * 
	 * Strangely the more hectic I get with the comments (screaming) the more efficient a portion of the 
	 *  code might get...
	 */
	
	public static void main(String[] args) {

		String printModeText = "";
		String playModeText = "";
		
		if (args.length == 2) {
			setPrintMode(Integer.parseInt(args[0]));
			setPlayMode(Integer.parseInt(args[1]));
			
		}
		
		switch (printMode) {
			default : printModeText = "only required information"; break;
			case 1 : printModeText = "only the board and required information"; break;
			case 2 : printModeText = "only the valid moves & required information"; break;
			case 3:	printModeText = "everything"; break;
		}
		
		System.out.println("C Currently printing " + printModeText + ".");
		
		switch (playMode) {
			default : playModeText = "first move generated";break;
			case 1 : playModeText = "random moves"; break;
			case 2 : playModeText = "simple maximum disk strategy"; break;
			case 3 : playModeText = "complex maximum disk strategy"; break;
			case 4 : playModeText = "weighted board"; break;	
			case 5 : playModeText = "complex maximum disk strategy & weighted board"; break;
			case 6 : playModeText = "alpha/beta"; break;	
		}
					
		System.out.println("C Current playstyle is " + playModeText + ".");
		
		while (!gameOver) {
			userInput();
		}
		
		if (gameOver) {
			System.out.println("C This is the end game *snap*.");
		}
	}	
	
	public static void setPrintMode(int printMode) {
		Driver.printMode = printMode;
	}

	public static void setPlayMode(int playMode) {
		Driver.playMode = playMode;
	}


}
