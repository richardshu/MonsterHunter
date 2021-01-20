package monster_hunter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

/**
 * This class allows a game to be saved to a file. 
 * It also allows a game to be loaded from a file.
 * 
 * @author Richard Shu
 * @version 1.0
 * @see GameGUIPane
 */
public class BinaryFile {
	
	/**
	 * Saves the game to a file (extension is .dat).
	 * 
	 * @param fileName file that the game will be saved in
	 * @param game the GameGUIPane that is going to be saved
	 */
	public void saveGameToFile (File fileName, GameGUIPane game) {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
			out.writeObject(game);
			out.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * Reads a game from a file (extension is .dat) and loads it to the screen.
	 * 
	 * @param fileName file from which the game will be loaded
	 * @return a previously saved GameGUIPane
	 */
	public GameGUIPane readGameFromFile (File fileName) {
		ObjectInputStream in = null;
		GameGUIPane game = null;
		try {
			in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)));
			game = (GameGUIPane) in.readObject();
			in.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return game;
	}
}