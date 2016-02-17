/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package gridviews;

import grids.Grid;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;

public class TriangleGridView extends GridView {

    public TriangleGridView (Grid grid) {
        super(grid);

    }

    /**
     * defaultShape instantiates shape to be put in 2D array to run simulation (not for UI),
     * so exact Triangle coordinates do not matter because they will be cleared for UI placing
     */
    @Override
    protected Shape defaultShape () {
        Polygon shape = new Polygon();
        shape.getPoints().addAll(generateTriangleCoordinates(
                                                             (double) getMyCellSize(),
                                                             (double) getMyCellSize(),
                                                             getMyCellSize(), true));
        return shape;

    }

    /**
     *
     * @param x-coordinate of top vertex
     * @param y-coordinate of top vertex
     * @param cellSize length of edge
     * @return 3 coordinates (consecutive coordinates pair for one (x,y) coordinate)
     *         going clockwise starting from the top vertex
     */
    private Double[] generateTriangleCoordinates (Double x,
                                                  Double y,
                                                  int cellSize,
                                                  boolean pointedUp) {
        Double[] coordinates = new Double[6];

        coordinates[0] = x;
        coordinates[1] = y;

        if (!pointedUp) {
            coordinates[2] = x - (cellSize / 2);
            coordinates[3] = y - (cellSize * Math.sqrt(3.0) / 2);
            coordinates[4] = x + (cellSize / 2);
            coordinates[5] = y - (cellSize * Math.sqrt(3.0) / 2);

        }
        else if (pointedUp) {
            coordinates[2] = x + (cellSize / 2);
            coordinates[3] = y + (cellSize * Math.sqrt(3.0) / 2);
            coordinates[4] = x - (cellSize / 2);
            coordinates[5] = y + (cellSize * Math.sqrt(3.0) / 2);
        }

        return coordinates;

    }

    @Override
    protected Group createUI () {
        Group root = new Group();

        Pane pane = new Pane();
        pane.setPrefHeight(GRID_VIEW_SIZE);
        pane.setPrefWidth(GRID_VIEW_SIZE);

        int cellSize = getMyCellSize();
        double triangleHeight = (cellSize * Math.sqrt(3.0)) / 2;

        for (int row = 0; row < getMyRows(); row++) {
            for (int col = 0; col < getMyColumns(); col++) {
                Polygon shape = (Polygon) getMyCellShapes()[row][col];
                shape.getPoints().clear();

                double xOffset = (cellSize / 2) + (col * (cellSize / 2));
                double yPointUpOffset = row * triangleHeight;
                double yPointDownOffset = yPointUpOffset + triangleHeight;

                // even rows
                if (row % 2 == 0) {
                    if (col % 2 == 0) {
                        shape.getPoints().addAll(generateTriangleCoordinates(
                                                                             xOffset,
                                                                             yPointDownOffset,
                                                                             cellSize, false));
                    }
                    else {
                        shape.getPoints().addAll(generateTriangleCoordinates(
                                                                             xOffset,
                                                                             yPointUpOffset,
                                                                             cellSize, true));
                    }

                }
                else if (row % 2 != 0) {
                    if (col % 2 == 0) {
                        shape.getPoints().addAll(generateTriangleCoordinates(
                                                                             xOffset,
                                                                             yPointUpOffset,
                                                                             cellSize, true));
                    }
                    else {
                        shape.getPoints().addAll(generateTriangleCoordinates(
                                                                             xOffset,
                                                                             yPointDownOffset,
                                                                             cellSize, false));
                    }
                }

                pane.getChildren().add(shape);
            }
        }

        root.getChildren().add(pane);
        return root;

    }

}
