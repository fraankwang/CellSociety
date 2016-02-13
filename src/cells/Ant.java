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
    
    public NeighborOffset getOrientation () {
        return orientation;
    }

    public void setOrientation (NeighborOffset orientation) {
        this.orientation = orientation;
    }

    public boolean hasFoodItem () {
        return hasFoodItem;
    }

    public void setHasFoodItem (boolean hasFoodItem) {
        this.hasFoodItem = hasFoodItem;
    }

    public boolean isLocatedAtNest () {
        return isLocatedAtNest;
    }

    public void setLocatedAtNest (boolean isLocatedAtNest) {
        this.isLocatedAtNest = isLocatedAtNest;
    }

    public boolean isLocatedAtFoodSource () {
        return isLocatedAtFoodSource;
    }

    public void setLocatedAtFoodSource (boolean isLocatedAtFoodSource) {
        this.isLocatedAtFoodSource = isLocatedAtFoodSource;
    }

   

}
