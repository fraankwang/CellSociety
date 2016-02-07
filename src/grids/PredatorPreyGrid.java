/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package grids;

import java.util.ArrayList;
import java.util.Collections;
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


/**
 * Grid subclass for Predator Prey simulation
 *
 */
public class PredatorPreyGrid extends Grid {

    private int fishBreed;
    private int sharkBreed;
    private int sharkHealth;
    
    public PredatorPreyGrid (Map<String, String> params) {
        super(params);
        fishBreed = Integer.parseInt(params.get("fishbreed"));
        sharkBreed = Integer.parseInt(params.get("sharkbreed"));
        sharkHealth = Integer.parseInt(params.get("sharkhealth"));

        initialize();
    }
    
    protected void initializeCell (int row, int col) {
        GridCell cell = new SimpleCell(State.EMPTY, row, col,
                new Rectangle(getMyCellSize(), getMyCellSize()));

        int s = getMyInitialStates()[row][col];
        switch (s) {
            case 0:
                cell =  new SimpleCell(State.EMPTY, row, col,
                        new Rectangle(getMyCellSize(), getMyCellSize()));;
                break;
            case 1:
                cell = new SharkCell(State.SHARK, row, col,
                        new Rectangle(getMyCellSize(), getMyCellSize()), sharkHealth,
                        sharkBreed);
                break;
            case 2:
            	cell = new FishCell(State.FISH, row, col,
                        new Rectangle(getMyCellSize(), getMyCellSize()), fishBreed);

        }

        getMyCells()[row][col] = cell;

    }

    @Override
    protected void setCellStates () { 
    	setSharkCellStates();
    	setFishCellStates();
    	for (int r = 0; r < getMyCells().length; r++) {
            for (int c = 0; c < getMyCells()[0].length; c++) {
            	if(getMyCells()[r][c].getMyCurrentState() == State.EMPTY){
            		setCellState(getMyCells()[r][c]);
            	}
            }
    	}
    	 
    }
    

    private void setFishCellStates() {
   	 for (int r = 0; r < getMyCells().length; r++) {
         for (int c = 0; c < getMyCells()[0].length; c++) {
        	 if(getMyCells()[r][c] instanceof FishCell && (getMyCells()[r][c].getMyNextState() == null || getMyCells()[r][c].getMyNextState()== State.DEAD)) {
        		 setFishCellState((FishCell)getMyCells()[r][c]);
        	 }
         }
	 }
	}

	private void setFishCellState(FishCell fishCell) {
		fishCell.update();
		List<GridCell> neighbors = getNeighbors(fishCell);
		if(fishCell.getMyNextState() == State.DEAD) {
			kill(fishCell, State.FISH);
		}
		else {
			int randIndex = (int)Math.random()*neighbors.size();
			move(fishCell, neighbors.get(randIndex));
		}
	}

	private void setSharkCellStates() {
   	 for (int r = 0; r < getMyCells().length; r++) {
         for (int c = 0; c < getMyCells()[0].length; c++) {
        	 if(getMyCells()[r][c] instanceof SharkCell && getMyCells()[r][c].getMyNextState() == null) {
        		 setSharkCellState((SharkCell)getMyCells()[r][c]);
        	 }
         }
	 }
	}

	private void setSharkCellState(SharkCell shark) {
		shark.update();
    	List<GridCell> neighbors = getNeighbors(shark);
    	List<FishCell> edible = new ArrayList<FishCell>();
    	GridCell toMove = neighbors.get(0);
    	if(shark.canEat(neighbors)){
       		for(GridCell cell : neighbors) {
    			if(cell instanceof FishCell){
    				edible.add((FishCell)cell);
    			}
    		}
       		Collections.shuffle(edible);
       		shark.eat(edible.remove(edible.size()-1));
       		shark.setMyNextState(shark.getMyCurrentState());
    	}
    	else if(shark.getMyNextState() == State.DEAD) {
    		kill(shark, shark.getMyCurrentState());
    	}
    	else{
    		move(shark,toMove);
    	}
	}
    
    protected void setCellState (GridCell cell) {
    		if(cell.getMyNextState()== null){
    			cell.setMyNextState(cell.getMyCurrentState());
    		}
    }

