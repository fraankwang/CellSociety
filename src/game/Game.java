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
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.util.Duration;

/**
 * 
 * 
 *
 */
public class Game {
	private String myGameType;
	private Grid myGrid;
	private Map<String,String> myParameters;
	
	private Group myGameRoot;
	private Timeline gameLoop;
	
	/**
	 * Given parsed XML data, construct appropriate subclass of Grid and specify KeyFrame based on created Grid
	 * @param type
	 * @param params
	 */
	public Game(Map<String,String> params){
		myGameType = params.get("Title");
		myParameters = params;
		
		System.out.println("Initializing grid");
		initializeGrid();
		initializeGameLoop();
	}
	
	/**
	 * Initializes game parameters based on myParameters, updates myGameRoot after myGrid updates its local root
	 * This function uses only global variables so the user can press the reset button (in the Main class) at any time
	 */
	public void initializeGrid(){
		if (myGameType.equals("Fire")){
			myGrid = new FireGrid(myParameters); 

		} else if (myGameType.equals("GameOfLife")){
			myGrid = new GameOfLifeGrid(myParameters);
			
		} else if (myGameType.equals("Segregation")){
			myGrid = new SegregationGrid(myParameters);
			
		} else if (myGameType.equals("PredatorPrey")){
			myGrid = new PredatorPreyGrid(myParameters);
		}
		
		System.out.println(myGameType + "Grid initialized");
		myGameRoot = myGrid.getRoot();
		System.out.println("myGameRoot initialized");
	}
	
	/**
	 * Initialize the game loop, using the delay parameter from xml to determine speed
	 */
	private void initializeGameLoop(){
		// "delay" given in milliseconds
	        double framesPerSecond = 1000 * 1 / Double.parseDouble(myParameters.get("delay"));
	        KeyFrame frame = new KeyFrame(Duration.millis(1000/framesPerSecond),
    			e -> myGrid.step());
		gameLoop = new Timeline();
		gameLoop.setCycleCount(Timeline.INDEFINITE);
		gameLoop.getKeyFrames().add(frame);
	}
	
	/**
	 * Start the game loop
	 */
	public void startGame(){
		if(gameLoop != null) gameLoop.play();
	}
	
	/**
	 * End the game loop
	 */
	public void stopGame(){
		if(gameLoop != null) gameLoop.stop();
	}
	
	
	public Group getGameRoot(){
		return this.myGameRoot;
	}
	
	public Grid getMyGrid(){
	    return this.myGrid;
	}
	
}