/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package gridviews;

import cells.GridCell;
import constants.Location;
import javafx.scene.Group;
import javafx.scene.shape.Shape;

public abstract class GridView {

    private Shape[][] myCellShapes;
    private Group myView;
    private int myRows;
    private int myColumns;
    
    /**
     * Instantiates variables and creates UI
     * @param cells
     */
    public GridView (GridCell[][] cells) {
       myRows = cells.length;
       myColumns = cells[0].length;
       myCellShapes = new Shape[myRows][myColumns];
       initialize(cells);
       myView = createUI();
    }
    
    /**
     * Initializes Shape[row][col] using given GridCells' rows/cols with GridCells' shape 
     * @param cells
     */
    private void initialize(GridCell[][] cells){
        for (int row = 0; row < getMyRows(); row++) {
            for (int col = 0; col < getMyColumns(); col++) {
                GridCell cell = cells[row][col];
                myCellShapes[row][col] = cell.getMyShape();
            }
        }
    }
    
    /**
     * Abstract class that customizes Grid and Game UI elements to be implemented by subclass of GridView
     * @return formatted Group
     */
    protected abstract Group createUI();
   
    /**
     * Updates 1-1 corresponding Shape[][] with GridCell's Shape attribute
     * @param cell
     */
    public void updateCellShape(GridCell cell){
        Location location = cell.getMyGridLocation();
        myCellShapes[location.getRow()][location.getCol()] = cell.getMyShape();
        
        //TODO: might need to update the view too depending on how we implement it 
        // (i.e. do something similar to remove/add that we did for the gridpane)
    }
    

    // =========================================================================
    // Getters and Setters
    // =========================================================================
    
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