    private void move (GridCell origin, GridCell destination) {
    	int originCol = origin.getMyGridLocation().getCol();
    	int originRow = origin.getMyGridLocation().getRow();
    	
    	int destinationCol = destination.getMyGridLocation().getCol();
    	int destinationRow = destination.getMyGridLocation().getRow();
    	

    	GridCell destinationCell = getMyCells()[destinationRow][destinationCol];
    	getMyGridPane().getChildren().remove(destinationCell.getMyShape());

    	
    	GridCell originCell = getMyCells()[originRow][originCol];
    	getMyGridPane().getChildren().remove(originCell.getMyShape());

    	
    	if(origin instanceof SharkCell){
    		destinationCell = new SharkCell((SharkCell)origin, destination.getMyGridLocation());
    		destinationCell.setMyNextState(State.SHARK);
    		originCell = new SimpleCell(State.SHARK, origin.getMyGridLocation().getRow(), origin.getMyGridLocation().getCol(), new Rectangle(getMyCellSize(), getMyCellSize()));
    	}
    	else if(origin instanceof FishCell){
    		destinationCell = new FishCell((FishCell)origin, destination.getMyGridLocation());
    		destinationCell.setMyNextState(State.FISH);
    		originCell = new SimpleCell(State.FISH, origin.getMyGridLocation().getRow(), origin.getMyGridLocation().getCol(), new Rectangle(getMyCellSize(), getMyCellSize()));
    	}
    	
    	getMyCells()[destinationRow][destinationCol] = destinationCell;
    	destinationCell.setMyCurrentState(State.EMPTY);
    	
    	getMyCells()[originRow][originCol] = originCell;
    	originCell.setMyNextState(State.EMPTY);


    	getMyGridPane().add(destinationCell.getMyShape(), destinationCol, destinationRow);
    	getMyGridPane().add(originCell.getMyShape(), originCol, originRow);
    	
    }
    
    private void kill(GridCell cell, State state){
		int row = cell.getMyGridLocation().getRow();
		int col = cell.getMyGridLocation().getCol();
		GridCell deadCell = getMyCells()[row][col];
		getMyGridPane().getChildren().remove(deadCell.getMyShape());
		
    	if(state == State.FISH){
    		deadCell =  new SimpleCell(State.FISH, deadCell.getMyGridLocation().getRow(), deadCell.getMyGridLocation().getCol(), new Rectangle(getMyCellSize(), getMyCellSize()));
    	}
    	else if(state == State.SHARK){
    		deadCell =  new SimpleCell(State.SHARK, deadCell.getMyGridLocation().getRow(), deadCell.getMyGridLocation().getCol(), new Rectangle(getMyCellSize(), getMyCellSize()));
    	}
		
		getMyCells()[row][col] = deadCell;
		getMyGridPane().add(deadCell.getMyShape(), col, row);
		deadCell.setMyNextState(State.EMPTY);

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
    
    @Override
    protected List<GridCell> getNeighbors(GridCell cell){
        int r = cell.getMyGridLocation().getRow();
        int c = cell.getMyGridLocation().getCol();
        
        List<Offset> offsets = neighborOffsets();
        List<GridCell> neighbors = new ArrayList<GridCell>();
        
        for(Offset offset : offsets){
        	int neighborRow = r + offset.getRow();
        	int neighborCol = c + offset.getCol();
        	neighborRow = checkAndSetRowWrapAround(neighborRow);
        	neighborCol = checkAndSetColWrapAround(neighborCol);
        	neighbors.add(getMyCells()[neighborRow][neighborCol]);
        }
        
        
        return neighbors;
    }
    
    private int checkAndSetRowWrapAround(int row){
    	if(row < 0) {
    		return row + getRows();
    	}
    	else if(row == getRows()){
    		return 0;
    	}
    	else{
    		return row;
    	}
    }
    
    private int checkAndSetColWrapAround(int col){
    	if(col < 0) {
    		return col + getColumns();
    	}
    	else if(col == getColumns()){
    		return 0;
    	}
    	else{
    		return col;
    	}
    }

}
