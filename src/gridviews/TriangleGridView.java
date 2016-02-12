package gridviews;

import cells.GridCell;
import javafx.scene.Group;
import javafx.scene.shape.Polygon;

public class TriangleGridView extends GridView {

    public TriangleGridView (GridCell[][] cells) {
        super(cells);
        setMyCellShapes(new Polygon[1][1]);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected Group createUI(){
        return new Group();
    }


}
