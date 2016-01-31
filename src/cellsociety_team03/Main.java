/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cellsociety_team03;

import java.io.File;
import java.util.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application{

	private Stage primaryStage;
	private Game primaryGame;
	private Scene primaryScene;
	private Group primaryRoot;
	private HBox splashRoot;
	private Group gameRoot;
	
	private Button startButton;
	private Button stopButton;
	private Button resetButton;
	private Button newGameButton;
	
	/**
	 * Sets primaryStage (which displays primaryScene) and primaryScene (which displays primaryRoot)
	 * primaryRoot contains two roots, splashRoot (start/stop/reset/newGame buttons - always present)
	 * and gameRoot, which contains varying Node elements depending on game type and Grid type (not always present)
	 */
	@Override
	public void start(Stage s) throws Exception {
		primaryStage = s;
		primaryRoot = new Group();
		primaryRoot.getChildren().add(gameRoot);

		initialize();
		
		primaryScene = new Scene(primaryRoot,500,500,Color.WHITE);
		primaryStage.setScene(primaryScene);
		primaryStage.show();
	}

	public static void main (String[] args) {
        launch(args);
    }
	
	/**
	 * Creates new HBox to hold all the game buttons that stay visible the entire time.
	 * Each of the buttons will call the handleButton function which sets off additional actions
	 */
	private void initialize(){
		//create horizontally aligned box with spacing of 50
		splashRoot = new HBox(50);
		
		startButton = new Button("Start");
		stopButton = new Button("Stop");
		resetButton = new Button("Reset");
		newGameButton = new Button("New Game");
		
		//set asynchronous functions to handle button clicks
		startButton.setOnAction(e-> handleButton(e));
		stopButton.setOnAction(e-> handleButton(e));
		resetButton.setOnAction(e-> handleButton(e));
		newGameButton.setOnAction(e-> handleButton(e));
		
		splashRoot.getChildren().addAll(startButton,stopButton,resetButton,newGameButton);
		
		primaryRoot.getChildren().add(splashRoot);
	}
	
	/**
	 * Determine which button is pressed and do corresponding action in primaryGame or parse new XML
	 * @param e
	 */
	private void handleButton(ActionEvent e){
		if (e.getSource() == startButton){
			primaryGame.startGame();
		} else if (e.getSource() == stopButton) {
			primaryGame.stopGame();
		} else if (e.getSource() == resetButton){
			primaryGame.initializeGrid();
		} else if (e.getSource() == newGameButton){
			// prompt user to pick an XML file
			
			// TESTING: using test.xml file
			File file = new File("test.xml");
			setUpGame(file);
		}
	}
	
	/**
	 * Takes a given file and specifies gameRoot by constructing a new Game and returning its created gameRoot variable 
	 * @param file
	 */
	private void setUpGame(File file){
		Map<String,String> params = parseXML(file);
		String type = params.get("Type");
		
		primaryGame = new Game(type, params);
		gameRoot = primaryGame.getGameRoot();
	}
	
	/**
	 * Takes @param file and converts XML data to a Map of Grid parameters and their initial values
	 * @param file
	 * @return
	 */
	private Map<String,String> parseXML(File file){
		Map<String,String> myParams = new HashMap<String,String>();
//		@TODO parse XML and convert to Map<String,String>		
		return myParams;
	}
}