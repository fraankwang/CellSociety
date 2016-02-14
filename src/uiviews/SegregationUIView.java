/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */
package uiviews;

import constants.Constants;
import constants.Parameters;
import grids.Grid;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class SegregationUIView extends UIView{

	private static final String SIMILARITY_PERCENTAGE = "similaritypercentage";

	public SegregationUIView(Grid grid, Parameters params) {
		super(grid, params);
	}

	@Override
	protected Group createView() {
		Group root = new Group();
		VBox box = new VBox();
		Label similarityLabel = new Label(Constants.RESOURCES.getString("similaritySliderTitle"));
		double similarityPercent = Double.parseDouble(getMyParams().getParameter(SIMILARITY_PERCENTAGE));
		Slider slider = createSlider(0, 100, similarityPercent);
		slider.valueProperty().addListener(e -> updateParamsAndGrid(SIMILARITY_PERCENTAGE, slider.getValue()));

		box.getChildren().addAll(similarityLabel, slider);
		root.getChildren().add(box);
		
		return root;
	}

}
