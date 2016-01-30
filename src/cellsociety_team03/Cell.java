package cellsociety_team03;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;


public abstract class Cell {
    private State myCurrentState;
    private State myNextState;
    private Color myColor; // TODO: make it part of State enum
    private int mySize;
    private Shape myShape; // TODO: can we figure out a way to make it Node?

    public Cell (State currentState, Color color, int size, Shape s) {
        myCurrentState = currentState;
        myShape = s;
        myShape.setFill(color);
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

    public Shape getMyShape () {
        return myShape;
    }

    public void setMyColor (Color myColor) {
        this.myColor = myColor;
        this.getMyShape().setFill(this.myColor);
    }

}
