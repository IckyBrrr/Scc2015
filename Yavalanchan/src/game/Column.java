package game;

public class Column {

	public Tile[] tiles;
	
	public Column(int size, int x, int offset) {
		tiles = new Tile[size];
		for(int i = 0; i < tiles.length; i++) {
			tiles[i] = new Tile(x, i + offset + 1);
		}
	}
	
	public void print() {
		for(int i = 0; i < tiles.length; i++) {
			System.out.print(tiles[i].getY());
		}
		System.out.print("\n");
	}
	
	public boolean occupy(TileState occupant, int y, Board board) {
		return tiles[y].occupy(occupant, board);
	}
}