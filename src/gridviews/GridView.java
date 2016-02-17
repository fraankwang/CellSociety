// This entire file is part of my code masterpiece
// Jeremy Schreck


package gridviews;

import java.awt.Dimension;
import java.util.ResourceBundle;
import cells.GridCell;
import constants.Constants;
import constants.Location;
import grids.Grid;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import main.MainView;


/**
 * This abstract class represents the front-end of a grid used in a simulation.
 *
 * Note: Subclasses specify a default shape to use for the grid's cells (i.e.
 * rectangle, hexagon, triangle, etc) and determine how to layout the cells to
 * form the grid.
 *
 * This class is well designed for the following reasons:
 *
 *  * It separates the view and model associated with a Grid
 *  
 *  * It allows for the project's functionality to be easily extended by 
 *  subclassing this class and specifying a new cell shape or grid layout
 *  
 *  * This class can be considered closed. To add new shapes/layouts, this 
 *  class does not need to be modified at all. All you have to do is make a 
 *  new subclass. Elsewhere in the code, you would have to add a new line
 *  that instantiates the new subclass, but that is unavoidable, and also 
 *  not part of my code masterpiece. The only part of this class that is not
 *  closed is using colors to represent state, rather than something else like
 *  image views. But that "open" part of this class is a choice ingrained in the
 *  entire project.
 *  
 *  * Because the Grid view and model are separated, it is easy to switch back
 *  and forth between different cell shapes (GridView subclasses) during the same
 *  simulation. If they were not separated, you might have to create a subclass
 *  of Grid for every simulation-shape pair (i.e. FireHexagonGrid, FireRectangle,
 *  etc), which would not be good design.
 *  
 *  * Various parameters are specified in a configuration resource file, so 
 *  they can be changed by a "non-developer" without having to recompile the 
 *  entire project.
 *  
 *  * The dependencies are pretty clear. For the most part, if someone wanted
 *  to use this class in another project, they could. They would also need the
 *  Grid class, but that is kind of unavoidable (the view needs to know the
 *  data to display). I probably could have just passed the grid's 2d array
 *  of cells. The dependencies for static constants are located at the top
 *  so that they are obvious. I could have given this class its own DEFAULT_
 *  RESOURCE_PACKAGE name and GRID_VIEW_SIZE, but I decided to set them equal
 *  to the ones in Constants and MainView so that the actual value is only
 *  in one place (less duplicated code). I declared copies of the variables here
 *  rather than referencing the other classes so that the dependencies were 
 *  clear and at the top of the class. 
 *  
 *  
 *  NOTE: upon first glance at the commit history of the project, it may look
 *  like Frank did most of the work on this class. However, that is because for
 *  much of the project, I made commits and pushed to my branch, then had Frank
 *  or Madhav review my pull request and merge it. I did most of the work in
 *  deciding on the design of a GridView, defining its API, and integrating
 *  it with the rest of the project. Frank edited it later to add an ability
 *  to change the cell size, and he implemented the subclasses. For the code
 *  masterpiece, I refactored some of my code in addition to some of the
 *  changes Frank made to this class when accommodating changing cell sizes.
 *  
 *  
 * @author Jeremy Schreck
 *
 */
public abstract class GridView {

    // Constants (repeated here so dependencies are clear and removable)
    public static final String DEFAULT_RESOURCE_PACKAGE = Constants.DEFAULT_RESOURCE_PACKAGE;
    public static final String RESOURCE_FILE_CONFIGURATION = "configuration";
    public static final int GRID_VIEW_SIZE = MainView.GRID_VIEW_SIZE;

    // Get configuration parameters from a resource file
    private ResourceBundle myConfiguration;

    // Configuration parameters
    private boolean outlined;
    private Dimension myGridSize;
    private int myCellSize;
    private int myCellSizeIncrement;

    // View
    private Group myView;
    private Shape[][] myCellShapes;
    private int myRows;
    private int myColumns;

    // Reference to Model
    private Grid myGrid;

