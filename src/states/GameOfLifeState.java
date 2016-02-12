/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package states;

import javafx.scene.paint.Color;

public enum GameOfLifeState implements State {
    DEAD(Color.BLACK, 0),
    ALIVE(Color.WHITE, 1);
	
    private Color myColor;
    private int stateValue;

    GameOfLifeState (Color color, int state) {
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