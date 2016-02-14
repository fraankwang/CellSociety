/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package uiviews;

import java.util.HashMap;
import java.util.Map;

import constants.Parameters;
import grids.Grid;
import javafx.scene.Group;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
/**
 * The abstract class for the View for the UI.
 * This is where the buttons are made for adjusting 
 * parameters of the simulation
 *
 */
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
	
	/**
	 * Allows each subclass to create their own view
	 * 
	 * @return the Group that is the View
	 */
	protected abstract Group createView ();
	
	/**
	 * Customized expanding line graph that 
	 */
	public abstract void createChart ();
	
	/**
	 * Abstract method for formatting NumberAxis
	 * @param label
	 * @return formatted xAxis
	 */
	protected NumberAxis formatXAxis (String label) {
		NumberAxis xAxis = new NumberAxis();
		xAxis.setLabel(label);
		xAxis.setAutoRanging(true);
		xAxis.setMinorTickVisible(false);
		
		return xAxis;
	}
	
	/**
	 * Abstract method for formatting NumberAxis
	 * @param label
	 * @return formatted yAxis
	 */
	protected NumberAxis formatYAxis(String label, double upperBound) {
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel(label);
		yAxis.setUpperBound(upperBound);
		yAxis.setAutoRanging(false);
		yAxis.setMinorTickVisible(false);
		
		return yAxis;
	}
	
	/**
	 * Convenience method to update series with data points
	 * @param series
	 * @param x-coordinate
	 * @param y-coordinate
	 */
	public void addDataPoint (XYChart.Series<Number, Number> series, double x, double y) {
		series.getData().add(new XYChart.Data<>(x,y));
		
	}
	
	
	/**
	 * Builds a slider that goes from min to max and starts at defaultValue
	 * 
	 * @param min the lowest value on the slider
	 * @param max the highest value on the slider
	 * @param defaultValue where the slider defaults to
	 * @return the created Slider
	 */
	protected Slider createSlider(double min, double max, double defaultValue){
    	Slider slider = new Slider(min,max, defaultValue);    	
    	slider.setMajorTickUnit(max/4);
		slider.setShowTickLabels(true);
    	return slider;
	}
	
	/**
	 * Updates the paramsMap as well as updating the Grid to match
	 * the changed parameters
	 * 
	 * @param name the name of the parameter being changed
	 * @param value the value the parameter is changed to
	 */
	protected void updateParamsAndGrid (String name, double value){
		paramsMap.put(name, value);
		getMyGrid().updateParams(getMyParamsMap());
	}
	
	/**
	 * The update method for TextFields. Only updates paramsMap and the Grid
	 * if the text in the TextField is a number
	 * 
	 * @param textfield
	 * @param mapString
	 */
	protected void updateFromTextField (TextField textfield, String mapString){
		if(textfield.getText().matches("\\d+")){
			updateParamsAndGrid(mapString, Double.parseDouble(textfield.getText()));
		}
	}
	
	/**
	 * Makes a TextField with an event listener
	 * 
	 * @param defaultText the text that starts in the TextField
	 * @param mapString the key for the Map that the value in the TextField goes to
	 * @return the created TextField
	 */
	protected TextField makeTextField (String defaultText, String mapString){
		TextField textfield = new TextField(defaultText);
		paramsMap.put(mapString, Double.parseDouble(textfield.getText()));
		textfield.textProperty().addListener(e -> updateFromTextField(textfield, mapString));
		return textfield;
	}
	
	/**
	 * Adds the Label and TextField to the GUI
	 * 
	 * @param label the label of the TextField
	 * @param textfield the TextField to be added
	 * @param box the VBox that both the label and textfield are added to
	 */
	protected void addLabelandTextField (String label, TextField textfield, VBox box){
		Label newLabel = new Label(label);
		box.getChildren().addAll(newLabel, textfield);
	}
	
	
	// =========================================================================
	// Getters and Setters
	// =========================================================================

	
	public Group getView (){
		return myView;
	}
	
	protected Grid getMyGrid (){
		return myGrid;
	}
	
	protected Map<String, Double> getMyParamsMap () {
		return paramsMap;
	}

	protected Parameters getMyParams() {
		return myParams;
	}

	public Group getLineChart() {
		return myLineChart;
	}
	
	protected void setLineChart(Group chart) {
		myLineChart = chart;
		
	}
}
