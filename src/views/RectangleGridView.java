package views;

import cells.GridCell;
import constants.Location;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Shape;

public class RectangleGridView extends GridView {

    private GridPane gp;
    
    public RectangleGridView (GridCell[][] cells) {
        super(cells);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected Group createUI(){
        GridPane gridPane = new GridPane();
        gridPane.setPrefSize(MainView.GRID_VIEW_SIZE, MainView.GRID_VIEW_SIZE);
       
        for (int row = 0; row < getMyRows(); row++) {
            for (int col = 0; col < getMyColumns(); col++) {
                Shape shape = getMyCellShapes()[row][col];
                gridPane.add(shape, col, row);
            }
        }

        Group group = new Group();
        group.getChildren().add(gridPane);
        
        gp = gridPane;
        
        return group;
    }

    public void updateCellShape(GridCell cell){
        super.updateCellShape(cell);
        gp.getChildren().remove(overallIndexFromRC());
        gp.add(cell.getMyShape(), cell.getMyGridLocation().getCol(), cell.getMyGridLocation().getRow());
        
        //TODO: might need to update the view too depending on how we implement it 
        // (i.e. do something similar to remove/add that we did for the gridpane
    }
    private int overallIndexFromRC(){
        
        
        return getMyRows() * getMyColumns() + getMyColumns();
    }

}
