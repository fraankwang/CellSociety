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
import grids.GameOfLifeGrid;
import grids.Grid;
import grids.PredatorPreyGrid;
import grids.SegregationGrid;
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
    private Timeline myGameLoop;

    /**
     * Given parsed XML data, construct appropriate Grid Model/View and gameLoop
     *
     * @param params A map containing parsed XML data
     */
    public Game (Parameters params) {
        myGameType = params.getGameType();
        myParameters = params;
        myGridShape = Constants.RESOURCES.getString("gridShape");
        myNeighborDirections = Constants.RESOURCES.getString("neighborDirections");

        initializeGrid();
        initializeGameLoop();

    }

    /**
     * Initializes the grid model and view to use in the simulation, based
     * on xml parameters. Also sets gridShape and neighborDirections based on
     * the resource file.
     */
    public void initializeGrid () {
        initializeGridModel();
        initializeGridView();
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

        if (myGameType.equals("Fire")) {
            myGrid = new FireGrid(myParameters);
            
        }
        else if (myGameType.equals("GameOfLife")) {
            myGrid = new GameOfLifeGrid(myParameters);

        }
        else if (myGameType.equals("Segregation")) {
            myGrid = new SegregationGrid(myParameters);

        }
        else if (myGameType.equals("PredatorPrey")) {
            myGrid = new PredatorPreyGrid(myParameters);
        }

    }

    /**
     * Instantiates GridView based on GridCell shapes and passes myCells to populate it
     */
    private void initializeGridView () {

        GridView gridView = null;

        if (myGridShape.equals("Rectangle")) {
            gridView = new RectangleGridView(myGrid);

        }
        else if (myGridShape.equals("Triangle")) {
            gridView = new TriangleGridView(myGrid);

        }
        else if (myGridShape.equals("Hexagon")) {
            if (myNeighborDirections.equals("All")) {
                gridView = new HexagonGridView(myGrid);
                
            }
            else {
                // TODO: return error
                String errorMessage =
                        Constants.RESOURCES.getString("errorMsgInvalidNeighborDirections");
                if (myNeighborDirections.equals("Cardinal") |
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
    
//    public void changeCellShape (String type) {
//    	if (type.equals("Hexagon")) {
//    		myNeighborDirections = "All";
//    		myGridShape = type;
//    	}
//    	initializeGridView();
//    	
//    }

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
     * Creates a scroll pane surrounding the GridView
     *
     * @return ScrollPane with appropriate GridView
     */
    private ScrollPane createScrollPane () {
        ScrollPane sp = new ScrollPane();
        sp.setPrefSize(myGrid.getMyGridSize().width, myGrid.getMyGridSize().height);
        sp.setContent(myGrid.getView());
        return sp;
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
        // "delay" given in milliseconds
        double framesPerSecond =
                MILLISECONDS_PER_SECOND * 1 / myParameters.getDelay();
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

    /**
     * Sets the rate of the animation played based on
     * the speed passed in
     *
     * @param speed the speed to set the animation
     */
    public void setTimelineRate (double speed) {
        myGameLoop.setRate(speed);
    }
    // =========================================================================
    // Getters and Setters
    // =========================================================================

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
}
