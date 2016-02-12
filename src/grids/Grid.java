/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package grids;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cells.GridCell;
import constants.Location;
import constants.NeighborOffset;
import constants.Offset;
import constants.Parameters;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;

/**
 * Abstract class representing a grid to be used for the simulation
 * In charge of determining how to update itself for each step of the simulation
 */
public abstract class Grid {

    // Model
    private int[][] myInitialStates;
    private int myColumns;
    private int myRows;
    private GridCell[][] myCells;

    // View
    private Dimension myGridSize;
    private int myCellSize;
    private Group myRoot;
    private GridPane myGridPane;


    /**
     * Constructor - initializes a Grid based on parameters from xml
     *
     * @param params Map of xml parameters
     */
    public Grid (Parameters params) {
        myGridSize = params.getDimension();
                
        myRows = params.getRows();
        myColumns = params.getColumns();
        myCellSize = params.getCellSize();
        myInitialStates = params.getInitialStates();
                
        
        // TODO: (for advanced specifications, create Buttons/Sliding Bars for UI)

        initialize();
        
    }

    // =========================================================================
    // Initialization
    // =========================================================================

    /**
     * Initializes the grid model (2d array) and grid UI (GridPane)
     */
    protected void initialize () {
        initializeCells();
        createGridPane();
        
    }

    /**
     * Initializes and populates myCells by calling initializeCell for each cell
     */
    private void initializeCells () {
        myCells = new GridCell[myRows][myColumns];
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                initializeCell(row, col);
            }
        }
        
    }

    /**
     * Each grid subclass determines how to initialize each cell based on myInitialStates
     *
     * @param row The row of the cell in the grid
     * @param column The column of the cell in the grid
     */
    protected abstract void initializeCell (int row, int column);

