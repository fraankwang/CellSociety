/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cells;

import java.util.HashSet;
import java.util.Set;
import states.ForagingAntsState;


public class AntPatch extends Patch {
    private Set<Ant> myAnts;

    private int myNumFoodPheromones;
    private int myNumHomePheromones;
    private int myMaxAnts;

    private int myFoodThresholdHigh;
    private int myFoodThresholdLow;
    private int myHomeThresholdHigh;
    private int myHomeThresholdLow;

    public AntPatch (int row,
                     int col,
                     int maxAnts,
                     int fTHigh,
                     int fTLow,
                     int hTHigh,
                     int hTLow) {
        super(null, row, col);
        myNumFoodPheromones = 0;
        myNumHomePheromones = 0;
        myAnts = new HashSet<Ant>();
        myMaxAnts = maxAnts;
        myFoodThresholdHigh = fTHigh;
        myFoodThresholdLow = fTLow;
        myHomeThresholdHigh = hTHigh;
        myHomeThresholdLow = hTLow;
        initializeState();
        
    }

    /**
     * Initializes the patch state based on pheromone levels and threshold values
     */
    public void initializeState(){
        setMyCurrentState(stateFromPheromoneLevels());
    }
    
    @Override
    public void update () {
        if (isOccupied()) {
            setMyNextState(ForagingAntsState.ANT);
        }
        else {
            setMyNextState(stateFromPheromoneLevels());
        }
    }

    public ForagingAntsState stateFromPheromoneLevels () {

        boolean foodHigh = myNumFoodPheromones >= myFoodThresholdHigh;
        boolean foodLow = myNumFoodPheromones >= myFoodThresholdLow;
        boolean homeHigh = myNumHomePheromones >= myHomeThresholdHigh;
        boolean homeLow = myNumHomePheromones >= myHomeThresholdLow;

        if (foodHigh && homeLow) {
            return ForagingAntsState.FOOD_HIGH_HOME_LOW;
        }

        if (foodLow && homeHigh) {
            return ForagingAntsState.FOOD_LOW_HOME_HIGH;
        }

        if (foodHigh && homeHigh) {
            return ForagingAntsState.FOOD_HOME_HIGH;
        }
        else if (foodHigh) {
            return ForagingAntsState.FOOD_HIGH;

        }
        else if (homeHigh) {
            return ForagingAntsState.HOME_HIGH;

        }

        if (foodLow && homeLow) {
            return ForagingAntsState.FOOD_HOME_LOW;
        }
        else if (foodLow) {
            return ForagingAntsState.FOOD_LOW;
        }
        else if (homeLow) {
            return ForagingAntsState.FOOD_LOW;
        }
        
        else{
            return ForagingAntsState.EMPTY;
        }

    }

    @Override
    public boolean isOccupied () {
        return myAnts != null && myAnts.size() > 0;
    }

    public boolean hasTooManyAnts () {
        return myAnts.size() >= myMaxAnts;
    }

    public int getMyNumFoodPheromones () {
        return myNumFoodPheromones;
    }

    public int getMyNumHomePheromones () {
        return myNumHomePheromones;
    }

}
