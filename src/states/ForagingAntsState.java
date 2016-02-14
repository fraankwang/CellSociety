/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package states;

import javafx.scene.paint.Color;

public enum ForagingAntsState implements State{
        

    EMPTY(Color.WHITE, 0),
    ANT(Color.BLACK, 1),
    NEST(Color.BROWN, 2),
    FOOD_SOURCE(Color.ORANGE, 3),
    HOME_LOW(Color.web("0x80b3ff"), 4), // light blue
    HOME_HIGH(Color.web("0x0066ff"), 5), // dark blue
    FOOD_LOW(Color.web("0xff9999"), 6), // light red
    FOOD_HIGH(Color.web("0xff0000"), 7), // dark red
    FOOD_LOW_HOME_HIGH(Color.web("0x8000ff"), 8), // reddish blue
    FOOD_HIGH_HOME_LOW(Color.web("0xb82e8a"), 9), // bluish red
    FOOD_HOME_LOW(Color.web("0xe6b3e6"), 10), // light purple
    FOOD_HOME_HIGH(Color.web("0xac39ac"), 11); // dark purple

    
    

    private Color myColor;
    private int stateValue;

    ForagingAntsState (Color color, int state) {
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
