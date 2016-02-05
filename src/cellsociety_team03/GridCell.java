package cellsociety_team03;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;


public abstract class GridCell {
    private State myCurrentState;
    private State myNextState;
    private Location myGridLocation;
    private Color myColor;
    private Shape myShape; // TODO: can we figure out a way to make it Node?

    public GridCell (State currentState, int r, int c, Shape s) {
        myCurrentState = currentState;
        myShape = s;
        myShape.setStroke(Color.BLACK);        
        myColor = myCurrentState.getColor();
        myShape.setFill(myColor);
        setMyGridLocation(new Location(r, c));
       
    }

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
    
    public void transitionStates(){
    	this.myCurrentState = this.myNextState;
    	this.myNextState = null;
    	this.setMyColor(myColor);
    }

    public Shape getMyShape () {
        return myShape;
    }

    public void setMyColor (Color myColor) {
        this.myColor = myColor;
        //this.getMyShape().setFill(this.myColor);
        this.getMyShape().setFill(this.getMyCurrentState().getColor());
    }

    public Location getMyGridLocation () {
        return myGridLocation;
    }

    public void setMyGridLocation (Location myGridLocation) {
        this.myGridLocation = myGridLocation;
    }

}
