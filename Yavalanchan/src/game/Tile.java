package game;

public class Tile {
	
	private boolean isTaken;
	private int xCor, yCor;
	private TileState state;
	private TileState owner;
	
	/**
	 * Default constructor
	 * @param x The x coordinate of the tile
	 * @param y The y coordinate of the tile
	 */
	public Tile(int x, int y) {
		isTaken = false;
		state = TileState.EMPTY;
		owner = TileState.EMPTY;
		xCor = x;
		yCor = y;
	}
	
	/**
	 * Attempts to add an occupant to this space
	 * @param occupant The color tile that you want to occupy the space with
	 * @param board The board that holds the space
	 * @return Whether the occupy attempt was successful
	 */
	public boolean occupy(TileState occupant, Board board) {
		boolean isLegal = true;
		
		if(isTaken) {
			System.out.println("Space occupied");
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
				System.out.println("No adjacent neutral");
				isLegal = false;
			}
		}
		
		if(isLegal) {
			state = occupant;
			isTaken = true;
		}
		return isLegal;
	}
	
	// Getters and setters
	public int getX() { return xCor; }
	public int getY() { return yCor; }
	public TileState getOccupant() { return state; }
	public TileState getOwner() { return owner; }
	public void setOwner(TileState willOwn) { owner = willOwn; }
	public boolean isTaken() { return isTaken; }
}