package monster_hunter;

import java.io.Serializable;
import java.util.Random;

/**
 * This class represents a CaveMap which is made up of a grid of rooms
 * 
 * @author Richard Shu
 * @version 1.0
 * @see Room
 */
public class CaveMap implements Serializable {
	private int gameSize;
	private int totalfireFlowers;
	private int totalGumbas;
	private int totalSpikes;
	protected Room[][] arrayOfRooms;
	
	/**
	 * Instantiates the CaveMap depending on the game size.
	 * 
	 * @param gameSize size of the board
	 */
	public CaveMap(int gameSize) {
		this.gameSize = gameSize;
		arrayOfRooms = new Room[gameSize][gameSize];
		
		// Game size determines # of items
		switch(gameSize) {
			case 5:
				totalfireFlowers = 2;
				totalGumbas = 3;
				totalSpikes = 3;
				break;
			case 7:
				totalfireFlowers = 4;
				totalGumbas = 6;
				totalSpikes = 6;
				break;
			case 10:
				totalfireFlowers = 6;	 
				totalGumbas = 9;
				totalSpikes = 9;
				break;
		}
	}
	
	/**
	 * Creates the map of rooms and determines what goes in each room.
	 * ------------------------------
	 * | 'B' stands for BOWSER      |
	 * | 'F' stands for FIRE FLOWER |
	 * | 'G' stands for GUMBA       |
	 * | 'M' stands for MARIO       |
	 * | 'S' stands for SPIKE       |
	 * | ' ' stands for EMPTY       |
	 * ------------------------------ */
	public void createMap() {
		Random rand = new Random();
		
		// Create EMPTY rooms
		for (int row = 0; row < gameSize; row++) {
			for (int col = 0; col < gameSize; col++) {
				arrayOfRooms[row][col] = new Room(row, col, ' ');
			}
		}
		
		// Create MARIO
		int playerRow = rand.nextInt(gameSize);
		int playerCol = rand.nextInt(gameSize);
		arrayOfRooms[playerRow][playerCol].setType('M');
		
		// Create BOWSER
		int bowserRow = rand.nextInt(gameSize);
		int bowserCol = rand.nextInt(gameSize);
		while (arrayOfRooms[bowserRow][bowserCol].getType() != ' ') {
			bowserRow = rand.nextInt(gameSize);
			bowserCol = rand.nextInt(gameSize);
		}
		arrayOfRooms[bowserRow][bowserCol].setType('B');
		
		// Create FIRE FLOWERS
		for (int i = 0; i < totalfireFlowers; i++) {
			int fireFlowerRow = rand.nextInt(gameSize);
			int fireFlowerCol = rand.nextInt(gameSize);
			while (arrayOfRooms[fireFlowerRow][fireFlowerCol].getType() != ' ') {
				fireFlowerRow = rand.nextInt(gameSize);
				fireFlowerCol = rand.nextInt(gameSize);
			}
			arrayOfRooms[fireFlowerRow][fireFlowerCol].setType('F');
		}
		
		// Create GUMBAS
		for (int i = 0; i < totalGumbas; i++) {
			int gumbaRow = rand.nextInt(gameSize);
			int gumbaCol = rand.nextInt(gameSize);
			while (arrayOfRooms[gumbaRow][gumbaCol].getType() != ' ') {
				gumbaRow = rand.nextInt(gameSize);
				gumbaCol = rand.nextInt(gameSize);
			}
			arrayOfRooms[gumbaRow][gumbaCol].setType('G');
		}
		
		// Create SPIKES
		for (int i = 0; i < totalSpikes; i++) {
			int spikeRow = rand.nextInt(gameSize);
			int spikeCol = rand.nextInt(gameSize);
			while (arrayOfRooms[spikeRow][spikeCol].getType() != ' ') {
				spikeRow = rand.nextInt(gameSize);
				spikeCol = rand.nextInt(gameSize);
			}
			arrayOfRooms[spikeRow][spikeCol].setType('S');
		}
	}
}