//    /**
//     * Creates an 2d int array based on a comma separated string from xml
//     *
//     * @param param The comma separated string
//     * @return The 2d array
//     */
//    private int[][] createInitialStatesArray (String param) {
//        int[][] initialStates = new int[myRows][myColumns];
//        String[] parsed = param.split(",");
//
//        for (int row = 0; row < parsed.length; row++) {
//            String s = parsed[row];
//            for (int col = 0; col < s.length(); col++) {
//                int state = Character.getNumericValue(s.charAt(col));
//                initialStates[row][col] = state;
//            }
//        }
//
//        return initialStates;
//        
//    }

    /**
     * Updates the visible GridPane by mapping the the cells from myCells to the same location in
     * the 2D array. GridCell's myShape attribute is set to toggle it's state on mouse click
     */
    private void createGridPane () {
        myGridPane = new GridPane();
        myGridPane.setPrefSize(myGridSize.getWidth(), myGridSize.getHeight());

        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
            	GridCell cell = myCells[row][col];
            	cell.getMyShape().setOnMouseClicked(e -> toggleState(cell));
                myGridPane.add(cell.getMyShape(), col, row);
            }
        }

        myRoot = new Group();
        myRoot.getChildren().add(myGridPane);
        
    }

    /**
     * Action to be carried out when GridCell's shape is clicked. Abstracted
     * so subclasses of Grid can toggle between only relevant states.
     * @param cell - the cell whose states will be toggled
     */
    protected abstract void toggleState (GridCell cell);
    // =========================================================================
    // Simulation
    // =========================================================================

    /**
     * Specifies what happens at each "step" of the game:
     * 1. Each Cell determines its own next State
     * 2. Each Cell is set to its next State
     *
     */
    public void step () {
        setCellStates();
        updateCellStates();
        
    }

    /**
     * Loops through each cell in the grid and updates its next state
     */
    protected void setCellStates () {
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                GridCell cell = myCells[row][col];
                this.setCellState(cell);
            }
        }

    }

    /**
     * Each subclass of Grid determines its own algorithms for determining each GridCell's next
     * state
     *
     * @param cell The GridCell
     */
    protected abstract void setCellState (GridCell cell);

    /**
     * Loop through myCells and set transition each cell to its next state
     */
    private void updateCellStates () {        
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                myCells[row][col].transitionStates();
            }
        }
        
    }
    
    /**
     * Returns a list of a GridCell's neighboring GridCells
     *
     * @param cell The cell of interest
     * @return The list of neighboring GridCells
     */
    protected List<GridCell> getNeighbors (GridCell cell) {

        List<Offset> offsets = neighborOffsets();
        List<GridCell> neighbors = new ArrayList<GridCell>();

        for (Offset offset : offsets) {
            Location neighborLocation = neighborLocationToroidal(cell, offset);
            
            if (cellInBounds(neighborLocation)) {
                neighbors.add(myCells[neighborLocation.getRow()][neighborLocation.getCol()]);

            }
        }

        return neighbors;
        
    }
    
    /**
     * Used by getNeighbors for a toroidal grid (I think it also works for finite)
     * @param cell GridCell
     * @param offset Offset where potential neighbor is located
     * @return Location of neighbor at offset from cell
     */
    private Location neighborLocationToroidal(GridCell cell, Offset offset){
       int neighborRow = (cell.getMyGridLocation().getRow() + offset.getRow()) % myRows;
       int neighborCol = (cell.getMyGridLocation().getCol() + offset.getCol()) % myColumns;

       return new Location(neighborRow, neighborCol);
    }
    
    //TODO: infinite grid
    //private Location neighborLocationInfinite
    // or private Location getNeighborsInfinite
    
    /**
     * Returns a list of offsets to check to find a GridCell's neighbors
     *
     * @return The list of offsets
     */
    protected List<Offset> neighborOffsets () {

        List<Offset> offsets = new ArrayList<Offset>();

        offsets.add(NeighborOffset.TOP_LEFT.getOffset());
        offsets.add(NeighborOffset.TOP.getOffset());
        offsets.add(NeighborOffset.TOP_RIGHT.getOffset());
        offsets.add(NeighborOffset.LEFT.getOffset());
        offsets.add(NeighborOffset.RIGHT.getOffset());
        offsets.add(NeighborOffset.BOTTOM_LEFT.getOffset());
        offsets.add(NeighborOffset.BOTTOM.getOffset());
        offsets.add(NeighborOffset.BOTTOM_RIGHT.getOffset());

        return offsets;

    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================

    /**
	 * Determines whether a grid index is out of bounds
	 *
	 * @param row The row to check
	 * @param col The column to check
	 * @return A boolean indicating whether a cell in that position would be out of bounds
	 */
	protected boolean cellInBounds (Location location) {
	    int row = location.getRow();
	    int col = location.getCol();
	    
	    boolean farTop = row < 0;
	    boolean farBottom = row > getRows()-1;
	    boolean farLeft = col < 0;
	    boolean farRight = col > getColumns()-1;
	
	    return !(farTop | farBottom | farLeft | farRight);
	    
	}

	/**
     * Aggregates current game parameters to be saved to an XML file.
     * Subclasses of Grid override the method by adding additional simulation-specific parameters
     * Grid does not know the delay time, which is gathered from Game
     * @return current game state's parameters that are common to all Grid types
     */
    public Map<String,String> getMyGameState () {
    	Map<String,String> gameStateParams = new HashMap<String,String>();
    	gameStateParams.put("rows", Integer.toString(this.getRows()));
    	gameStateParams.put("columns", Integer.toString(this.getColumns()));
    	gameStateParams.put("width", Integer.toString( (int) this.getMyGridSize().getWidth()));
    	gameStateParams.put("height", Integer.toString( (int) this.getMyGridSize().getHeight()));
    	gameStateParams.put("initialStates", getCurrentStatesArrayString());
    	return gameStateParams;
    	
    }
    
    public GridCell[][] getMyCells () {
        return myCells;
    }

    public void setMyCells (GridCell[][] myCells) {
        this.myCells = myCells;
    }

    public int getColumns () {
        return myColumns;
    }

    public void setColumns (int gridWidth) {
        myColumns = gridWidth;
    }

    public int getRows () {
        return myRows;
    }

    public void setRows (int gridHeight) {
        myRows = gridHeight;
    }

    public Group getRoot () {
        return myRoot;
    }

    public void setRoot (Group root) {
        this.myRoot = root;
    }
    
    public Dimension getMyGridSize () {
        return myGridSize;
    }

    protected void setMyGridSize (Dimension gridSize) {
        myGridSize = gridSize;
    }

    protected int getMyCellSize () {
        return myCellSize;
    }

    protected void setMyCellSize (int cellSize) {
        myCellSize = cellSize;
    }

    protected int[][] getMyInitialStates () {
        return myInitialStates;
    }
    
    protected GridPane getMyGridPane() {
    	return myGridPane;
    }
    
    /**
     * Loops through each GridCell and returns the State value (same translation as 
     * initialStates parameter from XML file) to be converted to String format
     * @return
     */
    private String getCurrentStatesArrayString () {
    	String currentStates = "";
    	
    	for (int row = 0; row < getRows(); row++) {
    		for (int col = 0; col < getColumns(); col++) {
    			GridCell cell = myCells[row][col];
    			int currentStateValue = cell.getMyCurrentState().getStateValue();
    			currentStates += Integer.toString(currentStateValue);
    		}
    		if (row != getRows()-1){
    			currentStates += ",";
    		}
    	}
    	
    	return currentStates;
    	
    }
}
