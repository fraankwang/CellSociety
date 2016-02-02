/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cellsociety_team03;

import java.util.Map;
import javafx.scene.shape.Rectangle;


public class GameOfLifeGrid extends Grid {

    private double nonEmptyPercentage;
    private double emptyPercentage;

    public GameOfLifeGrid (Map<String, String> params) {
        super(params);
        nonEmptyPercentage = Double.parseDouble(params.get("nonemptypercentage"));
        emptyPercentage = Double.parseDouble(params.get("emptypercentage"));
    }

    // TODO: implement based on xml
    @Override
    protected void initializeCell (int r, int c) {
        int s = myInitialStates[r][c];
        State state = State.DEAD;
        switch(s){
            case 0:
                state = State.DEAD;
                break;
            case 1:
                state = State.ALIVE;
                break;
        }
        myCells[r][c] = new SimpleCell(state, CELL_SIZE, new Rectangle(30, 30));
       
    }

    @Override
    protected void setCellState (GridCell cell, int r, int c) {
        int numNeighborsAlive = this.numNeighborsAlive(r, c);

        // TODO: can combine these if statements, but I thought it is clearer this way?
        if (cell.getMyCurrentState() == State.ALIVE) {
            if (numNeighborsAlive == 2 || numNeighborsAlive == 3) {
                cell.setMyNextState(State.ALIVE);
            }
            else {
                cell.setMyNextState(State.DEAD);
            }
        }
        else if (cell.getMyCurrentState() == State.DEAD) {
            if (numNeighborsAlive == 3) {
                cell.setMyNextState(State.ALIVE);
            }
            else {
                cell.setMyNextState(State.DEAD);
            }
        }
        else {
            System.out.println("uhoh");
        }
    }

    /**
     * Calculates the number of "neighbor" cells alive
     * 
     * @param r The row index of the cell in question
     * @param c The column index of the cell in question
     * @return The number of alive cells surrounding the cell in question
     */
    private int numNeighborsAlive (int r, int c) {
        int numNeighborsAlive = 0;

        // TODO: put these arrays elsewhere
        int[] rNeighbors = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] cNeighbors = { -1, 0, 1, -1, 1, -1, 0, 1 };
        int r2;
        int c2;
        for (int i = 0; i < rNeighbors.length; i++) {
            r2 = r + rNeighbors[i];
            c2 = c + cNeighbors[i];
            if (this.cellInBounds(r2, c2) &&
                this.getMyCells()[r2][c2].getMyCurrentState() == State.ALIVE) {
                numNeighborsAlive++;
            }

        }

        return numNeighborsAlive;
    }

}
