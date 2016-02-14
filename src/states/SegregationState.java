/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package states;

import javafx.scene.paint.Color;


/**
 *  Enum of states for the Segregation simulation 
 *
 */
public enum SegregationState implements State {

    EMPTY(Color.WHITE, 0),
    RED(Color.RED, 1),
    BLUE(Color.BLUE, 2);

    private Color myColor;
    private int stateValue;

    SegregationState (Color color, int state) {
        myColor = color;
        stateValue = state;
    }

    @Override
    public Color getColor () {
        return myColor;
    }

    @Override
    public int getStateValue () {
        return stateValue;
    }
}
