/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cellsociety_team03;

import java.util.Map;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;

public abstract class Grid {
	GridCell[][] myCells;
	int myGridWidth;
	int myGridHeight;
	Group myRoot;
	GridPane myGridPane;
	Map<String,String> myParameters;
	
	/**
	 * Reads the parameters passed to the constructor
	 * Initializes the 2D Cell array and corresponding GridPane Node 
	 * @param params
	 */
	public Grid(Map<String,String> params){
		myParameters = params;
		// read params
			// initializes Cells
			// TODO: (for advanced specifications, create Buttons/Sliding Bars for UI)
		
		initializeCells();
	}
	
	/**
	 * Specifies what happens at each "step" of the game:
	 * 1. Each Cell determines its own next State
	 * 2. Each Cell is set to its next State
	 * @param elapsedTime
	 */
	public void step(Double elapsedTime){
		setCellStates();
		updateCellStates();
	}
	
	/**
	 * Each subclass of Grid determines its own algorithms for determining each Cell's next state
	 */
	protected abstract void setCellStates();
	
	
	/**
	 * Loop through myCells and set each Cell's currentState to its nextState and reset nextState to null
	 */
	private void updateCellStates(){
		for (int r = 0; r < myCells.length; r++){
			for (int c = 0; c < myCells[0].length; c++){
				myCells[r][c].transitionStates();
			}
		}
	}
	
	
	/**
	 * Initializes and populates myCells given the initial grid set up parameters
	 */
	private void initializeCells(){
		// read myParameters to determine initial set up
		createBoard();
	}
	
	/**
	 * Updates the visible Pane by mapping the the cells from myCells in the same location in the 2D array
	 */
	private void createBoard(){
		myGridPane = new GridPane();
		
		// TODO: figure out dimensions to resize myGridPane to fit with UI
		myGridPane.setPrefSize(500, 500);
		
		for (int r = 0; r < myCells.length; r++){
			for (int c = 0; c < myCells[0].length; c++){
				myGridPane.add(myCells[r][c].getMyShape(),r,c);
			}
		}
	}

	private void handleMouseInput(ActionEvent e){
		
	}
	
	private void handleKeyEntry(ActionEvent e){
		
	}
	
	public GridCell[][] getMyCells() {
		return myCells;
	}

	public void setMyCells(GridCell[][] myCells) {
		this.myCells = myCells;
	}

	public int getGridWidth() {
		return myGridWidth;
	}

	public void setGridWidth(int gridWidth) {
		this.myGridWidth = gridWidth;
	}

	public int getGridHeight() {
		return myGridHeight;
	}

	public void setGridHeight(int gridHeight) {
		this.myGridHeight = gridHeight;
	}

	public Group getRoot() {
		return myRoot;
	}

	public void setRoot(Group root) {
		this.myRoot = root;
	}
	
	
	
	
	
}
