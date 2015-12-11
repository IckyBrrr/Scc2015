package game;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	public static final int SIZE = 5;
	public static final int MIN_VAL = 0;

	public static Scanner in;
	public static Board board;

	public static boolean isRunning;
	public static int turn;
	
	private static TileState player = TileState.WHITE;

	public static void main(String[] args) {

		in = new Scanner(System.in);
		board = new Board(SIZE);
		isRunning = true;
		turn = 0;

		render();

		while(isRunning) {
			update();
			render();
		}
	}

	/**
	 * Steps the game one turn
	 */
	public static void update() {
		
		ArrayList<String> possibleMoves = getPossibleMoves(board, player);
		int i = 0;
		System.out.println("Possible moves :");
		for(String move : possibleMoves) {
			System.out.printf("Move %d : %s\n", i, move);
			i++;
		}
		
		Integer x = null, y = null;
		String input;
		TileState occupant = TileState.EMPTY;
		boolean canMoveOn = false;;
		
		if(turn %2 == 0) {
			player = TileState.WHITE;
		} else {
			player = TileState.BLACK;
		}

		// Input collection
		do {
			//TODO: keep valid values
			System.out.print("What is your move? (x y tile) : ");

			// Checks that the first inputed value is an integer
			if(in.hasNextInt()) {

				x = in.nextInt() - 1;

				// Checks that the x inputed is valid
				if(x < MIN_VAL || x > board.getColumns().length) {
					System.out.printf("X needs to be in between %d and %d\n", MIN_VAL + 1, board.getColumns().length);
					x = null;
				}
			} else {

				System.out.println("X needs to be an integer");
				in.next();
			}
			
			// Collects Y input
			if(x != null) {
				
				// Checks that the second inputed value is an integer
				if(in.hasNextInt()) {

					y = in.nextInt() - 1;

					// Checks that the y inputed is valid
					if(y < MIN_VAL || y > board.getColumns()[x].tiles.length) {

						System.out.printf("y needs to be in between %d and %d\n", MIN_VAL + 1, board.getColumns()[x].tiles.length);
						y = null;
					}
				} else {

					System.out.println("Y needs to be an integer");
					in.next();
				}
			} else {

				in.next();
			}

			// Collects the tile color
			if(x != null && y != null) {
				
				input = in.next();
				input = input.toLowerCase();
				
				if(input.charAt(0) == 'q') {
					isRunning = false;
					break;
				} else if(input.charAt(0) == 'a' || input.charAt(0) == 'w') {
					occupant = TileState.WHITE;
				} else if(input.charAt(0) == 'b' || input.charAt(0) == 'b') {
					occupant = TileState.BLACK;
				} else if(input.charAt(0) == 'c' || input.charAt(0) == 'n') {
					occupant = TileState.NEUTRAL;
				}
				if(!(occupant == TileState.NEUTRAL || occupant == TileState.WHITE || occupant == TileState.BLACK)) {
					System.out.println("Invalid color");
				} else if ((player == TileState.WHITE && occupant == TileState.BLACK) || (player == TileState.BLACK && occupant == TileState.WHITE)) {
					System.out.println("It's not your turn!");
				} else {
					canMoveOn = board.occupy(occupant, x, y);
					
					if(!canMoveOn) {
						System.out.println("Invalid Move");
					}
				}
			} else {
				canMoveOn = false;
				in.next(); //TODO: account for more inputs than intended
			}
		} while(!canMoveOn);

		ArrayList<Chain> chains = board.findChains();
		for(Chain c : chains) {
			if(c.getLength() == SIZE) {
				System.out.print("Game won with ");
				c.printChain();
				isRunning = false;
			}
		}
		
		turn++;
	}
	
	/**
	 * Returns all possible moves to be made at the current board state
	 * @param tempBoard The board the moves will be checked against
	 * @param tempPlayer The player whose turn it is
	 * @return An array of possible moves
	 */
	public static ArrayList<String> getPossibleMoves(Board tempBoard, TileState tempPlayer) {
		ArrayList<String> possibleMoves = new ArrayList<String>();
		
		for(Column c : tempBoard.getColumns()) {
			for(Tile tile : c.tiles) {
				
				if(tile.canOccupy(tempPlayer, tempBoard)) {
					possibleMoves.add(tile.getPlayerX() + " " + tile.getPlayerY() + " " + tempPlayer.toString().charAt(0));
				} 
				if(tile.canOccupy(TileState.NEUTRAL, tempBoard)) {
					possibleMoves.add(tile.getPlayerX() + " " + tile.getPlayerY() + " " + TileState.NEUTRAL.toString().charAt(0));
				}
			}
		}
		
		return possibleMoves;
	}

	/**
	 * Prints the output for the game
	 */
	public static void render() {
		String line = "";
		for(int i = 0; i < 2 * SIZE - 1; i++) {
			line += "-----";
		}
		System.out.println(line);
		if(isRunning)
			System.out.printf("Turn %d | %s\n", turn, player.toString());
		else
			System.out.printf("Turn %d | End state\n", turn);
		board.print();
	}
}