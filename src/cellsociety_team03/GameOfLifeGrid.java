/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cellsociety_team03;

import java.util.Map;

public class GameOfLifeGrid extends Grid {

	public GameOfLifeGrid(Map<String, String> params) {
		super(params);
		// TODO Auto-generated constructor stub
	}

	protected void setCellStates(){
		for (int r = 0; r < myCells.length; r++){
			for (int c = 0; c < myCells[0].length; c++){
				// TODO: Cell State determining algorithms
			}		
		}
		
		
	}
}
