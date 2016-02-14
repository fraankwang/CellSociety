/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cells;

import states.State;

/**
 * Abstract class for an Agent 
 * 
 *  
 */
public abstract class Agent extends DataCell {

    public Agent (State currentState, int row, int col) {
        super(currentState, row, col);
    }

    @Override
    public void update () {

    }

}
