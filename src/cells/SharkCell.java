package cells;

import constants.State;
import javafx.scene.shape.Shape;


public class SharkCell extends DataCell {
    private int sharkMaxHealth;
    private int sharkCurrentHealth;
    private int sharkCurrentBreedTime;
    private int sharkMaxBreedTime;

    public SharkCell (State currentState,
                      int row,
                      int col,
                      Shape shape,
                      int health,
                      int breedTime) {
        super(currentState, row, col, shape);
        sharkMaxHealth = health;
        sharkCurrentHealth = sharkMaxHealth;
        sharkMaxBreedTime = breedTime;
        sharkCurrentBreedTime = sharkMaxBreedTime;

    }

    @Override
    public void update () {
        sharkCurrentHealth--;
        if (sharkCurrentHealth == 0) {
            setMyNextState(State.EMPTY);
        }
        sharkCurrentBreedTime--;

    }

    public void eat (FishCell fish) {
        sharkCurrentHealth = sharkMaxHealth;
        fish.setMyNextState(State.DEAD);
    }

    public int getTimeUntilBreed () {
        return sharkCurrentBreedTime;
    }
}
