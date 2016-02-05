/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cellsociety_team03;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javafx.scene.shape.Rectangle;


public class PredatorPreyGrid extends Grid {


    private int fishBreed;
    private int sharkBreed;
    private int sharkHealth;
    private double sharkPercentage;
    private double fishPercentage;
    private double emptyPercentage;
	private List<State> initializeList;

    public PredatorPreyGrid (Map<String, String> params) {
        super(params);
        fishBreed = Integer.parseInt(params.get("fishbreed"));
        sharkBreed = Integer.parseInt(params.get("sharkbreed"));
        sharkHealth = Integer.parseInt(params.get("sharkhealth"));
        sharkPercentage = Double.parseDouble(params.get("sharkpercentage"));
        fishPercentage = Double.parseDouble(params.get("fishpercentage"));
        emptyPercentage = Double.parseDouble(params.get("emptypercentage"));
        initializeList = new ArrayList<State>();
        
        addStatesToList(sharkPercentage, State.SHARK);
        addStatesToList(fishPercentage, State.FISH);
        addStatesToList(emptyPercentage, State.EMPTY);
        
        Collections.shuffle(initializeList);
        
        initializeCells();
    }
    
    
    @Override
    protected void initializeCell (int row, int column) {
    	myCells[row][column] = new SimpleCell(initializeList.remove(0), CELL_SIZE, new Rectangle(30,30));
    }

    @Override
    protected void setCellState (GridCell cell, int r, int c) {
        // TODO Auto-generated method stub

    }

    private void move (GridCell origin, GridCell destination) {

    }


}
