package game;

public class Tile {
	
	private boolean isTaken;
	private int[] coor = new int[2];
	private TileState state;
	
	public Tile(int x, int y) {
		isTaken = false;
		state = TileState.EMPTY;
		coor[0] = x;
		coor[1] = y;
	}
	
	// TODO: Single entry, single exit
	public boolean occupy(TileState occupant, Board board) {
		if(isTaken) {
			System.out.println("Space occupied");
			return false;
		}
		
		if(occupant == TileState.WHITE || occupant == TileState.BLACK) {
			boolean canPlace = false;
			Tile[] temp = board.getAdjacentTiles(coor);
			for(int i = 0; i < temp.length; i++) {
				if(temp[i].getOccupant() == TileState.NEUTRAL)
					canPlace = true;
			}
			if(!canPlace) {
				System.out.println("No adjacent neutral");
				return false;
			}
		}
		
		state = occupant;
		isTaken = true;
		return true;
	}
	
	public int[] getCoor() { return coor; }
	public int getX() { return coor[0]; }
	public int getY() { return coor[1]; }
	public TileState getOccupant() { return state; }
	public boolean isTaken() { return isTaken; }
}