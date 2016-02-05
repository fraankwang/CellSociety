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
	
	private List<State> stateList = new ArrayList<State>();

	
	
	public SegregationGrid(Map<String, String> params) {
		super(params);
		similarityPercentage = Double.parseDouble(params.get("similaritypercentage"));
		redPercentage = Double.parseDouble(params.get("redpercentage"));
		bluePercentage = Double.parseDouble(params.get("bluepercentage"));
		emptyPercentage = Double.parseDouble(params.get("emptypercentage"));
		
		addStatesToList(redPercentage, State.RED);
		addStatesToList(bluePercentage, State.BLUE);
		addStatesToList(emptyPercentage, State.EMPTY);
		Collections.shuffle(stateList);
		initializeCells();
		
	}
	
	private void addStatesToList(double percentage, State state) {
		for(int x = 0; x < percentage; x++){
			stateList.add(state);
		}
		
	}



	@Override
	protected void initializeCell(int row, int column) {
		getMyCells()[row][column] = new SimpleCell(stateList.remove(0), getMyCellSize(), new Rectangle(getMyCellSize(),getMyCellSize()));
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
				System.out.printf("The uncontent cell at (%d, %d) with state of: " + cell.getMyCurrentState() + "has ", r, c);
				move(cell);
				
				
			}
			else{
				cell.setMyNextState(cell.getMyCurrentState());
			}
		}
		
	}
	//TODO: Make better search for empty spots algorithm???
	private void move(GridCell cell) {
		for (int r = 0; r < getMyCells().length; r++) {
			for (int c = 0; c < getMyCells()[0].length; c++) {
				GridCell newCell = getMyCells()[r][c];
				if(newCell.getMyCurrentState().equals(State.EMPTY) && newCell.getMyNextState()==null) {
					newCell.setMyNextState(cell.getMyCurrentState());
					cell.setMyNextState(State.EMPTY);
					
					System.out.printf("moved to (%d,%d) ", r, c);
					System.out.printf("which now has state of %S\n", newCell.getMyNextState());
					return;
				}

			}
		}
		cell.setMyNextState(cell.getMyCurrentState());
		System.out.println("stayed in place.");
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
	    	if(cellInBounds(r2,c2) && !getMyCells()[r2][c2].getMyCurrentState().equals(State.EMPTY)){
	    		neighborStates.add(getMyCells()[r2][c2].getMyCurrentState());
	    	}
	    }
	    
	    return neighborStates;
	}
		
	private boolean isContent(double percent) {
		return percent >= similarityPercentage;
	}
	

}
