/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cells;

import constants.Location;
import javafx.scene.shape.Shape;
import states.State;
import states.WatorState;
import java.util.List;

/**
 * The class for the Shark
 *
 */
public class SharkCell extends DataCell {
    private int sharkMaxHealth;
    private int sharkCurrentHealth;
    private int sharkCurrentBreedTime;
    private int sharkBreedTime;

    public SharkCell (State currentState, int row, int col, Shape shape, int health, int breedTime) {
        super(currentState, row, col, shape);
        sharkMaxHealth = health;
        sharkCurrentHealth = sharkMaxHealth;
        sharkBreedTime = breedTime;
        sharkCurrentBreedTime = sharkBreedTime;

    }

    /**
     * A constructor used to make a copy of the shark
     * @param shark
     * @param location
     */
    
    public SharkCell(SharkCell shark, Location location){
    	super(shark.getMyCurrentState(), location.getRow(), location.getCol(), shark.getMyShape());
    	sharkCurrentHealth = shark.getCurrentHealth();
    	sharkCurrentBreedTime = shark.getTimeUntilBreed();
    	sharkBreedTime = shark.getBreedTime();
    	sharkMaxHealth = shark.getMaxHealth();
    }
    
	@Override
	public void update() {
		sharkCurrentHealth--;
		if(sharkCurrentHealth < 0) {
			setMyNextState(WatorState.DEAD);
		}
		
		if(sharkCurrentBreedTime != 0){
			sharkCurrentBreedTime--;
		}
		else {
			sharkCurrentBreedTime = sharkBreedTime;
		}
		
	}
	
	/**
	 * Eats the fish and then resets the shark's health
	 * to max	
	 *
	 * @param fish the fish to be eaten
	 */
	public void eat(FishCell fish) {
		sharkCurrentHealth = sharkMaxHealth;
		fish.setMyNextState(WatorState.DEAD);
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

    // =========================================================================
    // Getters and Setters
    // =========================================================================

	public int getTimeUntilBreed() {
		return sharkCurrentBreedTime;
	}
	
	public int getCurrentHealth() {
		return sharkCurrentHealth;
	}
	
	private int getBreedTime() {
		return sharkBreedTime;
	}
	
	private int getMaxHealth(){
		return sharkMaxHealth;
	}


}
