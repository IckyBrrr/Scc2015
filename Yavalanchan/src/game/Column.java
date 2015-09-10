package game;

public class Column {

	public Tile[] tiles;
	
	public Column(int size, int x) {
		tiles = new Tile[size];
		for(int i = 0; i < tiles.length; i++) {
			tiles[i] = new Tile(x, i);
		}
	}
	
	public void print() {
		for(int i = 0; i < tiles.length; i++) {
			System.out.print(tiles[i].getOccupant());
		}
		System.out.print("\n");
	}
}