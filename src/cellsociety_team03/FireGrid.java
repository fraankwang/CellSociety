/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cellsociety_team03;

import java.util.Map;
import java.util.Random;


public class FireGrid extends Grid {

    private double probCatch;

    public FireGrid (Map<String, String> params) {
        super(params);
        probCatch = Double.parseDouble(params.get("probcatch"));

    }

    protected void setCellStates () {
        for (int r = 0; r < myCells.length; r++) {
            for (int c = 0; c < myCells[0].length; c++) {
                // TODO: Cell State determining algorithms
                this.setCellState(r, c);
            }
        }

    }

    protected void setCellState (int r, int c) {
        GridCell cell = this.getMyCells()[r][c];
        boolean neighborIsBurning = this.neighborIsBurning(r, c);

        // TODO: can combine these if statements, but I thought it is clearer this way?
        if (cell.getMyCurrentState() == State.BURNING) {
            cell.setMyNextState(State.EMPTY);
        }else if (this.willCatch(cell, r, c)){
            cell.setMyNextState(State.BURNING);
        }else{
            cell.setMyNextState(cell.getMyCurrentState());
        }
    }

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
     * Determines whether a tree will catch on fire based on prob Catch
     * 
     * @param probCatch
     * @return
     */
    private boolean willCatch (GridCell cell, int r, int c) {
        System.out.println(neighborIsBurning(r,c) + " " + probCatchRandom() + " " + probCatch);
       return cell.getMyCurrentState() == State.TREE && neighborIsBurning(r,c) && probCatchRandom();
    }
    
    /**
     * TODO: can't think of better name
     * @return
     */
    private boolean probCatchRandom(){
        Random r = new Random();
        double value = r.nextDouble();

        return (value >= getProbCatch());
    }

   private double getProbCatch(){
       return probCatch;
   }
}
