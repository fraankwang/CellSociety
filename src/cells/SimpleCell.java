/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cells;

import states.State;


/**
 * A simple grid cell with no persistent data other than state
 *
 */
public class SimpleCell extends GridCell {

    public SimpleCell (State currentState, int row, int col) {
        super(currentState, row, col);
    }

}
