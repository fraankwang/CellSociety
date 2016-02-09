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
import constants.NeighborOffset;
import constants.Offset;
import constants.State;
import javafx.scene.shape.Rectangle;


/**
 * Grid subclass for Predator Prey simulation
 *
 */
public class PredatorPreyGrid extends Grid {

	private int fishBreed;
	private int sharkBreed;
	private int sharkHealth;

	private static final int MY_STATE_VALUE_EMPTY = 0;
	private static final int MY_STATE_VALUE_SHARK = 1;
	private static final int MY_STATE_VALUE_FISH = 2;

	public PredatorPreyGrid (Map<String, String> params) {
		super(params);
		fishBreed = Integer.parseInt(params.get("fishbreed"));
		sharkBreed = Integer.parseInt(params.get("sharkbreed"));
		sharkHealth = Integer.parseInt(params.get("sharkhealth"));
	}

	@Override
	protected void initializeCell (int row, int col) {
		GridCell cell = new SimpleCell(State.EMPTY, row, col,
				new Rectangle(getMyCellSize(), getMyCellSize()));

		int s = getMyInitialStates()[row][col];
		switch (s) {
		case MY_STATE_VALUE_EMPTY:
			cell =  new SimpleCell(State.EMPTY, row, col,
					new Rectangle(getMyCellSize(), getMyCellSize()));;
					break;
		case MY_STATE_VALUE_SHARK:
			cell = new SharkCell(State.SHARK, row, col,
					new Rectangle(getMyCellSize(), getMyCellSize()), sharkHealth,
					sharkBreed);
			break;
		case MY_STATE_VALUE_FISH:
			cell = new FishCell(State.FISH, row, col,
					new Rectangle(getMyCellSize(), getMyCellSize()), fishBreed);

		}

		getMyCells()[row][col] = cell;

	}

	@Override
	protected void setCellStates() { 
		setSharkCellStates();		//Shark states have to be set first because they will eat Fish
		setFishCellStates();		//don't want Fish to move before being eaten
		
		for (int r = 0; r < getRows(); r++) {
			for (int c = 0; c < getColumns(); c++) {
				if (getMyCells()[r][c].getMyCurrentState() == State.EMPTY) {
					setCellState(getMyCells()[r][c]);
				}
			}
		}

	}

	@Override
	protected void setCellState (GridCell cell) {
		if (cell.getMyNextState()== null){
			cell.setMyNextState(cell.getMyCurrentState());
		}
		
	}

	@Override
	protected List<Offset> neighborOffsets () {

		List<Offset> offsets = new ArrayList<Offset>();

		offsets.add(NeighborOffset.TOP.getOffset());
		offsets.add(NeighborOffset.LEFT.getOffset());
		offsets.add(NeighborOffset.RIGHT.getOffset());
		offsets.add(NeighborOffset.BOTTOM.getOffset());

		return offsets;
	}

	/**
	 * Overrides Grid method in order to include
	 * the wrap around
	 */
	@Override
	protected List<GridCell> getNeighbors(GridCell cell){
		int r = cell.getMyGridLocation().getRow();
		int c = cell.getMyGridLocation().getCol();

		List<Offset> offsets = neighborOffsets();
		List<GridCell> neighbors = new ArrayList<GridCell>();

		for(Offset offset : offsets){
			int neighborRow = r + offset.getRow();
			int neighborCol = c + offset.getCol();
			neighborRow = checkAndSetRowWrapAround(neighborRow);
			neighborCol = checkAndSetColWrapAround(neighborCol);
			neighbors.add(getMyCells()[neighborRow][neighborCol]);
		}

		return neighbors;
		
	}


	private void setFishCellStates() {
		for (int r = 0; r < getMyCells().length; r++) {
			for (int c = 0; c < getMyCells()[0].length; c++) {
				if(getMyCells()[r][c] instanceof FishCell && (getMyCells()[r][c].getMyNextState() == null || getMyCells()[r][c].getMyNextState()== State.DEAD)) {
					setFishCellState((FishCell)getMyCells()[r][c]);
				}
			}
		}
		
	}

	private void setFishCellState(FishCell fishCell) {
		fishCell.update();
		List<GridCell> neighbors = getNeighbors(fishCell);
		List<GridCell> validMoves = getValidCellList(neighbors);
		if (fishCell.getMyNextState() == State.DEAD) {
			kill(fishCell, State.FISH);
		}
		else {
			if (validMoves.size() > 0) {
				GridCell toMove = getRandomValidCell(validMoves);
				validMoves.remove(toMove);
				move(fishCell, toMove);
			}
			else {
				fishCell.setMyNextState(State.FISH);
			}
		}

		if (fishCell.getTimeUntilBreed() == 0) {
			if (validMoves.size() > 0) {
				GridCell toSpawn = getRandomValidCell(validMoves);
				breed(toSpawn, State.FISH);
			}
		}
		
	}

	private void setSharkCellStates() {
		for (int r = 0; r < getRows(); r++) {
			for (int c = 0; c < getColumns(); c++) {
				if (getMyCells()[r][c] instanceof SharkCell && (getMyCells()[r][c].getMyNextState() == null || getMyCells()[r][c].getMyNextState()== State.DEAD)) {
					setSharkCellState((SharkCell) getMyCells()[r][c]);
				}
			}
		}
		
	}

	private void setSharkCellState(SharkCell shark) {
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
			shark.eat((FishCell)(edible.remove(edible.size()-1)));
			shark.setMyNextState(shark.getMyCurrentState());
		}
		else if (shark.getMyNextState() == State.DEAD) {
			kill(shark, shark.getMyCurrentState());
		}
		else {
			if (validMoves.size()>0) {
				GridCell toMove = getRandomValidCell(validMoves);
				validMoves.remove(toMove);
				move(shark,toMove);
			}
			else {
				shark.setMyNextState(State.SHARK);
			}
		}

