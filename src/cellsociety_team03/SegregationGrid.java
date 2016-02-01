/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cellsociety_team03;
import java.util.Map;


public class SegregationGrid extends Grid {

	private double similarityPercentage;
	private double redPercentage;
	private double bluePercentage;
	
	public SegregationGrid(Map<String, String> params) {
		super(params);
		similarityPercentage = Double.parseDouble(params.get("similaritypercentage"));
		redPercentage = Double.parseDouble(params.get("redpercentage"));
		bluePercentage = Double.parseDouble(params.get("bluepercentage"));
	}
	
	protected void setCellStates(){
		for (int r = 0; r < myCells.length; r++){
			for (int c = 0; c < myCells[0].length; c++){
				// TODO: Cell State determining algorithms
			}		
		}
		
		
	}

}
