package monster_hunter;

import java.io.Serializable;
import java.util.Random;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * This class represents the game GUI pane. The GUI pane takes the form of a Border Pane and is divided into 
 * three sections: the header, center, and footer. The header holds the title of the game, the center holds 
 * the message box, grid pane, legend, and attack box. Finally, the footer holds the buttons for attacking, 
 * debugging, restarting, and saving the game.
 * 
 * @author Richard Shu
 * @version 1.0
 * @see CaveMap
 */
public class GameGUIPane extends BorderPane implements Serializable {
	protected int gameSize;
	private int totalFireBalls = 3;
	private boolean attackModeOn = false;
	private boolean debugModeOn = false;
	private boolean isGameOver = false;
	protected Mario mario;
	private Bowser bowser;
	private transient HBox header, center, footer;
	protected CaveMap map;
	private transient GridPane grid = new GridPane();
	protected transient Label[][] array;
	protected String[][] arrayOfText;
	private transient VBox messageBox, rightSideBox;
	private transient Text title = new Text("MONSTER HUNTER");
	private transient Label bowserMessage = new Label("BOWSER GOT YOU  \n\n");
	private transient Label fireBallMessage = new Label("+1 FIREBALL: You  \n" + "picked up a fire\n" + "flower\n\n");
	private transient Label gumbaMessage = new Label("-1 FIREBALL: You\n" + "stumbled upon a gumba  \n\n");
	private transient Label spikeMessage = new Label("You fell on a spike!\n\n");
	private transient Label bowserWarningMessage = new Label("You hear heavy  \n" + "breathing...\n\n");
	private transient Label fireFlowerWarningMessage = new Label("A warm fire\n" + "glows nearby...  \n\n");
	private transient Label gumbaWarningMessage = new Label("A tiny creature\n" + "shuffles past...  \n\n");
	private transient Label spikeWarningMessage = new Label("Something shiny\n" + "sparkles nearby...  \n\n");
	private transient Label noFireBallMessage = new Label("You ran out\n" + "of fireballs\n\n");
	private transient Label youWinMessage = new Label("You defeated Bowser  \n" + "with a fireball!\n\n");
	private transient Label youMissedMessage = new Label("Your fireball missed!  \n\n");
	protected transient Button saveButton = new Button("SAVE");
	private transient Button gameOverButton = new Button("GAME OVER");
	private transient Button youWinButton = new Button("YOU WIN");
	private char upperRoomType, lowerRoomType, rightRoomType, leftRoomType;
	
	/**
	 * Instantiates the GameGUIPane. This constructor is used when a new game is created.
	 * 
	 * @param gameSize the size of the board
	 */
	public GameGUIPane(int gameSize) {
		this.gameSize = gameSize;
		map = new CaveMap(gameSize);
		array = new Label[gameSize][gameSize];
		arrayOfText = new String[gameSize][gameSize];
		map.createMap();
	}
	
	/**
	 * Instantiates the GameGUIPane. This constructor is used when a saved game is loaded.
	 * 
	 * @param gameSize the size of the board
	 * @param map a collection of rooms
	 * @param arrayOfText array that stores the text of the room types
	 * @param mario the player
	 */
	public GameGUIPane(int gameSize, CaveMap map, String[][] arrayOfText, Mario mario) {
		this.gameSize = gameSize;
		this.map = map;
		this.arrayOfText = arrayOfText;
		this.mario = mario;
		array = new Label[gameSize][gameSize];
		for (int i = 0; i < gameSize; i++) {
			for (int j = 0; j < gameSize; j++) {
				array[i][j] = new Label(" ");
			}
		}
		array[mario.getRow()][mario.getCol()].getStyleClass().add("mario");
	}
	
