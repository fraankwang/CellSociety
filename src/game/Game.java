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
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.util.Duration;
import views.GridView;
import views.HexagonGridView;
import views.RectangleGridView;
import views.TriangleGridView;


/**
 * Manages the simulation - in charge of the game loop and grid subclass
 */
public class Game {
    public static final int MILLISECONDS_PER_SECOND = 1000;

    private String myGameType;
    private Grid myGrid;
    private Parameters myParameters;
    private String myGridShape;
    private String myNeighborsToConsider;

    private Group myGameRoot;
    private Timeline myGameLoop;

    /**
     * Given parsed XML data, construct appropriate Grid subclass and gameLoop
     * @param params A map containing parsed XML data
     */
    public Game (Parameters params) {
        myGameType = params.getGameType();
        myParameters = params;
        myGridShape = Constants.RESOURCES.getString("gridShape");
        myNeighborsToConsider = Constants.RESOURCES.getString("neighbors");

        
        initializeGrid();
        initializeGameLoop();

    }

    public void initializeGrid(){
        initializeGridModel();
        initializeGridView();
        initializeNeighborOffsets();
        
        myGameRoot = myGrid.getView();
        
    }
    /**
     * Initializes a specific grid based on the gameType parameter, and
     * updates myGameRoot after myGrid updates its local root
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

    private void initializeGridView () {

        GridView gridView = null;

        if (myGridShape.equals("Rectangle")) {
            gridView = new RectangleGridView(myGrid.getMyCells());

        }
        else if (myGridShape.equals("Triangle")) {
            gridView = new TriangleGridView(myGrid.getMyCells());

        }
        else if (myGridShape.equals("Hexagon")) {
            if (myNeighborsToConsider.equals("All")) {
                gridView = new HexagonGridView(myGrid.getMyCells());
            }
            else {
                // TODO: return error
                // TODO: move errorMessages to resource file
                String errorMessage = "Invalid neighbors to consider";
                if (myNeighborsToConsider.equals("Cardinal") |
                    myNeighborsToConsider.equals("Diagonal")) {
                    errorMessage =
                            String.format("Hexagonal grids can't go with %s neighbors",
                                          myNeighborsToConsider);
                }
            }

        }
        
        myGrid.setMyGridView(gridView);

    }

    private void initializeNeighborOffsets () {

        if (myNeighborsToConsider.equals("Cardinal")) {
            myGrid.setNeighborOffsets(neighborOffsetsCardinal());

        }
        else if (myNeighborsToConsider.equals("Diagonal")) {
            myGrid.setNeighborOffsets(neighborOffsetsDiagonal());

        }
        else if (myNeighborsToConsider.equals("All")) {
            if (myGridShape.equals("Hexagon")) {
                myGrid.setNeighborOffsets(neighborOffsetsAllHexagon());
            }
            else {
                myGrid.setNeighborOffsets(neighborOffsetsAll());
            }
        }
    }

    /**
     * Returns a list of offsets to check to find a GridCell's neighbors
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

    public List<Offset> neighborOffsetsDiagonal () {
        List<Offset> offsets = new ArrayList<Offset>();

        offsets.add(NeighborOffset.TOP_LEFT.getOffset());
        offsets.add(NeighborOffset.TOP_RIGHT.getOffset());
        offsets.add(NeighborOffset.BOTTOM_LEFT.getOffset());
        offsets.add(NeighborOffset.BOTTOM_RIGHT.getOffset());

        return offsets;
    }

    public List<Offset> neighborOffsetsAll () {
        List<Offset> offsets = neighborOffsetsCardinal();

        offsets.addAll(neighborOffsetsDiagonal());

        return offsets;

    }

    public List<Offset> neighborOffsetsAllHexagon () {
        List<Offset> offsets1 = neighborOffsetsCardinal();
        List<Offset> offsets2 = neighborOffsetsCardinal();

        offsets1.add(NeighborOffset.TOP_LEFT.getOffset());
        offsets1.add(NeighborOffset.TOP_RIGHT.getOffset());

        offsets2.add(NeighborOffset.BOTTOM_LEFT.getOffset());
        offsets2.add(NeighborOffset.BOTTOM_RIGHT.getOffset());

        return offsets1;

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
    public void setTimelineRate (double speed){
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
