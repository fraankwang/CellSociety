package cells;

import states.SugarscapeState;

/**
 * A SugarPatch is the Patch used in the Sugarscape simulation
 *
 */
public class SugarPatch extends Patch {
    private int mySugarGrowBackRate;
    private int mySugar;
    private int myMaxCapacity;
    private int myStepCount;
    private int mySugarGrowBackInterval;
    
    private int mySugarThresholdLow = 5;
    private int mySugarThresholdMedium = 10;
    private int mySugarThresholdHigh = 15;
    private int mySugarThresholdStrong = 20;


    /**
     * Constructor
     * @param row 
     * @param col
     * @param sugarGrowBackRate The rate at which the patch grows sugar
     * @param sugar The amount of sugar in the patch
     * @param maxCapacity The max amount of sugar the patch can hold
     * @param sugarGrowBackInterval The interval (game loop steps) between each sugar growth
     */
    public SugarPatch (int row,
                       int col,
                       int sugarGrowBackRate,
                       int sugar,
                       int maxCapacity,
                       int sugarGrowBackInterval, 
                       int sugarThresholdLow, 
                       int sugarThresholdMedium, 
                       int sugarThresholdHigh, 
                       int sugarThresholdStrong) {
        super(null, row, col);
        setMySugar(sugar);

        setMyCurrentState(stateFromSugarCount());

        mySugarGrowBackRate = sugarGrowBackRate;

        myMaxCapacity = maxCapacity;
        myStepCount = 0;
        mySugarGrowBackInterval = sugarGrowBackInterval;
        mySugarThresholdLow = sugarThresholdLow;
        mySugarThresholdMedium = sugarThresholdMedium;
        mySugarThresholdHigh = sugarThresholdHigh;
        mySugarThresholdStrong = sugarThresholdStrong;
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

    /**
     * Return the threshold amount of sugar from a given state
     * 
     * Note: used for setting the sugar count when toggling states
     * 
     * @param state The Sugarscape state
     * @return The amount of sugar
     */
    public int sugarCountFromState(SugarscapeState state){
        int sugar = 0;
        if (state == SugarscapeState.STRONG) {
            sugar = mySugarThresholdStrong;
        }
        else if (state == SugarscapeState.HIGH) {
            sugar = mySugarThresholdHigh;
        }
        else if (state == SugarscapeState.MEDIUM) {
            sugar = mySugarThresholdMedium;
        }
        else if (state == SugarscapeState.LOW) {
            sugar = mySugarThresholdLow;
        }
        else {
            sugar = 0;
        }
        
        return sugar;
    }
    
    /**
     * Returns the state based on mySugar and threshold values
     * @return The current SugarscapeState of the patch
     */
    public SugarscapeState stateFromSugarCount () {
       
        if (mySugar >= mySugarThresholdStrong) {
            return SugarscapeState.STRONG;
        }
        else if (mySugar >= mySugarThresholdHigh) {
            return SugarscapeState.HIGH;
        }
        else if (mySugar >= mySugarThresholdMedium) {
            return SugarscapeState.MEDIUM;
        }
        else if (mySugar >= mySugarThresholdLow) {
            return SugarscapeState.LOW;
        }
        else {
            return SugarscapeState.EMPTY;
        }
    }

    /**
     * Called when an agent eats a sugar patch
     * @param agent The agent
     */
    public void didGetEaten (SugarAgent agent) {
        setMySugar(0);
        setMyAgent(agent);
    }
    
    // =========================================================================
    // Getters and Setters
    // =========================================================================
    public int getMySugar () {
        return mySugar;
    }

    public void setMySugar (int mySugar) {
        this.mySugar = mySugar;
    }

}
