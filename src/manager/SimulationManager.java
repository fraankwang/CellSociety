// This entire file is part of my masterpiece.
// FRANK WANG

/**
 * This code represents my masterpiece because I built the original infrastructure (initialize() - which is now initializeGrid())
 * 
 * This is, as the name implies, a manager of everything within the simulation. It contains a Grid myGrid,
 * which contains both the model and view components. 
 * 
 * The global variables are all used in getters or setters for the MainController to display on the primaryPane,
 * for example the LineChartRoot or the UIRoot. 
 * 
 * The initializeGrid() method kicks off the simulation by instantiating the Model (new SubclassGrid and SubclassUIView)
 * along with the GridView which takes myGrid as a reference as the view depends on the model.
 * 
 * setRoot() and createScrollPane just work with whatever the global variable myGrid is at, which is important for
 * displaying in the MainController as we want a display of the current data without reinstantiating the model.
 * 
 * SimulationManager also manages the Timeline, and various methods (i.e. MainController telling the SimulationManager
 * to start/stop/reset or set the Timeline rate) affect the global variable. initializeGameLoop() is called
 * in the constructor, which is called at the end because it takes the Grid's step() function as something that
 * each KeyFrame of the Animation does. myGrid.step() continually updates the simulations, which are linked to their views,
 * which the MainController has linked with the MainView through display functions.
 */

/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package manager;

import java.util.ArrayList;
import java.util.List;
import constants.Constants;
import constants.NeighborOffset;
import constants.Offset;
import constants.Parameters;
import grids.FireGrid;
import grids.ForagingAntsGrid;
import grids.GameOfLifeGrid;
import grids.Grid;
import grids.PredatorPreyGrid;
import grids.SegregationGrid;
import grids.SugarscapeGrid;
import gridviews.GridView;
import gridviews.HexagonGridView;
import gridviews.RectangleGridView;
import gridviews.TriangleGridView;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.util.Duration;
import uiviews.FireUIView;
import uiviews.ForagingAntsUIView;
import uiviews.GameOfLifeUIView;
import uiviews.PredatorPreyUIView;
import uiviews.SegregationUIView;
import uiviews.SugarscapeUIView;
import uiviews.UIView;


/**
 * Manages the simulation - in charge of the Simulation loop and grid
 */
public class SimulationManager {
    public static final int MILLISECONDS_PER_SECOND = 1000;

    
    private String mySimulationType;

    private Grid myGrid;
    private Parameters myParameters;
    private String myGridShape;
    private String myNeighborDirections;

    private Group mySimulationRoot;
    private Group myUIRoot;
    private Group myLineChartRoot;
    private Timeline mySimulationLoop;

    /**
     * Given parsed XML data, construct appropriate Grid Model/View and SimulationLoop
     *
     * @param params A map containing parsed XML data
     */
    public SimulationManager (Parameters params) {
        mySimulationType = params.getSimulationType();
        myParameters = params;

        myGridShape = Constants.RESOURCES.getString("defaultGridShape");
        myNeighborDirections = Constants.RESOURCES.getString("defaultNeighborDirections");

        initializeGrid(params.getCellSize());
        initializeGameLoop();

    }

    /**
     * Initializes the grid model and view to use in the simulation, based
     * on xml parameters. Also sets gridShape and neighborDirections based on
     * the resource file.
     */
    public void initializeGrid (int cellSize) {
        initializeGridModel();
        initializeGridView(cellSize);
        initializeNeighborOffsets();
        setRoot();

    }

    /**
     * Initializes a specific grid based on the SimulationType parameter
     *
     * This function uses only global variables so the user can press the reset
     * button (in the Main class) at any time
     */
    private void initializeGridModel () {

    	UIView uiView = null;
    	
        if (mySimulationType.equals("Fire")) {
            myGrid = new FireGrid(myParameters);
            uiView = new FireUIView(myGrid, myParameters);

        }
        else if (mySimulationType.equals("GameOfLife")) {
            myGrid = new GameOfLifeGrid(myParameters);
            uiView = new GameOfLifeUIView(myGrid, myParameters);

        }
        else if (mySimulationType.equals("Segregation")) {
            myGrid = new SegregationGrid(myParameters);
            uiView = new SegregationUIView(myGrid, myParameters);

        }
        else if (mySimulationType.equals("PredatorPrey")) {
            myGrid = new PredatorPreyGrid(myParameters);
            uiView = new PredatorPreyUIView(myGrid, myParameters);

        }
        else if (mySimulationType.equals("Sugarscape")) {
            myGrid = new SugarscapeGrid(myParameters);
            uiView = new SugarscapeUIView(myGrid, myParameters);

        }

        else if (mySimulationType.equals("ForagingAnts")) {
            myGrid = new ForagingAntsGrid(myParameters);
            uiView = new ForagingAntsUIView(myGrid, myParameters);


        }
        
        myGrid.setMyUIView(uiView);

        myUIRoot = uiView.getView();
        
        uiView.createChart();
        myLineChartRoot = uiView.getLineChart();
        

    }

