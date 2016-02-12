/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package gridviews;

import cells.GridCell;
import javafx.scene.Group;

public class HexagonGridView extends GridView {

    public HexagonGridView (GridCell[][] cells) {
        super(cells);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected Group createUI () {
        // TODO Auto-generated method stub
        return new Group();
    }

}
