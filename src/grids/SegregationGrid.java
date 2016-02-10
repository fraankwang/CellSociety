package grids;


/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */


import java.util.List;
import java.util.Map;
import cells.GridCell;
import cells.SimpleCell;
import constants.State;
import javafx.scene.shape.Rectangle;


/**
 * Grid subclass for Fire simulation
 *
 */
public class SegregationGrid extends Grid {

	private double similarityPercentage;
	
	private static final int MY_STATE_VALUE_EMPTY = 0;
	private static final int MY_STATE_VALUE_RED = 1;
	private static final int MY_STATE_VALUE_BLUE = 2;
	
	public SegregationGrid(Map<String, String> params) {
		super(params);
		similarityPercentage = Double.parseDouble(params.get("similaritypercentage"));
	}

	@Override
    protected void initializeCell (int r, int c) {
        State state = State.EMPTY;

        int s = getMyInitialStates()[r][c];
        switch (s) {
            case MY_STATE_VALUE_EMPTY:
                state = State.EMPTY;
                break;
            case MY_STATE_VALUE_RED:
                state = State.RED;
                break;
            case MY_STATE_VALUE_BLUE:
            	state = State.BLUE;
            default:
                // Display error message
                break;
        }

        getMyCells()[r][c] =
                new SimpleCell(state, r, c, new Rectangle(getMyCellSize(), getMyCellSize()));

    }

	@Override
	protected void setCellState(GridCell cell) {
		if(!cell.getMyCurrentState().equals(State.EMPTY)) {
			List<GridCell> neighbors = getNeighbors(cell);
			double sameCount = 0;
			double nonEmptyCount = 0;
			for(GridCell neighbor : neighbors) {
				if(cell.getMyCurrentState().equals(neighbor.getMyCurrentState())) {
					sameCount++;
				}
				if(!(neighbor.getMyCurrentState() == State.EMPTY)){
				    nonEmptyCount++;
				}
			}
			if(!isContent((sameCount/nonEmptyCount)*100)){
				move(cell);
			}
			else{
				cell.setMyNextState(cell.getMyCurrentState());
			}
		}
		else {
			if(cell.getMyNextState() == null){
				cell.setMyNextState(cell.getMyCurrentState());
			}
		}
		
	}
	
	/**
	 * Moves a cell to the first empty location in the Grid that 
	 * hasn't already been moved into
	 * @param cell
	 */
	private void move(GridCell cell) {
		for (int r = 0; r < getMyCells().length; r++) {
			for (int c = 0; c < getMyCells()[0].length; c++) {
				GridCell newCell = getMyCells()[r][c];
				if(newCell.getMyCurrentState().equals(State.EMPTY) && (newCell.getMyNextState()==null || newCell.getMyNextState()==State.EMPTY)) {
					newCell.setMyNextState(cell.getMyCurrentState());
					cell.setMyNextState(State.EMPTY);
					return;
				}
			}
		}

		cell.setMyNextState(cell.getMyCurrentState());
	}



	/**
	 * Checks to see if a cell is content based on how many
	 * of its neighbors are the same race.
	 * @param percent the percentage of neighbors that are the same race
	 * @return True if the percent is at least the threshold percentage
	 */
	private boolean isContent(double percent) {
		return percent >= similarityPercentage;
	}
	
    // =========================================================================
    // Getters and Setters
    // =========================================================================
	
	private double getSimilarityPercentage () {
		return similarityPercentage;
	}
	
	@Override
	public Map<String,String> getMyGameState () {
		Map<String,String> currentGameState = super.getMyGameState();
		currentGameState.put("similaritypercentage", Double.toString(getSimilarityPercentage()));
		
		return currentGameState;
		
	}

}
