/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package grids;

import java.util.List;
import java.util.Map;
import cells.GridCell;
import cells.SimpleCell;
import constants.Parameters;
import javafx.scene.chart.XYChart;
import states.SegregationState;
import states.State;
import uiviews.SegregationUIView;


/**
 * Grid subclass for Segregation simulation
 *
 */
public class SegregationGrid extends Grid {

	private int redCount;
	private int blueCount;
	
	private int[][] myInitialStates;
    private double mySimilarityPercentage;
    
    public SegregationGrid (Parameters params) {
        super(params);
        mySimilarityPercentage = Double.parseDouble(params.getParameter("similaritypercentage"));
        myInitialStates = params.getInitialStates();
        
        initializeCells();
    }

    @Override
    protected GridCell initializeCell (int row, int col) {
        int s = myInitialStates[row][col];

        for (State state : SegregationState.values()) {
            if (s == state.getStateValue()) {
            	if (state == SegregationState.RED) {
            		redCount++;
            	}
            	else if (state == SegregationState.BLUE) {
            		blueCount++;
            	}
                return new SimpleCell(state, row, col);
            }
        }

        // TODO: Return error: invalid initial state
        return null;
    }

    @Override
    protected void setCellState (GridCell cell) {
        if (!cell.getMyCurrentState().equals(SegregationState.EMPTY)) {
            List<GridCell> neighbors = getNeighbors(cell);
            double sameCount = 0;
            double nonEmptyCount = 0;
            for (GridCell neighbor : neighbors) {
                if (cell.getMyCurrentState().equals(neighbor.getMyCurrentState())) {
                    sameCount++;
                }
                if (!(neighbor.getMyCurrentState() == SegregationState.EMPTY)) {
                    // we don't account for empty cells when calculating similarity percentage
                    nonEmptyCount++;
                }
            }
            if (!isContent((sameCount / nonEmptyCount) * 100)) {
                move(cell);
            }
            else {
                cell.setMyNextState(cell.getMyCurrentState());
            }
        }
        else {
            if (cell.getMyNextState() == null) {
                cell.setMyNextState(cell.getMyCurrentState());
            }
        }

    }

    @Override
    protected void toggleState (GridCell cell) {
        if (cell.getMyCurrentState() == SegregationState.EMPTY) {
            cell.setMyCurrentState(SegregationState.RED);
            redCount++;

        }
        else if (cell.getMyCurrentState() == SegregationState.RED) {
            cell.setMyCurrentState(SegregationState.BLUE);
            redCount--;
            blueCount++;
            
        }
        else if (cell.getMyCurrentState() == SegregationState.BLUE) {
            cell.setMyCurrentState(SegregationState.EMPTY);
            blueCount--;
            
        }

    }

    /**
     * Moves a cell to the first empty location in the Grid that
     * hasn't already been moved into
     *
     * @param cell the cell to be moved
     */
    private void move (GridCell cell) {
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                GridCell newCell = getMyCells()[row][col];
                if (newCell.getMyCurrentState().equals(SegregationState.EMPTY) &&
                    (newCell.getMyNextState() == null ||
                     newCell.getMyNextState() == SegregationState.EMPTY)) {
                    newCell.setMyNextState(cell.getMyCurrentState());
                    cell.setMyNextState(SegregationState.EMPTY);
                    return;
                }
            }
        }

        cell.setMyNextState(cell.getMyCurrentState());
    }

    /**
     * Checks to see if a cell is content based on how many
     * of its neighbors are the same race.
     *
     * @param percent the percentage of neighbors that are the same race
     * @return True if the percent is at least the threshold percentage
     */
    private boolean isContent (double percent) {
        return percent >= mySimilarityPercentage;
    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================

    private double getSimilarityPercentage () {
        return mySimilarityPercentage;
    }
    
    @Override
    public Map<String, String> getMyGameState () {
        Map<String, String> currentGameState = super.getMyGameState();
        currentGameState.put("similaritypercentage", Double.toString(getSimilarityPercentage()));

        return currentGameState;

    }

	@Override
	public void updateParams(Map<String, Double> map) {
		mySimilarityPercentage = map.get("similaritypercentage");
	}

	@Override
	protected void updateUIView() {
		SegregationUIView SegregationUIView = (SegregationUIView) getMyUIView();
		XYChart.Series<Number, Number> agentSeries = SegregationUIView.getAgentPopulationSeries();
		XYChart.Series<Number, Number> sugarSeries = SegregationUIView.getSugarCountSeries();
		    
		SegregationUIView.addDataPoint(agentSeries, getElapsedTime(), getRedCount());
		SegregationUIView.addDataPoint(sugarSeries, getElapsedTime(), getBlueCount());
		
	}
	
	/**
	 * Number of Red cells
	 * @return
	 */
	private int getRedCount () {
		return redCount / (getRows() * getColumns());
	}
	
	/**
	 * Number of Blue cells
	 * @return
	 */
	private double getBlueCount () {
		return blueCount * 100 / (getRows() * getColumns());
		
	}



}
