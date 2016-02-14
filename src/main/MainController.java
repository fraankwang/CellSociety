/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package main;

import java.io.File;
import java.util.Map;
import constants.Constants;
import constants.Parameters;
import game.Game;
import inputoutput.Parser;
import inputoutput.XMLGenerator;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


public class MainController {
    private MainView myView;
    private Game myPrimaryGame;

    public MainController (MainView view) {
        myView = view;
    }

    /**
     * Kickoff method that shows the primaryScene on the primaryStage in MainView
     */
    public void start () {
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
     * Event handler for resetting the current game. Changes cell size for re-initializing
     * GridView as user may have changed cell size so resetting maintains current cell size.
     */
    public void resetGame () {

        if (myPrimaryGame != null) {
            stopGame();

            int currentCellSize = myPrimaryGame.getMyGrid().getMyGridView().getMyCellSize();
            myPrimaryGame.changeCellSizeParameter(currentCellSize);
            myPrimaryGame.initializeGrid(currentCellSize);

            myView.displayGame(myPrimaryGame.getGameRoot());
            myView.displayParameters(myPrimaryGame.getMyUIRoot());

        }

    }

    /**
     * Event handler for choosing a new game to start
     */
    public void chooseNewGame () {

        File file = myView.getFileFromUser();
        if (file != null) {
            setUpGame(file);
        }

    }

    /**
     * Tells myPrimaryGame to change relevant variables
     *
     * @param type
     */
    public void setCellShape (String type) {
        stopGame();
        if (myPrimaryGame != null) {
            myPrimaryGame.changeCellShape(type);
            myView.displayGame(myPrimaryGame.getGameRoot());

        }

    }

    /**
     * Changes GridView's cell size parameter and updates UI display
     *
     * @param increment
     */
    public void incrementCellSize (boolean increment) {
        if (myPrimaryGame != null) {
            myPrimaryGame.changeCellSize(increment);

            if (myView != null) {
                myView.displayGame(myPrimaryGame.getGameRoot());

            }
        }

    }

    /**
     * Changes neighbor directions and reinitializes neighbors
     *
     * @param neighborDirections
     */
    public void setNeighborDirections (String neighborDirections) {
        if (myPrimaryGame != null) {
            myPrimaryGame.setNeighborDirections(neighborDirections);
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
        myView.displayLineChart(myPrimaryGame.getLineChartRoot());
        myView.displayParameters(myPrimaryGame.getMyUIRoot());

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

            int[][] currentStates = myPrimaryGame.getMyGrid().getCurrentStatesArray();
            generator.writeXML(currentGameState, currentStates);

            String confirmation = Constants.RESOURCES.getString("XMLSavedConfirmation");
            Alert savedAlert = new Alert(AlertType.INFORMATION, confirmation, new ButtonType("OK"));
            savedAlert.showAndWait();
        }

    }

    /**
     * Reads user input and sets animation speed to given rate
     *
     *
     * @param speed how fast the animation should go
     */
    public void setAnimationSpeed (double speed) {
        if (myPrimaryGame != null) {
            myPrimaryGame.setTimelineRate(speed);
        }
    }

    public void updateParams () {
        // myPrimaryGame.getMyGrid().updateParameters();
    }

}
