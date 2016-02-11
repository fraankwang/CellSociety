/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package game;

import java.util.Map;
import grids.FireGrid;
import grids.GameOfLifeGrid;
import grids.Grid;
import grids.PredatorPreyGrid;
import grids.SegregationGrid;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.util.Duration;


/**
 * Manages the simulation - in charge of the game loop and grid subclass
 *
 */
public class Game {
    public static final int MILLISECONDS_PER_SECOND = 1000;

    private String myGameType;
    private Grid myGrid;
    private Map<String, String> myParameters;

    private Group myGameRoot;
    private Timeline myGameLoop;

    /**
     * Given parsed XML data, construct appropriate Grid subclass and gameLoop
     *
     * @param params A map containing parsed XML data
     */
    public Game (Map<String, String> params) {
        myGameType = params.get("gameType");
        myParameters = params;

        initializeGrid();
        initializeGameLoop();
        
    }

    /**
     * Initializes a specific grid based on the gameType parameter, and
     * updates myGameRoot after myGrid updates its local root
     * This function uses only global variables so the user can press the reset
     * button (in the Main class) at any time
     */
    public void initializeGrid () {
        if (myGameType.equals("Fire")) {
            myGrid = new FireGrid(myParameters);

        }
        else if (myGameType.equals("GameOfLife")) {
            myGrid = new GameOfLifeGrid(myParameters);

        }
        else if (myGameType.equals("Segregation")) {
            myGrid = new SegregationGrid(myParameters);

        }
        else if (myGameType.equals("PredatorPrey")) {
            myGrid = new PredatorPreyGrid(myParameters);
        }

        myGameRoot = myGrid.getRoot();
        
    }

    /**
     * Initialize the game loop, using the delay parameter from xml to determine speed
     * In each KeyFrame of the animation, myGameLoop calls the step() function from myGrid
     */
    private void initializeGameLoop () {
        // "delay" given in milliseconds
        double framesPerSecond =
                MILLISECONDS_PER_SECOND * 1 / Double.parseDouble(myParameters.get("delay"));
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECONDS_PER_SECOND / framesPerSecond),
                                      e -> myGrid.step());
        myGameLoop = new Timeline();
        myGameLoop.setCycleCount(Animation.INDEFINITE);
        myGameLoop.getKeyFrames().add(frame);
    }

    /**
     * Start the game loop
     */
    public void startGame () {
        if (myGameLoop != null) {
            myGameLoop.play();
        }
        
    }

    /**
     * End the game loop
     */
    public void stopGame () {
        if (myGameLoop != null) {
            myGameLoop.stop();
        }
        
    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================

    
    public Group getGameRoot () {
        return myGameRoot;
    }

    public Grid getMyGrid () {
        return myGrid;
    }

	public Map<String, String> getMyParameters () {
		return myParameters;
	}
	
	public String getDelay () {
		return myParameters.get("delay");
	}

	public String getMyGameType () {
		return myGameType;
	}
}
