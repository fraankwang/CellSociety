/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package gridviews;

import grids.Grid;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import main.MainView;


public class HexagonGridView extends GridView {

    public HexagonGridView (Grid grid, int cellSize) {
        super(grid, cellSize);

    }

    /**
     * defaultShape instantiates shape to be put in 2D array to run simulation (not for UI),
     * so exact Hexagon coordinates do not matter because they will be cleared for UI placing
     */
    @Override
    protected Shape defaultShape () {
        Polygon shape = new Polygon();
        shape.getPoints().addAll(generateHexCoordinates(
                                                        (double) getMyCellSize(),
                                                        (double) getMyCellSize(), getMyCellSize()));
        return shape;

    }

    /**
     *
     * @param x-coordinate of top left vertex
     * @param y-coordinate of top left vertex
     * @param cellSize length of edge
     * @return 6 coordinates (consecutive coordinates pair for one (x,y) coordinate)
     *         going clockwise starting from the top left vertex
     */
    private Double[] generateHexCoordinates (Double x, Double y, int cellSize) {
        Double[] coordinates = new Double[12];
        coordinates[0] = x;
        coordinates[1] = y;
        coordinates[2] = x + (cellSize * Math.sqrt(3.0)) / 2;
        coordinates[3] = y - cellSize / 2;
        coordinates[4] = x + (cellSize * Math.sqrt(3.0));
        coordinates[5] = y;
        coordinates[6] = x + (cellSize * Math.sqrt(3.0));
        coordinates[7] = y + cellSize;
        coordinates[8] = x + (cellSize * Math.sqrt(3.0)) / 2;
        coordinates[9] = y + cellSize + cellSize / 2;
        coordinates[10] = x;
        coordinates[11] = y + cellSize;

        return coordinates;

    }

    @Override
    protected Group createUI () {
        Group root = new Group();

        Pane pane = new Pane();
        pane.setPrefHeight(MainView.GRID_VIEW_SIZE);
        pane.setPrefWidth(MainView.GRID_VIEW_SIZE);

        int cellSize = getMyCellSize();

        for (int row = 0; row < getMyRows(); row++) {
            for (int col = 0; col < getMyColumns(); col++) {
                Polygon shape = (Polygon) getMyCellShapes()[row][col];
                shape.getPoints().clear();

                double xOffset = col * (cellSize * Math.sqrt(3.0));
                double xStaggerOffset = xOffset + ((cellSize * Math.sqrt(3.0)) / 2);
                double yOffset = cellSize / 2 + (row * (cellSize + cellSize / 2));

                if (row % 2 == 0) {
                    shape.getPoints().addAll(generateHexCoordinates(xOffset, yOffset, cellSize));

                }
                else if (row % 2 != 0) {
                    shape.getPoints()
                            .addAll(generateHexCoordinates(xStaggerOffset, yOffset, cellSize));

                }

                pane.getChildren().add(shape);
            }
        }

        root.getChildren().add(pane);
        return root;

    }

}
