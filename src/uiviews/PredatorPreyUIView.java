/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */
package uiviews;

import constants.Parameters;
import grids.Grid;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PredatorPreyUIView extends UIView {

	public PredatorPreyUIView(Grid grid, Parameters params) {
		super(grid, params);
	}

	@Override
	protected Group createView() {
		Group root = new Group();
		VBox box = new VBox();
		TextField sharkHealthField = makeTextField(getMyParams().getParameter("sharkhealth"), "sharkhealth");
		addLabelandTextField("Shark Health", sharkHealthField, box);

		TextField sharkBreedField = makeTextField(getMyParams().getParameter("sharkbreed"), "sharkbreed");
		addLabelandTextField("Shark Breed Time", sharkBreedField, box);
		
		TextField fishBreedField = makeTextField(getMyParams().getParameter("fishbreed"), "fishbreed");
		addLabelandTextField("Fish Breed Time", fishBreedField, box);

		root.getChildren().add(box);
    	return root;
	}

}
