package cellsociety_team03;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class FishCell extends DataCell {
	private int fishHealth;
	
    public FishCell (State currentState, int size, Shape s, int health) {
        super(currentState, size, s);
        fishHealth = health;
    }

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
