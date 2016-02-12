/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package grids;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import cells.GridCell;
import cells.SimpleCell;
import constants.NeighborOffset;
import constants.Offset;
import constants.Parameters;
import constants.State;
import javafx.scene.shape.Rectangle;


/**
 * Grid subclass for Fire simulation
 *
 */
public class FireGrid extends Grid {
    private static final int MY_STATE_VALUE_EMPTY = 0;
    private static final int MY_STATE_VALUE_TREE = 1;
    private static final int MY_STATE_VALUE_BURNING = 2;
    private static final int MY_STATE_VALUE_BURNED = 3;

    private double myProbCatch;

    public FireGrid (Parameters params) {
        super(params);
        myProbCatch = Double.parseDouble(params.getParameter("probcatch"));

    }

    @Override
    protected GridCell initializeCell (int row, int col) {
        State state = State.EMPTY;
        
        int s = getMyInitialStates()[row][col];

        switch (s) {
            case MY_STATE_VALUE_EMPTY:
                state = State.EMPTY;
                break;
            case MY_STATE_VALUE_TREE:
                state = State.TREE;
                break;
            case MY_STATE_VALUE_BURNING:
                state = State.BURNING;
                break;
            case MY_STATE_VALUE_BURNED:
            	state = State.BURNED;
            
            default:
                // Display error message
                break;

        }

        return new SimpleCell(state, row, col, new Rectangle(getMyCellSize(), getMyCellSize()));

    }

    @Override
    protected void setCellState (GridCell cell) {
        if (cell.getMyCurrentState() == State.BURNING) {
            cell.setMyNextState(State.BURNED);
        }
        else if (willCatch(cell)) {
            cell.setMyNextState(State.BURNING);
        }
        else {
            cell.setMyNextState(cell.getMyCurrentState());
        }
        
    }

    @Override
	protected void toggleState(GridCell cell) {
		if (cell.getMyCurrentState() == State.BURNING) {
			cell.setMyCurrentState(State.BURNED);
			
		}
		else if (cell.getMyCurrentState() == State.BURNED) {
			cell.setMyCurrentState(State.TREE);
			
		} 
		else if (cell.getMyCurrentState() == State.TREE) {
			cell.setMyCurrentState(State.BURNING);
			
		}
		
		cell.setMyColor();
		
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
     * Determines if any of a cell's neighbor cells are currently burning
     *
     * @param r The row index of the cell in question
     * @param c The column index of the cell in question
     * @return A boolean indicating whether one of the neighbor cells is burning
     */
    private boolean neighborIsBurning (GridCell cell) {
        boolean neighborIsBurning = false;

        for (GridCell neighbor : getNeighbors(cell)) {

            if (neighbor.getMyCurrentState() == State.BURNING) {
                neighborIsBurning = true;
            }

        }

        return neighborIsBurning;
        
    }

    /**
     * Determines whether a cell will catch on fire
     *
     * @param cell The cell in question
     * @param r The row index of the cell in question
     * @param c The column index of the cell in question
     * @return A boolean indicating whether to set the cell's next state to burning
     */
    private boolean willCatch (GridCell cell) {
        return cell.getMyCurrentState() == State.TREE && neighborIsBurning(cell) &&
               probCatchRandom();
    }

    /**
     * Returns a boolean if a random number generated is greater than probCatch
     *
     * @return The boolean
     */
    private boolean probCatchRandom () {
        Random r = new Random();
        double value = r.nextDouble();

        return value >= getProbCatch();
        
    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================
    private double getProbCatch () {
        return myProbCatch;
    }
    
    @Override
	public Map<String,String> getMyGameState () {
		Map<String,String> currentGameState = super.getMyGameState();
		currentGameState.put("probcatch", Double.toString(getProbCatch()));
		
		return currentGameState;
		
	}
}
