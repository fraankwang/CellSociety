package cells;

import states.State;

public abstract class Patch extends DataCell {

    public Patch (State currentState, int row, int col) {
        super(currentState, row, col);
        // TODO Auto-generated constructor stub
    }

    @Override
    public abstract void update ();

}
