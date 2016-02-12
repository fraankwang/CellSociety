package views;

import cells.GridCell;
import constants.Location;
import grids.Grid;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


public class RectangleGridView extends GridView {

    private GridPane gp;

    public RectangleGridView (Grid grid) {
        super(grid);
    }

    @Override
    protected Group createUI () {
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

    public void updateCellShape (GridCell cell) {
        super.updateCellShape(cell);

        Location l = cell.getMyGridLocation();
        Shape old = getMyCellShapes()[l.getRow()][l.getCol()];
        updateShapeUI(old, cell);
        
        //gp.getChildren().remove(old);
        //gp.add(cell.getMyShape(), cell.getMyGridLocation().getCol(), cell.getMyGridLocation().getRow());

    }

    protected Shape defaultShape(){
        //TODO: replace 30 with cell size from resource file
        return new Rectangle(30,30);
    }

}
