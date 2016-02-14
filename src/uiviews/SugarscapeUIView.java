package uiviews;

import constants.Parameters;
import grids.Grid;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SugarscapeUIView extends UIView {

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

}
