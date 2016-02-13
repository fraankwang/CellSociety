package uiviews;

import constants.Parameters;
import grids.Grid;
import javafx.scene.Group;

public class GameOfLifeUIView extends UIView {

	public GameOfLifeUIView(Grid grid, Parameters params) {
		super(grid, params);
	}

	@Override
	protected Group createView() {
		Group root = new Group();
		return root;
	}

}
