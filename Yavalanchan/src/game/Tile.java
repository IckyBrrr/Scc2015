package game;

public class Tile {
	
	private boolean isTaken;
	private byte occupant;
	private int[] coor = new int[2];
	
	public Tile(int x, int y) {
		isTaken = false;
		occupant = Main.EMPTY;
		coor[0] = x;
		coor[1] = y;
	}
	
	public boolean occupy(byte color, Board board) {
		if(isTaken) {
			System.out.println("Space occupied");
			return false;
		}
		
		if(color == Main.PLAYER_A || color == Main.PLAYER_B) {
			boolean canPlace = false;
			Tile[] temp = board.getAdjacentTiles(coor);
			for(int i = 0; i < temp.length; i++) {
				if(temp[i].getOccupant() == Main.NEUTRAL)
					canPlace = true;
			}
			if(!canPlace) {
				System.out.println("No adjacent neutral");
				return false;
			}
		}
		
		occupant = color;
		isTaken = true;
		return true;
	}
	
	public int[] getCoor() { return coor; }
	public int getX() { return coor[0]; }
	public int getY() { return coor[1]; }
	public byte getOccupant() { return occupant; }
	public boolean isTaken() { return isTaken; }
}