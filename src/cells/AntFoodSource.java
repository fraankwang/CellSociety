package cells;

import states.State;

public class AntFoodSource extends Patch {

    public AntFoodSource (State currentState, int row, int col) {
        super(currentState, row, col);
    }

    @Override
    public void update () {
        setMyNextState(getMyCurrentState());
    }

}
