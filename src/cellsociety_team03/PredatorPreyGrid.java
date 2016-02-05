/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cellsociety_team03;

import java.util.ArrayList;
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
        
        initializeCells();
    }
    
    @Override
    protected void initializeCell(int row, int column) {
    	State state = getInitializeList().remove(0);
    	GridCell toAdd;
    	if(state == State.SHARK){
    		toAdd = new SharkCell(State.SHARK, CELL_SIZE, new Rectangle(30,30),sharkHealth,sharkBreed);
    	}
    	else if(state == State.FISH){
    		toAdd = new FishCell(State.FISH, CELL_SIZE, new Rectangle(30,30),fishBreed);
    	}
    	else {
    		toAdd = new SimpleCell(State.EMPTY, CELL_SIZE, new Rectangle(30,30));
    	}
    	
    	myCells[row][column] = toAdd;
    }
    
    @Override
    protected void setCellStates () { 
    	 for (int r = 0; r < myCells.length; r++) {
             for (int c = 0; c < myCells[0].length; c++) {
            	 if(myCells[r][c] instanceof SharkCell) {
            		 setSharkCellState((SharkCell)myCells[r][c]);
            	 }
             }
    	 }
    }
    

    private void setSharkCellState(SharkCell shark, int row, int column) {
    	List<GridCell> neighbors = getNeighborCells(row,column);
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

	@Override
    protected void setCellState (GridCell cell, int r, int c) {
    	List<GridCell> neighbors = getNeighborCells(r,c);
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
    			myCells[r][c] = new SimpleCell(State.EMPTY, CELL_SIZE, new Rectangle(30,30));
    		}
    		else {
    			
    		}
    	}
    }

    private void move (GridCell origin, GridCell destination) {

    }
    
    
	private List<GridCell> getNeighborCells(int r, int c) {
		List<GridCell> neighborStates = new ArrayList<GridCell>();
		int[] rMod = { -1, 0, 1, 0};
	    int[] cMod = { 0, 1, 0, -1};
	    int r2;
	    int c2;
	    for(int x = 0; x < rMod.length; x++){
	    	r2 = r+rMod[x];
	    	c2 = c+cMod[x];
	    	
	    	if(r2 < 0){
	    		r2 += getRows();
	    	}
	    	else if(r2 >= getRows()) {
	    		r2 = 0;
	    	}
	    	
	    	if(c2 < 0){
	    		c2 += getColumns();
	    	}
	    	else if(c2 >= getColumns()){
	    		c2 = 0;
	    	}
	    	
	    	neighborStates.add(myCells[r2][c2]);
	    	
	    }
	    
	    return neighborStates;
	}

}
