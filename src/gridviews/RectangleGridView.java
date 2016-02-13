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


public class RectangleGridView extends GridView {

    public RectangleGridView (Grid grid) {
        super(grid);
    }

    @Override
    protected Shape defaultShape() {
    	System.out.println(getMyCellSize());
    	Polygon shape = new Polygon();
    	shape.getPoints().addAll(generateSquareCoordinates(
    			(double) getMyCellSize(), (double) getMyCellSize(), getMyCellSize()));
        return shape;
        
    }
    
    /**
     * 
     * @param x-coordinate of top left vertex
     * @param y-coordinate of top left vertex
     * @param cellSize length of edge
     * @return 4 coordinates (consecutive coordinates pair for one (x,y) coordinate) 
     * going clockwise starting from the top left vertex
     */
    private Double[] generateSquareCoordinates (Double x, Double y, int cellSize) {
    	Double[] coordinates = new Double[8];
    	
    	coordinates[0] = x; 
		coordinates[1] = y;
		coordinates[2] = x + cellSize;
		coordinates[3] = y;
		coordinates[4] = x + cellSize;
		coordinates[5] = y + cellSize;
		coordinates[6] = x;
		coordinates[7] = y + cellSize;
		
		return coordinates;
		
    }
    
    public Group createUI () {
        Group root = new Group();
    	
    	Pane pane = new Pane();
    	pane.setPrefHeight(MainView.GRID_VIEW_SIZE);
    	pane.setPrefWidth(MainView.GRID_VIEW_SIZE);
    	
    	int cellSize = getMyCellSize();
    	
    	for (int row = 0; row < getMyRows(); row++) {
            for (int col = 0; col < getMyColumns(); col++) {
                Polygon shape = (Polygon) getMyCellShapes()[row][col];
                shape.getPoints().clear();
                
                double xOffset = col * cellSize;
                double yOffset = row * cellSize;
                
                shape.getPoints().addAll(generateSquareCoordinates(xOffset, yOffset, cellSize));
                
                pane.getChildren().add(shape);
            }
        }

    	root.getChildren().add(pane);
        return root;
        
    }
   
}
