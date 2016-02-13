/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cells;

import states.State;


/**
 * A grid cell with persistent data in addition to state
 *
 */
public abstract class DataCell extends GridCell {

    public DataCell (State currentState, int row, int col) {
        super(currentState, row, col);
    }

    /**
     * allows the the DataCell to update its persistent information
     *
     */
    public abstract void update ();
}
