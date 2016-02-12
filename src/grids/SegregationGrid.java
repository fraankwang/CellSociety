package grids;

/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

import java.util.List;
import java.util.Map;
import cells.GridCell;
import cells.SimpleCell;
import constants.Parameters;
import constants.State;
import javafx.scene.shape.Rectangle;


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
        State state = State.EMPTY;

        int s = getMyInitialStates()[row][col];
        switch (s) {
            case MY_STATE_VALUE_EMPTY:
                state = State.EMPTY;
                break;
            case MY_STATE_VALUE_RED:
                state = State.RED;
                break;
            case MY_STATE_VALUE_BLUE:
                state = State.BLUE;
            default:
                // Display error message
                break;
        }

        return new SimpleCell(state, row, col, new Rectangle(getMyCellSize(), getMyCellSize()));

    }

    @Override
    protected void setCellState (GridCell cell) {
        if (!cell.getMyCurrentState().equals(State.EMPTY)) {
            List<GridCell> neighbors = getNeighbors(cell);
            double sameCount = 0;
            double nonEmptyCount = 0;
            for (GridCell neighbor : neighbors) {
                if (cell.getMyCurrentState().equals(neighbor.getMyCurrentState())) {
                    sameCount++;
                }
                if (!(neighbor.getMyCurrentState() == State.EMPTY)) {
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
        if (cell.getMyCurrentState() == State.EMPTY) {
            cell.setMyCurrentState(State.RED);

        }
        else if (cell.getMyCurrentState() == State.RED) {
            cell.setMyCurrentState(State.BLUE);

        }
        else if (cell.getMyCurrentState() == State.BLUE) {
            cell.setMyCurrentState(State.EMPTY);

        }

        cell.setMyColor();

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
                if (newCell.getMyCurrentState().equals(State.EMPTY) &&
                    (newCell.getMyNextState() == null || newCell.getMyNextState() == State.EMPTY)) {
                    newCell.setMyNextState(cell.getMyCurrentState());
                    cell.setMyNextState(State.EMPTY);
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

}
