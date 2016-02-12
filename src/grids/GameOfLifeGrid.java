/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package grids;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import cells.GridCell;
import cells.SimpleCell;
import constants.Parameters;
import constants.State;
import javafx.scene.shape.Rectangle;


/**
 * Grid subclass for Game of Life simulation
 *
 */
public class GameOfLifeGrid extends Grid {

    private static final int MY_STATE_VALUE_DEAD = 0;
    private static final int MY_STATE_VALUE_ALIVE = 1;
    private static final int NUM_NEIGHBORS_TO_RESURRECT = 3;
    private static final Set<Integer> NUM_NEIGHBORS_TO_STAY_ALIVE =
            new HashSet<Integer>(Arrays.asList(2, 3));

    public GameOfLifeGrid (Parameters params) {
        super(params);
        
    }

    @Override
    protected GridCell initializeCell (int row, int col) {
        State state = State.DEAD;

        int s = getMyInitialStates()[row][col];

        switch (s) {
            case MY_STATE_VALUE_DEAD:
                state = State.DEAD;
                break;
            case MY_STATE_VALUE_ALIVE:
                state = State.ALIVE;
                break;
            default:
                // Display error message
                break;
        }

        return new SimpleCell(state, row, col, new Rectangle(getMyCellSize(), getMyCellSize()));

    }

    @Override
    protected void setCellState (GridCell cell) {

        int numNeighborsAlive = numNeighborsAlive(cell);

        // Can combine these if statements, but I think it's more readable this way
        if (cell.getMyCurrentState() == State.ALIVE) {
            if (NUM_NEIGHBORS_TO_STAY_ALIVE.contains(numNeighborsAlive)) {
                cell.setMyNextState(State.ALIVE);
            }
            else {
                cell.setMyNextState(State.DEAD);
            }
        }
        else if (cell.getMyCurrentState() == State.DEAD) {
            if (numNeighborsAlive == NUM_NEIGHBORS_TO_RESURRECT) {
                cell.setMyNextState(State.ALIVE);
            }
            else {
                cell.setMyNextState(State.DEAD);
            }
        }

    }

    @Override
	protected void toggleState(GridCell cell) {
		if (cell.getMyCurrentState() == State.DEAD) {
			cell.setMyCurrentState(State.ALIVE);
			
		}
		else if (cell.getMyCurrentState() == State.ALIVE) {
			cell.setMyCurrentState(State.DEAD);
			
		} 
		
		cell.setMyColor();
		
	}

	/**
     * Calculates the number of "neighbor" cells alive
     *
     * @param r The row index of the cell in question
     * @param c The column index of the cell in question
     * @return The number of alive cells surrounding the cell in question
     */
    private int numNeighborsAlive (GridCell cell) {
        int numNeighborsAlive = 0;

        for (GridCell neighbor : getNeighbors(cell)) {
            if (neighbor.getMyCurrentState() == State.ALIVE) {
                numNeighborsAlive++;
            }
        }

        return numNeighborsAlive;

    }

}
