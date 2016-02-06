package grids;

/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import cells.GridCell;
import cells.SimpleCell;
import constants.State;
import javafx.scene.shape.Rectangle;


/**
 * Grid subclass for Fire simulation
 *
 */
public class SegregationGrid extends Grid {

    private double similarityPercentage;
    private double redPercentage;
    private double bluePercentage;
    private double emptyPercentage;

    private List<State> stateList = new ArrayList<State>();

    public SegregationGrid (Map<String, String> params) {
        super(params);
        similarityPercentage = Double.parseDouble(params.get("similaritypercentage"));
        redPercentage = Double.parseDouble(params.get("redpercentage"));
        bluePercentage = Double.parseDouble(params.get("bluepercentage"));
        emptyPercentage = Double.parseDouble(params.get("emptypercentage"));

        addStatesToList(redPercentage, State.RED);
        addStatesToList(bluePercentage, State.BLUE);
        addStatesToList(emptyPercentage, State.EMPTY);

        Collections.shuffle(stateList);
        initialize();
    }

    @Override
    protected void initializeCell (int row, int column) {
        getMyCells()[row][column] =
                new SimpleCell(stateList.remove(0), row, column,
                               new Rectangle(getMyCellSize(), getMyCellSize()));
    }

    @Override
    protected void setCellState (GridCell cell) {
        if (!cell.getMyCurrentState().equals(State.EMPTY) && cell.getMyNextState() == null) {
            List<GridCell> neighbors = getNeighbors(cell);
            double sameCount = 0;
            double nonEmptyCount = 0;
            for (GridCell neighbor : neighbors) {
                if (cell.getMyCurrentState().equals(neighbor.getMyCurrentState())) {
                    sameCount++;
                }
                if (!(neighbor.getMyCurrentState() == State.EMPTY)) {
                    nonEmptyCount++;
                }
            }

            if (!isContent((sameCount / nonEmptyCount) * 100)) {
                move(cell);
            }
            else {
                cell.setMyNextState(cell.getMyCurrentState());
            }
        }
        else {
            if ((cell.getMyNextState() == null)) {
                cell.setMyNextState(cell.getMyCurrentState());
            }
        }

    }

    // TODO: Make better search for empty spots algorithm??? most likely make it random instead of
    // just first open spot
    private void move (GridCell cell) {
        for (int r = 0; r < getMyCells().length; r++) {
            for (int c = 0; c < getMyCells()[0].length; c++) {
                GridCell newCell = getMyCells()[r][c];
                if (newCell.getMyCurrentState().equals(State.EMPTY) &&
                    newCell.getMyNextState() == null) {
                    newCell.setMyNextState(cell.getMyCurrentState());
                    cell.setMyNextState(State.EMPTY);
                    return;
                }

            }
        }
        cell.setMyNextState(cell.getMyCurrentState());
    }

    private boolean isContent (double percent) {
        return percent >= similarityPercentage;
    }

}
