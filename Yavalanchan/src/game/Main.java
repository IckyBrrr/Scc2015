package game;

import java.util.Scanner;

public class Main {

	public static final int SIZE = 4;
	public static final int MIN_VAL = 0;

	public static Scanner in;
	public static Board board;

	public static boolean isRunning;
	public static int turn;

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

	public static void update() {
		Integer x = null, y = null;
		String input;
		TileState occupant = TileState.EMPTY;
		boolean canMoveOn = false;;

		do {
			//TODO: keep valid values
			System.out.print("What is your move? (x y tile) : ");

			if(in.hasNextInt()) {

				x = in.nextInt() - 1;

				if(x < MIN_VAL || x > board.columns.length) {
					System.out.printf("X needs to be in between %d and %d\n", MIN_VAL + 1, board.columns.length);
					x = null;
				}
			} else {

				System.out.println("X needs to be an integer");
				in.next();
			}

			if(x != null) {
				if(in.hasNextInt()) {

					y = in.nextInt() - 1;

					if(y < MIN_VAL || y > board.columns[x].tiles.length) {

						System.out.printf("y needs to be in between %d and %d\n", MIN_VAL + 1, board.columns[x].tiles.length);
						y = null;
					}
				} else {

					System.out.println("Y needs to be an integer");
					in.next();
				}
			} else {

				in.next();
			}

			if(x != null && y != null) {
				input = in.next();
				input = input.toLowerCase();
				System.out.println(input);
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
			} else {
				canMoveOn = false;
				in.next(); //TODO: account for more inputs than intended
			}
		} while(!canMoveOn);

		//int tempx = 4, tempy = 4;
		//System.out.printf("Owner for (%d, %d) is : %s\n", tempx, tempy, board.getTile(tempx, tempy).getOwner().toString());
		turn++;
	}

	public static void render() {
		System.out.printf("Turn %d:\n", turn);
		board.print();
	}

	public static void print(String str) {
		System.out.println(str);
	}
}