package views;

import cells.GridCell;
import javafx.scene.Group;

public class RectangleGridView extends GridView {

    public RectangleGridView (GridCell[][] cells) {
        super(cells);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected Group createUI(){
        return new Group();
    }


}
