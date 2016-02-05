/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package grids;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import cells.FishCell;
import cells.GridCell;
import cells.SharkCell;
import cells.SimpleCell;
import constants.NeighborOffset;
import constants.Offset;
import constants.State;
import javafx.scene.shape.Rectangle;


public class PredatorPreyGrid extends Grid {


    private int fishBreed;
    private int sharkBreed;
    private int sharkHealth;
    private double sharkPercentage;
    private double fishPercentage;
    private double emptyPercentage;

    public PredatorPreyGrid (Map<String, String> params) {
        super(params);
        fishBreed = Integer.parseInt(params.get("fishbreed"));
        sharkBreed = Integer.parseInt(params.get("sharkbreed"));
        sharkHealth = Integer.parseInt(params.get("sharkhealth"));
        sharkPercentage = Double.parseDouble(params.get("sharkpercentage"));
        fishPercentage = Double.parseDouble(params.get("fishpercentage"));
        emptyPercentage = Double.parseDouble(params.get("emptypercentage"));
        
        addStatesToList(sharkPercentage, State.SHARK);
        addStatesToList(fishPercentage, State.FISH);
        addStatesToList(emptyPercentage, State.EMPTY);
        
        initialize();
    }
    
    @Override
    protected void initializeCell(int row, int column) {
    	State state = getInitializeList().remove(0);
    	GridCell toAdd;
    	if(state == State.SHARK){
    		toAdd = new SharkCell(State.SHARK, row, column, new Rectangle(getMyCellSize(), getMyCellSize()),sharkHealth,sharkBreed);
    	}
    	else if(state == State.FISH){
    		toAdd = new FishCell(State.FISH, row, column, new Rectangle(getMyCellSize(), getMyCellSize()),fishBreed);
    	}
    	else {
    		toAdd = new SimpleCell(State.EMPTY, row, column, new Rectangle(getMyCellSize(), getMyCellSize()));
    	}
    	
    	getMyCells()[row][column] = toAdd;
    }
    
    @Override

    protected void setCellStates () { 
    	 for (int r = 0; r < getMyCells().length; r++) {
             for (int c = 0; c < getMyCells()[0].length; c++) {
            	 if(getMyCells()[r][c] instanceof SharkCell) {
            		 setSharkCellState((SharkCell)getMyCells()[r][c]);
            	 }
             }
    	 }
    }
    

    private void setSharkCellState(SharkCell shark) {
    	List<GridCell> neighbors = getNeighbors(shark);
    	shark.update();
    	GridCell toMove = shark;
		for(GridCell cell : neighbors){
			if(cell instanceof FishCell){
				shark.eat((FishCell)cell);
				break;
			}
			else if(cell.getMyCurrentState() == State.EMPTY && cell.getMyNextState() == null) {
				toMove = cell;
			}
		}
		move(shark,toMove);
	}
    
    protected void setCellState (GridCell cell) {
        // TODO Auto-generated method stub
    	List<GridCell> neighbors = getNeighbors(cell);
    	GridCell toMove = cell;
    	if(cell instanceof SharkCell) {
    		((SharkCell) cell).update();
    		for(GridCell gc : neighbors){
    			if(gc instanceof FishCell){
    				((SharkCell) cell).eat((FishCell)gc);
    				break;
    			}
    			else if(gc.getMyCurrentState() == State.EMPTY && gc.getMyNextState() == null) {
    				toMove = gc;
    			}
    		}
    		move(cell,toMove);
    		
    	}
    	else if(cell instanceof FishCell){
    		if(cell.getMyNextState() == State.DEAD) {
    			getMyCells()[cell.getMyGridLocation().getRow()][cell.getMyGridLocation().getCol()] = new SimpleCell(State.EMPTY, cell.getMyGridLocation().getRow(), cell.getMyGridLocation().getCol(), new Rectangle(getMyCellSize(), getMyCellSize()));
    		}
    		else {
    			
    		}
    	}
    }

    private void move (GridCell origin, GridCell destination) {

    }
    
    
    @Override
    protected List<Offset> neighborOffsets () {

        List<Offset> offsets = new ArrayList<Offset>();

        offsets.add(NeighborOffset.TOP.getOffset());
        offsets.add(NeighborOffset.LEFT.getOffset());
        offsets.add(NeighborOffset.RIGHT.getOffset());
        offsets.add(NeighborOffset.BOTTOM.getOffset());

        return offsets;
    }

}
