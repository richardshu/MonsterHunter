package monster_hunter;

import java.io.Serializable;

/**
 * This class represents a Room that has three properties: row, column, and type.
 * The row and column indicate the position of the room on a grid of rooms. The 
 * type of the room indicates what occupies the room. Possible types include:
 * 
 * ------------------------------
 * | 'B' stands for BOWSER      |
 * | 'F' stands for FIRE FLOWER |
 * | 'G' stands for GUMBA       |
 * | 'M' stands for MARIO       |
 * | 'S' stands for SPIKE       |
 * | ' ' stands for EMPTY       |
 * ------------------------------ 
 * 
 * @author Richard Shu
 * @version 1.0
 */
public class Room implements Serializable {
	private int row;
	private int col;
	private char type;
	
	/**
	 * Instantiates the room.
	 * 
	 * @param row an int that denotes the row #
	 * @param col an int that denotes the column #
	 * @param type a char that denotes what occupies the room
	 */
	public Room(int row, int col, char type) {
		setRow(row);
		setCol(col);
		setType(type);
	}
	
	/**
	 * Gets the row number of the room.
	 * 
	 * @return an int denoting the row number of the room
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Gets the column number of the room.
	 * 
	 * @return an int denoting the column number of the room
	 */
	public int getCol() {
		return col;
	}
	
	/**
	 * Gets the room type (also known as what occupies the room).
	 * 
	 * @return a char denoting the room type
	 */
	public char getType() {
		return type;
	}
	
	/**
	 * Sets the row number of a room.
	 * 
	 * @param row the row number that the room is set to
	 */
	public void setRow(int row) {
		this.row = row;
	}
	
	/**
	 * Sets the column number of a room.
	 * 
	 * @param col the column number that the room is set to
	 */
	public void setCol(int col) {
		this.col = col;
	}
	
	/**
	 * Sets the type of a room.
	 * 
	 * @param type the room type that the room is set to.
	 */
	public void setType(char type) {
		this.type = type;
	}
}