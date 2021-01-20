package monster_hunter;

import java.io.Serializable;

/**
 * This class represents Mario, the character that the player controls.
 * 
 * @author Richard Shu
 * @version 1.0
 * @see Room
 */
public class Mario extends Room implements Serializable {
	private String weapon = "fire ball";
	
	/**
	 * Instantiates Mario with the row number and column number where he spawns.
	 * 
	 * @param row row number of Mario
	 * @param col column number of Mario
	 */
	public Mario(int row, int col) {
		super(row, col, 'M');
	}
	
	/**
	 * Gets the weapon of Mario.
	 * 
	 * @return a String that represents the weapon
	 */
	public String getWeapon() {
		return weapon;
	}
}