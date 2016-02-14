/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package grids;

import java.util.Map;
import java.util.Random;
import cells.GridCell;
import cells.SimpleCell;
import constants.Parameters;
import javafx.scene.chart.XYChart;
import states.FireState;
import states.State;
import uiviews.FireUIView;


/**
 * Grid subclass for Fire simulation
 *
 */
public class FireGrid extends Grid {

    private int[][] myInitialStates;
    private double myProbCatch;
    private int burnedCount = 0;
    private int treeCount = 0;
    
    public FireGrid (Parameters params) {
        super(params);
        myProbCatch = Double.parseDouble(params.getParameter("probcatch"));
        myInitialStates = params.getInitialStates();

        initializeCells();
    }

    @Override
    protected GridCell initializeCell (int row, int col) {
        int s = myInitialStates[row][col];

        // Note: duplicated code, but no way to subclass an enum to abstract FireState.values
        // Can maybe use reflection, but we don't know that yet
        for (State state : FireState.values()) {
            if (s == state.getStateValue()) {
            	if (state == FireState.TREE) {
            		treeCount++;
            	}
            	if (state == FireState.BURNED) {
            		burnedCount++;
            	}
            	
                return new SimpleCell(state, row, col);
            }
        }

        // TODO: Return error: invalid initial state
        return null;

    }

    @Override
    protected void setCellState (GridCell cell) {
        if (cell.getMyCurrentState() == FireState.BURNING) {
            cell.setMyNextState(FireState.BURNED);
            burnedCount++;
        }
        else if (willCatch(cell)) {
            cell.setMyNextState(FireState.BURNING);
            treeCount--;
        }
        else {
            cell.setMyNextState(cell.getMyCurrentState());
        }

    }

    @Override
    protected void toggleState (GridCell cell) {
        if (cell.getMyCurrentState() == FireState.BURNING) {
            cell.setMyCurrentState(FireState.BURNED);
            burnedCount++;
            treeCount--;
            
        }
        else if (cell.getMyCurrentState() == FireState.BURNED) {
            cell.setMyCurrentState(FireState.TREE);
            treeCount++;

        }
        else if (cell.getMyCurrentState() == FireState.TREE) {
            cell.setMyCurrentState(FireState.BURNING);

        }

    }

    /**
     * Determines if any of a cell's neighbor cells are currently burning
     *
     * @param r The row index of the cell in question
     * @param c The column index of the cell in question
     * @return A boolean indicating whether one of the neighbor cells is burning
     */
    private boolean neighborIsBurning (GridCell cell) {
        boolean neighborIsBurning = false;

        for (GridCell neighbor : getNeighbors(cell)) {

            if (neighbor.getMyCurrentState() == FireState.BURNING) {
                neighborIsBurning = true;
            }

        }

        return neighborIsBurning;

    }

    /**
     * Determines whether a cell will catch on fire
     *
     * @param cell The cell in question
     * @param r The row index of the cell in question
     * @param c The column index of the cell in question
     * @return A boolean indicating whether to set the cell's next state to burning
     */
    private boolean willCatch (GridCell cell) {
        return cell.getMyCurrentState() == FireState.TREE && neighborIsBurning(cell) &&
               probCatchRandom();
    }

    /**
     * Returns a boolean if a random number generated is greater than probCatch
     *
     * @return The boolean
     */
    private boolean probCatchRandom () {
        Random r = new Random();
        double value = r.nextDouble();

        return value <= getProbCatch();

    }

    @Override
    public void updateParams(Map<String, Double> map) {
    	myProbCatch = map.get("probcatch");
    	
    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================
    private double getProbCatch () {
        return myProbCatch;
    }
    
    @Override
	public Map<String,String> getMyGameState () {
		Map<String,String> currentGameState = super.getMyGameState();
		currentGameState.put("probcatch", Double.toString(getProbCatch()));
		
		return currentGameState;
		
	}

	@Override
	protected void updateUIView() {
		FireUIView fireView = (FireUIView) getMyUIView();
		XYChart.Series<Number, Number> treePopulationSeries = fireView.getTreePopulationSeries();
		XYChart.Series<Number, Number> burnedTrees = fireView.getBurnedTreesSeries();

		fireView.addDataPoint(treePopulationSeries, getElapsedTime(), percentageTrees());
		fireView.addDataPoint(burnedTrees, getElapsedTime(), percentagedBurnedTrees());
		
	}

	/**
	 * Current percentage of non-burned trees
	 * @param states
	 * @return
	 */
	private int percentageTrees () {
		return treeCount * 100 / (getRows() * getColumns());
		
	}
	
	/**
	 * Accumulated percentage of burned trees
	 * @param states
	 * @return
	 */
	private double percentagedBurnedTrees () {
		return burnedCount * 100 / (getRows() * getColumns());
		
	}

}
