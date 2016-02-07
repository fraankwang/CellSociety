/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cells;

import constants.State;
import javafx.scene.shape.Shape;


/**
 * A grid cell with persistent data in addition to state
 *
 */
public abstract class DataCell extends GridCell {


    public DataCell (State currentState, int row, int col, Shape shape) {
        super(currentState, row, col, shape);
    }

    public abstract void update ();
}
