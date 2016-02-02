package cellsociety_team03;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;


public abstract class GridCell {
    private State myCurrentState;
    private State myNextState;
    private Color myColor;
    private int mySize;
    private Shape myShape; // TODO: can we figure out a way to make it Node?

    public GridCell (State currentState, int size, Shape s) {
        myCurrentState = currentState;
        myShape = s;
        myShape.setStroke(Color.BLACK);        
        myColor = myCurrentState.getColor();
        myShape.setFill(myColor);
        mySize = size;
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

}
