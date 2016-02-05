/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cellsociety_team03;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
    private Map<String, String> myParameters;
	private List<State> initializeList;
    protected static int CELL_SIZE = 50; // TODO: set dynamically based on board size

    /**
     * Reads the parameters passed to the constructor
     * Initializes the 2D Cell array and corresponding GridPane Node
     * 
     * @param params
     */
    public Grid (Map<String, String> params) {
        myParameters = params;
        myRows = Integer.parseInt(params.get("rows"));
        myColumns = Integer.parseInt(params.get("columns"));
        delay = Long.parseLong(params.get("delay"));
        myRoot = new Group();
        initializeList = new ArrayList<State>();
        // TODO: (for advanced specifications, create Buttons/Sliding Bars for UI)

        //initializeCells();
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
                this.setCellState(cell, r, c);
            }
        }

    }

    /**
     * 
     * @param r
     * @param c
     */
    protected abstract void setCellState (GridCell cell, int r, int c);

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

    /**
     * Initializes and populates myCells given the initial grid set up parameters
     */
    protected void initializeCells () {
        myCells = new GridCell[myRows][myColumns];

        // TODO read myParameters to determine initial set up
        Collections.shuffle(initializeList);
        for (int r = 0; r < myCells.length; r++) {
            for (int c = 0; c < myCells[0].length; c++) {
                initializeCell(r, c);
            }
        }

        System.out.println("Cells initialized");
        createBoard();
    }

    protected void initializeCell (int row, int column) {
    	myCells[row][column] = new SimpleCell(initializeList.remove(0), CELL_SIZE, new Rectangle(30,30));
    }

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
    
    protected void addStatesToList(double percentage, State state) {
		int total = getRows()*getColumns();
		int numberOfStates = (int)(total*percentage)/100;
		for(int x = 0; x < numberOfStates; x++){
			initializeList.add(state);
		}
		
	}

    private void handleMouseInput (ActionEvent e) {

    }

    private void handleKeyEntry (ActionEvent e) {

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
}
