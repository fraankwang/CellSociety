package views;

import java.awt.Dimension;
import cells.GridCell;
import constants.Constants;
import constants.Location;
import grids.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class GridView {
    public static final boolean OUTLINED = Constants.RESOURCES.getString("outlined").equals("Yes");

    private Shape[][] myCellShapes;
    private Group myView;
    private int myRows;
    private int myColumns;
    
    private int myCellSize;
    private Dimension myGridSize;

    
    private Grid myGrid;  //TODO: use interface to specifiy how grid view interacts with grid
    
    public GridView (Grid grid) {
       myGrid = grid;
       myCellSize = Integer.parseInt(Constants.RESOURCES.getString("cellSize"));
       myGridSize = new Dimension(MainView.GRID_VIEW_SIZE, MainView.GRID_VIEW_SIZE);
       
       GridCell[][] cells = grid.getMyCells();
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
                Shape shape = defaultShape();
                formatShape(shape, cell);
               
                shape.setOnMouseClicked(e -> myGrid.toggleStateAndUpdateUI(cell));
                myCellShapes[row][col] = shape;
            }
        }
    }
    
    
    protected void formatShape(Shape shape, GridCell cell){
         if (OUTLINED== true){
                    shape.setStroke(Color.BLACK);
                }
         updateShapeUI(shape, cell);
    }

    protected void setShapeColor(Shape shape, Color color){
        shape.setFill(color);
    }
    
    protected void updateShapeUI(Shape shape, GridCell cell){
        setShapeColor(shape, cell.getMyCurrentState().getColor());

    }
    
    protected abstract Shape defaultShape();
    protected abstract Group createUI();
   
    
    public void updateCellShape(GridCell cell){
        Location location = cell.getMyGridLocation();
        Shape shape = myCellShapes[location.getRow()][location.getCol()];
        updateShapeUI(shape, cell);
        
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

    public Grid getMyGrid () {
        return myGrid;
    }

    public void setMyGrid (Grid myGrid) {
        this.myGrid = myGrid;
    }
    
    protected int getMyCellSize () {
        return myCellSize;
    }

    protected void setMyCellSize (int cellSize) {
        myCellSize = cellSize;
    }
    
    public Dimension getMyGridSize () {
        return myGridSize;
    }


}
