/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package main;

import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import constants.Constants;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


public class MainView {

    // Important numbers that shouldn't be changed by user
    public static final int TOOLBAR_HEIGHT = 50;
    public static final int GRID_VIEW_SIZE = 600;
    public static final int WINDOW_HEIGHT = GRID_VIEW_SIZE + TOOLBAR_HEIGHT;
    public static final int WINDOW_WIDTH = 800;
    public static final int TOOLBAR_BUTTON_INSET_HORIZONTAL = 10;
    public static final int TOOLBAR_BUTTON_INSET_VERTICAL = 2;

    private Stage myPrimaryStage;
    private BorderPane myPrimaryPane;
    private Group myPrimaryRoot;
    private MainController myController; 
    private DynamicChart myDynamicChart;
                                         

    /**
     * Instantiates all UI variables to be displayed
     * @param s - the Stage variable to be displayed
     */
    public MainView (Stage s) {
        myPrimaryStage = s;
        myPrimaryRoot = initializeRoot();

    }

    /**
     * Sets primaryStage (which displays primaryScene) and primaryScene (which displays
     * primaryRoot).
     *
     * PrimaryRoot contains a BorderPane with two groups
     * - A tool bar (start/stop/reset/newGame buttons - always present) called myPrimaryPane
     * - The game (contains varying Node elements depending on game and Grid type - not always
     * present)
     * which is returned from myPrimaryGame is displayGame()
     */
    public void display () {
        Scene myPrimaryScene = new Scene(myPrimaryRoot, WINDOW_WIDTH, WINDOW_HEIGHT, Color.WHITE);

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
//        createLineGraph();
        myPrimaryPane.setTop(toolbar);
       // myPrimaryPane.setRight(myDynamicChart.root);
        myPrimaryPane.setPrefSize(WINDOW_WIDTH, WINDOW_HEIGHT);

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
        List<Control> buttons = createGameControls();

        HBox toolbar = new HBox();
        toolbar.getChildren().addAll(buttons);
        toolbar.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        int insetHorizontal = TOOLBAR_BUTTON_INSET_HORIZONTAL;
        int insetVertical = TOOLBAR_BUTTON_INSET_VERTICAL;

        for (Control b : buttons) {
            HBox.setMargin(b, new Insets(insetHorizontal, insetVertical, insetHorizontal,
                                         insetVertical));
        }

        toolbar.setPrefHeight(TOOLBAR_HEIGHT);

        return toolbar;

    }

    private void createLineGraph (){
    	try {
    		myDynamicChart = new DynamicChart();
			myDynamicChart.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
 
    /**
     * Creates a list of buttons to display in the tool bar
     *
     * @return The list of buttons
     */
    private List<Control> createGameControls () {
        List<Control> list = new ArrayList<Control>();
    	
        Slider slider = new Slider(0, Float.parseFloat(Constants.RESOURCES.getString("sliderMaxValue")), 
        							Float.parseFloat(Constants.RESOURCES.getString("sliderDefaultValue")));

    	slider.valueProperty().addListener(e -> myController.setSpeed(slider.getValue()));

        Button startButton =
                makeButton(Constants.RESOURCES.getString("toolbarButtonTitleStart"),
                           event -> myController.startGame());
        Button stopButton =
                makeButton(Constants.RESOURCES.getString("toolbarButtonTitleStop"),
                           event -> myController.stopGame());
        Button stepButton =
                makeButton(Constants.RESOURCES.getString("toolbarButtonTitleStep"),
                           event -> myController.stepGame());
        Button resetButton =
                makeButton(Constants.RESOURCES.getString("toolbarButtonTitleReset"),
                           event -> myController.resetGame());
        Button newGameButton =
                makeButton(Constants.RESOURCES.getString("toolbarButtonTitleNewGame"),
                           event -> myController.chooseNewGame());
        Button saveXMLButton =
                makeButton(Constants.RESOURCES.getString("toolbarButtonTitleSaveXML"),
                           event -> myController.saveXML());

        list.add(startButton);
        list.add(stopButton);
        list.add(stepButton);
        list.add(slider);
        list.add(resetButton);
        list.add(newGameButton);
        list.add(saveXMLButton);

        return list;

    }

    /**
     * Creates a button that is set on action based on the parameter given
     * 
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
    
    
    public void addParameterButtons(VBox box) {
    	Button submit = makeButton("Submit", e-> myController.updateParams());
    	box.getChildren().add(submit);
    	myPrimaryPane.setLeft(box);
    
    }

    /**
     * Switches to a new game by displaying the game root in the center of the BorderPane
     *
     * @param myPrimaryGame The game to be displayed
     */
    public void displayGame (Group gameRoot) {
        BorderPane.setAlignment(gameRoot, Pos.TOP_LEFT);
        myPrimaryPane.setCenter(gameRoot);

        // TODO: deal with infinite scroll
        // setStageSizeToMatchGrid(myPrimaryGame.getMyGrid().getMyGridSize());

    }

    /**
     * Specifies the available files the user can choose and returns it
     * 
     * @return File picked by user
     */
    public File getFileFromUser () {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(Constants.RESOURCES.getString("fileChooserTitle"));
        fileChooser.getExtensionFilters().add(
                                              new ExtensionFilter(Constants.RESOURCES
                                                      .getString("fileExtensionFilterDescription"),
                                                                  Constants.RESOURCES
                                                                          .getString("fileExtensionFilterExtension")));

        return fileChooser.showOpenDialog(myPrimaryStage);

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
        int stageHeight = (int) gridDimension.getHeight() + TOOLBAR_HEIGHT;

        if (stageWidth < WINDOW_WIDTH) {
            myPrimaryStage.setWidth(WINDOW_WIDTH);
        }
        else {
            myPrimaryStage.setWidth(stageWidth);
        }

        if (stageHeight < WINDOW_HEIGHT) {
            myPrimaryStage.setHeight(WINDOW_HEIGHT);
        }
        else {
            myPrimaryStage.setHeight(stageHeight);

        }

    }

    public void setController (MainController controller) {
        myController = controller;
    }

}
