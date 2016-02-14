/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package states;

import javafx.scene.paint.Color;

/**
 *  Enum of states for the GameOfLife simulation 
 *
 */
public enum GameOfLifeState implements State {

    DEAD(Color.WHITE, 0),
    ALIVE(Color.BLACK, 1);

    private Color myColor;
    private int stateValue;

    GameOfLifeState (Color color, int state) {
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
