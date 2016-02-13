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

    private static final int NUM_NEIGHBORS_TO_RESURRECT = 3;
    private static final Set<Integer> NUM_NEIGHBORS_TO_STAY_ALIVE =
            new HashSet<Integer>(Arrays.asList(2, 3));

    private int[][] myInitialStates;
    
    public GameOfLifeGrid (Parameters params) {
        super(params);
        myInitialStates = params.getInitialStates();
        
        initializeCells();
    }

    @Override
    protected GridCell initializeCell (int row, int col) {

        int s = myInitialStates[row][col];

        for (State state : GameOfLifeState.values()) {
            if (s == state.getStateValue()) {
                return new SimpleCell(state, row, col);
            }

        }

        // TODO: Return error: invalid initial state
        return null;

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
