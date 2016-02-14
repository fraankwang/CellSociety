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


public class PredatorPreyUIView extends UIView {

    private static final String FISH_BREED = "fishbreed";
    private static final String SHARK_BREED = "sharkbreed";
    private static final String SHARK_HEALTH = "sharkhealth";

    public PredatorPreyUIView (Grid grid, Parameters params) {
        super(grid, params);
    }

    @Override
    protected Group createView () {
        Group root = new Group();
        VBox box = new VBox();
        TextField sharkHealthField =
                makeTextField(getMyParams().getParameter(SHARK_HEALTH), SHARK_HEALTH);
        addLabelandTextField(Constants.RESOURCES.getString("sharkHealthFieldTitle"),
                             sharkHealthField, box);

        TextField sharkBreedField =
                makeTextField(getMyParams().getParameter(SHARK_BREED), SHARK_BREED);
        addLabelandTextField(Constants.RESOURCES.getString("sharkBreedFieldTitle"), sharkBreedField,
                             box);

        TextField fishBreedField =
                makeTextField(getMyParams().getParameter(FISH_BREED), FISH_BREED);
        addLabelandTextField(Constants.RESOURCES.getString("fishBreedFieldTitle"), fishBreedField,
                             box);

        root.getChildren().add(box);
        return root;
    }

}
