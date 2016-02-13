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
import constants.Offset;
import constants.Parameters;
import gridviews.GridView;
import javafx.scene.Group;


/**
 * Abstract class representing a grid to be used for the simulation
 * In charge of determining how to update itself for each step of the simulation
 */
public abstract class Grid {
    public static final String EDGE_TYPE = Constants.RESOURCES.getString("edgeType");

    // Model
    private int[][] myInitialStates;

    private int myColumns;
    private int myRows;
    private GridCell[][] myCells;

    // Determined by resource properties gridShape and neighborDirections
    private List<Offset> myNeighborOffsets;

    // View
    private GridView myGridView;

    /**
     * Constructor - initializes a Grid based on parameters from xml
     *
     * @param params Xml parameters stored as an instance of Parameters class
     */
    public Grid (Parameters params) {
        myRows = params.getRows();
        myColumns = params.getColumns();



    }

    // =========================================================================
    // Initialization
    // =========================================================================

    /**
     * Initializes and populates myCells by calling initializeCell for each cell
     */
    protected void initializeCells () {
        myCells = new GridCell[myRows][myColumns];
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                GridCell cell = initializeCell(row, col);
                myCells[row][col] = cell;
            }
        }

    }

    /**
     * Each grid can subclass this to determine how to initialize each cell based on myInitialStates
     *
     * @param row The row of the cell in the grid
     * @param column The column of the cell in the grid
     */
    protected abstract GridCell initializeCell (int row, int column);

    /**
     * Toggles the state of a cell. Called when user clicks a cell
     *
     * @param cell The cell whose states will be toggled
     */
    public void toggleStateAndUpdateUI (GridCell cell) {
        toggleState(cell);
        updateCellUI(cell);
    }

    /**
     * Action to be carried out when GridCell's shape is clicked. Abstracted
     * so subclasses of Grid can toggle between only relevant states.
     *
     * @param cell - The cell whose states will be toggled
     */
    protected abstract void toggleState (GridCell cell);

    /**
     * Updates the visual appearance of a cell based on its current state
     *
     * @param cell The cell to update
     */
    private void updateCellUI (GridCell cell) {
        myGridView.updateCellShape(cell);
    }

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
     * Updates each cell's next state by calling setCellState for each cell
     */
    protected void setCellStates () {

        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                GridCell cell = myCells[row][col];
                setCellState(cell);
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
     * Transitions each cell to its next state and updates the UI
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
     * Returns a list of a GridCell's neighboring GridCells
     *
     * @param cell The cell of interest
     * @return The list of neighboring GridCells
     */
    protected List<GridCell> getNeighbors (GridCell cell) {

        List<GridCell> neighbors = new ArrayList<GridCell>();

        for (Offset offset : getMyNeighborOffsets()) {
            Location neighborLocation = null;

            if (EDGE_TYPE.equals("Finite")) {
                neighborLocation = neighborLocationFinite(cell, offset);

            }
            else if (EDGE_TYPE.equals("Toroidal")) {
                neighborLocation = neighborLocationToroidal(cell, offset);

            }
            else if (EDGE_TYPE.equals("Infinite")) {
                neighborLocation = neighborLocationFinite(cell, offset);
                // If out of bounds then expand grid
            }

            if (cellInBounds(neighborLocation)) {
                neighbors.add(myCells[neighborLocation.getRow()][neighborLocation.getCol()]);

            }
        }

        return neighbors;

    }

    /**
     * Used by getNeighbors for a finite grid to get the cell at located at a specified offset
     * from another cell
     *
     * @param cell GridCell
     * @param offset Offset where potential neighbor is located
     * @return Location of neighbor at offset from cell
     */
    private Location neighborLocationFinite (GridCell cell, Offset offset) {
        int neighborRow = cell.getMyGridLocation().getRow() + offset.getRow();
        int neighborCol = cell.getMyGridLocation().getCol() + offset.getCol();

        return new Location(neighborRow, neighborCol);
    }

    /**
     * Used by getNeighbors for a toroidal grid to get the cell at located at a specified offset
     * from another cell
     *
     * @param cell GridCell
     * @param offset Offset where potential neighbor is located
     * @return Location of neighbor at offset from cell
     */
    private Location neighborLocationToroidal (GridCell cell, Offset offset) {
        int neighborRow = (cell.getMyGridLocation().getRow() + offset.getRow()) % myRows;
        int neighborCol = (cell.getMyGridLocation().getCol() + offset.getCol()) % myColumns;

        return new Location(neighborRow, neighborCol);
    }

    /**
     * Aggregates current game parameters to be saved to an XML file.
     * Subclasses of Grid override the method by adding additional simulation-specific parameters
     * Grid does not know the delay time, which is gathered from Game
     *
     * @return Map containing current game state's parameters that are common to all Grid types
     */
    public Map<String, String> getMyGameState () {
        Map<String, String> gameStateParams = new HashMap<String, String>();
        gameStateParams.put("rows", Integer.toString(getRows()));
        gameStateParams.put("columns", Integer.toString(getColumns()));
        return gameStateParams;

    }

    /**
     * Loops through each GridCell and returns the State value (same translation as
     * initialStates parameter from XML file) to be converted to String format
     *
     * @return A 2d int array containing the current grid state
     */
    public int[][] getCurrentStatesArray () {
        int[][] currentStates = new int[getRows()][getColumns()];

        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                GridCell cell = myCells[row][col];
                int currentStateValue = cell.getMyCurrentState().getStateValue();
                currentStates[row][col] = currentStateValue;
            }
        }

        return currentStates;

    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================

    protected int[][] getMyInitialStates () {
        return myInitialStates;
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
        return myGridView.getMyView();
    }

    public Dimension getMyGridSize () {
        return myGridView.getMyGridSize();
    }

    public GridView getMyGridView () {
        return myGridView;
    }

    public void setMyGridView (GridView myGridView) {
        this.myGridView = myGridView;

    }
    
    public void setNeighborOffsets (List<Offset> offsets) {
        myNeighborOffsets = offsets;
    }
    protected List<Offset> getMyNeighborOffsets () {
        return myNeighborOffsets;
    }

}
