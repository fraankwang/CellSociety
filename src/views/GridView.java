package views;

import cells.GridCell;
import constants.Location;
import javafx.scene.Group;
import javafx.scene.shape.Shape;

public abstract class GridView {

    private Shape[][] myCellShapes;
    private Group myView;
    private int myRows;
    private int myColumns;
    
    public GridView (GridCell[][] cells) {
       myRows = cells.length;
       myColumns = cells[0].length;
       myCellShapes = new Shape[myRows][myColumns];
       initialize(cells);
       myView = createUI();
    }
    
    private void initialize(GridCell[][] cells){
        for (int row = 0; row < getMyRows(); row++) {
            for (int col = 0; col < getMyColumns(); col++) {
                GridCell cell = cells[row][col];
                myCellShapes[row][col] = cell.getMyShape();
            }
        }
    }
    
    protected abstract Group createUI();
   
    
    public void updateCellShape(GridCell cell){
        Location location = cell.getMyGridLocation();
        myCellShapes[location.getRow()][location.getCol()] = cell.getMyShape();
        
        //TODO: might need to update the view too depending on how we implement it 
        // (i.e. do something similar to remove/add that we did for the gridpane
    }
    
    public Group getMyView(){
        return myView;
    }
    
    protected Shape[][] getMyCellShapes(){
        return myCellShapes;
    }
    
    protected void setMyCellShapes(Shape[][] cellShapes){
        myCellShapes = cellShapes;
    }

    protected int getMyRows () {
        return myRows;
    }
 
    protected int getMyColumns () {
        return myColumns;
    }


}
