package constants;

import javafx.scene.paint.Color;

/**
 * Convenience enum for cell states and color representations
 *
 */
public enum State {
                   EMPTY(Color.WHITE),
                   DEAD(Color.WHITE),
                   ALIVE(Color.BLACK),
                   BURNING(Color.ORANGE),
                   TREE(Color.GREEN),
                   BURNED(Color.BROWN),
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
