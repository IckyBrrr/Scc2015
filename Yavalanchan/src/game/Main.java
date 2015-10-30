package game;

import java.util.Scanner;

public class Main {
	
	public static final byte DNE = -1; // Tile doesn't exist
	public static final byte EMPTY = 0; // Empty slot
	public static final byte PLAYER_A = 1; // Player A
	public static final byte PLAYER_B = 2; // Player B
	public static final byte NEUTRAL = 3; // Neutral
	
	public static final int SIZE = 4;
	
	public static Scanner in;
	public static Board board;
	
	public static boolean isRunning;
	
	public static void main(String[] args) {

		in = new Scanner(System.in);
		board = new Board(SIZE);
		isRunning = true;
		
		render();
		
		while(isRunning) {
			update();
			render();
		}
	}
	
	public static void update() {
		int x;
		int y;
		String input;
		TileState occupant = TileState.EMPTY;
		boolean canMoveOn;
		do {
			do {
				System.out.println("What X?");
				x = in.nextInt() - 1;
				if((x < SIZE * 2 - 1) && (x >= 0)) {
					canMoveOn = true;
				} else {
					System.out.println("X doesn't exist");
					canMoveOn = false;
				}
			} while(!canMoveOn);
			
			canMoveOn = false;
			
			do {
				System.out.println("What Y?");
				y = in.nextInt() - 1;
				if((y < board.columns[x].tiles.length) && (y >= 0)) {
					canMoveOn = true;
				} else {
					System.out.println("Y doesn't exist");
					canMoveOn = false;
				}
			} while(!canMoveOn);
		
			canMoveOn = false;
		
			System.out.println("What Color?");
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
			} else {
				canMoveOn = board.occupy(occupant, x, y);
			}
		} while(!canMoveOn);
		int tempx = 4, tempy = 4;
		System.out.printf("Owner for (%d, %d) is : %s\n", tempx, tempy, board.getTile(tempx, tempy).getOwner().toString());
	}
	
	public static void render() {
		board.print();
	}
	
	public static void print(String str) {
		System.out.println(str);
	}
}