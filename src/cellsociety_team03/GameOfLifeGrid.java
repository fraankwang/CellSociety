/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cellsociety_team03;

import java.util.Map;


public class GameOfLifeGrid extends Grid {

    private double nonEmptyPercentage;
    private double emptyPercentage;

    public GameOfLifeGrid (Map<String, String> params) {
        super(params);
        nonEmptyPercentage = Double.parseDouble(params.get("nonemptypercentage"));
        emptyPercentage = Double.parseDouble(params.get("emptypercentage"));
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
        int numNeighborsAlive = this.numNeighborsAlive(r,c);
        System.out.println("set Next state numNeighborsAlive" + numNeighborsAlive);

        // TODO: can combine these if statements, but I thought it is clearer this way?
        if (cell.getMyCurrentState() == State.DEAD) {
            if(numNeighborsAlive == 2 || numNeighborsAlive == 3){
                cell.setMyNextState(State.ALIVE);
            }else{
                cell.setMyNextState(State.DEAD);
            }
        }else if (cell.getMyCurrentState() == State.ALIVE){
            if(numNeighborsAlive == 3){
                cell.setMyNextState(State.DEAD);
            }else{
                cell.setMyNextState(State.ALIVE);
            }
        }else{
            System.out.println("uhoh");
        }
    }

    private int numNeighborsAlive (int r, int c) {
        int numNeighborsAlive = 0;

        // TODO: put these arrays elsewhere
        int[] neighbors = { -1, 0, 1 };
        int r2;
        int c2;
        for (int i = 0; i < neighbors.length; i++) {
            for (int j = 0; j < neighbors.length; j++) {
                r2 = r + neighbors[i];
                c2 = c + neighbors[j];
                if (this.cellInBounds(r2, c2) &&
                    this.getMyCells()[r2][c2].getMyCurrentState() == State.ALIVE) {
                    numNeighborsAlive++;
                }
            }
        }
        
        return numNeighborsAlive;
    }

}
