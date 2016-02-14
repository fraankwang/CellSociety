/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package uiviews;

import constants.Constants;
import constants.Parameters;
import grids.Grid;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import main.MainView;

public class FireUIView extends UIView {
	
	LineChart<Number,Number> myChart;
	XYChart.Series<Number, Number> treePopulation;
	XYChart.Series<Number, Number> burnedTrees;
	
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
    	box.setPrefWidth(MainView.PARAMETER_WIDTH);
    	box.getChildren().addAll(textLabel, slider);
    	root.getChildren().add(box);
    	
    	return root;
    	
	}

	/**
	 * Specifies FireGrid specific parameters to graph
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void createChart() {
		myChart = null;
		
		NumberAxis xAxis = formatXAxis("Time");

		double upperBound = 100.0;
		NumberAxis yAxis = formatYAxis("Percentage Burned", upperBound);

		myChart = new LineChart<Number,Number>(xAxis, yAxis);
		myChart.setMaxSize(MainView.WINDOW_WIDTH, MainView.GRAPH_SIZE);		
		myChart.setTitle(Constants.RESOURCES.getString("FireGraphTitle"));
		
		treePopulation = new XYChart.Series<>();
		treePopulation.setName("Trees");
		burnedTrees = new XYChart.Series<>();
		burnedTrees.setName("Burned Trees");

		myChart.getData().addAll(treePopulation, burnedTrees);
		
		myChart.setHorizontalGridLinesVisible(false);
		myChart.setVerticalGridLinesVisible(false);
		
		
		Group root = new Group();
		root.getChildren().add(myChart);
		
		setLineChart(root);
		
	}
	
	public XYChart.Series<Number, Number> getTreePopulation () {
		return treePopulation;
	}

	public XYChart.Series<Number, Number> getBurnedTrees () {
		return burnedTrees;
	}
	
	
}
	
