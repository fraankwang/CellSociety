/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cells;

import constants.Location;
import states.State;


/**
 * Abstract class for a cell in a grid
 *
 */
public abstract class GridCell {

    private State myCurrentState;
    private State myNextState;
    private Location myGridLocation;

    /**
     * Constructor
     *
     * @param initialState The initial state of the cell
     * @param row The row of the grid in which the cell in located
     * @param col The column of the grid in which the cell in located
     */
    public GridCell (State initialState, int row, int col) {
        myCurrentState = initialState;
        setMyGridLocation(new Location(row, col));

    }

    /**
     * Changes the cell's currentState to its nextState, sets next state to null
     */
    public void transitionStates () {
        myCurrentState = myNextState;
        myNextState = null;
    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================

    public State getMyCurrentState () {
        return myCurrentState;
    }

    public void setMyCurrentState (State currentState) {
        myCurrentState = currentState;
    }

    public State getMyNextState () {
        return myNextState;
    }

    public void setMyNextState (State nextState) {
        myNextState = nextState;
    }

    public Location getMyGridLocation () {
        return myGridLocation;
    }

    public void setMyGridLocation (Location gridLocation) {
        myGridLocation = gridLocation;
    }

}
