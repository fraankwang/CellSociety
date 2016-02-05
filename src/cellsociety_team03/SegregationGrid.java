/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cellsociety_team03;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javafx.scene.shape.Rectangle;


public class SegregationGrid extends Grid {


	private double similarityPercentage;
	private double redPercentage;
	private double bluePercentage;
	private double emptyPercentage;
	


	
	
	public SegregationGrid(Map<String, String> params) {
		super(params);
		similarityPercentage = Double.parseDouble(params.get("similaritypercentage"));
		redPercentage = Double.parseDouble(params.get("redpercentage"));
		bluePercentage = Double.parseDouble(params.get("bluepercentage"));
		emptyPercentage = Double.parseDouble(params.get("emptypercentage"));
		
		addStatesToList(redPercentage, State.RED);
		addStatesToList(bluePercentage, State.BLUE);
		addStatesToList(emptyPercentage, State.EMPTY);

		initializeCells();
		
	}


	@Override
	protected void setCellState(GridCell cell, int r, int c) {
		if(!cell.getMyCurrentState().equals(State.EMPTY) && cell.getMyNextState() == null) {
			List<State> neighbors = getNeighborStates(r,c);
			double total = neighbors.size();
			double sameCount = 0;
			for(State state : neighbors) {
				if(cell.getMyCurrentState().equals(state)) {
					sameCount++;
				}
			}
			
			
			if(!isContent((sameCount/total)*100)){
				move(cell);
				
				
			}
			else{
				cell.setMyNextState(cell.getMyCurrentState());
			}
		}
		else {
			if((cell.getMyNextState()== null)){
				cell.setMyNextState(cell.getMyCurrentState());
			}
		}
		
	}
	
	//TODO: Make better search for empty spots algorithm??? most likely make it random instead of just first open spot
	private void move(GridCell cell) {
		for (int r = 0; r < myCells.length; r++) {
			for (int c = 0; c < myCells[0].length; c++) {
				GridCell newCell = getMyCells()[r][c];
				if(newCell.getMyCurrentState().equals(State.EMPTY) && newCell.getMyNextState()==null) {
					newCell.setMyNextState(cell.getMyCurrentState());
					cell.setMyNextState(State.EMPTY);
					return;
				}

			}
		}
		cell.setMyNextState(cell.getMyCurrentState());
	}

	private List<State> getNeighborStates(int r, int c) {
		List<State> neighborStates = new ArrayList<State>();
		int[] rMod = { -1, -1, -1, 0, 0, 1, 1, 1 };
	    int[] cMod = { -1, 0, 1, -1, 1, -1, 0, 1 };
	    int r2;
	    int c2;
	    for(int x = 0; x < rMod.length; x++){
	    	r2 = r+rMod[x];
	    	c2 = c+cMod[x];
	    	if(cellInBounds(r2,c2) && !myCells[r2][c2].getMyCurrentState().equals(State.EMPTY)){
	    		neighborStates.add(myCells[r2][c2].getMyCurrentState());
	    	}
	    }
	    
	    return neighborStates;
	}
		
	private boolean isContent(double percent) {
		return percent >= similarityPercentage;
	}
	

}
