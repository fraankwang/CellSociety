/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package gridviews;

import java.awt.Dimension;
import cells.GridCell;
import constants.Constants;
import constants.Location;
import grids.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import main.MainView;


public abstract class GridView {
    public static final boolean OUTLINED = Constants.RESOURCES.getString("outlined").equals("Yes");
    private final int CELL_SIZE_INCREMENT = 3;

    private Shape[][] myCellShapes;
    private Group myView;
    private int myRows;
    private int myColumns;

    private int myCellSize;
    private Dimension myGridSize;

    private Grid myGrid; // TODO: use interface to specify how grid view
                         // interacts with grid

    /**
     * Instantiates variables and creates UI
     *
     * @param grid
     */
    public GridView (Grid grid, int cellSize) {
        myGrid = grid;

        myCellSize = cellSize;

        myGridSize = new Dimension(MainView.GRID_VIEW_SIZE, MainView.GRID_VIEW_SIZE);

        GridCell[][] cells = grid.getMyCells();
        myRows = grid.getRows();
        myColumns = grid.getColumns();
        myCellShapes = new Shape[myRows][myColumns];

        initialize(cells);
        myView = createUI();

    }

    /**
     * Initializes Shape[row][col] using given GridCells' rows/cols with
     * GridCells' shape
     *
     * @param cells
     */
    private void initialize (GridCell[][] cells) {
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

    /**
     * Changes cell size, recreated root in GridView
     *
     * @param increment
     */
    public void updateUI () {
        initialize(myGrid.getMyCells());
        myView = createUI();

    }

    /**
     * Formats Shape of GridCell based on specified parameters, then update's the Shape UI
     *
     * @param shape
     * @param cell
     */
    protected void formatShape (Shape shape, GridCell cell) {
        if (OUTLINED) {
            shape.setStroke(Color.BLACK);
        }

        updateShapeUI(shape, cell);
    }

    /**
     * UI updating function which reads currentState of GridCell and sets
     *
     * @param shape to match the corresponding color of @param cell
     *        Shape and GridCell passed in should be referencing the same element
     */
    protected void updateShapeUI (Shape shape, GridCell cell) {
        setShapeColor(shape, cell.getMyCurrentState().getColor());

    }

    /**
     *
     * @return default Shape to be specified by subclass of GridView
     */
    protected abstract Shape defaultShape ();

    /**
     * Abstract class that customizes Grid and Game UI elements to be
     * implemented by subclass of GridView
     *
     * @return formatted Group
     */
    protected abstract Group createUI ();

    /**
     * Updates 1-1 corresponding Shape[][] with GridCell's Shape attribute
     *
     * @param cell
     */
    public void updateCellShape (GridCell cell) {
        Location location = cell.getMyGridLocation();
        Shape shape = myCellShapes[location.getRow()][location.getCol()];
        updateShapeUI(shape, cell);

    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================

    protected void setShapeColor (Shape shape, Color color) {
        shape.setFill(color);
    }

    public Group getMyView () {
        return myView;
    }

    protected Shape[][] getMyCellShapes () {
        return myCellShapes;
    }

    protected void setMyCellShapes (Shape[][] cellShapes) {
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

    public int getMyCellSize () {
        return myCellSize;
    }

    public void setMyCellSize (int cellSize) {
        myCellSize = cellSize;
    }

    public Dimension getMyGridSize () {
        return myGridSize;
    }

    public static boolean isOutlined () {
        return OUTLINED;
    }

    public int getCellSizeIncrement () {
        return CELL_SIZE_INCREMENT;
    }

}
