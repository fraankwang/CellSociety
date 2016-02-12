/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package controllers;

import java.io.File;
import java.util.Map;
import constants.Constants;
import constants.Parameters;
import game.Game;
import inputoutput.Parser;
import inputoutput.XMLGenerator;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import view.MainView;


public class MainController {
    private MainView myView;
    private Game myPrimaryGame;

    public MainController (MainView view) {
        myView = view;
    }

    public void start(){
        myView.display();
    }
    /**
     * Event handler for starting the current game
     */
    public void startGame () {
        if (myPrimaryGame != null) {
            myPrimaryGame.startGame();
        }

    }

    /**
     * Event handler for stopping the current game
     */
    public void stopGame () {
        if (myPrimaryGame != null) {
            myPrimaryGame.stopGame();
        }

    }

    /**
     * Event handler for a single step through a game
     */
    public void stepGame () {
        if (myPrimaryGame != null) {
            stopGame();
            myPrimaryGame.getMyGrid().step();
        }

    }

    /**
     * Event handler for resetting the current game
     */
    public void resetGame () {
        stopGame();
        myPrimaryGame.initializeGrid();
        myView.displayGame(myPrimaryGame.getGameRoot());

    }

    /**
     * Event handler for choosing a new game to start
     */
    public void chooseNewGame () {
        stopGame();

        File file = myView.getFileFromUser();
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
        Parameters params = parseXML(file);
        myPrimaryGame = new Game(params);
        myView.displayGame(myPrimaryGame.getGameRoot());
    }

    /**
     * Takes a file and converts XML data to a Map of Grid parameters and their initial values
     *
     * @param file The file to parse
     * @return A map of grid parameter keys and values
     */
    private Parameters parseXML (File file) {
        Parser parser = new Parser();
        return parser.parse(file);

    }

    /**
     * Calls myGrid in myPrimaryGame to return updated game parameters, then
     * adds game parameters that are not visible to myGrid, then generating the .xml file
     * Delay is not accessible from the grid. If the user modifies delay time, then it will
     * be included in currentGameState. If not, the default is used.
     */
    public void saveXML () {
        XMLGenerator generator = new XMLGenerator();

        if (myPrimaryGame != null) {
            Map<String, String> currentGameState = myPrimaryGame.getMyGrid().getMyGameState();
            currentGameState.put("gameType", myPrimaryGame.getMyGameType());
            
            if (!currentGameState.containsKey("delay")) {
            	currentGameState.put("delay", Double.toString(myPrimaryGame.getDelay()));            	
            }
            
            generator.writeXML(currentGameState);
        }

        String confirmation = Constants.RESOURCES.getString("XMLSavedConfirmation");
        Alert savedAlert = new Alert(AlertType.INFORMATION, confirmation, new ButtonType("OK"));
        savedAlert.showAndWait();
    }

    
    public void setSpeed (double time){
    	myPrimaryGame.setTimelineRate(time);
    }
}
