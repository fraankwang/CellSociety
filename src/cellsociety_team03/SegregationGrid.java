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
		initialize();
		
	}
	
	private void addStatesToList(double percentage, State state) {
		for(int x = 0; x < percentage; x++){
			stateList.add(state);
		}
		
	}



	@Override
	protected void initializeCell(int row, int column) {
		getMyCells()[row][column] = new SimpleCell(stateList.remove(0), row, column, new Rectangle(getMyCellSize(),getMyCellSize()));
	}

	@Override
	protected void setCellState(GridCell cell) {
		if(!cell.getMyCurrentState().equals(State.EMPTY) && cell.getMyNextState() == null) {
			List<GridCell> neighbors = getNeighbors(cell);
			double sameCount = 0;
			double nonEmptyCount = 0;
			for(GridCell neighbor : neighbors) {
				if(cell.getMyCurrentState().equals(neighbor.getMyCurrentState())) {
					sameCount++;
				}
				if(!(neighbor.getMyCurrentState() == State.EMPTY)){
				    nonEmptyCount++;
				}
			}
			
			
			if(!isContent((sameCount/nonEmptyCount)*100)){
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

		
	private boolean isContent(double percent) {
		return percent >= similarityPercentage;
	}
	

}
