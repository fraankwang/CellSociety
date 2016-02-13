package cells;

import java.util.Set;
import states.State;

public class AntPatch extends Patch {
    private int myNumFoodPheromones;
    private int myNumHomePheromones;
    private int myMaxAnts;
    
    private Set<Ant> myAnts;
    
    public AntPatch (State currentState, int row, int col) {
        super(currentState, row, col);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update () {
        // TODO Auto-generated method stub

    }
    
    public boolean hasTooManyAnts(){
        return myAnts.size() >= myMaxAnts;
    }

    public int getMyNumFoodPheromones () {
        return myNumFoodPheromones;
    }

    public int getMyNumHomePheromones () {
        return myNumHomePheromones;
    }

}
