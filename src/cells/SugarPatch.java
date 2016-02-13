package cells;

import states.State;
import states.SugarscapeState;


public class SugarPatch extends Patch {
    private int mySugarGrowBackRate;
    private int mySugar;
    private int myMaxCapacity;
    private int myStepCount;
    private int mySugarGrowBackInterval;
    private boolean isOccupied;

    public SugarPatch (int row,
                       int col,
                       int sugarGrowBackRate,
                       int sugar,
                       int maxCapacity,
                       int sugarGrowBackInterval) {
        super(null, row, col);
        setMySugar(sugar);

        setMyCurrentState(stateFromSugarCount());

        mySugarGrowBackRate = sugarGrowBackRate;

        myMaxCapacity = maxCapacity;
        myStepCount = 0;
        mySugarGrowBackInterval = sugarGrowBackInterval;
    }

    @Override
    public void update () {
        myStepCount++;
        if (myStepCount >= mySugarGrowBackInterval) {

            setMySugar(Math.min(getMySugar() + mySugarGrowBackRate, myMaxCapacity));
            setMyNextState(stateFromSugarCount());
            myStepCount = 0;
        }
        
        if (isOccupied == true){
            setMyNextState(SugarscapeState.AGENT);
        }

    }

    public SugarscapeState stateFromSugarCount () {
        int sugarThresholdLow = 5;
        int sugarThresholdMedium = 10;
        int sugarThresholdHigh = 15;
        int sugarThresholdStrong = 20;

        if (mySugar >= sugarThresholdStrong) {
            return SugarscapeState.STRONG;
        }
        else if (mySugar >= sugarThresholdHigh) {
            return SugarscapeState.HIGH;
        }
        else if (mySugar >= sugarThresholdMedium) {
            return SugarscapeState.MEDIUM;
        }
        else if (mySugar >= sugarThresholdLow) {
            return SugarscapeState.LOW;
        }
        else {
            return SugarscapeState.EMPTY;
        }
    }

    public void didGetEaten () {
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