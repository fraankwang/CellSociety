package uiviews;

import constants.Parameters;
import grids.Grid;
import javafx.scene.Group;
import javafx.scene.control.Label;
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
    	Label sharkHealthLabel = new Label("Shark Health");
    	TextField sharkHealthField = new TextField(getMyParams().getParameter("sharkhealth"));
    	
 
    	sharkHealthField.textProperty().addListener(e -> addToParamsMapAndUpdateGrid("sharkhealth", Double.parseDouble(sharkHealthField.getText())));
    	Label sharkBreedLabel = new Label("Shark Breed Time");
    	TextField sharkBreedField = new TextField(getMyParams().getParameter("sharkbreed"));
    	
    	sharkBreedField.textProperty().addListener(e ->addToParamsMapAndUpdateGrid("sharkbreed", Double.parseDouble(sharkBreedField.getText())) );
    	
    	Label fishBreedLabel = new Label("Fish Breed Time");
    	TextField fishBreedField = new TextField(getMyParams().getParameter("fishbreed"));
    	
    	fishBreedField.textProperty().addListener(e -> addToParamsMapAndUpdateGrid("fishbreed", Double.parseDouble(fishBreedField.getText())));
    	
    	box.getChildren().addAll(sharkHealthLabel, sharkHealthField, 
    							sharkBreedLabel, sharkBreedField,
    							fishBreedLabel, fishBreedField);
    	
    	root.getChildren().add(box);
    	return root;
	}

}
