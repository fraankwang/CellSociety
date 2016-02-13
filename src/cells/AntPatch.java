package cells;

import java.util.Set;
import states.ForagingAntsState;
import states.State;
import states.SugarscapeState;


public class AntPatch extends Patch {
    private int myNumFoodPheromones;
    private int myNumHomePheromones;
    private int myMaxAnts;
    private Set<Ant> myAnts;

    private int myFoodThresholdHigh;
    private int myFoodThresholdLow;
    private int myHomeThresholdHigh;
    private int myHomeThresholdLow;

    public AntPatch (State currentState,
                     int row,
                     int col,
                     int foodP,
                     int homeP,
                     int maxAnts,
                     int fTHigh,
                     int fTLow,
                     int hTHigh,
                     int hTLow) {
        super(currentState, row, col);
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
