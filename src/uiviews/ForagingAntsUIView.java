/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package uiviews;

import constants.Parameters;
import grids.Grid;
import javafx.scene.Group;

public class ForagingAntsUIView extends UIView {

    public ForagingAntsUIView (Grid grid, Parameters params) {
        super(grid, params);
    }

    @Override
    protected Group createView () {
        Group root = new Group();
        return root;
    }

}
