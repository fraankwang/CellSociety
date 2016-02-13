package cells;

import constants.NeighborOffset;
import states.State;

public class Ant extends Agent {
    private NeighborOffset orientation;
    private boolean hasFoodItem;
    private boolean isLocatedAtNest;
    private boolean isLocatedAtFoodSource;
    
    public Ant (State currentState, int row, int col) {
        super(currentState, row, col);
        // TODO Auto-generated constructor stub
    }

}
