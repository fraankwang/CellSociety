/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package grids;

import java.util.Map;
import java.util.Random;
import cells.GridCell;
import cells.SimpleCell;
import constants.Parameters;
import states.FireState;
import states.State;


/**
 * Grid subclass for Fire simulation
 *
 */
public class FireGrid extends Grid {

    private double myProbCatch;

    public FireGrid (Parameters params) {
        super(params);
        myProbCatch = Double.parseDouble(params.getParameter("probcatch"));

    }

    @Override
    protected GridCell initializeCell (int row, int col) {
        int s = getMyInitialStates()[row][col];

        // Note: duplicated code, but no way to subclass an enum to abstract FireState.values
        // Can maybe use reflection, but we don't know that yet
        for (State state : FireState.values()) {
            if (s == state.getStateValue()) {
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
        }
        else if (willCatch(cell)) {
            cell.setMyNextState(FireState.BURNING);
        }
        else {
            cell.setMyNextState(cell.getMyCurrentState());
        }

    }

    @Override
    protected void toggleState (GridCell cell) {
        if (cell.getMyCurrentState() == FireState.BURNING) {
            cell.setMyCurrentState(FireState.BURNED);

        }
        else if (cell.getMyCurrentState() == FireState.BURNED) {
            cell.setMyCurrentState(FireState.TREE);

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

        return value >= getProbCatch();

    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================
    private double getProbCatch () {
        return myProbCatch;
    }

    @Override
    public Map<String, String> getMyGameState () {
        Map<String, String> currentGameState = super.getMyGameState();
        currentGameState.put("probcatch", Double.toString(getProbCatch()));

        return currentGameState;

    }
}
