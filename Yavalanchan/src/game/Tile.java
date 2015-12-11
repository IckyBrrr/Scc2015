package game;

public class Tile {
	
	private boolean isTaken;
	private int xCor, yCor;
	private int pX, pY;
	private TileState state;
	private TileState owner;
	
	/**
	 * Default constructor
	 * @param x The x coordinate of the tile
	 * @param y The y coordinate of the tile
	 */
	public Tile(int x, int y, int pX, int pY) {
		isTaken = false;
		state = TileState.EMPTY;
		owner = TileState.EMPTY;
		xCor = x;
		yCor = y;
		this.pX = pX;
		this.pY = pY;
	}
	
	/**
	 * Adds a tile to the board if the move is legal
	 * @param occupant The color tile that you want to occupy the space with
	 * @param board The board that holds the space
	 * @return Whether the occupy attempt was successful
	 */
	public boolean occupy(TileState occupant, Board board) {
		boolean isSuccess = false;
		
		if(canOccupy(occupant, board)) {
			state = occupant;
			if(occupant != TileState.NEUTRAL) owner = occupant;
			isTaken = true;
			isSuccess = true;
		}
		
		return isSuccess;
	}
	
	/**
	 * Returns if a move is legal
	 * @param occupant The color tile that you want to occupy the space with
	 * @param board The board that holds the space
	 * @return Whether the occupy was successful
	 */
	public boolean canOccupy(TileState occupant, Board board) {
		boolean isLegal = true;
		
		if(isTaken) {
			isLegal = false;
		}
		
		if((occupant == TileState.WHITE || occupant == TileState.BLACK) && isLegal) {
			boolean canPlace = false;
			Tile[] temp = board.getAdjacentTiles(xCor, yCor);
			for(int i = 0; i < temp.length; i++) {
				if(temp[i].getOccupant() == TileState.NEUTRAL)
					canPlace = true;
			}
			if(!canPlace) {
				isLegal = false;
			}
		}
		return isLegal;
	}
	
	// Getters and setters
	public int getX() { return xCor; }
	public int getY() { return yCor; }
	
	public int getPlayerX() { return pX; }
	public int getPlayerY() { return pY; }
	
	public TileState getOccupant() { return state; }
	public TileState getOwner() { return owner; }
	public void setOwner(TileState willOwn) { owner = willOwn; }
	public boolean isTaken() { return isTaken; }
}