package cellsociety_team03;

import javafx.scene.shape.Shape;

public abstract class DataCell extends GridCell {

    public DataCell (State currentState, int r, int c, Shape s) {
        super(currentState, r, c, s);
    }

    public abstract void update();
}
