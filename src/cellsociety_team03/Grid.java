/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cellsociety_team03;

import java.util.Map;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public abstract class Grid {
	protected GridCell[][] myCells;
	private int myColumns;
	private int myRows;
	private long delay;
	private Group myRoot;
	private GridPane myGridPane;
	private Map<String,String> myParameters;
	private int CELL_SIZE = 50;
	
	/**
	 * Reads the parameters passed to the constructor
	 * Initializes the 2D Cell array and corresponding GridPane Node 
	 * @param params
	 */
	public Grid(Map<String,String> params){
		myParameters = params;
		myRows = Integer.parseInt(params.get("rows"));
		myColumns = Integer.parseInt(params.get("columns"));
		delay = Long.parseLong(params.get("delay"));
		myRoot = new Group();
		
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
		myCells = new GridCell[myRows][myColumns];
		
		// TODO read myParameters to determine initial set up
		
		for (int r = 0; r < myCells.length; r++){
			for (int c = 0; c < myCells[0].length; c++){
				myCells[r][c] = new SimpleCell(State.BURNING, CELL_SIZE, new Rectangle(30, 30));
				if (r % 3 == 0) myCells[r][c] = new SimpleCell(State.TREE, CELL_SIZE, new Rectangle(30, 30)); 
			}
		}
		
		System.out.println("Cells initialized");
		createBoard();
	}
	
	/**
	 * Updates the visible Pane by mapping the the cells from myCells in the same location in the 2D array
	 */
	private void createBoard(){
		myGridPane = new GridPane();
		
		// TODO: figure out dimensions to resize myGridPane to fit with UI
		myGridPane.setPrefSize(150, 150);
		
		for (int r = 0; r < myCells.length; r++){
			for (int c = 0; c < myCells[0].length; c++){
				myGridPane.add(myCells[r][c].getMyShape(),c,r);
			}
		}
		
		System.out.println("GridPane initialized and added to root");
		myRoot.getChildren().add(myGridPane);
		
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

	public int getColumns() {
		return myColumns;
	}

	public void setColumns(int gridWidth) {
		this.myColumns = gridWidth;
	}

	public int getRows() {
		return myRows;
	}

	public void setRows(int gridHeight) {
		this.myRows = gridHeight;
	}

	public Group getRoot() {
		return myRoot;
	}

	public void setRoot(Group root) {
		this.myRoot = root;
	}
	
	
	
	
	
}
