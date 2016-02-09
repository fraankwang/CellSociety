package cells;

import constants.Location;
import constants.State;
import javafx.scene.shape.Shape;

/**
 * The class for Fish
 */
public class FishCell extends DataCell {
	private int fishBreedTime;
	private int fishCurrentBreedTime;
	
    public FishCell (State currentState, int r, int c, Shape s, int breed) {
        super(currentState, r, c, s);
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
	
	public int getTimeUntilBreed(){
		return fishCurrentBreedTime;
	}
	
	private int getBreedTime() {
		return fishBreedTime;
	}


}
