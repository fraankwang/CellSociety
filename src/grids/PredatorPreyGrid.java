/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package grids;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import cells.FishCell;
import cells.GridCell;
import cells.SharkCell;
import cells.SimpleCell;
import constants.Parameters;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import states.State;
import states.WatorState;


/**
 * Grid subclass for Predator Prey simulation
 *
 */
public class PredatorPreyGrid extends Grid {

	private int[][] myInitialStates;
    private int fishBreed;
    private int sharkBreed;
    private int sharkHealth;

    public PredatorPreyGrid (Parameters params) {
        super(params);
        myInitialStates = params.getInitialStates();
        
        fishBreed = Integer.parseInt(params.getParameter("fishbreed"));
        sharkBreed = Integer.parseInt(params.getParameter("sharkbreed"));
        sharkHealth = Integer.parseInt(params.getParameter("sharkhealth"));

        initializeCells();
    }

    @Override
    protected GridCell initializeCell (int row, int col) {
        GridCell cell = null;

        int s = myInitialStates[row][col];

        if (s == WatorState.EMPTY.getStateValue()) {
            cell = new SimpleCell(WatorState.EMPTY, row, col);

        }
        else if (s == WatorState.SHARK.getStateValue()) {
            cell = new SharkCell(WatorState.SHARK, row, col, sharkHealth, sharkBreed);

        }
        else if (s == WatorState.FISH.getStateValue()) {
            cell = new FishCell(WatorState.FISH, row, col, fishBreed);

        }

        if (cell == null) {
            // TODO: return error
        }

        return cell;

    }

