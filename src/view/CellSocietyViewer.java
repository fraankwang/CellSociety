package view;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import constants.Constants;
import game.Game;
import input.Parser;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class CellSocietyViewer {

	private Stage myPrimaryStage;
	private Scene myPrimaryScene;
	private BorderPane myPrimaryPane;
	private Group myPrimaryRoot;
	private Game myPrimaryGame;
	
	/**
	 * Instantiates all UI variables to be displayed
	 * @param s - the Stage variable to be displayed 
	 * @param g - the Game variable which must be linked to the view
	 */
	public CellSocietyViewer(Stage s, Game g) {
		myPrimaryStage = s;
		myPrimaryGame = g;
		
		myPrimaryRoot = initializeRoot();
		
	}
	
	/**
     * Sets primaryStage (which displays primaryScene) and primaryScene (which displays
     * primaryRoot).
     *
     * PrimaryRoot contains a BorderPane with two groups
     * - A tool bar (start/stop/reset/newGame buttons - always present) called myPrimaryPane
     * - The game (contains varying Node elements depending on game and Grid type - not always present) 
     * which is returned from myPrimaryGame is displayGame()
	 */
	public void display() {
		myPrimaryScene = new Scene(myPrimaryRoot, Constants.DEFAULT_WINDOW_SIZE.getWidth(),
				Constants.DEFAULT_WINDOW_SIZE.getHeight(),
				Color.WHITE);
		myPrimaryStage.setScene(myPrimaryScene);
		myPrimaryStage.show();
	}
	
	/**
     * Initializes myPrimaryRoot with a BorderPane
     * The BorderPane is initialized with an HBox tool bar on top to hold all the game buttons that
     * stay visible the entire time.
     *
     * @return The scene's primary root
     */
    private Group initializeRoot () {
        myPrimaryPane = new BorderPane();
        HBox toolbar = createToolbar();
        
        myPrimaryPane.setTop(toolbar);
        myPrimaryPane.setPrefSize(Constants.DEFAULT_WINDOW_SIZE.getWidth(),
                                  Constants.DEFAULT_WINDOW_SIZE.getHeight());

        Group root = new Group();
        root.getChildren().add(myPrimaryPane);

        return root;
    }
    
    /**
     * Creates a formatted tool bar to display in the top of the screen
     *
     * @return The HBox tool bar
     */
    private HBox createToolbar () {
        List<Button> buttons = createGameButtons();

        HBox toolbar = new HBox();
        toolbar.getChildren().addAll(buttons);
        toolbar.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        float insetHorizontal =
                Float.parseFloat(Constants.RESOURCES.getString("toolbarButtonInsetHorizontal"));
        float insetVertical =
                Float.parseFloat(Constants.RESOURCES.getString("toolbarButtonInsetVertical"));
        
        for (Button b : buttons) {
            HBox.setMargin(b, new Insets(insetHorizontal, insetVertical, insetHorizontal,
                                         insetVertical));
        }

        toolbar.setPrefHeight(Constants.TOOLBAR_HEIGHT);

        return toolbar;

    }
    
    /**
     * Creates a list of buttons to display in the tool bar
     *
     * @return The list of buttons
     */
    private List<Button> createGameButtons () {
        List<Button> list = new ArrayList<Button>();

        Button startButton = makeButton(Constants.RESOURCES.getString("toolbarButtonTitleStart"), event -> startGame());
        Button stopButton = makeButton(Constants.RESOURCES.getString("toolbarButtonTitleStop"), event -> stopGame());
        Button stepButton = makeButton(Constants.RESOURCES.getString("toolbarButtonTitleStep"), event -> stepGame());
        Button resetButton = makeButton(Constants.RESOURCES.getString("toolbarButtonTitleReset"), event -> resetGame());
        Button newGameButton = makeButton(Constants.RESOURCES.getString("toolbarButtonTitleNewGame"), event -> chooseNewGame());
        
        list.add(startButton);
        list.add(stopButton);
        list.add(stepButton);
        list.add(resetButton);
        list.add(newGameButton);

        return list;
        
    }
    
    /**
     * Creates a button that is set on action based on the parameter given
     * @param name - label of button
     * @param handler - ActionEvent the button will do
     * @return named and Button (which is set on action)
     */
    public Button makeButton (String name, EventHandler<ActionEvent> handler) {
    	Button button = new Button();
    	button.setText(name);
    	button.setOnAction(handler);
    	return button;
    	
    }
    
    /**
     * Event handler for starting the current game
     */
    private void startGame () {
    	if (myPrimaryGame != null){
    		myPrimaryGame.startGame();    		
    	}
    	
    }

    /**
     * Event handler for stopping the current game
     */
    private void stopGame () {
        if (myPrimaryGame != null){
        	myPrimaryGame.stopGame();
        }
        
    }

    /**
     * Event handler for a single step through a game
     */
    private void stepGame () {
        if (myPrimaryGame != null){
        	stopGame();
        	myPrimaryGame.getMyGrid().step();        	
        }
        
    }

    /**
     * Event handler for resetting the current game
     */
    private void resetGame () {
    	stopGame();
        myPrimaryGame.initializeGrid();
        displayPrimaryGame();
        
    }

    /**
     * Event handler for choosing a new game to start
     */
    private void chooseNewGame () {
    	stopGame();
    	
        File file = getFileFromUser();
        if (file != null) {
            setUpGame(file);
        }
        
    }
    
    /**
     * Specifies the available files the user can choose and returns it
     * @return File picked by user
     */
    private File getFileFromUser () {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle(Constants.RESOURCES.getString("fileChooserTitle"));
    	fileChooser.getExtensionFilters().add(
    			new ExtensionFilter(Constants.RESOURCES.getString("fileExtensionFilterDescription"),
    			Constants.RESOURCES.getString("fileExtensionFilterExtension")));
    	
    	return fileChooser.showOpenDialog(myPrimaryStage);
    	
    }
    
    /**
     * Constructs a new game based on a given file and switches to it
     *
     * @param file The file containing the game parameters
     */
    private void setUpGame (File file) {
        Map<String, String> params = parseXML(file);
        myPrimaryGame = new Game(params);
        displayPrimaryGame();
        
    }

    /**
     * Switches to a new game by displaying the game root in the center of the BorderPane
     *
     * @param myPrimaryGame The game to be displayed
     */
    private void displayPrimaryGame () {
        BorderPane.setAlignment(myPrimaryGame.getGameRoot(), Pos.TOP_LEFT);
        myPrimaryPane.setCenter(myPrimaryGame.getGameRoot());
        setStageSizeToMatchGrid(myPrimaryGame.getMyGrid().getMyGridSize());
        
    }

    /**
     * Takes a file and converts XML data to a Map of Grid parameters and their initial values
     *
     * @param file The file to parse
     * @return A map of grid parameter keys and values
     */
    private Map<String, String> parseXML (File file) {
        Parser parser = new Parser();
        return parser.parse(file);
        
    }

    /**
     * Adjusts stage size when grid size changes
     *
     * NOTE: this method does not deal with BorderPane insets, so sizing may be
     * off if insets are not 0. Can't call myPrimaryPane.getWidth() because this
     * is not set until layout
     *
     * @param gridDimension The dimension of the new grid
     */
    private void setStageSizeToMatchGrid (Dimension gridDimension) {
        int stageWidth = (int) gridDimension.getWidth();
        int stageHeight = (int) gridDimension.getHeight() + Constants.TOOLBAR_HEIGHT;

        if (stageWidth < Constants.DEFAULT_WINDOW_SIZE.getWidth()) {
            myPrimaryStage.setWidth(Constants.DEFAULT_WINDOW_SIZE.getWidth());
        }
        else {
            myPrimaryStage.setWidth(stageWidth);
        }

        if (stageHeight < Constants.DEFAULT_WINDOW_SIZE.getHeight()) {
            myPrimaryStage.setHeight(Constants.DEFAULT_WINDOW_SIZE.getHeight());
        }
        else {
            myPrimaryStage.setHeight(stageHeight);

        }

    }
	
}