    /**
     * Instantiates GridView based on GridCell shapes and passes myCells to populate it
     */
    private void initializeGridView (int cellSize) {

        GridView gridView = null;
        if (cellSize == 0) {
            cellSize = Integer.parseInt(Constants.RESOURCES.getString("cellSize"));

        }

        if (myGridShape.equals(Constants.RESOURCES.getString("ShapeRectangle"))) {
            gridView = new RectangleGridView(myGrid, cellSize);

        }
        else if (myGridShape.equals(Constants.RESOURCES.getString("ShapeTriangle"))) {
            gridView = new TriangleGridView(myGrid, cellSize);

        }
        else if (myGridShape.equals(Constants.RESOURCES.getString("ShapeHexagon"))) {
            if (myNeighborDirections.equals("All")) {
                gridView = new HexagonGridView(myGrid, cellSize);

            }
            else {
                // TODO: return error
                String errorMessage =
                        Constants.RESOURCES.getString("errorMsgInvalidNeighborDirections");
                if (myNeighborDirections.equals("Cardinal") ||
                    myNeighborDirections.equals("Diagonal")) {
                    errorMessage =
                            String.format(Constants.RESOURCES
                                    .getString("errorMsgGridShapeNeighborDirections"),
                                          myGridShape, myNeighborDirections);
                }
                System.out.println(errorMessage);
            }

        }

        myGrid.setMyGridView(gridView);

    }


	/**
	 * Creates ScrollPane with current GridView and puts it in myGameRoot,
	 * the primary UI element to be displayed when MainController sets up a new Game
	 */
	private void setRoot () {
	    Group group = new Group();
	    group.getChildren().add(createScrollPane());
	    mySimulationRoot = group;
	    
	}

	/**
     * Creates a scroll pane surrounding the GridView
     *
     * @return ScrollPane with appropriate GridView
     */
    private ScrollPane createScrollPane () {
        ScrollPane sp = new ScrollPane();
        sp.setPrefSize(myGrid.getMyGridSize().width, myGrid.getMyGridSize().height);
        sp.setContent(myGrid.getGridView());

        return sp;

    }



    /**
     * Commanded by MainController to re-initialize GridView and create a new ScrollPane with
     * re-initialized GridView
     *
     * @param type
     */
    public void changeCellShape (String type) {

        if (type.equals("Hexagon")) {
            myNeighborDirections = "All";
        }

        myGridShape = type;
        initializeGridView(getMyGrid().getMyGridView().getMyCellSize());

        setRoot();

    }

    /**
     * Changes cell size parameter and re-initializes GridView
     *
     * @param increment
     */
    public void changeCellSize (boolean increment) {
        int cellSize = getMyGrid().getMyGridView().getMyCellSize();

        if (increment) {
            cellSize += getMyGrid().getMyGridView().getCellSizeIncrement();

        }
        else {
            cellSize -= getMyGrid().getMyGridView().getCellSizeIncrement();

        }

        getMyGrid().setCellSize(cellSize);
        getMyGrid().getMyGridView().updateUI();
        setRoot();

    }

    /**
     * Modifies cell size parameter (to be read by initialize methods) to
     *
     * @param currentCellSize
     */
    public void changeCellSizeParameter (int currentCellSize) {
        myParameters.setCellSize(currentCellSize);

    }

    /**
     * Changes myNeighborDirections and resets Grid's neighbors-to-be-looked at
     * for various state-determining algorithms
     *
     * @param neighborDirections
     */
    public void setNeighborDirections (String neighborDirections) {
        myNeighborDirections = neighborDirections;
        initializeNeighborOffsets();

    }
    
