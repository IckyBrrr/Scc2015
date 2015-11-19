package game;

public class Column {

	public Tile[] tiles;
	
	/**
	 * Default Constructor
	 * @param size The y size of the column
	 * @param x The x coordinate of the column
	 * @param offset The coordinate offset for the columns
	 */
	public Column(int size, int x, int offset) {
		tiles = new Tile[size];
		for(int i = 0; i < tiles.length; i++) {
			tiles[i] = new Tile(x, i + offset + 1);
		}
	}
	
	/**
	 * Attempts to add an occupant to a space in the column
	 * @param occupant The color tile that you want to occupy the space with
	 * @param y The y coordinate of the space
	 * @param board The board that holds the space
	 * @return Whether the occupy attempt was successful
	 */
	public boolean occupy(TileState occupant, int y, Board board) {
		return tiles[y].occupy(occupant, board);
	}
}