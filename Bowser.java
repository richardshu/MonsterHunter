package monster_hunter;

import java.io.Serializable;

/**
 * This class represents Bowser, the monster that eats Mario.
 * 
 * @author Richard Shu
 * @version 1.0
 * @see Room
 */
public class Bowser extends Room implements Serializable {
	
	/**
	 * Instantiates Bowser with the row number and column number where he spawns.
	 * 
	 * @param row row number of Bowser
	 * @param col column number of Bowser
	 */
	public Bowser(int row, int col) {
		super(row, col, 'B');
	}
}