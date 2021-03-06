/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package states;

import javafx.scene.paint.Color;

/**
 *  Enum of states for the PredatorPrey simulation 
 *
 */
public enum WatorState implements State {
    
    EMPTY(Color.BLUE, 0),
    SHARK(Color.RED, 1),
    FISH(Color.YELLOW, 2),
    DEAD(Color.WHITE, 3);

    private Color myColor;
    private int stateValue;

    WatorState (Color color, int state) {
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
