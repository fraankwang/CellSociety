/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package states;

import javafx.scene.paint.Color;


public enum FireState implements State {
                                        EMPTY(Color.WHITE, 0),
                                        TREE(Color.GREEN, 1),
                                        BURNING(Color.RED, 2),
                                        BURNED(Color.YELLOW, 3);

    private Color myColor;
    private int stateValue;

    FireState (Color color, int state) {
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