    /**
     * Constructor - Creates a GridView based on a Grid and stores it in myView
     *
     * @param grid - The Grid (model) containing information about the state
     *        of each cell
     */
    public GridView (Grid grid) {

        // Initialize Variables
        myGrid = grid;
        myRows = grid.getRows();
        myColumns = grid.getColumns();

        myConfiguration =
                ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + RESOURCE_FILE_CONFIGURATION);
        myGridSize = new Dimension(GRID_VIEW_SIZE, GRID_VIEW_SIZE);
        myCellSize = Integer.parseInt(myConfiguration.getString("cellSize"));
        myCellSizeIncrement = Integer.parseInt(myConfiguration.getString("cellSizeIncrement"));
        outlined = myConfiguration.getString("outlined").equals("Yes");

        // Setup Display
        initializeView();

    }

    /**
     * Abstract method that specifies the default shape of each cell
     *
     * @return The default Shape to be specified by subclass of GridView
     */
    protected abstract Shape defaultShape ();

    /**
     * Abstract method that specifies the layout of the grid view
     *
     * @return A Group containing the grid view
     */
    protected abstract Group createUI ();

    /**
     * Initializes the view by initializing each shape and placing them in the view
     *
     * NOTE: the color of each cell is determined by its state (defined in the
     * grid model), and shape of each cell/grid layout is specified by each
     * subclass' implementation of this abstract GridView class
     *
     */
    private void initializeView () {
        myCellShapes = initializeShapes(myGrid.getMyCells());
        myView = createUI();
    }

    /**
     * Reloads the view by reinitializing it
     *
     */
    public void reloadView () {
        initializeView();
    }

    /**
     * Initializes myShapes based on the current states of the GridCells
     *
     * @param cells A 2d-array containing data about the cells' states
     */
    private Shape[][] initializeShapes (GridCell[][] cells) {

        Shape[][] shapes = new Shape[myRows][myColumns];

        for (int row = 0; row < myRows; row++) {
            for (int col = 0; col < myColumns; col++) {

                GridCell cell = cells[row][col];
                Shape shape = defaultShape();

                formatShape(shape, cell);
                shape.setOnMouseClicked(e -> myGrid.toggleStateAndUpdateUI(cell));

                shapes[row][col] = shape;

            }
        }

        return shapes;
    }

    /**
     * Formats the Shape based on specified parameters, then update's the its UI
     * to match the visual appearance associated with its initial state
     *
     * @param shape The shape to format
     * @param cell The cell whose current state tells the shape how to update
     */
    private void formatShape (Shape shape, GridCell cell) {
        if (outlined) {
            shape.setStroke(Color.BLACK);
        }

        updateShapeUI(shape, cell);
    }

    /**
     * Updates the view by setting the visual appearance of each shape to the
     * visual appearance corresponding to the shape of each cell
     *
     * Note: Shape and GridCell passed in should be referencing the same location in the grid
     *
     * Note: Currently it sets the shapes color to the color corresponding to
     * each cell state, but this method is here to hide the implementation
     * details of how states are represented visually
     *
     * @param shape The shape to update
     * @paran cell The cell whose current state tells the shape how to update
     *
     */
    private void updateShapeUI (Shape shape, GridCell cell) {
        setShapeColor(shape, cell.getMyCurrentState().getColor());

    }

    /**
     * Updates the shape of the corresponding cell location
     *
     * @param cell The cell whose current state tells the shape how to update
     */
    public void updateCellShape (GridCell cell) {
        Location location = cell.getMyGridLocation();
        Shape shape = myCellShapes[location.getRow()][location.getCol()];
        updateShapeUI(shape, cell);

    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================

    public Group getMyView () {
        return myView;
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

    public int getCellSizeIncrement () {
        return myCellSizeIncrement;
    }

    protected void setShapeColor (Shape shape, Color color) {
        shape.setFill(color);
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

    protected Grid getMyGrid () {
        return myGrid;
    }
}
