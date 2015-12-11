package game;

import java.util.ArrayList;

/*
 * A visual explanation of the coordinate system for the board (as the code sees it), using a 3 x 3 hex grid
 * 
 * y's
 *           4            
 *      3         4       
 * 2         3         4  
 *      2         3       
 * 1         2         3  
 *      1         2       
 * 0         1         2  
 *      0         1       
 *           0            
 * x's
 * 0    1    2    3    4
 * 
 * A visual explanation of the coordinate system for the board (as the player sees it), using a 3 x 3 hex grid
 * 
 * y's
 *           5            
 *      4         4       
 * 3         4         3  
 *      3         3       
 * 2         3         2  
 *      2         2       
 * 1         2         1  
 *      1         1       
 *           1            
 * x's
 * 1    2    3    4    5
 */

public class Board {

	private Column[] columns;
	
	/**
	 * Default Constructor
	 * @param size The side lengths of the hexagonal grid
	 */
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
			
			columns[i] = new Column(y, x, i + 1, offset);
		}
	}
	
	/**
	 * Prints the board to the console
	 */
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
		
		// Sets up the coordinate system for the board
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
	
	/**
	 * Attempts to add an occupant to a space on the board
	 * @param occupant The color tile you want to occupy the space with
	 * @param x The x coordinate of the space
	 * @param y The y coordinate of the space
	 * @return Whether the occupy was successful
	 */
	public boolean occupy(TileState occupant, int x, int y) {
		boolean isSuccessful = columns[x].tiles[y].occupy(occupant, this);
		if(isSuccessful) {
			
			// Changes the player coordinates to the program coordinates
			int tileX = columns[x].tiles[y].getX();
			int tileY = columns[x].tiles[y].getY();
			
			if(occupant == TileState.WHITE || occupant == TileState.BLACK) {
				
				// Update all adjacent neutral tiles
				Tile[] adjacentTiles = getAdjacentTiles(tileX, tileY);
				for(int i = 0; i < adjacentTiles.length; i++) {
					if(adjacentTiles[i].getOccupant() == TileState.NEUTRAL) {
						updateOwnership(adjacentTiles[i].getX(), adjacentTiles[i].getY());
					}
				}
			} else if(occupant == TileState.NEUTRAL) {
				// Update this tile
				updateOwnership(tileX, tileY);
			}
		}
		return isSuccessful;
	}
	
	/**
	 * Changes the ownership of a neutral space in accordance to neutral tiles, if necessary
	 * @param x The x of the space
	 * @param y The y of the space
	 */
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
	
	/**
	 * Checks if two tiles are adjacent
	 * @param x1 The x of the first space
	 * @param y1 The y of the first space
	 * @param x2 The x of the second space
	 * @param y2 The y of the second space
	 * @return Whether or not the two tiles are adjacent
	 */
	public boolean isAdjacent(int x1, int y1, int x2, int y2) {
		return((x1 == x2 && Math.abs(y1 - y2) == 1) ||
			   (Math.abs(x1 - x2) == 1 && Math.abs(y1 - y2) == 1) ||
			   (Math.abs(x1 - x2) == 1 && y1 == y2));
	}
	
	/**
	 * Gets a tile with the given coordinates
	 * @param xCor The x coordinate of the tile
	 * @param yCor The y coordinate of the tile
	 * @return The tile at the given coordinates
	 */
	public Tile getTile(int x, int y) {
		for(int column = 0; column < columns.length; column++) {
			for(int row = 0; row < columns[column].tiles.length; row++) {
				if(columns[column].tiles[row].getX() == x &&
					columns[column].tiles[row].getY() == y) {
					return columns[column].tiles[row];
				}
			}
		}
		return new Tile(-1, -1, -1, -1);
	}
	
	/**
	 * Returns a list of all the tiles adjacent to a given tile
	 * @param x The x of the tile
	 * @param y The y of the tile
	 * @return A list of adjacent tiles
	 */
	public Tile[] getAdjacentTiles(int x, int y) {
		Tile[] adjacent = new Tile[6];
		int tempX, tempY;
		for(int i = 0; i < adjacent.length; i++) {
			switch(i) {
			case 0:
				tempX = x;
				tempY = y + 1;
				break;
			case 1:
				tempX = x;
				tempY = y - 1;
				break;
			case 2:
				tempX = x - 1;
				tempY = y;
				break;
			case 3:
				tempX = x - 1;
				tempY = y - 1;
				break;
			case 4:
				tempX = x + 1;
				tempY = y + 1;
				break;
			case 5:
				tempX = x + 1;
				tempY = y;
				break;
			default:
				tempX = 1;
				tempY = 1;
			}
			adjacent[i] = getTile(tempX, tempY);
		}
		return adjacent;
	}
	
	public ArrayList<Chain> findChains() {
		
		ArrayList<Chain> chains = new ArrayList<Chain>();
		
		for(Column cols : columns) {
			for(Tile tile : cols.tiles) {
				if(tile.getOwner() != TileState.EMPTY) {
					Tile[] temps = getAdjacentTiles(tile.getX(), tile.getY());
					
					// Cycle through the adjacent tiles
					for(Tile adjacent : temps) {
						// If one matches, start searching for chains
						if(adjacent.getOwner() == tile.getOwner()) {
							
							Chain tempChain = new Chain(tile.getOwner());
							
							int dy = adjacent.getY() - tile.getY(); // Delta X
							int dx = adjacent.getX() - tile.getX(); // Delta Y
							int tx = tile.getX();
							int ty = tile.getY();
							
							boolean chainContinues = false;
							
							// Search for a start point in one direction
							do {
								tx += dx;
								ty += dy;
								if(getTile(tx, ty).getOwner() == tile.getOwner()) {
									chainContinues = true;
								} else {
									tempChain.setStart(tx - dx, ty - dy);
									chainContinues = false;
								}
							} while(chainContinues);
							tx = tile.getX();
							ty = tile.getY();
							
							// Search for an end point in the other direction
							do {
								tx -= dx;
								ty -= dy;
								if(getTile(tx, ty).getOwner() == tile.getOwner()) {
									chainContinues = true;
								} else {
									tempChain.setEnd(tx + dx, ty + dy);
									chainContinues = false;
								}
							} while(chainContinues);
							
							// Switch start and end points if necessary
							tempChain.rectify();
							
							// Add to list of chains
							boolean canAdd = true;
							if(chains.size() > 0) {
								for(Chain c : chains) {
									if((c.getStartX() == tempChain.getStartX() &&
									   c.getStartY() == tempChain.getStartY() &&
									   c.getEndX() == tempChain.getEndX() &&
									   c.getEndY() == tempChain.getEndY()))
										canAdd = false;
								}
							} else {
								canAdd = true;
							}
							
							if(canAdd)
								chains.add(tempChain);
						}
					}
				}
			}
		}
		
		return chains;
	}
	
	// Getters
	public Column[] getColumns() { return columns; }
}