	public void resetGraph() {
		getMyGrid().getMyUIView().createChart();
        myLineChartRoot = getMyGrid().getMyUIView().getLineChart();
		
	}


    /**
     * Sets myGrid's myNeighbor's attribute with appropriate directional headings
     */
    private void initializeNeighborOffsets () {

        if (myNeighborDirections.equals("Cardinal")) {
            myGrid.setNeighborOffsets(neighborOffsetsCardinal());

        }
        else if (myNeighborDirections.equals("Diagonal")) {
            myGrid.setNeighborOffsets(neighborOffsetsDiagonal());

        }
        else if (myNeighborDirections.equals("All")) {
            if (myGridShape.equals("Hexagon")) {
                myGrid.setNeighborOffsets(neighborOffsetsAllHexagon());
            }
            else {
                myGrid.setNeighborOffsets(neighborOffsetsAll());
            }
        }
    }

    /**
     * Returns a list of offsets to check to find a GridCell's cardinal neighbors
     *
     * @return The list of offsets
     */
    public List<Offset> neighborOffsetsCardinal () {
        List<Offset> offsets = new ArrayList<Offset>();

        offsets.add(NeighborOffset.TOP.getOffset());
        offsets.add(NeighborOffset.LEFT.getOffset());
        offsets.add(NeighborOffset.RIGHT.getOffset());
        offsets.add(NeighborOffset.BOTTOM.getOffset());

        return offsets;
    }

    /**
     * Returns a list of offsets to check to find a GridCell's diagonal neighbors
     *
     * @return The list of offsets
     */
    public List<Offset> neighborOffsetsDiagonal () {
        List<Offset> offsets = new ArrayList<Offset>();

        offsets.add(NeighborOffset.TOP_LEFT.getOffset());
        offsets.add(NeighborOffset.TOP_RIGHT.getOffset());
        offsets.add(NeighborOffset.BOTTOM_LEFT.getOffset());
        offsets.add(NeighborOffset.BOTTOM_RIGHT.getOffset());

        return offsets;
    }

    /**
     * Returns a list of offsets to check to find all neighbors of a GridCell
     *
     * @return The list of offsets
     */
    public List<Offset> neighborOffsetsAll () {
        List<Offset> offsets = neighborOffsetsCardinal();
        offsets.addAll(neighborOffsetsDiagonal());

        return offsets;

    }

    /**
     * Returns a list of offsets to check to find all neighbors of a hexagonal cell
     *
     * @return The list of offsets
     */
    public List<Offset> neighborOffsetsAllHexagon () {
        List<Offset> offsets = neighborOffsetsCardinal();
        offsets.add(NeighborOffset.BOTTOM_RIGHT.getOffset());
        offsets.add(NeighborOffset.TOP_RIGHT.getOffset());

        return offsets;

    }

    /**
     * Initialize the Simulation loop, using the delay parameter from xml to determine speed
     * In each KeyFrame of the animation, myGameLoop calls the step() function from myGrid
     */
    private void initializeGameLoop () {

        double framesPerSecond =
                MILLISECONDS_PER_SECOND * 1 / myParameters.getDelay();
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECONDS_PER_SECOND / framesPerSecond),
                                      e -> myGrid.step());

        mySimulationLoop = new Timeline();
        mySimulationLoop.setCycleCount(Animation.INDEFINITE);
        mySimulationLoop.getKeyFrames().add(frame);
        

    }

    /**
     * Start the Simulation loop
     */
    public void startGame () {
        if (mySimulationLoop != null) {
            mySimulationLoop.play();
        }

    }

    /**
     * End the Simulation loop
     */
    public void stopGame () {
        if (mySimulationLoop != null) {
            mySimulationLoop.stop();
        }

    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================

    /**
     * Sets the rate of the animation played based on
     * the speed passed in
     *
     * @param speed the speed to set the animation
     */
    public void setTimelineRate (double speed) {
    	mySimulationLoop.setRate(speed);

    }

    public Group getSimulationRoot () {
        return mySimulationRoot;
    }

    public Grid getMyGrid () {
        return myGrid;
    }

    public Double getDelay () {
        return myParameters.getDelay();
    }

    public String getMyGameType () {
        return mySimulationType;
    }

    public Group getMyUIRoot () {
        return myUIRoot;
    }

    public Group getLineChartRoot () {
        return myLineChartRoot;
    }

}
