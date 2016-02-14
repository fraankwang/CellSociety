/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cells;

import states.State;


/**
 * A SugarAgent is the Agent used in the Sugarscape simulation
 *
 */
public class SugarAgent extends Agent {

    private int mySugar;
    private int mySugarMetabolism;
    private int myVision;

    /**
     * Constructor
     * 
     * @param currentState
     * @param row
     * @param col
     * @param sugar The amount of sugar the agent has
     * @param sugarMetabolism The rate at while the agent metabolizes sugar
     * @param vision How far the agent can see in each direction to detect sugar patches
     */
    public SugarAgent (State currentState,
                       int row,
                       int col,
                       int sugar,
                       int sugarMetabolism,
                       int vision) {
        super(currentState, row, col);
        mySugar = sugar;
        mySugarMetabolism = sugarMetabolism;
        myVision = vision;
    }

    /**
     * Method called when agent moves to a new patch and eats its sugar
     * 
     * @param addedSugar The amount of sugar the Agent eats
     */
    public void eat (int addedSugar) {
        mySugar += addedSugar;
        mySugar -= mySugarMetabolism;
    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================
    public int getMyVision () {
        return myVision;
    }

    public int getMySugar () {
        return mySugar;
    }

}
