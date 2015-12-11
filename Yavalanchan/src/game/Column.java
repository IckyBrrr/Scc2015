package game;

public class Column {

	public Tile[] tiles;
	
	/**
	 * Default Constructor
	 * @param size The y size of the column
	 * @param x The x coordinate of the column
	 * @param offset The coordinate offset for the columns
	 */
	public Column(int size, int x, int pX, int offset) {
		tiles = new Tile[size];
		for(int i = 0; i < tiles.length; i++) {
			tiles[i] = new Tile(x, i + offset + 1, pX, i + 1);
		}
	}
}