package uiviews;

import java.util.HashMap;
import java.util.Map;

import constants.Parameters;
import grids.Grid;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public abstract class UIView {


	private Map<String, Double> paramsMap;
	private Group myView;
	private Grid myGrid;
	private Parameters myParams;
	
	public UIView(Grid grid, Parameters params) {
		myParams = params;
		paramsMap = new HashMap<String, Double>();
		myView = createView();
		myGrid = grid;
		
	}
	
	protected abstract Group createView ();
	
	protected Slider createSlider(double min, double max, double defaultValue){
    	Slider slider = new Slider(min,max, defaultValue);    	
    	slider.setMajorTickUnit(max/4);
		slider.setShowTickLabels(true);
    	return slider;
	}
	
	protected void updateParamsAndGrid (String name, double value){
		paramsMap.put(name, value);
		getMyGrid().updateParams(getMyParamsMap());
	}
	
	protected void updateFromTextField (TextField textfield, String mapString){
		if(textfield.getText().matches("\\d+")){
			updateParamsAndGrid(mapString, Double.parseDouble(textfield.getText()));
		}
	}
	
	protected TextField makeTextField (String defaultText, String mapString){
		TextField textfield = new TextField(defaultText);
		paramsMap.put(mapString, Double.parseDouble(textfield.getText()));
		textfield.textProperty().addListener(e -> updateFromTextField(textfield, mapString));
		return textfield;
	}
	
	protected void addLabelandTextField (String label, TextField textfield, VBox box){
		Label newLabel = new Label(label);
		box.getChildren().addAll(newLabel, textfield);
	}
	
	
	public Group getView (){
		return myView;
	}
	
	protected Grid getMyGrid (){
		return myGrid;
	}
	
	protected void setMyGrid (Grid grid){
		myGrid = grid;
	}

	protected Map<String, Double> getMyParamsMap () {
		return paramsMap;
	}

	protected Parameters getMyParams (){
		return myParams;
	}
}
