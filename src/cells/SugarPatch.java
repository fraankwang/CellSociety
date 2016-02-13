package cells;

import states.SugarscapeState;


public class SugarPatch extends Patch {
    private int mySugarGrowBackRate;
    private int mySugar;
    private int myMaxCapacity;
    private int myStepCount;
    private int mySugarGrowBackInterval;

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
    public void initializeWithAgent(Agent agent){
        super.initializeWithAgent(agent);
        setMyCurrentState(SugarscapeState.AGENT);
    }
    
    @Override
    public void update () {
        myStepCount++;
        if (myStepCount >= mySugarGrowBackInterval) {

            setMySugar(Math.min(getMySugar() + mySugarGrowBackRate, myMaxCapacity));
            setMyNextState(stateFromSugarCount());
            myStepCount = 0;
        }
        
        if (isOccupied() == true){
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

    public void didGetEaten (SugarAgent agent) {
        setMySugar(0);
        setMyAgent(agent);
    }


    public int getMySugar () {
        return mySugar;
    }

    private void setMySugar (int mySugar) {
        this.mySugar = mySugar;
    }

}
