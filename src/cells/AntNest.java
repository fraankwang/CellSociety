package cells;

import states.State;

public class AntNest extends Patch {

    public AntNest (State currentState, int row, int col) {
        super(currentState, row, col);
    }

    @Override
    public void update () {
        setMyNextState(getMyCurrentState());
    }

}
