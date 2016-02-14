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

public class SegregationUIView extends UIView{

	private LineChart<Number,Number> myChart;
	private XYChart.Series<Number, Number> redPopulation;
	private XYChart.Series<Number, Number> bluePopulation;
	
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


	/**
	 * Specifies Segregation specific parameters to graph
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void createChart() {
		NumberAxis xAxis = formatXAxis("Time");

		double upperBound = 100.0;
		NumberAxis yAxis = formatYAxis("Counts", upperBound);
		
		myChart = new LineChart<Number,Number>(xAxis, yAxis);
		myChart.setMaxSize(MainView.WINDOW_WIDTH, MainView.GRAPH_SIZE);		
		myChart.setTitle(Constants.RESOURCES.getString("SegregationGraphTitle"));
		
		redPopulation = new XYChart.Series<>();
		redPopulation.setName(Constants.RESOURCES.getString("SegregationRedSeries"));
		bluePopulation = new XYChart.Series<>();
		bluePopulation.setName(Constants.RESOURCES.getString("SegregationBlueSeries"));

		myChart.getData().addAll(redPopulation, bluePopulation);
		
		myChart.setHorizontalGridLinesVisible(false);
		myChart.setVerticalGridLinesVisible(false);
		
		Group root = new Group();
		root.getChildren().add(myChart);
		setLineChart(root);
		
	}
	
	public XYChart.Series<Number, Number> getRedPopulation () {
		return redPopulation;
	}

	public XYChart.Series<Number, Number> getBluePopulation () {
		return bluePopulation;
	}
}
