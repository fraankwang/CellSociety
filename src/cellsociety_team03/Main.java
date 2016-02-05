/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cellsociety_team03;

import java.io.File;
import java.util.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class Main extends Application{

        // Model
        private Game primaryGame;

        // View
	private Stage primaryStage;
	private Scene primaryScene;
        private BorderPane primaryPane;

		
	
	/**
	 * Sets primaryStage (which displays primaryScene) and primaryScene (which displays primaryRoot).
	 * primaryRoot contains a BorderPane with two groups: toolbar (start/stop/reset/newGame buttons - always present)
	 * and gameRoot, which contains varying Node elements depending on game type and Grid type (not always present)
	 */
	@Override
	public void start(Stage s) throws Exception {
		primaryStage = s;
		
		Group primaryRoot = initializeRoot();
		
		// TODO: figure out primaryScene's dimensions
		primaryScene = new Scene(primaryRoot, 500, 500, Color.WHITE);		
		primaryStage.setScene(primaryScene);
		primaryStage.show();
	}

	public static void main (String[] args) {
        launch(args);
    }
	
	
	/**
	 * Initializes the main root with a BorderPane 
	 * The BorderPane is initialized with an HBox toolbar on top to hold all the game buttons that stay visible the entire time.
	 * @return
	 */
	private Group initializeRoot(){	        
	        primaryPane = new BorderPane();
	        
	        HBox toolbar = createToolbar(); 
		primaryPane.setTop(toolbar);
	              
	        primaryPane.setPrefSize(500, 500 + toolbar.getPrefHeight());

		Group primaryRoot = new Group();
		primaryRoot.getChildren().add(primaryPane);

		return primaryRoot;
	}
	
	/**
	 * Creates a toolbar to display in the top of the screen
	 * @return The HBox toolbar
	 */
	private HBox createToolbar(){
	    HBox toolbar = new HBox();
	    
	    List<Button> buttons = createGameButtons();

            toolbar.getChildren().addAll(buttons);
            toolbar.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
            for(Button b : buttons){
               HBox.setMargin(b, new Insets(2, 10, 2, 10));
            }
            
            
            return toolbar;

	}
	
	/**
	 * Creates a list of buttons to display in the toolbar
	 * @return The list of buttons
	 */
	private List<Button> createGameButtons(){
	    List<Button> list = new ArrayList<Button>();
	    
	    Button startButton = new Button("Start");
	    startButton.setOnAction(e -> startGame());
            Button stopButton = new Button("Stop");
            stopButton.setOnAction(e -> stopGame());
            Button stepButton = new Button("Step");
            stepButton.setOnAction(e -> stepGame());
            Button resetButton = new Button("Reset");
            resetButton.setOnAction(e -> resetGame());
            Button newGameButton = new Button("New Game");
            newGameButton.setOnAction(e -> chooseNewGame());

            
            list.add(startButton);
            list.add(stopButton);
            list.add(stepButton);
            list.add(resetButton);
            list.add(newGameButton);
            
            
            return list;
	}
	
	/**
	 * Event handler for starting the current game
	 */
	private void startGame(){
	    primaryGame.startGame();
	}
	
	/**
	 * Event handler for stopping the current game
	 */
	private void stopGame(){
	    primaryGame.stopGame();
	}
	
	/**
	 * Event handler for a single step through a game
	 */
	private void stepGame(){
	    primaryGame.getMyGrid().step(1.0/60);
	}
	
	/**
	 * Event handler for resetting the current game
	 */
	private void resetGame(){
	    primaryGame.initializeGrid();
            switchToGame(primaryGame);
	}
	
	/**
	 * Event handler for choosing a new game to start
	 */
	private void chooseNewGame(){
	    FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select an XML file");
            fileChooser.getExtensionFilters().add(new ExtensionFilter("XML files", "*.xml"));
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                setUpGame(file);
            }
	}
	
	/**
	 * Constructs a new game based on a given file and switches to it
	 * @param file The file containing the game parameters
	 */
	private void setUpGame(File file){
		Map<String,String> params = parseXML(file);
		primaryGame = new Game(params);
		switchToGame(primaryGame);
	}
	
	/**
	 * Switches to a new game by displaying the game root in the center of the BorderPane
	 * @param primaryGame The game to be displayed
	 */
	private void switchToGame(Game primaryGame){
	    BorderPane.setMargin(primaryGame.getGameRoot(), new Insets(12,12,12,12));
	    primaryPane.setPrefWidth(500); //change to primaryGame.getMyGrid().getWidth
	    //do I have to change scene's width/height too?
	   
	    primaryPane.setCenter(primaryGame.getGameRoot());
	}
	
	/**
	 * Takes a file and converts XML data to a Map of Grid parameters and their initial values
	 * @param file The file to parse
	 * @return A map of grid parameter keys and values
	 */
	private Map<String,String> parseXML(File file){
		Parser parser = new Parser();
		return parser.parse(file);
	}

}
