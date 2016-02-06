/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package main;

import java.awt.Dimension;
import java.io.File;
import java.util.*;
import constants.Constants;
import game.Game;
import input.Parser;
import javafx.application.Application;
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
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


public class Main extends Application {

    // Model
    private Game myPrimaryGame;

    // View
    private Stage myPrimaryStage;
    private Scene myPrimaryScene;
    private BorderPane myPrimaryPane;

    /**
     * Sets primaryStage (which displays primaryScene) and primaryScene (which displays
     * primaryRoot).
     * primaryRoot contains a BorderPane with two groups: toolbar (start/stop/reset/newGame buttons
     * - always present)
     * and gameRoot, which contains varying Node elements depending on game type and Grid type (not
     * always present)
     */
    @Override
    public void start (Stage s) throws Exception {

        myPrimaryStage = s;

        Group primaryRoot = initializeRoot();

        // TODO: put color in resource file??
        myPrimaryScene =
                new Scene(primaryRoot, Constants.DEFAULT_WINDOW_SIZE.getWidth(),
                          Constants.DEFAULT_WINDOW_SIZE.getHeight(), Color.WHITE);
        myPrimaryStage.setScene(myPrimaryScene);
        myPrimaryStage.show();
    }

    public static void main (String[] args) {
        launch(args);
    }

    /**
     * Initializes the primary root with a BorderPane
     * The BorderPane is initialized with an HBox toolbar on top to hold all the game buttons that
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

        Group primaryRoot = new Group();
        primaryRoot.getChildren().add(myPrimaryPane);

        return primaryRoot;
    }

    /**
     * Creates a toolbar to display in the top of the screen
     * 
     * @return The HBox toolbar
     */
    private HBox createToolbar () {
        List<Button> buttons = createGameButtons();

        HBox toolbar = new HBox();
        toolbar.getChildren().addAll(buttons);
        toolbar.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        // TODO: maybe this should be css instead
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
     * Creates a list of buttons to display in the toolbar
     * 
     * @return The list of buttons
     */
    private List<Button> createGameButtons () {
        List<Button> list = new ArrayList<Button>();

        Button startButton = new Button(Constants.RESOURCES.getString("toolbarButtonTitleStart"));
        startButton.setOnAction(e -> startGame());
        Button stopButton = new Button(Constants.RESOURCES.getString("toolbarButtonTitleStop"));
        stopButton.setOnAction(e -> stopGame());
        Button stepButton = new Button(Constants.RESOURCES.getString("toolbarButtonTitleStep"));
        stepButton.setOnAction(e -> stepGame());
        Button resetButton = new Button(Constants.RESOURCES.getString("toolbarButtonTitleReset"));
        resetButton.setOnAction(e -> resetGame());
        Button newGameButton =
                new Button(Constants.RESOURCES.getString("toolbarButtonTitleNewGame"));
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
    private void startGame () {
        myPrimaryGame.startGame();
    }

    /**
     * Event handler for stopping the current game
     */
    private void stopGame () {
        myPrimaryGame.stopGame();
    }

    /**
     * Event handler for a single step through a game
     */
    private void stepGame () {
        myPrimaryGame.getMyGrid().step();
    }

    /**
     * Event handler for resetting the current game
     */
    private void resetGame () {
        myPrimaryGame.initializeGrid();
        switchToGame(myPrimaryGame);
    }

    /**
     * Event handler for choosing a new game to start
     */
    private void chooseNewGame () {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(Constants.RESOURCES.getString("fileChooserTitle"));
        // TODO: put these constants in resource file?
        fileChooser.getExtensionFilters().add(new ExtensionFilter("XML files", "*.xml"));
        File file = fileChooser.showOpenDialog(myPrimaryStage);
        if (file != null) {
            setUpGame(file);
        }
    }

    /**
     * Constructs a new game based on a given file and switches to it
     * 
     * @param file The file containing the game parameters
     */
    private void setUpGame (File file) {
        Map<String, String> params = parseXML(file);
        myPrimaryGame = new Game(params);
        switchToGame(myPrimaryGame);
    }

    /**
     * Switches to a new game by displaying the game root in the center of the BorderPane
     * 
     * @param myPrimaryGame The game to be displayed
     */
    private void switchToGame (Game game) {
        BorderPane.setAlignment(game.getGameRoot(), Pos.TOP_LEFT);

        myPrimaryPane.setCenter(game.getGameRoot());

        setStageSizeToMatchGrid(game.getMyGrid().getMyGridSize());
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
     * @param width New grid width
     * @param height New grid height
     */
    private void setStageSizeToMatchGrid (Dimension d) {
        int width = (int) d.getWidth();
        int height = (int) d.getHeight();

        if (width < Constants.DEFAULT_WINDOW_SIZE.getWidth()) {
            myPrimaryStage.setWidth(Constants.DEFAULT_WINDOW_SIZE.getWidth());
        }
        else {
            myPrimaryStage.setWidth(width);
        }

        int stageHeight = height + Constants.TOOLBAR_HEIGHT;
        if (stageHeight < Constants.DEFAULT_WINDOW_SIZE.getHeight()) {
            myPrimaryStage.setHeight(Constants.DEFAULT_WINDOW_SIZE.getHeight());
        }
        else {
            myPrimaryStage.setHeight(stageHeight);

        }

    }

}
