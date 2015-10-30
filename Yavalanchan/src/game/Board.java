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
		
		int spacing = 2;
		String blank = " ";
		String buffer = "";
		for(int i = 0; i < spacing; i++) buffer += blank;
		
		// Initialize output
		int xMax = 2 * Main.SIZE - 1;
		int yMax = 4 * Main.SIZE - 3;
		String[][] output = new String[xMax][yMax];
		for(int x = 0; x < xMax; x++) {
			for(int y = 0; y < yMax; y++) {
				output[x][y] = blank;
			}
		}
		
		// Yes
		for(int x = 0; x < columns.length; x++) {
			for(int y = 0; y < columns[x].tiles.length; y++) {
				int offset = Math.abs((Main.SIZE - 1) - x);
				int yCor = ((y + 1) * 2 - 1) + offset - 1;
				
				output[x][yCor] = columns[x].tiles[y].getOccupant().toString().substring(0, 1);
			}
		}
		
		// Print output
		for(int y = yMax - 1; y >= 0; y--) {
			for(int x = 0; x < xMax; x++) {
				System.out.print(buffer + output[x][y] + buffer);
			}
			System.out.print("\n");
		}
	}
	
	public boolean occupy(TileState occupant, int x, int y) {
		boolean isSuccessful = columns[x].occupy(occupant, y, this);
		if(isSuccessful) {
			int[] coor = columns[x].tiles[y].getCoor();
			System.out.printf("(%d, %d)\n", coor[0], coor[1]);
			if(occupant == TileState.WHITE || occupant == TileState.BLACK) {
				// update all adjacent neutral tiles
				Tile[] adjacentTiles = getAdjacentTiles(coor[0], coor[1]);
				for(int i = 0; i < adjacentTiles.length; i++) {
					if(adjacentTiles[i].getOccupant() == TileState.NEUTRAL) {
						updateOwnership(adjacentTiles[i].getX(), adjacentTiles[i].getY());
					}
				}
			} else if(occupant == TileState.NEUTRAL) {
				// update this tile
				updateOwnership(coor[0], coor[1]);
			}
		}
		return isSuccessful;
	}
	
	private void updateOwnership(int x, int y) {
		int numWhite = 0, numBlack = 0;
		Tile[] adjacentTiles= getAdjacentTiles(x, y);
		for(Tile temp : adjacentTiles) {
			if(temp.getOccupant() == TileState.WHITE) numWhite++;
			else if(temp.getOccupant() == TileState.BLACK) numBlack++;
		}
		if(numWhite >= 2 || numBlack >= 2) {
			if(numWhite > numBlack) {
				getTile(x, y).setOwner(TileState.WHITE);
			} else if(numBlack > numWhite) {
				getTile(x, y).setOwner(TileState.BLACK);
			} else {
				getTile(x, y).setOwner(TileState.NEUTRAL);
			}
		} else {
			getTile(x, y).setOwner(TileState.NEUTRAL);
		}
	}
	
	public boolean isAdjacent(int[] coor1, int[] coor2) {
		int x1 = coor1[0];
		int y1 = coor1[1];
		int x2 = coor2[0];
		int y2 = coor2[1];
		return((x1 == x2 && Math.abs(y1 - y2) == 1) ||
			   (Math.abs(x1 - x2) == 1 && Math.abs(y1 - y2) == 1) ||
			   (Math.abs(x1 - x2) == 1 && y1 == y2));
	}
	
	public Tile getTile(int xCor, int yCor) {
		for(int x = 0; x < columns.length; x++) {
			for(int y = 0; y < columns[x].tiles.length; y++) {
				if(columns[x].tiles[y].getX() == xCor &&
					columns[x].tiles[y].getY() == yCor) {
					return columns[x].tiles[y];
				}
			}
		}
		return new Tile(-1, -1);
	}
	
	public Tile[] getAdjacentTiles(int x, int y) {
		Tile[] adjacent = new Tile[6];
		int[] temp = new int[2];
		for(int i = 0; i < adjacent.length; i++) {
			switch(i) {
			case 0:
				temp[0] = x;
				temp[1] = y + 1;
				break;
			case 1:
				temp[0] = x;
				temp[1] = y - 1;
				break;
			case 2:
				temp[0] = x - 1;
				temp[1] = y;
				break;
			case 3:
				temp[0] = x - 1;
				temp[1] = y - 1;
				break;
			case 4:
				temp[0] = x + 1;
				temp[1] = y + 1;
				break;
			case 5:
				temp[0] = x + 1;
				temp[1] = y;
				break;
			default:
				temp[0] = 1;
				temp[1] = 1;
			}
			adjacent[i] = getTile(temp[0], temp[1]);
		}
		return adjacent;
	}
}