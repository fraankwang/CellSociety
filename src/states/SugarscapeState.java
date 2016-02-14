package states;

import javafx.scene.paint.Color;

/**
 *  Enum of states for the Sugarscape simulation 
 *
 */
public enum SugarscapeState implements State {

      AGENT(Color.RED, 0),
      EMPTY(Color.web("0xffffff"), 1),
      LOW(Color.web("0xfff5e6"), 2),
      MEDIUM(Color.web("ffcc80"), 3),
      HIGH(Color.web("#ff9900"), 4),
      STRONG(Color.web("#cc7a00"), 5);



    private Color myColor;
    private int stateValue;

    SugarscapeState (Color color, int state) {
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
