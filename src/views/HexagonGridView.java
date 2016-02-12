package views;

import grids.Grid;
import javafx.scene.Group;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class HexagonGridView extends GridView {

    public HexagonGridView (Grid grid) {
        super(grid);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected Group createUI () {
        // TODO Auto-generated method stub
        return new Group();
    }
    
    @Override
    protected Shape defaultShape(){
        return new Polygon();
    }

}
