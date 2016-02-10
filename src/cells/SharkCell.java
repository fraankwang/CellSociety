package cells;

import java.util.List;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;

import constants.Location;
import constants.State;
import javafx.scene.shape.Shape;

/**
 * The class for the Shark
 *
 */
public class SharkCell extends DataCell {
    private int sharkMaxHealth;
    private int sharkCurrentHealth;
    private int sharkCurrentBreedTime;
    private int sharkBreedTime;

    public SharkCell (State currentState,
                      int row,
                      int col,
                      Shape shape,
                      int health,
                      int breedTime) {
        super(currentState, row, col, shape);
        sharkMaxHealth = health;
        sharkCurrentHealth = sharkMaxHealth;
        sharkBreedTime = breedTime;
        sharkCurrentBreedTime = sharkBreedTime;

    }

    
    public SharkCell(SharkCell shark, Location location){
    	super(shark.getMyCurrentState(), location.getRow(), location.getCol(), shark.getMyShape());
    	sharkCurrentHealth = shark.getCurrentHealth();
    	sharkCurrentBreedTime = shark.getTimeUntilBreed();
    	sharkBreedTime = shark.getBreedTime();
    }
    
	@Override
	public void update() {
		sharkCurrentHealth--;
		if(sharkCurrentHealth < 0) {
			setMyNextState(State.DEAD);
		}
		
		if(sharkCurrentBreedTime != 0){
			sharkCurrentBreedTime--;
		}
		else {
			sharkCurrentBreedTime = sharkBreedTime;
		}
		
	}

	public void eat(FishCell fish) {
		sharkCurrentHealth = sharkMaxHealth;
		fish.setMyNextState(State.DEAD);
	}
	/**
	 * Checks to see if the shark can eat
	 * @param neighbors
	 * @return true if there's a fish in its neighbors
	 */
	public boolean canEat(List<GridCell> neighbors){
		for(GridCell cell : neighbors) {
			if(cell instanceof FishCell){
				return true;
			}
		}
		return false;
	}
	
	public int getTimeUntilBreed() {
		return sharkCurrentBreedTime;
	}
	
	public int getCurrentHealth() {
		return sharkCurrentHealth;
	}
	
	private int getBreedTime() {
		return sharkBreedTime;
	}
	

}
