package game;

public class Chain {

	private int startX, startY, endX, endY;
	private TileState owner;
	
	public Chain(TileState owner) {
		startX = 0;
		startY = 0;
		endX = 0;
		endY = 0;
		this.owner = owner; 
	}
	
	public int getLength() {
		int length = 0;
		if(startX - endX != 0) {
			length = endX - startX;
		} else {
			length = endY - startY;
		}
		
		return length + 1;
	}
	
	public void setStart(int x, int y) {
		startX = x;
		startY = y;
	}
	public void setEnd(int x, int y) {
		endX = x;
		endY = y;
	}

	public void rectify() {
		if(endX < startX || endY < startY) {
			int temp = startX;
			startX = endX;
			endX = temp;
			temp = startY;
			startY = endY;
			endY = temp;
		}
	}
	
	public void printChain() {
		System.out.printf("chain starting at (%d, %d) ending at (%d, %d) with length %d owned by %s\n", 
						   startX, startY, endX, endY, getLength(), owner.toString().toLowerCase());
	}

	public int getStartX() { return startX; }
	public int getStartY() { return startY; }
	public int getEndX() { return endX; }
	public int getEndY() { return endY; }
	public TileState getOwner() { return owner; }
}
