/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cellsociety_team03;
import java.util.Map;


public class FireGrid extends Grid {

	private double probCatch;
	
	public FireGrid(Map<String, String> params) {
		super(params);
		probCatch = Double.parseDouble(params.get("probCatch"));
	}
	
	protected void setCellStates(){
		for (int r = 0; r < myCells.length; r++){
			for (int c = 0; c < myCells[0].length; c++){
				// TODO: Cell State determining algorithms
			}		
		}
		
		
	}

	
	private void move(GridCell origin, GridCell destination){
		
	}
}
