package cellsociety_team03;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class DataCell extends GridCell {

    public DataCell (State currentState, int size, Shape s) {
        super(currentState, size, s);
    }

    public abstract void update();
}
