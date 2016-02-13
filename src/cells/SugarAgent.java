package cells;

import states.State;

public class SugarAgent extends Agent {

    private int mySugar;
    private int mySugarMetabolism;
    private int myVision;
    
    public SugarAgent (State currentState, int row, int col, int sugar, int sugarMetabolism, int vision) {
        super(currentState, row, col);
        mySugar = sugar;
        mySugarMetabolism = sugarMetabolism;
        myVision = vision;
    }

    public int getMyVision () {
        return myVision;
    }

    public void addSugar(int addedSugar){
        mySugar += addedSugar;
        mySugar -= mySugarMetabolism;
    }

    public int getMySugar () {
        return mySugar;
    }

}
