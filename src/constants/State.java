/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package constants;

import javafx.scene.paint.Color;


/**
 * Convenience enum for cell states and color representations
 *
 */
public enum State {
                   DEAD(Color.WHITE, 0),
                   ALIVE(Color.BLACK, 1),
                   EMPTY(Color.WHITE, 0),
                   TREE(Color.GREEN, 1),
                   BURNING(Color.RED, 2),
                   BURNED(Color.YELLOW, 3),
                   RED(Color.RED, 1),
                   BLUE(Color.BLUE, 2),
                   SHARK(Color.GRAY, 1),
                   FISH(Color.YELLOW, 2);

    private Color myColor;
    private int stateValue;

    State (Color color, int state) {
        myColor = color;
        stateValue = state;
    }

    public Color getColor () {
        return myColor;
    }
    
    public int getStateValue () {
    	return stateValue;
    }
}
