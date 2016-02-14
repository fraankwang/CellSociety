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

public class SugarscapeUIView extends UIView {

	private LineChart<Number,Number> myChart;
	private XYChart.Series<Number, Number> agentPopulation;
	private XYChart.Series<Number, Number> sugarCount;

	public SugarscapeUIView(Grid grid, Parameters params) {
		super(grid, params);
	}

	@Override
	protected Group createView() {
		Group root = new Group();
		VBox box = new VBox();
		
		TextField myAgentSugarMinField = makeTextField(getMyParams().getParameter("agentSugarMin"), "agentSugarMin");
		addLabelandTextField("Minimum Agent Sugar", myAgentSugarMinField, box);
		
		
		TextField myAgentSugarMaxField = makeTextField(getMyParams().getParameter("agentSugarMax"), "agentSugarMax");
		addLabelandTextField("Maximum Agent Sugar", myAgentSugarMaxField, box);
		
		
		TextField myAgentMetabolismMinField = makeTextField(getMyParams().getParameter("agentMetabolismMin"), "agentMetabolismMin");
		addLabelandTextField("Minimum Agent Metabolism", myAgentMetabolismMinField, box);
		
	
		TextField myAgentMetabolismMaxField = makeTextField(getMyParams().getParameter("agentMetabolismMax"), "agentMetabolismMax");
		addLabelandTextField("Maximum Agent Metabolism", myAgentMetabolismMaxField, box);
		
	
		TextField myAgentVisionMinField = makeTextField(getMyParams().getParameter("agentVisionMin"), "agentVisionMin");
		addLabelandTextField("Minimum Agent Vision", myAgentVisionMinField, box);
		
		
		TextField myAgentVisionMaxField = makeTextField(getMyParams().getParameter("agentVisionMax"), "agentVisionMax");
		addLabelandTextField("Maximum Agent Vision", myAgentVisionMaxField, box);
		

		TextField mySugarGrowBackRateField = makeTextField(getMyParams().getParameter("sugarGrowBackRate"), "sugarGrowBackRate");
		addLabelandTextField("Sugar Grow Back Rate", mySugarGrowBackRateField, box);
		

		TextField mySugarGrowBackIntervalField = makeTextField(getMyParams().getParameter("sugarGrowBackInterval"), "sugarGrowBackInterval");
		addLabelandTextField("Sugar Grow Back Interval", mySugarGrowBackIntervalField, box);
		
		root.getChildren().add(box);
		return root;
	}
	
	/**
	 * Specifies Sugarscape specific parameters to graph
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void createChart() {
		NumberAxis xAxis = formatXAxis("Time");

		double upperBound = 100.0;
		NumberAxis yAxis = formatYAxis("Counts", upperBound);
		yAxis.setAutoRanging(true);
		
		myChart = new LineChart<Number,Number>(xAxis, yAxis);
		myChart.setMaxSize(MainView.WINDOW_WIDTH, MainView.GRAPH_SIZE);		
		myChart.setTitle(Constants.RESOURCES.getString("SugarscapeGraphTitle"));
		
		agentPopulation = new XYChart.Series<>();
		agentPopulation.setName(Constants.RESOURCES.getString("SugarscapeAgentSeries"));
		sugarCount = new XYChart.Series<>();
		sugarCount.setName(Constants.RESOURCES.getString("SugarscapeSugarSeries"));

		myChart.getData().addAll(agentPopulation, sugarCount);
		
		myChart.setHorizontalGridLinesVisible(false);
		myChart.setVerticalGridLinesVisible(false);
		
		Group root = new Group();
		root.getChildren().add(myChart);
		
		setLineChart(root);
		
	}
	
	public XYChart.Series<Number, Number> getAgentPopulationSeries () {
		return agentPopulation;
	}

	public XYChart.Series<Number, Number> getSugarCountSeries () {
		return sugarCount;
	}

}
