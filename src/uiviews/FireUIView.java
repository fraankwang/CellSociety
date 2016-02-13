package uiviews;

import java.util.Map;

import constants.Parameters;
import grids.Grid;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class FireUIView extends UIView {
	

	public FireUIView(Grid grid, Parameters params) {
		super(grid, params);
		
	}

	@Override
	protected Group createView() {
		Group root = new Group();
		double probCatch = Float.parseFloat(getMyParams().getParameter("probcatch"));
    	Label textLabel = new Label("Probability to Catch Fire");
    	Slider slider = createSlider(0,1,probCatch);
    	slider.setMajorTickUnit(0.25);
    	slider.setShowTickLabels(true);
    	
    	slider.valueProperty().addListener(e -> {getMyParamsMap().put("probcatch", slider.getValue());
    											getMyGrid().updateParams(getMyParamsMap());
    											});
    	VBox box = new VBox();
    	box.getChildren().addAll(textLabel, slider);
    	root.getChildren().add(box);
    	
    	
    	return root;
	}

}
