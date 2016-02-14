package uiviews;

import constants.Parameters;
import grids.Grid;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
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

//	@Override
//	protected void createLineChart() {
////		NumberAxis xAxis = new NumberAxis();
////		NumberAxis yAxis = new NumberAxis();
////		
////		LineChart<Number,Number> chart = new LineChart<Number,Number>(xAxis, yAxis);
////		chart.setTitle("Populations");
////		XYChart.Series series1 = new XYChart.Series();
////		series1.setName("Trees");
////		series1.getData().add(new XYChart.Data(1,1));
////		
////		chart.getData().add(series1);
//		
//		final NumberAxis xAxis = new NumberAxis();
//        final NumberAxis yAxis = new NumberAxis();
//        xAxis.setLabel("Number of Month");
//        //creating the chart
//        final LineChart<Number,Number> lineChart = 
//                new LineChart<Number,Number>(xAxis,yAxis);
//                
//        lineChart.setTitle("Stock Monitoring, 2010");
//        //defining a series
//        XYChart.Series series = new XYChart.Series();
//        series.setName("My portfolio");
//        //populating the series with data
//        series.getData().add(new XYChart.Data(1, 23));
//        series.getData().add(new XYChart.Data(2, 14));
//        series.getData().add(new XYChart.Data(3, 15));
//        series.getData().add(new XYChart.Data(4, 24));
//        series.getData().add(new XYChart.Data(5, 34));
//        series.getData().add(new XYChart.Data(6, 36));
//        series.getData().add(new XYChart.Data(7, 22));
//        series.getData().add(new XYChart.Data(8, 45));
//        series.getData().add(new XYChart.Data(9, 43));
//        series.getData().add(new XYChart.Data(10, 17));
//        series.getData().add(new XYChart.Data(11, 29));
//        series.getData().add(new XYChart.Data(12, 25));
//		
////		super.myLineChart = chart;
//		setLineChart(lineChart);
//		
//	}

}
