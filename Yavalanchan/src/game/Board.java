package game;

public class Board {

	// TODO: change to private and add getter/setter functions
	public Column[] columns;
	
	public Board(int size) {

		int maxLength = 2 * size - 1;
		columns = new Column[maxLength];
		
		/*
		 * Creates a hexagonal board with size n using the equation
		 * y = -|x - n| + 2n - 1
		 */
		for(int i = 0; i < columns.length; i++) {
			int x = i + 1;
			int y = -1 * Math.abs(x - size) + maxLength;
			
			int offset;
			if(x <= size) offset = 0;
			else offset = x - size;
			
			columns[i] = new Column(y, x, offset);
		}
	}
	
	public void print() {
		for(int i = 0; i < columns.length; i++) {
			columns[i].print();
		}
	}
	
	public boolean occupy(byte color, int x, int y) {
		
		return columns[x].occupy(color, y);
	}
}