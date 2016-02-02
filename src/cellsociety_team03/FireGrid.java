/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cellsociety_team03;

import java.util.Map;
import java.util.Random;
import javafx.scene.shape.Rectangle;


public class FireGrid extends Grid {

    private double probCatch;

    public FireGrid (Map<String, String> params) {
        super(params);
        probCatch = Double.parseDouble(params.get("probcatch"));

    }

    //TODO: implement based on xml
    @Override 
    protected void initializeCell(int r, int c){
        myCells[r][c] = new SimpleCell(State.EMPTY, CELL_SIZE, new Rectangle(30, 30));
        if (r % 5 == 0)
            myCells[r][c] = new SimpleCell(State.TREE, CELL_SIZE, new Rectangle(30, 30));
        if (r % 3 == 0)
            myCells[r][c] = new SimpleCell(State.BURNING, CELL_SIZE, new Rectangle(30, 30));
    }
    
    @Override
    protected void setCellState (GridCell cell, int r, int c) {
        if (cell.getMyCurrentState() == State.BURNING) {
            cell.setMyNextState(State.EMPTY);
        }
        else if (this.willCatch(cell, r, c)) {
            cell.setMyNextState(State.BURNING);
        }
        else {
            cell.setMyNextState(cell.getMyCurrentState());
        }
    }

    /**
     * Determines if any of a cell's neighbor cells are currently burning
     * 
     * @param r The row index of the cell in question
     * @param c The column index of the cell in question
     * @return A boolean indicating whether one of the neighbor cells is burning
     */
    private boolean neighborIsBurning (int r, int c) {
        // TODO: remove duplicated code with game of life grid
        boolean neighborIsBurning = false;
        int[] rNeighbors = { -1, 0, 0, 1 };
        int[] cNeighbors = { 0, 1, -1, 0 };
        int r2;
        int c2;
        for (int i = 0; i < rNeighbors.length; i++) {
            for (int j = 0; j < cNeighbors.length; j++) {
                r2 = r + rNeighbors[i];
                c2 = c + cNeighbors[j];
                if (this.cellInBounds(r2, c2) &&
                    this.getMyCells()[r2][c2].getMyCurrentState() == State.BURNING) {
                    neighborIsBurning = true;
                }
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
    private boolean willCatch (GridCell cell, int r, int c) {
        return cell.getMyCurrentState() == State.TREE && neighborIsBurning(r, c) &&
               probCatchRandom();
    }

    /**
     * Returns a boolean if a random number generated is greater than probCatch
     * TODO: can't think of better name
     * 
     * @return The boolean
     */
    private boolean probCatchRandom () {
        Random r = new Random();
        double value = r.nextDouble();

        return (value >= getProbCatch());
    }

    private double getProbCatch () {
        return probCatch;
    }
}
