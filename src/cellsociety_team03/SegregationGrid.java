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

	protected void setCellStates(){
		for (int r = 0; r < myCells.length; r++){
			for (int c = 0; c < myCells[0].length; c++){
				// TODO: Cell State determining algorithms
			}		
		}
		
		
	}


	@Override
	protected void initializeCell(int row, int column) {
		myCells[row][column] = new SimpleCell(stateList.remove(0), 50, new Rectangle(30,30));
	}

	@Override
	protected void setCellState(GridCell cell, int r, int c) {
		// TODO Auto-generated method stub
		
	}
		
	

}
