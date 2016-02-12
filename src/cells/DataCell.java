/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cells;

import javafx.scene.shape.Shape;
import states.State;


/**
 * A grid cell with persistent data in addition to state
 *
 */
public abstract class DataCell extends GridCell {

    public DataCell (State currentState, int row, int col, Shape shape) {
        super(currentState, row, col, shape);
    }

    /**
     * allows the the DataCell to update its persistent information
     * 
     */
    public abstract void update ();
}
