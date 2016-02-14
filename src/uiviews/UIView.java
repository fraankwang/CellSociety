package uiviews;

import java.util.HashMap;
import java.util.Map;

import constants.Parameters;
import grids.Grid;
import javafx.scene.Group;
import javafx.scene.chart.Chart;
import javafx.scene.control.Slider;

public abstract class UIView {

	private Map<String, Double> paramsMap;
	private Group myView;
	private Grid myGrid;
	private Parameters myParams;
	protected Group myLineChart;
	
	public UIView(Grid grid, Parameters params) {
		myParams = params;
		paramsMap = new HashMap<String, Double>();
		myView = createView();
		myGrid = grid;
		
	}
	

	
	protected abstract Group createView();
	
//	protected abstract void createLineChart();
	
	protected Slider createSlider(double min, double max, double defaultValue){
    	Slider slider = new Slider(min,max, defaultValue);    	
    	slider.setMajorTickUnit(max/4);
		slider.setShowTickLabels(true);
    	return slider;
	}
	
	protected void addToParamsMapAndUpdateGrid(String name, double value){
		paramsMap.put(name, value);
		getMyGrid().updateParams(getMyParamsMap());
	}
	
	public Group getView() {
		return myView;
	}
	
	protected Grid getMyGrid() {
		return myGrid;
	}
	
	protected void setMyGrid(Grid grid) {
		myGrid = grid;
	}

	protected Map<String, Double> getMyParamsMap() {
		return paramsMap;
	}

	protected Parameters getMyParams() {
		return myParams;
	}

	public Group getLineChart() {
		return myLineChart;
	}
	
	protected void setLineChart(Chart chart) {
		myLineChart = new Group();
		myLineChart.getChildren().add(chart);
	}
}
