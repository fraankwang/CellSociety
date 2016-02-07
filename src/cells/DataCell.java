package cells;

import constants.State;
import javafx.scene.shape.Shape;

public abstract class DataCell extends GridCell {

    public DataCell (State currentState, int row, int col, Shape shape) {
        super(currentState, row, col, shape);
    }

    public abstract void update();
}
