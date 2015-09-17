package game;

import java.util.Scanner;

public class Main {
	
	public static final byte DNE = -1; // Tile doesn't exist
	public static final byte EMPTY = 0; // Empty slot
	public static final byte PLAYER_A = 1; // Player A
	public static final byte PLAYER_B = 2; // Player B
	public static final byte NEUTRAL = 3; // Neutral
	
	public static final int SIZE = 2;
	
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
		byte color;
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
			color = in.nextByte();
			if(color == EMPTY) {
				isRunning = false;
				break;
			}
			if(!(color == NEUTRAL || color == PLAYER_A || color == PLAYER_B)) {
				System.out.println("Invalid color");
			} else {
				canMoveOn = board.occupy(color, x, y);
			}
		} while(!canMoveOn);
	}
	
	public static void render() {
		board.print();
	}
	
	public static void print(String str) {
		System.out.println(str);
	}
}