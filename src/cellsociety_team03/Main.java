/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cellsociety_team03;

import java.io.File;
import java.util.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Main extends Application{

	private Stage primaryStage;
	private Game primaryGame;
	private Scene primaryScene;
	private Group primaryRoot;
	private BorderPane primaryPane;
	private HBox splashRoot;
	private Group gameRoot;
	
	private Button startButton;
	private Button stopButton;
	private Button stepButton;
	private Button resetButton;
	private Button newGameButton;
	private Button exitButton;
	
	/**
	 * Sets primaryStage (which displays primaryScene) and primaryScene (which displays primaryRoot)
	 * primaryRoot contains two roots, splashRoot (start/stop/reset/newGame buttons - always present)
	 * and gameRoot, which contains varying Node elements depending on game type and Grid type (not always present)
	 */
	@Override
	public void start(Stage s) throws Exception {
		primaryStage = s;
		primaryRoot = new Group();
		
		initialize();
		
		// TODO: figure out primaryScene's dimensions
		primaryScene = new Scene(primaryRoot,500,500,Color.WHITE);		
		primaryStage.setScene(primaryScene);
		primaryStage.show();
	}

	public static void main (String[] args) {
        launch(args);
    }
	
	/**
	 * Initializes both splashRoot and gameRoot to be added to primaryRoot.
	 * Creates new HBox to hold all the game buttons that stay visible the entire time.
	 * Each of the buttons will call the handleButton function which sets off additional actions
	 */
	private void initialize(){
		//create horizontally aligned box with spacing of 50
	        splashRoot = new HBox(50);
		gameRoot = new Group();
	        primaryPane = new BorderPane();

		
		startButton = new Button("Start");
		stopButton = new Button("Stop");
		stepButton = new Button("Step");
		resetButton = new Button("Reset");
		newGameButton = new Button("New Game");
		//exitButton = new Button("Exit");
		
		//set asynchronous functions to handle button clicks
		//TODO: should we give each a unique handler instead? If not, we should put
		//buttons in an array to minimize duplicated code
		startButton.setOnAction(e-> handleButton(e));
		stopButton.setOnAction(e-> handleButton(e));
	        stepButton.setOnAction(e-> handleButton(e));

		resetButton.setOnAction(e-> handleButton(e));
		newGameButton.setOnAction(e-> handleButton(e));
		//exitButton.setOnAction(e-> handleButton(e));
		
		splashRoot.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		splashRoot.getChildren().addAll(startButton,stopButton,stepButton,resetButton,newGameButton);
		
		
		primaryPane.setTop(splashRoot);
		primaryPane.setPrefWidth(500);
		primaryRoot.getChildren().add(primaryPane);
		//primaryRoot.getChildren().add(splashRoot);
	}
	
	/**
	 * Determine which button is pressed and do corresponding action in primaryGame or parse new XML
	 * @param e
	 */
	private void handleButton(ActionEvent e){
		if (e.getSource() == startButton){
			if (primaryGame != null) primaryGame.startGame();
		} else if (e.getSource() == stopButton) {
			if (primaryGame != null) primaryGame.stopGame();
		} else if (e.getSource() == stepButton) {
                    if (primaryGame != null) primaryGame.getMyGrid().step(1.0/60);
                }else if (e.getSource() == resetButton){
			if (primaryGame != null) {
			    primaryGame.initializeGrid();
			    gameRoot = primaryGame.getGameRoot();
			    this.switchToGame(gameRoot);
			   
			}
			
		} else if (e.getSource() == newGameButton){
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select an XML file");
			fileChooser.getExtensionFilters().add(new ExtensionFilter("XML files", "*.xml"));
			File file = fileChooser.showOpenDialog(primaryStage);
			if (file != null) {
			    setUpGame(file);
			}

		} else if (e.getSource() == exitButton){
			primaryStage.close();
		}
	}
	
	/**
	 * Takes a given file and specifies gameRoot by constructing a new Game and returning its created gameRoot variable 
	 * @param file
	 */
	private void setUpGame(File file){
		Map<String,String> params = parseXML(file);

		primaryGame = new Game(params);
		System.out.println("Game created");
		gameRoot = primaryGame.getGameRoot();
		System.out.println("Got initialized gameRoot from Game.java");
		
		this.switchToGame(gameRoot);
		//primaryRoot.getChildren().add(gameRoot);
		///gameRoot.setTranslateY(50);
	}
	
	private void switchToGame(Node gameRoot){
	    BorderPane.setMargin(gameRoot, new Insets(12,12,12,12));
	    primaryPane.setCenter(gameRoot);

	}
	/**
	 * Takes @param file and converts XML data to a Map of Grid parameters and their initial values
	 * @param file
	 * @return
	 */
	private Map<String,String> parseXML(File file){
		Parser parser = new Parser();
		return parser.parse(file);
	}

}
