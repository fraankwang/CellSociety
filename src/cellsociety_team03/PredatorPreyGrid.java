/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cellsociety_team03;

import java.util.Map;


public class PredatorPreyGrid extends Grid {


    private int fishBreed;
    private int sharkBreed;
    private int sharkHealth;
    private double sharkPercentage;
    private double fishPercentage;
    private double emptyPercentage;

    public PredatorPreyGrid (Map<String, String> params) {
        super(params);
        fishBreed = Integer.parseInt(params.get("fishbreed"));
        sharkBreed = Integer.parseInt(params.get("sharkbreed"));
        sharkHealth = Integer.parseInt(params.get("sharkhealth"));
        sharkPercentage = Double.parseDouble(params.get("sharkpercentage"));
        fishPercentage = Double.parseDouble(params.get("fishpercentage"));
        emptyPercentage = Double.parseDouble(params.get("emptypercentage"));
    }

    @Override
    protected void initializeCell (int r, int c) {

    }

    @Override
    protected void setCellState (GridCell cell) {
        // TODO Auto-generated method stub

    }

    private void move (GridCell origin, GridCell destination) {

    }


}
