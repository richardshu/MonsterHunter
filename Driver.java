package monster_hunter;

import java.io.File;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * This class is a driver that runs the game. 
 * 
 * @author Richard Shu
 * @version 1.0
 * @see BinaryFile
 * @see MenuGUIPane
 * @see GameGUIPane
 */
public class Driver extends Application {
	
	/**
	 * Runs the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	/**
	 * Displays the menu of the game to the screen.
	 */
	public void start(Stage primaryStage) {

		// Set up menu pane
		MenuGUIPane menuPane = new MenuGUIPane();
		menuPane.createGUI();
		menuPane.getStyleClass().add("menu");
		Scene menuScene = new Scene(menuPane, 800, 800); 
		menuScene.getStylesheets().add("monster_hunter/MenuStyle.css");
		
		// Set up game pane
		GameGUIPane gamePane = new GameGUIPane(10);
		//gamePane.createGUI(10);
		Scene gameScene = new Scene(gamePane, 1000, 1000);
		gameScene.getStylesheets().add("monster_hunter/GameStyle.css");
		
		// Create BinaryFile object
		BinaryFile binaryFile = new BinaryFile();
		BorderPane borderPane = new BorderPane();
		Scene fileScene = new Scene(borderPane, 700, 700);
		Stage stage = new Stage();
		
		// Save button
		gamePane.saveButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				stage.setScene(fileScene);
				FileChooser fileChooser = new FileChooser();
				File fileName = fileChooser.showOpenDialog(stage);
				if (fileName != null) {
					binaryFile.saveGameToFile(fileName, gamePane);
				}
			}
		});
		
		// Load button
		menuPane.loadButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				stage.setScene(fileScene);
				FileChooser fileChooser = new FileChooser();
				File fileName = fileChooser.showOpenDialog(stage);
				if (fileName != null) {
					GameGUIPane savedGamePane = binaryFile.readGameFromFile(fileName);
					Scene savedGameScene = new Scene(savedGamePane);
					savedGameScene.getStylesheets().add("Lab0/GameStyle.css");
					
					// Declare new variables
					int gameSize = savedGamePane.gameSize;
					CaveMap map = savedGamePane.map;
					String[][] arrayOfText = savedGamePane.arrayOfText;
					Mario mario = savedGamePane.mario;
					
					GameGUIPane newGamePane = new GameGUIPane(gameSize, map, arrayOfText, mario);
					Scene newGameScene = new Scene(newGamePane);
					newGameScene.getStylesheets().add("Lab0/GameStyle.css");
					newGamePane.createGUI();
					
					//savedGamePane = new GameGUIPane(gameSize, map, arrayOfText);
					//savedGamePane.createGUI();
					
					System.out.println(mario.getRow() + " " + mario.getCol());
					for (int i = 0; i < 7; i++) {
						for (int j = 0; j < 7; j++) {
							System.out.print(newGamePane.arrayOfText[i][j] + " ");
						}
						System.out.println();
					}
					
					primaryStage.setScene(newGameScene);
					primaryStage.setMaximized(true);
				}
			}
		});
		
		// Clicking menu buttons determines game size
		menuPane.button5x5.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				gamePane.gameSize = 5;
				gamePane.createGUI();
				primaryStage.setScene(gameScene);
				primaryStage.setMaximized(true);
			}
		});
		menuPane.button7x7.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				gamePane.gameSize = 7;
				gamePane.createGUI();
				primaryStage.setScene(gameScene);
				primaryStage.setMaximized(true);
			}
		});
		menuPane.button10x10.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				gamePane.gameSize = 10;
				gamePane.createGUI();
				primaryStage.setScene(gameScene);
				primaryStage.setMaximized(true);
			}
		});
		
		// Display GUI
		primaryStage.setScene(menuScene);
		primaryStage.show();
	}
}