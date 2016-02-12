/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package states;

import javafx.scene.paint.Color;

public enum WatorState implements State {
    EMPTY(Color.WHITE, 0),
    SHARK(Color.GRAY, 1),
	FISH(Color.YELLOW, 2),
	DEAD(Color.WHITE, 3);
	
    private Color myColor;
    private int stateValue;

    WatorState (Color color, int state) {
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