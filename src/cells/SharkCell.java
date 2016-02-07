package cells;

import java.util.List;

import constants.State;
import grids.Location;
import javafx.scene.shape.Shape;


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
	
	public int getTimeUntilBreed() {
		return sharkCurrentBreedTime;
	}
	
	public int getCurrentHealth() {
		return sharkCurrentHealth;
	}
	
	public boolean canEat(List<GridCell> neighbors){
		for(GridCell cell : neighbors) {
			if(cell instanceof FishCell){
				return true;
			}
		}
		return false;
	}

}
