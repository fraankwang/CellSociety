/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cellsociety_team03;

import java.util.Map;


public class SegregationGrid extends Grid {

    private double similarityPercentage;
    private double redPercentage;
    private double bluePercentage;

    public SegregationGrid (Map<String, String> params) {
        super(params);
        similarityPercentage = Double.parseDouble(params.get("similaritypercentage"));
        redPercentage = Double.parseDouble(params.get("redpercentage"));
        bluePercentage = Double.parseDouble(params.get("bluepercentage"));
    }

    @Override
    protected void initializeCell (int r, int c) {

    }

    @Override
    protected void setCellState (GridCell cell, int r, int c) {
        // TODO Auto-generated method stub

    }

    private void move (GridCell origin, GridCell destination) {

    }

}
