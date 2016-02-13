/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package grids;

import java.util.List;
import java.util.Map;
import cells.GridCell;
import cells.SimpleCell;
import constants.Parameters;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import states.SegregationState;
import states.State;


/**
 * Grid subclass for Fire simulation
 *
 */
public class SegregationGrid extends Grid {

    private double mySimilarityPercentage;

    private static final int MY_STATE_VALUE_EMPTY = 0;
    private static final int MY_STATE_VALUE_RED = 1;
    private static final int MY_STATE_VALUE_BLUE = 2;

    public SegregationGrid (Parameters params) {
        super(params);
        mySimilarityPercentage = Double.parseDouble(params.getParameter("similaritypercentage"));
    }

    @Override
    protected GridCell initializeCell (int row, int col) {
        State state = SegregationState.EMPTY;

        int s = getMyInitialStates()[row][col];
        switch (s) {
            case MY_STATE_VALUE_EMPTY:
                state = SegregationState.EMPTY;
                break;
            case MY_STATE_VALUE_RED:
                state = SegregationState.RED;
                break;
            case MY_STATE_VALUE_BLUE:

                state = SegregationState.BLUE;
            default:
                // Display error message
                break;
        }

        return new SimpleCell(state, row, col);

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
                    nonEmptyCount++;	// we don't account for empty cells when calculating
                                    	// similarity percentage
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

        }
        else if (cell.getMyCurrentState() == SegregationState.RED) {
            cell.setMyCurrentState(SegregationState.BLUE);

        }
        else if (cell.getMyCurrentState() == SegregationState.BLUE) {
            cell.setMyCurrentState(SegregationState.EMPTY);

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
	public VBox createParameterButtons() {
		VBox box = new VBox();
    	Label similarityLabel = new Label("Similarity Percentage");
    	TextField similarityField = new TextField(""+mySimilarityPercentage);
    	
    	box.getChildren().addAll(similarityLabel, similarityField);
    	return box;
	}

	@Override
	public void updateParameters(Parameters params) {
		// TODO Auto-generated method stub
		
	}

}
