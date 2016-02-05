package cells;

import constants.State;
import grids.Location;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;


public abstract class GridCell {
    private State myCurrentState;
    private State myNextState;
    private Location myGridLocation;
    private Shape myShape;

    /**
     * Constructor
     * 
     * @param initialState The initial state of the cell
     * @param r The row of the grid in which the cell in located
     * @param c The column of the grid in which the cell in located
     * @param s The shape of the cell
     */
    public GridCell (State initialState, int r, int c, Shape s) {
        myCurrentState = initialState;
        myShape = s;
        initializeShape();
        setMyGridLocation(new Location(r, c));
       
    }
    
    /**
     * Sets the initial color of the cell and gives it a border
     */
    private void initializeShape(){
        this.getMyShape().setStroke(Color.BLACK);        
        setMyColor();
    }
    
    /**
     * Changes the cell's currentState to its nextState, sets next state to null, and updates shape UI
     */
    public void transitionStates(){
        this.myCurrentState = this.myNextState;
        this.myNextState = null;
        this.setMyColor();
    }
    
    /**
     * Sets the cell's shape's color based on the cell's current state
     */
    private void setMyColor () {
        this.getMyShape().setFill(this.getMyCurrentState().getColor());
    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================
    
    public State getMyCurrentState () {
        return myCurrentState;
    }

    public void setMyCurrentState (State myCurrentState) {
        this.myCurrentState = myCurrentState;
    }

    public State getMyNextState () {
        return myNextState;
    }

    public void setMyNextState (State myNextState) {
        this.myNextState = myNextState;
    }
    
    public Shape getMyShape () {
        return myShape;
    }
   
    public Location getMyGridLocation () {
        return myGridLocation;
    }

    private void setMyGridLocation (Location myGridLocation) {
        this.myGridLocation = myGridLocation;
    }

}
