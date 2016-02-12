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
import constants.Constants;
import constants.Location;
import constants.NeighborOffset;
import constants.Offset;
import constants.Parameters;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import views.GridView;


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
    private List<Offset> myNeighborOffsets;

    // View
    private Dimension myGridSize;
    private int myCellSize;

    private GridView myGridView;

    /**
     * Constructor - initializes a Grid based on parameters from xml
     *
     * @param params Map of xml parameters
     */
    public Grid (Parameters params) {
        //myGridSize = params.getDimension();
        myGridSize = new Dimension(500, 500);
        myCellSize = Integer.parseInt(Constants.RESOURCES.getString("cellSize"));

        myRows = params.getRows();
        myColumns = params.getColumns();
        myInitialStates = params.getInitialStates();

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
    }

    /**
     * Initializes and populates myCells by calling initializeCell for each cell
     */
    private void initializeCells () {
        myCells = new GridCell[myRows][myColumns];
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                GridCell cell = initializeCell(row, col);
                cell.getMyShape().setOnMouseClicked(e -> toggleState(cell));
                myCells[row][col] = cell;
            }
        }

    }

    /**
     * Each grid subclass determines how to initialize each cell based on myInitialStates
     *
     * @param row The row of the cell in the grid
     * @param column The column of the cell in the grid
     */
    protected abstract GridCell initializeCell (int row, int column);

    /**
     * Action to be carried out when GridCell's shape is clicked. Abstracted
     * so subclasses of Grid can toggle between only relevant states.
     * 
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
                myGridView.updateCellShape(myCells[row][col]);
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

        List<GridCell> neighbors = new ArrayList<GridCell>();

        for (Offset offset : myNeighborOffsets) {
            Location neighborLocation = neighborLocationNonInfinite(cell, offset);

            if (cellInBounds(neighborLocation)) {
                neighbors.add(myCells[neighborLocation.getRow()][neighborLocation.getCol()]);

            }
        }

        return neighbors;

    }

    /**
     * Used by getNeighbors for a finite or toroidal grid (I think it also works for finite)
     * 
     * @param cell GridCell
     * @param offset Offset where potential neighbor is located
     * @return Location of neighbor at offset from cell
     */
    private Location neighborLocationNonInfinite (GridCell cell, Offset offset) {
        int neighborRow = (cell.getMyGridLocation().getRow() + offset.getRow()) % myRows;
        int neighborCol = (cell.getMyGridLocation().getCol() + offset.getCol()) % myColumns;

        return new Location(neighborRow, neighborCol);
    }

    // TODO: infinite grid
    // private Location neighborLocationInfinite
    // or private Location getNeighborsInfinite

    public void setNeighborOffsets (List<Offset> offsets) {
        myNeighborOffsets = offsets;
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
        boolean farBottom = row > getRows() - 1;
        boolean farLeft = col < 0;
        boolean farRight = col > getColumns() - 1;

        return !(farTop | farBottom | farLeft | farRight);

    }

    /**
     * Aggregates current game parameters to be saved to an XML file.
     * Subclasses of Grid override the method by adding additional simulation-specific parameters
     * Grid does not know the delay time, which is gathered from Game
     * 
     * @return current game state's parameters that are common to all Grid types
     */
    public Map<String, String> getMyGameState () {
        Map<String, String> gameStateParams = new HashMap<String, String>();
        gameStateParams.put("rows", Integer.toString(this.getRows()));
        gameStateParams.put("columns", Integer.toString(this.getColumns()));
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

    public Group getView () {
        return getMyGridView().getMyView();
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

    /**
     * Loops through each GridCell and returns the State value (same translation as
     * initialStates parameter from XML file) to be converted to String format
     * 
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
            if (row != getRows() - 1) {
                currentStates += ",";
            }
        }

        return currentStates;

    }

    public GridView getMyGridView () {
        return myGridView;
    }

    public void setMyGridView (GridView myGridView) {
        this.myGridView = myGridView;
    }
}
