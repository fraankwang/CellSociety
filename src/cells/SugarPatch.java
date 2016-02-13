package cells;

import states.State;
import states.SugarscapeState;

public class SugarPatch extends Patch {
    private int mySugarGrowBackRate;
    private int mySugar;
    private int myMaxCapacity;
    private boolean isOccupied;
    
    public SugarPatch (State currentState, int row, int col, int sugarGrowBackRate, int sugar, int maxCapacity) {
        super(currentState, row, col);
        mySugarGrowBackRate = sugarGrowBackRate;
        setMySugar(sugar);
        myMaxCapacity = maxCapacity;
    }

    @Override
    public void update () {
        setMySugar(Math.min(getMySugar() + mySugarGrowBackRate,  myMaxCapacity));

        //TODO: figure out next state color based on mySugar
        setMyNextState(SugarscapeState.MEDIUM);
    }
    
    public void didGetEaten(){
        setMySugar(0);
        isOccupied = true;
    }

    public boolean isOccupied () {
        return isOccupied;
    }

    public void setOccupied (boolean isOccupied) {
        this.isOccupied = isOccupied;
    }

    public int getMySugar () {
        return mySugar;
    }

    private void setMySugar (int mySugar) {
        this.mySugar = mySugar;
    }
 
}
