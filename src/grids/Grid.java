/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package grids;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import cells.GridCell;
import constants.NeighborOffset;
import constants.Offset;
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

    // M/V
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
    public Grid (Map<String, String> params) {
        myGridSize =
                new Dimension(Integer.parseInt(params.get("width")),
                              Integer.parseInt(params.get("height")));
        myRows = Integer.parseInt(params.get("rows"));
        myColumns = Integer.parseInt(params.get("columns"));
        myCellSize = (int) myGridSize.getWidth() / myRows;

        
        
        // TODO: (for advanced specifications, create Buttons/Sliding Bars for UI)
        setMyInitialStates(createInitialStatesArray(params.get("initialStates")));
        initialize();
    }

    // =========================================================================
    // Initialization
    // =========================================================================

    /**
     * Initializes the grid model (2d array) and grid view (GridPane)
     */
    protected void initialize () {
        initializeCells();
        createUI();
    }

    /**
     * Initializes and populates myCells by calling initializeCell for each cell
     */
    private void initializeCells () {
        myCells = new GridCell[myRows][myColumns];
        for (int r = 0; r < myCells.length; r++) {
            for (int c = 0; c < myCells[0].length; c++) {
                initializeCell(r, c);
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

    /**
     * Creates an 2d int array based on a comma separated string from xml
     *
     * @param param The comma separated string
     * @return The 2d array
     */
    private int[][] createInitialStatesArray (String param) {
        int[][] initialStates = new int[myRows][myColumns];
        String[] parsed = param.split(",");

        for (int r = 0; r < parsed.length; r++) {
            String s = parsed[r];
            for (int c = 0; c < s.length(); c++) {
                int state = Character.getNumericValue(s.charAt(c));
                initialStates[r][c] = state;
            }
        }

        return initialStates;

    }

    /**
     * Updates the visible GridPane by mapping the the cells from myCells to the same location in
     * the 2D
     * array
     */
    private void createUI () {
        myGridPane = new GridPane();
        myGridPane.setPrefSize(myGridSize.getWidth(), myGridSize.getHeight());

        for (int r = 0; r < myCells.length; r++) {
            for (int c = 0; c < myCells[0].length; c++) {
                myGridPane.add(myCells[r][c].getMyShape(), c, r);
            }
        }

        myRoot = new Group();
        myRoot.getChildren().add(myGridPane);

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
     * Loops through each cell in the grid and updates its next state
     */
    protected void setCellStates () {
        for (int r = 0; r < myCells.length; r++) {
            for (int c = 0; c < myCells[0].length; c++) {
                GridCell cell = this.getMyCells()[r][c];
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
        for (GridCell[] myCell : myCells) {
            for (int c = 0; c < myCells[0].length; c++) {
                myCell[c].transitionStates();

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
    protected boolean cellInBounds (int row, int col) {

        boolean farTop = row < 0;
        boolean farBottom = row > getMyCells().length - 1;
        boolean farLeft = col < 0;
        boolean farRight = col > getMyCells()[0].length - 1;

        return !(farTop | farBottom | farLeft | farRight);
    }

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

    /**
     * Returns a list of a GridCell's neighboring GridCells
     *
     * @param cell The cell of interest
     * @return The list of neighboring GridCells
     */
    protected List<GridCell> getNeighbors (GridCell cell) {
        int r = cell.getMyGridLocation().getRow();
        int c = cell.getMyGridLocation().getCol();

        List<Offset> offsets = neighborOffsets();
        List<GridCell> neighbors = new ArrayList<GridCell>();

        for (Offset offset : offsets) {
            int neighborRow = r + offset.getRow();
            int neighborCol = c + offset.getCol();

            if (cellInBounds(neighborRow, neighborCol)) {
                neighbors.add(myCells[neighborRow][neighborCol]);
            }
        }

        return neighbors;
    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================

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

    protected void setMyInitialStates (int[][] initialStates) {
        myInitialStates = initialStates;
    }
    
    protected GridPane getMyGridPane() {
    	return myGridPane;
    }
}
