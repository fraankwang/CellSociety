/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cellsociety_team03;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;


public abstract class Grid {

    // Model
    private int[][] myInitialStates;
    private int myColumns;
    private int myRows;
    
    // View
    private GridCell[][] myCells;
    private Dimension myGridSize;
    private int myCellSize;
    private long delay;
    private Group myRoot;
    private GridPane myGridPane;
    
    private Map<String, String> myParameters;
    
    /**
     * Reads the parameters passed to the constructor
     * Initializes the 2D Cell array and corresponding GridPane Node
     * 
     * @param params
     */
    public Grid (Map<String, String> params) {
        myParameters = params;
        myGridSize = new Dimension(Integer.parseInt(params.get("width")),Integer.parseInt(params.get("height")));
        myRows = Integer.parseInt(params.get("rows"));
        myColumns = Integer.parseInt(params.get("columns"));
        myCellSize = (int) myGridSize.getWidth() / myRows; //TODO: make different cell widths and heights?
        
        if(params.containsKey("initialStates")) setMyInitialStates(createInitialStatesArray(params.get("initialStates")));
        delay = Long.parseLong(params.get("delay"));
        myRoot = new Group();

        // TODO: (for advanced specifications, create Buttons/Sliding Bars for UI)

        initializeCells();
    }

    /**
     * Specifies what happens at each "step" of the game:
     * 1. Each Cell determines its own next State
     * 2. Each Cell is set to its next State
     * 
     * @param elapsedTime
     */
    public void step (Double elapsedTime) {
        setCellStates();
        updateCellStates();
    }

    /**
     * Each subclass of Grid determines its own algorithms for determining each Cell's next state
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
     * 
     * @param r
     * @param c
     */
    protected abstract void setCellState (GridCell cell);

    /**
     * Loop through myCells and set each Cell's currentState to its nextState and reset nextState to
     * null
     */
    private void updateCellStates () {
        for (int r = 0; r < myCells.length; r++) {
            for (int c = 0; c < myCells[0].length; c++) {
                myCells[r][c].transitionStates();

            }
        }
    }

    
    private int[][] createInitialStatesArray(String param){
        int[][] initialStates = new int[myRows][myColumns];
        System.out.println(param);
        String[] parsed = param.split(",");
        System.out.println(parsed);
        System.out.println(parsed.toString());
        for(int r = 0; r<parsed.length; r++){
            String s = parsed[r];
            for(int c = 0; c<s.length(); c++){
                int state = Character.getNumericValue(s.charAt(c));
                initialStates[r][c] = state;
            }
        }
        
        return initialStates;
        
    }
    /**
     * Initializes and populates myCells given the initial grid set up parameters
     */
    protected void initializeCells () {
        myCells = new GridCell[myRows][myColumns];

        // TODO read myParameters to determine initial set up

        for (int r = 0; r < myCells.length; r++) {
            for (int c = 0; c < myCells[0].length; c++) {
                initializeCell(r, c);
            }
        }

        System.out.println("Cells initialized");
        createBoard();
    }

    protected abstract void initializeCell (int row, int column);

    /**
     * Updates the visible Pane by mapping the the cells from myCells in the same location in the 2D
     * array
     */
    private void createBoard () {
        myGridPane = new GridPane();

        // TODO: figure out dimensions to resize myGridPane to fit with UI
        myGridPane.setPrefSize(150, 150);

        for (int r = 0; r < myCells.length; r++) {
            for (int c = 0; c < myCells[0].length; c++) {
                myGridPane.add(myCells[r][c].getMyShape(), c, r); // TODO: c, r??
            }
        }

        System.out.println("GridPane initialized and added to root");
        myRoot.getChildren().add(myGridPane);

    }

    protected boolean cellInBounds (int r, int c) {
       
        boolean farTop = r < 0;
        boolean farBottom = r > this.getMyCells().length - 1;
        boolean farLeft = c < 0;
        boolean farRight = c > this.getMyCells()[0].length - 1;

        return !(farTop | farBottom | farLeft | farRight);
    }
    
    protected List<Offset> neighborOffsets(){
        
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
    
    protected List<GridCell> getNeighbors(GridCell cell){
        int r = cell.getMyGridLocation().getRow();
        int c = cell.getMyGridLocation().getCol();
        
        List<Offset> offsets = neighborOffsets();
        List<GridCell> neighbors = new ArrayList<GridCell>();
        
        for(Offset offset : offsets){
            int neighborRow = r + offset.getRow();
            int neighborCol = c + offset.getCol();
            if(cellInBounds(neighborRow, neighborCol)){
                neighbors.add(myCells[neighborRow][neighborCol]);
            }
        }
        
        
        return neighbors;
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
        this.myColumns = gridWidth;
    }

    public int getRows () {
        return myRows;
    }

    public void setRows (int gridHeight) {
        this.myRows = gridHeight;
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

    protected void setMyGridSize (Dimension myGridSize) {
        this.myGridSize = myGridSize;
    }

    protected int getMyCellSize () {
        return myCellSize;
    }

    protected void setMyCellSize (int myCellSize) {
        this.myCellSize = myCellSize;
    }

    protected int[][] getMyInitialStates () {
        return myInitialStates;
    }

    protected void setMyInitialStates (int[][] myInitialStates) {
        this.myInitialStates = myInitialStates;
    }
}
