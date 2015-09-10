package game;

public class Main {
	
	public static final byte EMPTY = 0; // Empty slot
	public static final byte WHITE = 1; // Player A
	public static final byte BLACK = 2; // Player B
	public static final byte RED = 3; // Neutral
	
	public static void main(String[] args) {

		Board board = new Board(4);
		board.print();
	}

}