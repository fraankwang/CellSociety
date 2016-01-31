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
	
	
	public PredatorPreyGrid(Map<String, String> params) {
		super(params);
		fishBreed = Integer.parseInt(params.get("fishBreed"));
		sharkBreed = Integer.parseInt(params.get("sharkBreed"));
		sharkHealth = Integer.parseInt(params.get("sharkHealth"));
		sharkPercentage = Double.parseDouble(params.get("sharkPercentage"));
		fishPercentage = Double.parseDouble(params.get("fishPercentage"));
		emptyPercentage = Double.parseDouble(params.get("emptyPercentage"));
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
