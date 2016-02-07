package cells;

import constants.State;
import javafx.scene.shape.Shape;


public class FishCell extends DataCell {
    private int fishHealth;

    public FishCell (State currentState, int row, int col, Shape s, int health) {
        super(currentState, row, col, s);
        fishHealth = health;

    }

    @Override
    public void update () {
        // TODO Auto-generated method stub

    }

}
