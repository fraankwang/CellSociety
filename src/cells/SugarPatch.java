package cells;

import states.SugarscapeState;


public class SugarPatch extends Patch {
    private int mySugarGrowBackRate;
    private int mySugar;
    private int myMaxCapacity;
    private int myStepCount;
    private int mySugarGrowBackInterval;
    
    private int sugarThresholdLow = 5;
    private int sugarThresholdMedium = 10;
    private int sugarThresholdHigh = 15;
    private int sugarThresholdStrong = 20;


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
    public void initializeWithAgent (Agent agent) {
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

        if (isOccupied() == true) {

            setMyNextState(SugarscapeState.AGENT);
        }

    }

    public int sugarCountFromState(SugarscapeState state){
        int sugar = 0;
        if (state == SugarscapeState.STRONG) {
            sugar = sugarThresholdStrong;
        }
        else if (state == SugarscapeState.HIGH) {
            sugar = sugarThresholdHigh;
        }
        else if (state == SugarscapeState.MEDIUM) {
            sugar = sugarThresholdMedium;
        }
        else if (state == SugarscapeState.LOW) {
            sugar = sugarThresholdLow;
        }
        else {
            sugar = 0;
        }
        
        return sugar;
    }
    
    public SugarscapeState stateFromSugarCount () {
       
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

    public void setMySugar (int mySugar) {
        this.mySugar = mySugar;
    }

}
