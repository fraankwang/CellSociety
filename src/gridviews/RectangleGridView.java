/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package gridviews;


import grids.Grid;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import main.MainView;


public class RectangleGridView extends GridView {

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

        return group;
    }


    @Override
    protected Shape defaultShape () {
        return new Rectangle(getMyCellSize(), getMyCellSize());
        
    }

}
