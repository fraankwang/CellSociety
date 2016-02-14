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
import main.MainView;


public class GameOfLifeUIView extends UIView {

	private LineChart<Number,Number> myChart;
	private XYChart.Series<Number, Number> alivePopulation;
	private XYChart.Series<Number, Number> deadPopulation;
	
	public GameOfLifeUIView(Grid grid, Parameters params) {
		super(grid, params);
	}

    @Override
    protected Group createView () {
        Group root = new Group();
        return root;
    }

	/**
	 * Specifies GameOfLife specific parameters to graph
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void createChart() {
		NumberAxis xAxis = formatXAxis("Time");

		double upperBound = 100.0;
		NumberAxis yAxis = formatYAxis("Percentage Alive", upperBound);
		
		myChart = new LineChart<Number,Number>(xAxis, yAxis);
		myChart.setMaxSize(MainView.WINDOW_WIDTH, MainView.GRAPH_SIZE);		
		myChart.setTitle(Constants.RESOURCES.getString("GameOfLifeGraphTitle"));
		
		alivePopulation = new XYChart.Series<>();
		alivePopulation.setName(Constants.RESOURCES.getString("GameOfLifeAliveSeries"));
		deadPopulation = new XYChart.Series<>();
		deadPopulation.setName(Constants.RESOURCES.getString("GameOfLifeDeadSeries"));

		myChart.getData().addAll(alivePopulation, deadPopulation);
		
		myChart.setHorizontalGridLinesVisible(false);
		myChart.setVerticalGridLinesVisible(false);
		
		Group root = new Group();
		root.getChildren().add(myChart);
		
		setLineChart(root);
		
	}
	
	public XYChart.Series<Number, Number> getAlivePopulationSeries () {
		return alivePopulation;
	}

	public XYChart.Series<Number, Number> getDeadPopulationSeries () {
		return deadPopulation;
	}
}


