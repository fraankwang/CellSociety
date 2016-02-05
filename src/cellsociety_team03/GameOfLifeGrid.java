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
        int s = getMyInitialStates()[r][c];
        State state = State.DEAD;
        switch(s){
            case 0:
                state = State.DEAD;
                break;
            case 1:
                state = State.ALIVE;
                break;
        }
        getMyCells()[r][c] = new SimpleCell(state, r, c, new Rectangle(getMyCellSize(), getMyCellSize()));
       
    }

    @Override
    protected void setCellState (GridCell cell) {
        
        int numNeighborsAlive = this.numNeighborsAlive(cell);

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
    private int numNeighborsAlive (GridCell cell) {
        int numNeighborsAlive = 0;

        for(GridCell neighbor : getNeighbors(cell)){
            if(neighbor.getMyCurrentState() == State.ALIVE){
                numNeighborsAlive++;
            }
        }
        
        return numNeighborsAlive;
        
    }
    
    

}
