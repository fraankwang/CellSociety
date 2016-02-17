// This entire file is part of my masterpiece
// FRANK WANG

/**
 * This is also part of my code masterpiece. This works in close conjunction with the SimulationManager
 * class as the controller is mostly controlling elements within SimulationManager. It acts in many ways
 * as a middleman between MainView and SimulationManager by calling display functions in MainView on UI components
 * (like Group roots) from the SimulationManager getters. The original simulation is actually instantiated in the
 * setUpSimulation function, which is after a user selects an XML to be parsed, which can be passed into the 
 * SimulationManager constructor so that a proper simulation is constructed from the parameters. 
 * 
 * I did not include MainView in the masterpiece because I believe it is very simple and only displays
 * UI elements taken from the MainController, which is why I decided to only include the classes that
 * hold the primary interactions so that the user can actually view how the model looks.
 * 
 */

/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package main;

import java.io.File;
import java.util.Map;
import constants.Constants;
import constants.Parameters;
import inputoutput.Parser;
import inputoutput.XMLGenerator;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import manager.SimulationManager;
import javafx.scene.control.ButtonType;


public class MainController {
    private MainView myView;
    private SimulationManager mySimulation;

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
     * Event handler for starting the current Simulation 
     */
    public void startSimulation () {
    	if (mySimulation != null) {
    		mySimulation.startGame();
        
    	}

    }

    /**
     * Event handler for stopping the current Simulation 
     */
    public void stopSimulation () {
    	if (mySimulation != null) {
    		mySimulation.stopGame();
    	}

    }

    /**
     * Event handler for a single step through a Simulation 
     */
    public void stepGame () {
    	if (mySimulation != null) {
    		stopSimulation();
    		mySimulation.getMyGrid().step();
    	
    	}

    }

    /**
     * Event handler for resetting the current Simulation. Changes cell size for re-initializing 
     * GridView as user may have changed cell size so resetting maintains current cell size. 
     */
    public void resetGame () {
    	
    	if (mySimulation != null) {
    		stopSimulation();
    		
    		int currentCellSize = mySimulation.getMyGrid().getMyGridView().getMyCellSize();
    		mySimulation.changeCellSizeParameter(currentCellSize);
    		mySimulation.initializeGrid(currentCellSize);
    		mySimulation.resetGraph();
    		
    		myView.displayGame(mySimulation.getSimulationRoot());
    		myView.displayParameters(mySimulation.getMyUIRoot());
    		myView.displayLineChart(mySimulation.getLineChartRoot());
    		
    	}
    	
    }


    /**
     * Event handler for choosing a new simulation to start
     */
    public void chooseNewGame () {

        File file = myView.getFileFromUser();
        if (file != null) {
            setUpSimulation(file);
        }

    }

    /**
     * Tells mySimulation to change relevant variables
     * @param type
     */
    public void setCellShape (String type) {
    	stopSimulation();
    	if (mySimulation != null) {
    		mySimulation.changeCellShape(type);
    		myView.displayGame(mySimulation.getSimulationRoot());
    	
    	}
    	
    }

    /**

	 * Changes GridView's cell size parameter and updates UI display
	 * @param increment
	 */
	public void incrementCellSize (boolean increment) {
		if (mySimulation != null) {
			mySimulation.changeCellSize(increment);
		
			if (myView != null) {
				myView.displayGame(mySimulation.getSimulationRoot());
				
			}
		}
		
	}
	
	/**
     * Changes neighbor directions and reinitializes neighbors
     *
     * @param neighborDirections
     */

    public void setNeighborDirections(String neighborDirections) {
    	if (mySimulation != null) {
    		mySimulation.setNeighborDirections(neighborDirections);
    	}

    }

	/**
     * Constructs a new Simulation based on a given file and switches to it
     * @param file The file containing the Simulation parameters
     */
    private void setUpSimulation (File file) {
        Parameters params = parseXML(file);
        
        mySimulation = new SimulationManager(params);
        
        myView.displayGame(mySimulation.getSimulationRoot());
        myView.displayParameters(mySimulation.getMyUIRoot());
        myView.displayLineChart(mySimulation.getLineChartRoot());

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
     * Calls myGrid in mySimulation to return updated Simulation parameters, then
     * adds Simulation parameters that are not visible to myGrid, then generating the .xml file
     * Delay is not accessible from the grid. If the user modifies delay time, then it will
     * be included in currentGameState. If not, the default is used.
     */
    public void saveXML () {
        XMLGenerator generator = new XMLGenerator();

        if (mySimulation != null) {
	        Map<String, String> currentGameState = mySimulation.getMyGrid().getMyGameState();
	        currentGameState.put("gameType", mySimulation.getMyGameType());
	
	        if (!currentGameState.containsKey("delay")) {
	            currentGameState.put("delay", Double.toString(mySimulation.getDelay()));
	        }
	
	        int[][] currentStates = mySimulation.getMyGrid().getCurrentStatesArray();
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
    public void setAnimationSpeed (double speed){
    	if (mySimulation != null){
    		mySimulation.setTimelineRate(speed);
    	}
    }
        


}
