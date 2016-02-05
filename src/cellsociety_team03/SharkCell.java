package cellsociety_team03;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class SharkCell extends DataCell {
	private int sharkMaxHealth;
	private int sharkCurrentHealth;
	private int sharkBreedTime;
	
    public SharkCell (State currentState, int size, Shape s, int health, int breedTime) {
        super(currentState, size, s);
        sharkMaxHealth = health;
        sharkCurrentHealth = sharkMaxHealth;
        sharkBreedTime = breedTime;
    }

	@Override
	public void update() {
		sharkCurrentHealth--;
		sharkBreedTime--;
		
	}

	public void eat(FishCell fish) {
		sharkCurrentHealth = sharkMaxHealth;
		fish.setMyNextState(State.DEAD);
	}
}
