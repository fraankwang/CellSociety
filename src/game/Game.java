/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package game;

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
import uiviews.GameOfLifeUIView;
import uiviews.PredatorPreyUIView;
import uiviews.SegregationUIView;
import uiviews.SugarscapeUIView;
import uiviews.UIView;


/**
 * Manages the simulation - in charge of the game loop and grid
 */
public class Game {
    public static final int MILLISECONDS_PER_SECOND = 1000;
    
    private String myGameType;
    private Grid myGrid;
    private Parameters myParameters;
    private String myGridShape;
    private String myNeighborDirections;

    private Group myGameRoot;
    private Group myUIRoot;
    private Group myLineChartRoot;
    private Timeline myGameLoop;

    /**
     * Given parsed XML data, construct appropriate Grid Model/View and gameLoop
     *
     * @param params A map containing parsed XML data
     */
    public Game (Parameters params) {
        myGameType = params.getGameType();
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
     * Initializes a specific grid based on the gameType parameter
     *
     * This function uses only global variables so the user can press the reset
     * button (in the Main class) at any time
     */
    private void initializeGridModel () {
    	UIView uiView = null;
    	
        if (myGameType.equals("Fire")) {
            myGrid = new FireGrid(myParameters);
            uiView = new FireUIView(myGrid, myParameters);
            
        }
        else if (myGameType.equals("GameOfLife")) {
            myGrid = new GameOfLifeGrid(myParameters);
            uiView = new GameOfLifeUIView(myGrid, myParameters);

        }
        else if (myGameType.equals("Segregation")) {
            myGrid = new SegregationGrid(myParameters);
            uiView = new SegregationUIView(myGrid, myParameters);

        }
        else if (myGameType.equals("PredatorPrey")) {
            myGrid = new PredatorPreyGrid(myParameters);
            uiView = new PredatorPreyUIView(myGrid, myParameters);
            
        }
        else if (myGameType.equals("Sugarscape")) {
            myGrid = new SugarscapeGrid(myParameters);
            uiView = new SugarscapeUIView(myGrid, myParameters);
        
        }
        else if (myGameType.equals("ForagingAnts")) {
            myGrid = new ForagingAntsGrid(myParameters);
        
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
	 * Creates ScrollPane with current GridView and puts it in myGameRoot,
	 * the primary UI element to be displayed when MainController sets up a new Game
	 */
	private void setRoot () {
	    Group group = new Group();
	    group.getChildren().add(createScrollPane());
	    myGameRoot = group;
	    
	}

	/**
     * Commanded by MainController to re-initialize GridView and create a new ScrollPane with
     * re-initialized GridView
     * @param type
     */
    public void changeCellShape (String type) {
    	
    	if (type.equals("Hexagon")){
    		myNeighborDirections = "All";
    	}
    	
    	myGridShape = type;
    	initializeGridView(getMyGrid().getMyGridView().getMyCellSize());
    	
    	setRoot();
    	
    }

    /**
     * Changes cell size parameter and re-initializes GridView
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
     * @param currentCellSize
     */
    public void changeCellSizeParameter(int currentCellSize) {
		myParameters.setCellSize(currentCellSize);
		
	}
    
    /**
     * Changes myNeighborDirections and resets Grid's neighbors-to-be-looked at 
     * for various state-determining algorithms
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
     * Initialize the game loop, using the delay parameter from xml to determine speed
     * In each KeyFrame of the animation, myGameLoop calls the step() function from myGrid
     */
    private void initializeGameLoop () {

        double framesPerSecond =
                MILLISECONDS_PER_SECOND * 1 / myParameters.getDelay() / 10;
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECONDS_PER_SECOND / framesPerSecond),
                                      e -> myGrid.step());

        myGameLoop = new Timeline();
        myGameLoop.setCycleCount(Animation.INDEFINITE);
        myGameLoop.getKeyFrames().add(frame);
        
    }

    /**
     * Start the game loop
     */
    public void startGame () {
        if (myGameLoop != null) {
            myGameLoop.play();
        }

    }

    /**
     * End the game loop
     */
    public void stopGame () {
        if (myGameLoop != null) {
            myGameLoop.stop();
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
    	myGameLoop.setRate(speed);
    }

    public Group getGameRoot () {
        return myGameRoot;
    }

    public Grid getMyGrid () {
        return myGrid;
    }

    public Double getDelay () {
        return myParameters.getDelay();
    }

    public String getMyGameType () {
        return myGameType;
    }

	public Group getMyUIRoot() {
		return myUIRoot;
	}

	public Group getLineChartRoot() {
		return myLineChartRoot;
	}

}
