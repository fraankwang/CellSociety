/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cells;

import constants.Location;
import constants.State;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;


/**
 * Abstract class for a cell in a grid
 *
 */
public abstract class GridCell {
    private State myCurrentState;
    private State myNextState;
    private Location myGridLocation;
    private Shape myShape;

    /**
     * Constructor
     *
     * @param initialState The initial state of the cell
     * @param row The row of the grid in which the cell in located
     * @param col The column of the grid in which the cell in located
     * @param shape The shape of the cell
     */
    public GridCell (State initialState, int row, int col, Shape shape) {
        myCurrentState = initialState;
        myShape = shape;
        initializeShape();
        setMyGridLocation(new Location(row, col));

    }

    /**
     * Sets the initial color of the cell and gives it a border
     */
    private void initializeShape () {
        getMyShape().setStroke(Color.BLACK);
        setMyColor();
        
    }

    /**
     * Changes the cell's currentState to its nextState, sets next state to null, and updates shape
     * UI
     */
    public void transitionStates () {
        myCurrentState = myNextState;
        myNextState = null;
        setMyColor();
        
    }

    /**
     * Sets the cell's shape's color based on the cell's current state
     */
    private void setMyColor () {
        getMyShape().setFill(getMyCurrentState().getColor());
        
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

    public Shape getMyShape () {
        return myShape;
    }

    public Location getMyGridLocation () {
        return myGridLocation;
    }

    private void setMyGridLocation (Location gridLocation) {
        myGridLocation = gridLocation;
    }

}
