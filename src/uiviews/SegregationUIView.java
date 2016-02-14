package uiviews;

import constants.Parameters;
import grids.Grid;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class SegregationUIView extends UIView{

	public SegregationUIView(Grid grid, Parameters params) {
		super(grid, params);
	}

	@Override
	protected Group createView() {
		Group root = new Group();
		VBox box = new VBox();
		Label similarityLabel = new Label("Similarity Percentage");
		double similarityPercent = Double.parseDouble(getMyParams().getParameter("similaritypercentage"));
		Slider slider = createSlider(0, 100, similarityPercent);
		slider.valueProperty().addListener(e -> updateParamsAndGrid("similaritypercentage", slider.getValue()));

		box.getChildren().addAll(similarityLabel, slider);
		root.getChildren().add(box);
		
		return root;
	}

}