		if (shark.getTimeUntilBreed() == 0) {
			if(validMoves.size()>0){
				GridCell toSpawn = getRandomValidCell(validMoves);;
				breed(toSpawn, State.SHARK);
			}
		}
		
	}

	/**
	 * Breeds a new Shark or Fish
	 * 
	 * @param toSpawn the place to spawn a new fish
	 * @param fishOrShark determines whether to make a Fish or Shark
	 */
	private void breed(GridCell toSpawn, State fishOrShark) {
		int row = toSpawn.getMyGridLocation().getRow();
		int col = toSpawn.getMyGridLocation().getCol();

		getMyGridPane().getChildren().remove(toSpawn.getMyShape());
		if (fishOrShark == State.SHARK) {
			toSpawn = new SharkCell(State.EMPTY, row, col, new Rectangle(getMyCellSize(), getMyCellSize()), sharkHealth,sharkBreed);
			toSpawn.setMyNextState(State.SHARK);
		}
		else if (fishOrShark == State.FISH) {
			toSpawn = new FishCell(State.EMPTY, row, col, new Rectangle(getMyCellSize(), getMyCellSize()), fishBreed);
			toSpawn.setMyNextState(State.FISH);
		}

		getMyCells()[row][col] = toSpawn;
		getMyGridPane().add(toSpawn.getMyShape(), col, row);
		
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
		getMyGridPane().getChildren().remove(destinationCell.getMyShape());

		GridCell originCell = getMyCells()[originRow][originCol];
		getMyGridPane().getChildren().remove(originCell.getMyShape());

		if (origin instanceof SharkCell) {
			destinationCell = new SharkCell((SharkCell) origin, destination.getMyGridLocation());
			destinationCell.setMyNextState(State.SHARK);
			originCell = new SimpleCell(State.SHARK, origin.getMyGridLocation().getRow(), origin.getMyGridLocation().getCol(), new Rectangle(getMyCellSize(), getMyCellSize()));
		}
		else if (origin instanceof FishCell) {
			destinationCell = new FishCell((FishCell) origin, destination.getMyGridLocation());
			destinationCell.setMyNextState(State.FISH);
			originCell = new SimpleCell(State.FISH, origin.getMyGridLocation().getRow(), origin.getMyGridLocation().getCol(), new Rectangle(getMyCellSize(), getMyCellSize()));
		}

		getMyCells()[destinationRow][destinationCol] = destinationCell;
		destinationCell.setMyCurrentState(State.EMPTY);

		getMyCells()[originRow][originCol] = originCell;
		originCell.setMyNextState(State.EMPTY);

		getMyGridPane().add(destinationCell.getMyShape(), destinationCol, destinationRow);
		getMyGridPane().add(originCell.getMyShape(), originCol, originRow);

	}

	/**
	 * Kills the cell passed into it
	 * 
	 * @param cell the state to kill
	 * @param sharkOrFish determines whether or the cell is a shark or fish
	 */
	private void kill(GridCell cell, State sharkOrFish){
		int row = cell.getMyGridLocation().getRow();
		int col = cell.getMyGridLocation().getCol();
		GridCell deadCell = getMyCells()[row][col];
		getMyGridPane().getChildren().remove(deadCell.getMyShape());

		if (sharkOrFish == State.FISH){
			deadCell =  new SimpleCell(State.FISH, deadCell.getMyGridLocation().getRow(), deadCell.getMyGridLocation().getCol(), new Rectangle(getMyCellSize(), getMyCellSize()));
		}
		else if (sharkOrFish == State.SHARK){
			deadCell =  new SimpleCell(State.SHARK, deadCell.getMyGridLocation().getRow(), deadCell.getMyGridLocation().getCol(), new Rectangle(getMyCellSize(), getMyCellSize()));
		}

		getMyCells()[row][col] = deadCell;
		getMyGridPane().add(deadCell.getMyShape(), col, row);
		deadCell.setMyNextState(State.EMPTY);

	}



	/**
	 * Gets a random cell from the neighbors that is 
	 * valid i.e. empty and doesn't have a next state
	 * 
	 * @param neighbors
	 * @return
	 */
	private GridCell getRandomValidCell(List<GridCell> validCells){
		Collections.shuffle(validCells);
		return validCells.get(0);
	}
	
	private List<GridCell> getValidCellList(List<GridCell> neighbors){
		List<GridCell> validCells = new ArrayList<GridCell>();
		for (GridCell cell: neighbors) {
			if (cell.getMyCurrentState()==State.EMPTY && cell.getMyNextState() == null) {
				validCells.add(cell);
			}
		}
		return validCells;
		
	}

	/**
	 * Checks to see if the row wraps around and
	 * returns itself it doesn't or does the math
	 * for the wrap around
	 * @param row
	 * @return the number to set the row to
	 */
	private int checkAndSetRowWrapAround(int row){
		if (row < 0) {
			return row + getRows();
		}
		else if (row == getRows()){
			return 0;
		}
		else {
			return row;
		}
		
	}

	/**
	 * Checks to see if the col wraps around and
	 * returns itself it doesn't or does the math
	 * for the wrap around
	 * @param col
	 * @return the number to set the col to
	 */
	private int checkAndSetColWrapAround(int col){
		if(col < 0) {
			return col + getColumns();
		}
		else if(col == getColumns()){
			return 0;
		}
		else{
			return col;
		}
		
	}

}
