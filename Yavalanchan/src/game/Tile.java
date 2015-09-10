package game;

public class Tile {
	
	private boolean isTaken;
	private byte occupant;
	private int[] coor = new int[2];
	
	public Tile(int x, int y) {
		isTaken = false;
		occupant = Main.EMPTY;
		int[] coor = {x, y};
	}
	
	public boolean occupy(byte color) {
		if(isTaken) return false;
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