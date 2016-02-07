package cells;


import constants.State;
import grids.Location;
import javafx.scene.shape.Shape;

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
    	fishCurrentBreedTime = getBreedTime();
    }

	@Override
	public void update() {
		fishBreedTime--;
		
	}
	
	private int getBreedTime(){
		return fishCurrentBreedTime;
	}

}
