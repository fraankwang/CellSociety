/**
* Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
*/

package gridviews;

import cells.GridCell;
import grids.Grid;
import javafx.scene.Group;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class TriangleGridView extends GridView {

    public TriangleGridView (Grid grid) {
        super(grid);
        setMyCellShapes(new Polygon[1][1]);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected Group createUI(){
        return new Group();
    }

    @Override
    protected Shape defaultShape(){
        return new Polygon();
    }
}
