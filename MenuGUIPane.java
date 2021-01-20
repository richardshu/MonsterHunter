package monster_hunter;

import java.io.Serializable;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * This class represents the MenuGUIPane. The MenuGUIPane takes the form of a FlowPane 
 * and contains the title of the game, the game size buttons, and the load button. 
 * 
 * @author Richard Shu
 * @version 1.0
 */
public class MenuGUIPane extends FlowPane implements Serializable {
	HBox buttonBox = new HBox();
	Text playText = new Text("PLAY >>> ");
	Button button5x5 = new Button("5 x 5");
	Button button7x7 = new Button("7 x 7");
	Button button10x10 = new Button("10 x 10");
	Button loadButton = new Button("LOAD");
	
	/**
	 * Launches the GUI for the game. This method creates the menu layout, the game size buttons, and the load feature.
	 */
	public void createGUI() {
		
		// Create title
		Text title = new Text("MONSTER HUNTER");
				
		// Add CSS to text and buttons
		title.getStyleClass().add("title");
		playText.getStyleClass().add("play-text");
		buttonBox.getStyleClass().add("button-box");
		button5x5.getStyleClass().add("button");
		button7x7.getStyleClass().add("button");
		button10x10.getStyleClass().add("button");
		loadButton.getStyleClass().add("button");
		buttonBox.getChildren().addAll(playText, button5x5, button7x7, button10x10, loadButton);
				
		// Format title and buttons
		VBox titleAndButtonsBox = new VBox();
		titleAndButtonsBox.getChildren().addAll(title, buttonBox);
		
		// Add everything to Flow Pane
		getChildren().add(titleAndButtonsBox);
	}
}