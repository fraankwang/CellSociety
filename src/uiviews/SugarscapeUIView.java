/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package uiviews;

import constants.Constants;
import constants.Parameters;
import grids.Grid;
import javafx.scene.Group;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class SugarscapeUIView extends UIView {

    private static final String SUGAR_GROW_BACK_INTERVAL = "sugarGrowBackInterval";
    private static final String SUGAR_GROW_BACK_RATE = "sugarGrowBackRate";
    private static final String AGENT_VISION_MAX = "agentVisionMax";
    private static final String AGENT_VISION_MIN = "agentVisionMin";
    private static final String AGENT_METABOLISM_MAX = "agentMetabolismMax";
    private static final String AGENT_METABOLISM_MIN = "agentMetabolismMin";
    private static final String AGENT_SUGAR_MAX = "agentSugarMax";
    private static final String AGENT_SUGAR_MIN = "agentSugarMin";

    public SugarscapeUIView (Grid grid, Parameters params) {
        super(grid, params);
    }

    @Override
    protected Group createView () {
        Group root = new Group();
        VBox box = new VBox();

        TextField myAgentSugarMinField =
                makeTextField(getMyParams().getParameter(AGENT_SUGAR_MIN), AGENT_SUGAR_MIN);
        addLabelandTextField(Constants.RESOURCES.getString("AgentSugarMinField"),
                             myAgentSugarMinField, box);

        TextField myAgentSugarMaxField =
                makeTextField(getMyParams().getParameter(AGENT_SUGAR_MAX), AGENT_SUGAR_MAX);
        addLabelandTextField(Constants.RESOURCES.getString("AgentSugarMaxField"),
                             myAgentSugarMaxField, box);

        TextField myAgentMetabolismMinField =
                makeTextField(getMyParams().getParameter(AGENT_METABOLISM_MIN),
                              AGENT_METABOLISM_MIN);
        addLabelandTextField(Constants.RESOURCES.getString("AgentMetabolismMinField"),
                             myAgentMetabolismMinField, box);

        TextField myAgentMetabolismMaxField =
                makeTextField(getMyParams().getParameter(AGENT_METABOLISM_MAX),
                              AGENT_METABOLISM_MAX);
        addLabelandTextField(Constants.RESOURCES.getString("AgentMetabolismMaxField"),
                             myAgentMetabolismMaxField, box);

        TextField myAgentVisionMinField =
                makeTextField(getMyParams().getParameter(AGENT_VISION_MIN), AGENT_VISION_MIN);
        addLabelandTextField(Constants.RESOURCES.getString("AgentVisionMinField"),
                             myAgentVisionMinField, box);

        TextField myAgentVisionMaxField =
                makeTextField(getMyParams().getParameter(AGENT_VISION_MAX), AGENT_VISION_MAX);
        addLabelandTextField(Constants.RESOURCES.getString("AgentVisionMaxField"),
                             myAgentVisionMaxField, box);

        TextField mySugarGrowBackRateField =
                makeTextField(getMyParams().getParameter(SUGAR_GROW_BACK_RATE),
                              SUGAR_GROW_BACK_RATE);
        addLabelandTextField(Constants.RESOURCES.getString("SugarGrowBackRateField"),
                             mySugarGrowBackRateField, box);

        TextField mySugarGrowBackIntervalField =
                makeTextField(getMyParams().getParameter(SUGAR_GROW_BACK_INTERVAL),
                              SUGAR_GROW_BACK_INTERVAL);
        addLabelandTextField(Constants.RESOURCES.getString("SugarGrowBackIntervalField"),
                             mySugarGrowBackIntervalField, box);

        root.getChildren().add(box);
        return root;
    }

}
