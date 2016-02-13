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
import states.GameOfLifeState;
import states.State;


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
        State state = GameOfLifeState.DEAD;

        int s = getMyInitialStates()[row][col];
        
        switch (s) {
            case MY_STATE_VALUE_DEAD:
                state = GameOfLifeState.DEAD;
                break;
            case MY_STATE_VALUE_ALIVE:
                state = GameOfLifeState.ALIVE;
                break;
            default:
                // Display error message
                break;
        }

        return new SimpleCell(state, row, col);

    }

    @Override
    protected void setCellState (GridCell cell) {

        int numNeighborsAlive = numNeighborsAlive(cell);

        // Can combine these if statements, but I think it's more readable this way
        if (cell.getMyCurrentState() == GameOfLifeState.ALIVE) {
            if (NUM_NEIGHBORS_TO_STAY_ALIVE.contains(numNeighborsAlive)) {
                cell.setMyNextState(GameOfLifeState.ALIVE);
            }
            else {
                cell.setMyNextState(GameOfLifeState.DEAD);
            }
        }
        else if (cell.getMyCurrentState() == GameOfLifeState.DEAD) {
            if (numNeighborsAlive == NUM_NEIGHBORS_TO_RESURRECT) {
                cell.setMyNextState(GameOfLifeState.ALIVE);
            }
            else {
                cell.setMyNextState(GameOfLifeState.DEAD);
            }
        }

    }

    @Override
    protected void toggleState (GridCell cell) {
        if (cell.getMyCurrentState() == GameOfLifeState.DEAD) {
            cell.setMyCurrentState(GameOfLifeState.ALIVE);

        }
        else if (cell.getMyCurrentState() == GameOfLifeState.ALIVE) {
            cell.setMyCurrentState(GameOfLifeState.DEAD);

        }

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
            if (neighbor.getMyCurrentState() == GameOfLifeState.ALIVE) {
                numNeighborsAlive++;
            }
        }

        return numNeighborsAlive;

    }

}
