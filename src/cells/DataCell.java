package cells;

import constants.State;
import javafx.scene.shape.Shape;

public abstract class DataCell extends GridCell {

    public DataCell (State currentState, int r, int c, Shape s) {
        super(currentState, r, c, s);
    }

    public abstract void update();
}
