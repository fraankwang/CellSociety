/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package states;

import javafx.scene.paint.Color;


/**
 * Convenience enum for cell states and color representations
 *
 */
public interface State {
    public Color getColor ();

    public int getStateValue ();
}