	/**
	 * Launches the GUI for the game. This method creates the game layout, 
	 * the buttons, and the functionality that allows the game to run.
	 */
	public void createGUI() {
		
		// Header and footer
		header = new HBox();
		center = new HBox();
		footer = new HBox();
		
//		Image ammoImage = new Image("File:C:/Users/richa/Desktop/workspace/CS 2013/src/Lab0/ammo-image.png");
		addLabelsToArrayOfRooms();
		
		// Display total ammo
		Text totalFireBallsText = new Text("TOTAL FIREBALLS: " + totalFireBalls + " ");
		
		// Buttons
		Button attackButton = new Button("ATTACK");
		Button debugButton = new Button("DEBUG OFF");
		Button restartButton = new Button("RESTART");
		
		messageBox = new VBox();
		rightSideBox = new VBox();
		
		// Add buttons for attacking up, down, right, & left
		VBox attackButtonBox = new VBox();
		HBox upperAttackButtonBox = new HBox();
		HBox middleAttackButtonBox = new HBox();
		HBox lowerAttackButtonBox = new HBox();
		Button attackButtonUp = new Button(" U ");
		Button attackButtonDown = new Button(" D ");
		Button attackButtonRight = new Button(" R ");
		Button attackButtonLeft = new Button(" L ");
		upperAttackButtonBox.getChildren().addAll(new Text("       "), attackButtonUp);
		middleAttackButtonBox.getChildren().addAll(new Text("  "), attackButtonLeft, new Text("     "), attackButtonRight);
		lowerAttackButtonBox.getChildren().addAll(new Text("       "), attackButtonDown);
		attackButtonBox.getChildren().addAll(new Text("       ATTACK    "),
											 new Text("  ----------------"),
											 		   upperAttackButtonBox,
											 		  middleAttackButtonBox,
											 		   lowerAttackButtonBox,
											 new Text("  ----------------"));
		
		// Add a legend to explain each symbol
		Label legend = new Label("      LEGEND       "
							 + "\n  ---------------"
							 + "\n  B |      Bowser"
							 + "\n  F | Fire Flower"
							 + "\n  G |       Gumba"
							 + "\n  S |       Spike"
							 + "\n  ---------------");
		
		// Add elements to header, center, & footer
		header.getChildren().add(title);
		center.getChildren().addAll(messageBox, grid, rightSideBox);
		footer.getChildren().addAll(totalFireBallsText, attackButton, debugButton, restartButton, saveButton);
		
		// Toggle between attack mode on & off
		attackButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (attackModeOn) {
					attackModeOn = false;
					attackButton.getStyleClass().remove("green-button");
					rightSideBox.getChildren().removeAll(attackButtonBox);
				}
				else {
					attackModeOn = true;
					attackButton.getStyleClass().add("green-button");
					rightSideBox.getChildren().addAll(attackButtonBox);
				}
			}
		});
		
		// Toggle between debugging mode on & off
		debugButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (debugModeOn) {
					debugModeOn = false;
					debugButton.setText("DEBUG OFF");
					debugButton.getStyleClass().remove("green-button");
					rightSideBox.getChildren().remove(legend);
					for (int row = 0; row < gameSize; row++) {
						for (int col = 0; col < gameSize; col++) {
							array[row][col].setText("");
						}
					}
				}
				else {
					debugModeOn = true;
					debugButton.setText("DEBUG ON");
					debugButton.getStyleClass().add("green-button");
					rightSideBox.getChildren().add(legend);
					for (int row = 0; row < gameSize; row++) {
						for (int col = 0; col < gameSize; col++) {
							String text = arrayOfText[row][col];
							array[row][col].setText(text);
						}
					}
				}
			}
		});
		
		// Restart button
		restartButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				totalFireBalls = 3;
				isGameOver = false;
				map = new CaveMap(gameSize);
				map.createMap();
				createGUI();
			}
		});
		
		// Display in-game messages
		if (mario.getRow() != 0) {
			upperRoomType = map.arrayOfRooms[mario.getRow() - 1][mario.getCol()].getType();
			addMessageToMessageBox(upperRoomType);
		}
		if (mario.getRow() != gameSize - 1) {
			lowerRoomType = map.arrayOfRooms[mario.getRow() + 1][mario.getCol()].getType();
			addMessageToMessageBox(lowerRoomType);
		}
		if (mario.getCol() != gameSize - 1) {
			rightRoomType = map.arrayOfRooms[mario.getRow()][mario.getCol() + 1].getType();
			addMessageToMessageBox(rightRoomType);
		}
		if (mario.getCol() != 0) {
			leftRoomType = map.arrayOfRooms[mario.getRow()][mario.getCol() - 1].getType();
			addMessageToMessageBox(leftRoomType);
		}
		
		// Attack buttons
		Random rand = new Random();
		attackButtonUp.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (!isGameOver) {
					totalFireBalls--;
					totalFireBallsText.setText("TOTAL FIREBALLS: " + totalFireBalls + " ");
					if (mario.getRow() != 0 && upperRoomType == 'B') {
						isGameOver = true;
						messageBox.getChildren().clear();
						messageBox.getChildren().add(youWinMessage);
						footer.getChildren().add(youWinButton);
					}
					else {
						checkFireBallCount();
						int randomNum = rand.nextInt(4);
						moveBowser(randomNum);
						checkIfBowserAteMario();
					}
				}
			}
		});
		attackButtonDown.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (!isGameOver) {
					totalFireBalls--;
					totalFireBallsText.setText("TOTAL FIREBALLS: " + totalFireBalls + " ");
					if (mario.getRow() != gameSize - 1 && lowerRoomType == 'B') {
						isGameOver = true;
						messageBox.getChildren().clear();
						messageBox.getChildren().add(youWinMessage);
						footer.getChildren().add(youWinButton);
					}
					else {
						checkFireBallCount();
						int randomNum = rand.nextInt(4);
						moveBowser(randomNum);
						checkIfBowserAteMario();
					}
				}	
			}
		});
		attackButtonRight.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (!isGameOver) {
					totalFireBalls--;
					totalFireBallsText.setText("TOTAL FIREBALLS: " + totalFireBalls + " ");
					if (mario.getCol() != gameSize - 1 && rightRoomType == 'B') {
						isGameOver = true;
						messageBox.getChildren().clear();
						messageBox.getChildren().add(youWinMessage);
						footer.getChildren().add(youWinButton);
					}
					else {
						checkFireBallCount();
						int randomNum = rand.nextInt(4);
						moveBowser(randomNum);
						checkIfBowserAteMario();
					}
				}	
			}
		});
		attackButtonLeft.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				if (!isGameOver) {
					totalFireBalls--;
					totalFireBallsText.setText("TOTAL FIREBALLS: " + totalFireBalls + " ");
					if (mario.getCol() != 0 && leftRoomType == 'B') {
						isGameOver = true;
						messageBox.getChildren().clear();
						messageBox.getChildren().add(youWinMessage);
						footer.getChildren().add(youWinButton);
					}
					else {
						checkFireBallCount();
						int randomNum = rand.nextInt(4);
						moveBowser(randomNum);
						checkIfBowserAteMario();
					}
				}
			}
		});
		
		// Add KeyEvent to Border Pane
		setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				Label marioCurrentSquare = array[mario.getRow()][mario.getCol()];
				Label marioNextSquare = new Label();
				
				if (!isGameOver) {
					
					// Move Mario around the grid
					if (event.getCode() == KeyCode.UP && mario.getRow() != 0) {
						marioCurrentSquare.getStyleClass().remove("mario");
						marioNextSquare = array[mario.getRow() - 1][mario.getCol()];
						mario.setRow(mario.getRow() - 1);
					}
					else if (event.getCode() == KeyCode.DOWN && mario.getRow() != gameSize - 1) {
						marioCurrentSquare.getStyleClass().remove("mario");
						marioNextSquare = array[mario.getRow() + 1][mario.getCol()];
						mario.setRow(mario.getRow() + 1);
					}
					else if (event.getCode() == KeyCode.RIGHT && mario.getCol() != gameSize - 1) {
						marioCurrentSquare.getStyleClass().remove("mario");
						marioNextSquare = array[mario.getRow()][mario.getCol() + 1];
						mario.setCol(mario.getCol() + 1);
					}
					else if (event.getCode() == KeyCode.LEFT && mario.getCol() != 0) {
						marioCurrentSquare.getStyleClass().remove("mario");
						marioNextSquare = array[mario.getRow()][mario.getCol() - 1];
						mario.setCol(mario.getCol() - 1);
					}
					marioNextSquare.getStyleClass().add("mario");
					
					// Display in-game messages
					checkAdjacentRoomsOfMario();
					
					// Remove messages once Mario passes each room
					if (mario.getType() != 'B')
						messageBox.getChildren().remove(bowserMessage);
					if (mario.getType() != 'F')
						messageBox.getChildren().remove(fireBallMessage);
					if (mario.getType() != 'G')
						messageBox.getChildren().remove(gumbaMessage);
					if (mario.getType() != 'S')
						messageBox.getChildren().remove(spikeMessage);
					if (upperRoomType != 'B' && lowerRoomType != 'B' && rightRoomType != 'B' && leftRoomType != 'B')
						messageBox.getChildren().remove(bowserWarningMessage);
					if (upperRoomType != 'F' && lowerRoomType != 'F' && rightRoomType != 'F' && leftRoomType != 'F')
						messageBox.getChildren().remove(fireFlowerWarningMessage);
					if (upperRoomType != 'G' && lowerRoomType != 'G' && rightRoomType != 'G' && leftRoomType != 'G')
						messageBox.getChildren().remove(gumbaWarningMessage);
					if (upperRoomType != 'S' && lowerRoomType != 'S' && rightRoomType != 'S' && leftRoomType != 'S')
						messageBox.getChildren().remove(spikeWarningMessage);
					messageBox.getChildren().remove(youMissedMessage);
					
					// Triggers events when Mario steps into different rooms
					char marioCurrentRoomType = map.arrayOfRooms[mario.getRow()][mario.getCol()].getType();
					if (marioCurrentRoomType == 'B') {
						isGameOver = true;
						footer.getChildren().add(gameOverButton);
						messageBox.getChildren().clear();
						messageBox.getChildren().add(bowserMessage);
					}
					else if (marioCurrentRoomType == 'F') {
						totalFireBalls++;
						totalFireBallsText.setText("TOTAL FIREBALLS: " + totalFireBalls + " ");
						array[mario.getRow()][mario.getCol()].setText("");
						arrayOfText[mario.getRow()][mario.getCol()] = "";
						map.arrayOfRooms[mario.getRow()][mario.getCol()].setType(' ');
						messageBox.getChildren().add(fireBallMessage);
					}
					else if (marioCurrentRoomType == 'G') {
						totalFireBalls--;
						totalFireBallsText.setText("TOTAL FIREBALLS: " + totalFireBalls + " ");
						messageBox.getChildren().add(gumbaMessage);
					}
					else if (marioCurrentRoomType == 'S') {
						isGameOver = true;
						footer.getChildren().add(gameOverButton);
						messageBox.getChildren().clear();
						messageBox.getChildren().add(spikeMessage);
					}
					checkFireBallCount();
				}
			}
		});
		
		// Add items to Border Pane
		setTop(header);
		setCenter(center);
		setBottom(footer);
				
		// Add CSS to everything
		header.getStyleClass().add("header");
		center.getStyleClass().add("center");
		footer.getStyleClass().add("footer");
		title.getStyleClass().add("title");
		grid.getStyleClass().add("grid");
		messageBox.getStyleClass().add("message-box");
		rightSideBox.getStyleClass().add("right-side-box");
		totalFireBallsText.getStyleClass().add("total-ammo");
		attackButton.getStyleClass().add("button");
		debugButton.getStyleClass().add("button");
		restartButton.getStyleClass().add("button");
		saveButton.getStyleClass().add("button");
		gameOverButton.getStyleClass().addAll("button", "red-button");
		youWinButton.getStyleClass().addAll("button", "green-button");
	}
	

	/**
	 * Checks that the fire ball count is above 0. If the fire ball count reaches 0, then the game ends.
	 */
	public void checkFireBallCount() {
		if (totalFireBalls <= 0) {
			isGameOver = true;
			footer.getChildren().add(gameOverButton);
			messageBox.getChildren().clear();
			messageBox.getChildren().add(youMissedMessage);
			messageBox.getChildren().add(noFireBallMessage);
			messageBox.getChildren().add(bowserMessage);
		}
	}
	
	/**
	 * Checks if Mario and Bowser are in the same room. If not, the game continues. If so, the game ends.
	 */
	public void checkIfBowserAteMario() {
		if (!messageBox.getChildren().contains(youMissedMessage)) {
			messageBox.getChildren().add(youMissedMessage);
		}
		if (bowser.getRow() == mario.getRow() && bowser.getCol() == mario.getCol()) {
			isGameOver = true;
			footer.getChildren().add(gameOverButton);
			messageBox.getChildren().clear();
			messageBox.getChildren().add(youMissedMessage);
			messageBox.getChildren().add(bowserMessage);
		}
		else {
			messageBox.getChildren().remove(bowserMessage);
		}
	}
	
	/**
	 * Adds labels to each room on the grid. The labels allow for the rooms to be styled using CSS.
	 * This method also fills a 2D array of strings. Each string in this 2D array stores the type of 
	 * each room on the grid. This allows us to toggle between having the debugging mode on and off 
	 * without losing track of the room types.
	 */
	public void addLabelsToArrayOfRooms() {
		for (int row = 0; row < gameSize; row++) {
			for (int col = 0; col < gameSize; col++) {
				Room room = map.arrayOfRooms[row][col];
				Label square = new Label();
				square.getStyleClass().add("room");
				switch (room.getType()) {
					case 'B': arrayOfText[row][col] = "B";
							  bowser = new Bowser(row, col);
							  break;
					case 'F': arrayOfText[row][col] = "F";
//							  ImageView ammoImageView = new ImageView(ammoImage);
//							  ammoImageView.setFitHeight(40);
//							  ammoImageView.setFitWidth(40);
//							  ammoImageView.setVisible(true);
//							  grid.add(ammoImageView, col, row);
							  break;
					case 'G': arrayOfText[row][col] = "G";
							  break;
					case 'M': square.getStyleClass().add("mario");
							  mario = new Mario(row, col);
							  break;
					case 'S': arrayOfText[row][col] = "S";
							  break;
					default:  arrayOfText[row][col] = " ";
				}
				array[row][col] = square;
				grid.add(square, col, row);
			}
		}
	}
	
	/**
	 * Checks the adjacent rooms of Mario to see if anything is nearby. This method will 
	 * call the addMessageToMessageBox method if something is nearby.
	 */
	public void checkAdjacentRoomsOfMario() {
		if (mario.getRow() != 0) {
			upperRoomType = map.arrayOfRooms[mario.getRow() - 1][mario.getCol()].getType();
			addMessageToMessageBox(upperRoomType);
		}
		if (mario.getRow() != gameSize - 1) {
			lowerRoomType = map.arrayOfRooms[mario.getRow() + 1][mario.getCol()].getType();
			addMessageToMessageBox(lowerRoomType);
		}
		if (mario.getCol() != gameSize - 1) {
			rightRoomType = map.arrayOfRooms[mario.getRow()][mario.getCol() + 1].getType();
			addMessageToMessageBox(rightRoomType);
		}
		if (mario.getCol() != 0) {
			leftRoomType = map.arrayOfRooms[mario.getRow()][mario.getCol() - 1].getType();
			addMessageToMessageBox(leftRoomType);
		}
	}
	
	/**
	 * Adds a warning message to the message box when something is in the room adjacent to Mario.
	 * 
	 * @param roomType type of the room
	 */
	public void addMessageToMessageBox(char roomType) {
		switch (roomType) {
			case 'B': if (!messageBox.getChildren().contains(bowserWarningMessage)) {
					  	messageBox.getChildren().add(bowserWarningMessage);
					  }
					  break;
			case 'F': if (!messageBox.getChildren().contains(fireFlowerWarningMessage)) {
					  	messageBox.getChildren().add(fireFlowerWarningMessage);
					  }
					  break;
			case 'G': if (!messageBox.getChildren().contains(gumbaWarningMessage)) {
						messageBox.getChildren().add(gumbaWarningMessage);
					  }
					  break;
			case 'S': if (!messageBox.getChildren().contains(spikeWarningMessage)) {
						messageBox.getChildren().add(spikeWarningMessage);
					  }
		}
	}
	
	/**
	 * Moves Bowser randomly into an adjacent room whenever Mario attacks.
	 * 
	 * @param randomNum random number generated to determine what direction Bowser should move in
	 */
	public void moveBowser(int randomNum) {
		array[bowser.getRow()][bowser.getCol()].setText(" ");
		arrayOfText[bowser.getRow()][bowser.getCol()] = " ";
		map.arrayOfRooms[bowser.getRow()][bowser.getCol()].setType(' ');
		switch (randomNum) {
			case 0: if (bowser.getRow() != 0)
						bowser.setRow(bowser.getRow() - 1);
					else
						moveBowser(1);
					break;
			case 1: if (bowser.getRow() != gameSize - 1)
						bowser.setRow(bowser.getRow() + 1);
					else
						moveBowser(0);
					break;
			case 2: if (bowser.getCol() != gameSize - 1)
						bowser.setCol(bowser.getCol() + 1);
					else
						moveBowser(3);
					break;
			case 3: if (bowser.getCol() != 0)
						bowser.setCol(bowser.getCol() - 1);
					else
						moveBowser(2);
		}
		if (debugModeOn) {
			array[bowser.getRow()][bowser.getCol()].setText("B");
		}
		arrayOfText[bowser.getRow()][bowser.getCol()] = "B";
		map.arrayOfRooms[bowser.getRow()][bowser.getCol()].setType('B');
		checkAdjacentRoomsOfMario();
	}
}