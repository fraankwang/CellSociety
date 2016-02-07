/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cells;

import constants.State;
import javafx.scene.shape.Shape;


/**
 * A simple grid cell with no persistent data other than state
 *
 */
public class SimpleCell extends GridCell {

    public SimpleCell (State currentState, int row, int col, Shape s) {
        super(currentState, row, col, s);
    }

}
