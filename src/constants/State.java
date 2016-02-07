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
                   DEAD(Color.WHITE),
                   ALIVE(Color.BLACK),
                   EMPTY(Color.WHITE),
                   BURNED(Color.YELLOW),
                   TREE(Color.GREEN),
                   BURNING(Color.RED),
                   RED(Color.RED),
                   BLUE(Color.BLUE),
                   SHARK(Color.GRAY),
                   FISH(Color.YELLOW);

    private Color myColor;

    State (Color color) {
        myColor = color;
    }

    public Color getColor () {
        return myColor;
    }
}
