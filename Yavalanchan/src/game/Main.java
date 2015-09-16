package game;

import java.util.Scanner;

public class Main {
	
	public static final byte EMPTY = 0; // Empty slot
	public static final byte WHITE = 1; // Player A
	public static final byte BLACK = 2; // Player B
	public static final byte RED = 3; // Neutral
	
	public static final int SIZE = 3;
	
	public static Scanner in;
	public static Board board;
	
	public static boolean isRunning;
	
	public static void main(String[] args) {

		in = new Scanner(System.in);
		board = new Board(SIZE);
		isRunning = true;
		
		render();
		
		/*while(isRunning) {
			update();
			render();
		}*/
	}
	
	public static void update() {
		int x;
		int y;
		byte color;
		boolean canMoveOn;
		
		do {
			print("What coordinate? (color, x, y)");
			color = in.nextByte();
			if(color == EMPTY) {
				isRunning = false;
				break;
			}
			x = in.nextInt();
			y = in.nextInt();
			System.out.println(color + " " + x + " " + y);
			canMoveOn = board.occupy(color, x, y);
			if(!canMoveOn)  print("That space is occupied!");
		} while(!canMoveOn);
	}
	
	public static void render() {
		board.print();
	}
	
	public static void print(String str) {
		System.out.println(str);
	}
}