    @Override
    protected void setCellStates () {
        setSharkCellStates();		// Shark states have to be set first because they will eat
                             		// Fish
        setFishCellStates();		// don't want Fish to move before being eaten

        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                if (getMyCells()[row][col].getMyCurrentState() == WatorState.EMPTY) {
                    setCellState(getMyCells()[row][col]);
                }
            }
        }

    }

    @Override
    protected void toggleState (GridCell cell) {
        if (cell.getMyCurrentState() == WatorState.EMPTY) {
            cell.setMyCurrentState(WatorState.SHARK);

        }
        else if (cell.getMyCurrentState() == WatorState.SHARK) {
            cell.setMyCurrentState(WatorState.FISH);

        }
        else if (cell.getMyCurrentState() == WatorState.FISH) {
            cell.setMyCurrentState(WatorState.EMPTY);

        }
        else if (cell.getMyCurrentState() == WatorState.EMPTY) {
            cell.setMyCurrentState(WatorState.DEAD);

        }

    }

    @Override
    protected void setCellState (GridCell cell) {
        if (cell.getMyNextState() == null) {
            cell.setMyNextState(cell.getMyCurrentState());
        }

    }

    /**
     * Iterates through the Fish Cell states that haven't already been updated
     * and sets their next states
     */
    private void setFishCellStates () {
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                GridCell cell = getMyCells()[row][col];
                if (cell instanceof FishCell &&
                    (cell.getMyNextState() == null || cell.getMyNextState() == WatorState.DEAD)) {
                    setFishCellState((FishCell) getMyCells()[row][col]);
                }
            }
        }

    }

    /**
     * Sets the state for the Fish Cell passed in. It will kill or move the
     * fish and then breeds if that is possible.
     *
     * @param fishCell the Fish Cell that needs to be updated
     */
    private void setFishCellState (FishCell fishCell) {
        fishCell.update();
        List<GridCell> neighbors = getNeighbors(fishCell);
        List<GridCell> validMoves = getValidCellList(neighbors);
        if (fishCell.getMyNextState() == WatorState.DEAD) {
            kill(fishCell, WatorState.FISH);
        }
        else {
            moveAndBreed(fishCell, validMoves, fishCell.getTimeUntilBreed());
        }

    }

    /**
     * Iterates through the Shark Cell states that haven't already been updated
     * and sets their next states
     */
    private void setSharkCellStates () {
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                GridCell cell = getMyCells()[row][col];
                if (cell instanceof SharkCell &&
                    (cell.getMyNextState() == null || cell.getMyNextState() == WatorState.DEAD)) {
                    setSharkCellState((SharkCell) cell);
                }
            }
        }

    }

    /**
     * Sets the state for the Shark Cell passed in. It will kill, move, or have the
     * shark eat and then breeds if that is possible.
     *
     * @param shark the Shark Cell that needs to be updated
     */
    private void setSharkCellState (SharkCell shark) {
        shark.update();
        List<GridCell> neighbors = getNeighbors(shark);
        List<FishCell> edible = new ArrayList<FishCell>();
        List<GridCell> validMoves = getValidCellList(neighbors);

        if (shark.canEat(neighbors)) {
            for (GridCell cell : neighbors) {
                if (cell instanceof FishCell) {
                    edible.add((FishCell) cell);
                }
            }
            Collections.shuffle(edible);
            shark.eat((edible.remove(edible.size() - 1)));
            shark.setMyNextState(shark.getMyCurrentState());
        }
        else if (shark.getMyNextState() == WatorState.DEAD) {
            kill(shark, shark.getMyCurrentState());
        }
        else {
            moveAndBreed(shark, validMoves, shark.getTimeUntilBreed());
        }

    }

    /**
     * Breeds a new Shark or Fish
     *
     * @param toSpawn the place to spawn a new fish
     * @param fishOrShark determines whether to make a Fish or Shark
     */
    private void breed (GridCell toSpawn, State fishOrShark) {
        int row = toSpawn.getMyGridLocation().getRow();
        int col = toSpawn.getMyGridLocation().getCol();

        // getMyGridPane().getChildren().remove(toSpawn.getMyShape());
        if (fishOrShark == WatorState.SHARK) {
            toSpawn = new SharkCell(WatorState.EMPTY, row, col, sharkHealth, sharkBreed);
            toSpawn.setMyNextState(fishOrShark);
        }
        else if (fishOrShark == WatorState.FISH) {
            toSpawn = new FishCell(WatorState.EMPTY, row, col, fishBreed);
            toSpawn.setMyNextState(fishOrShark);
        }

        getMyCells()[row][col] = toSpawn;
        // getMyGridPane().add(toSpawn.getMyShape(), col, row);

    }

    /**
     * Moves the cell at origin to destination
     *
     * @param origin the start position
     * @param destination the end position
     */
    private void move (GridCell origin, GridCell destination) {
        int originCol = origin.getMyGridLocation().getCol();
        int originRow = origin.getMyGridLocation().getRow();

        int destinationCol = destination.getMyGridLocation().getCol();
        int destinationRow = destination.getMyGridLocation().getRow();

        GridCell destinationCell = getMyCells()[destinationRow][destinationCol];
        // getMyGridPane().getChildren().remove(destinationCell.getMyShape());

        GridCell originCell = getMyCells()[originRow][originCol];
        // getMyGridPane().getChildren().remove(originCell.getMyShape());

        if (origin instanceof SharkCell) {
            destinationCell = new SharkCell((SharkCell) origin, destination.getMyGridLocation());
            destinationCell.setMyNextState(WatorState.SHARK);
            originCell =
                    new SimpleCell(WatorState.SHARK, origin.getMyGridLocation().getRow(),
                                   origin.getMyGridLocation().getCol());
        }
        else if (origin instanceof FishCell) {
            destinationCell = new FishCell((FishCell) origin, destination.getMyGridLocation());
            destinationCell.setMyNextState(WatorState.FISH);
            originCell =
                    new SimpleCell(WatorState.FISH, origin.getMyGridLocation().getRow(),
                                   origin.getMyGridLocation().getCol());
        }

        getMyCells()[destinationRow][destinationCol] = destinationCell;
        destinationCell.setMyCurrentState(WatorState.EMPTY);

        getMyCells()[originRow][originCol] = originCell;
        originCell.setMyNextState(WatorState.EMPTY);

        // getMyGridPane().add(destinationCell.getMyShape(), destinationCol, destinationRow);
        // getMyGridPane().add(originCell.getMyShape(), originCol, originRow);

    }

    /**
     * Kills the cell passed into it
     *
     * @param cell the state to kill
     * @param sharkOrFish determines whether or the cell is a shark or fish
     */
    private void kill (GridCell cell, State sharkOrFish) {
        int row = cell.getMyGridLocation().getRow();
        int col = cell.getMyGridLocation().getCol();
        GridCell deadCell = getMyCells()[row][col];
        // getMyGridPane().getChildren().remove(deadCell.getMyShape());

        if (sharkOrFish == WatorState.FISH) {
            deadCell =
                    new SimpleCell(WatorState.FISH, deadCell.getMyGridLocation().getRow(),
                                   deadCell.getMyGridLocation().getCol());
        }
        else if (sharkOrFish == WatorState.SHARK) {
            deadCell =
                    new SimpleCell(WatorState.SHARK, deadCell.getMyGridLocation().getRow(),
                                   deadCell.getMyGridLocation().getCol());
        }

        getMyCells()[row][col] = deadCell;
        // getMyGridPane().add(deadCell.getMyShape(), col, row);
        deadCell.setMyNextState(WatorState.EMPTY);

    }

    private void moveAndBreed (GridCell cell, List<GridCell> validMoves, int timeUntilBreed) {
        if (validMoves.size() > 0) {
            GridCell toMove = getRandomValidCell(validMoves);
            validMoves.remove(toMove);
            move(cell, toMove);
        }
        else {
            cell.setMyNextState(cell.getMyCurrentState());
        }

        if (timeUntilBreed == 0) {
            if (validMoves.size() > 0) {
                GridCell toSpawn = getRandomValidCell(validMoves);
                breed(toSpawn, cell.getMyCurrentState());
            }
        }
    }

    /**
     * Gets a random cell from list
     *
     * @param validCells should be cell that is either empty and hasn't had its next state set
     * @return a random cell
     */
    private GridCell getRandomValidCell (List<GridCell> validCells) {
        Collections.shuffle(validCells);
        return validCells.get(0);
    }

    /**
     * Takes the list of neighbors of a cell and returns
     * another list of cells that are valid for movement/breeding
     *
     * @param neighbors
     * @return a list of valid cells
     */
    private List<GridCell> getValidCellList (List<GridCell> neighbors) {
        List<GridCell> validCells = new ArrayList<GridCell>();
        for (GridCell cell : neighbors) {
            if (cell.getMyCurrentState() == WatorState.EMPTY && cell.getMyNextState() == null) {
                validCells.add(cell);
            }
        }
        return validCells;

    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================

    @Override
    public Map<String, String> getMyGameState () {
        Map<String, String> currentGameState = super.getMyGameState();
        currentGameState.put("fishbreed", Integer.toString(getFishBreed()));
        currentGameState.put("sharkbreed", Integer.toString(getSharkBreed()));
        currentGameState.put("sharkhealth", Integer.toString(getSharkHealth()));

        return currentGameState;

    }

    public int getFishBreed () {
        return fishBreed;
    }

    public void setFishBreed (int fishBreed) {
        this.fishBreed = fishBreed;
    }

    public int getSharkBreed () {
        return sharkBreed;
    }

    public void setSharkBreed (int sharkBreed) {
        this.sharkBreed = sharkBreed;
    }

    public int getSharkHealth () {
        return sharkHealth;
    }

    public void setSharkHealth (int sharkHealth) {
        this.sharkHealth = sharkHealth;
    }

	@Override
	public VBox createParameterButtons() {
		VBox box = new VBox();
    	Label sharkHealthLabel = new Label("Shark Health");
    	TextField sharkHealthField = new TextField(""+sharkHealth);
    	
    	Label sharkBreedLabel = new Label("Shark Breed Time");
    	TextField sharkBreedField = new TextField(""+sharkBreed)
    			;
    	Label fishBreedLabel = new Label("Fish Breed Time");
    	TextField fishBreedField = new TextField(""+fishBreed);
    	
    	box.getChildren().addAll(sharkHealthLabel, sharkHealthField, 
    							sharkBreedLabel, sharkBreedField,
    							fishBreedLabel, fishBreedField);
    	
    	return box;
	}

	@Override
	public void updateParameters(Parameters params) {
		// TODO Auto-generated method stub
		
	}
}
