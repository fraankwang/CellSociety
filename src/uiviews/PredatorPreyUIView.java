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
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import main.MainView;


public class PredatorPreyUIView extends UIView {

	private LineChart<Number,Number> myChart;
	private XYChart.Series<Number, Number> fishPopulation;
	private XYChart.Series<Number, Number> sharkPopulation;
	
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

	/**
	 * Specifies Predator Prey specific parameters to graph
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void createChart() {
		NumberAxis xAxis = formatXAxis("Time");

		double upperBound = 100.0;
		NumberAxis yAxis = formatYAxis("Population %", upperBound);
		yAxis.setAutoRanging(true);
		
		myChart = new LineChart<Number,Number>(xAxis, yAxis);
		myChart.setMaxSize(MainView.WINDOW_WIDTH, MainView.GRAPH_SIZE);		
		myChart.setTitle(Constants.RESOURCES.getString("PredatorPreyGraphTitle"));
		
		fishPopulation = new XYChart.Series<>();
		fishPopulation.setName(Constants.RESOURCES.getString("PredatorPreyFishSeries"));
		sharkPopulation = new XYChart.Series<>();
		sharkPopulation.setName(Constants.RESOURCES.getString("PredatorPreySharkSeries"));

		myChart.getData().addAll(fishPopulation, sharkPopulation);
		
		myChart.setHorizontalGridLinesVisible(false);
		myChart.setVerticalGridLinesVisible(false);
		
		Group root = new Group();
		root.getChildren().add(myChart);
		
		setLineChart(root);
		
	}
	
	public XYChart.Series<Number, Number> getFishPopulationSeries () {
		return fishPopulation;
	}

	public XYChart.Series<Number, Number> getSharkPopulationSeries () {
		return sharkPopulation;
	}
	
}
