/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cellsociety_team03;

import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.util.Duration;

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

		} else if (myGameType.equals("Game of Life")){
			myGrid = new GameOfLifeGrid(myParameters);
			
		} else if (myGameType.equals("Segregation")){
			myGrid = new SegregationGrid(myParameters);
			
		} else if (myGameType.equals("PredatorPrey")){
			myGrid = new PredatorPreyGrid(myParameters);
		}
		
		myGameRoot = myGrid.getRoot();
	}
	
	private void initializeGameLoop(){
		//1000 milliseconds / 60 frames/second is Duvall's original MILLISECOND_DELAY variable
		//1.0 seconds /60 frame/second is Duvall's original SECOND_DELAY variable in the step function 
    	KeyFrame frame = new KeyFrame(Duration.millis(1000/60),
    			e -> myGrid.step(1.0/60));
		gameLoop = new Timeline();
		gameLoop.setCycleCount(Timeline.INDEFINITE);
		gameLoop.getKeyFrames().add(frame);
	}
	
	public void startGame(){
		if(gameLoop != null) gameLoop.play();
	}
	public void stopGame(){
		if(gameLoop != null) gameLoop.stop();
	}
	
	public Group getGameRoot(){
		return this.myGameRoot;
	}
	
}
