/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package uiviews;

import constants.Constants;
import constants.Parameters;
import grids.Grid;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;


public class FireUIView extends UIView {

    private static final String PROB_CATCH = "probcatch";

    public FireUIView (Grid grid, Parameters params) {
        super(grid, params);

    }

    @Override
    protected Group createView () {
        Group root = new Group();
        double probCatch = Float.parseFloat(getMyParams().getParameter(PROB_CATCH));
        Label textLabel = new Label(Constants.RESOURCES.getString("probCatchSliderTitle"));
        Slider slider = createSlider(0, 1, probCatch);
        slider.setMajorTickUnit(0.25);
        slider.setShowTickLabels(true);

        slider.valueProperty().addListener(e -> {
            getMyParamsMap().put(PROB_CATCH, slider.getValue());
            getMyGrid().updateParams(getMyParamsMap());
        });
        VBox box = new VBox();
        box.getChildren().addAll(textLabel, slider);
        root.getChildren().add(box);

        return root;
    }

}
