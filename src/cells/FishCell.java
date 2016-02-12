/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cells;

import constants.Location;
import javafx.scene.shape.Shape;
import states.State;

/**
 * The class for Fish
 */
public class FishCell extends DataCell {
	private int fishBreedTime;
	private int fishCurrentBreedTime;
	
    public FishCell (State currentState, 
    				int row, 
    				int col, 
    				Shape shape, 
    				int breed) {
        super(currentState, row, col, shape);
        fishBreedTime = breed;
        fishCurrentBreedTime = fishBreedTime;
    }
    
    public FishCell (FishCell fish, Location location){
    	super(fish.getMyCurrentState(), location.getRow(), location.getCol(), fish.getMyShape());
  
    	fishCurrentBreedTime = fish.getTimeUntilBreed();
    	fishBreedTime = fish.getBreedTime();
    }


	@Override
	public void update() {
		if(fishCurrentBreedTime != 0){
			fishCurrentBreedTime--;
		}
		else {
			fishCurrentBreedTime = fishBreedTime;
		}
		
	}
	

    // =========================================================================
    // Getters and Setters
    // =========================================================================

	
	public int getTimeUntilBreed(){
		return fishCurrentBreedTime;
	}
	
	private int getBreedTime() {
		return fishBreedTime;
	}


}
