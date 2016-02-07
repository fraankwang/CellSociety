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

        initialize();
    }
    
    protected void initializeCell (int row, int column) {
        State state = State.EMPTY;
        GridCell toAdd;
        if (state == State.SHARK) {
            toAdd =
                    new SharkCell(State.SHARK, row, column,
                                  new Rectangle(getMyCellSize(), getMyCellSize()), sharkHealth,
                                  sharkBreed);
        }
        else if (state == State.FISH) {
            toAdd =
                    new FishCell(State.FISH, row, column,
                                 new Rectangle(getMyCellSize(), getMyCellSize()), fishBreed);
        }
        else {
            toAdd =
                    new SimpleCell(State.EMPTY, row, column,
                                   new Rectangle(getMyCellSize(), getMyCellSize()));
        }

        getMyCells()[row][column] = toAdd;
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
        	 if(getMyCells()[r][c] instanceof FishCell) {
        		 setFishCellState((FishCell)getMyCells()[r][c]);
        	 }
         }
	 }
	}

	private void setFishCellState(FishCell fishCell) {
		List<GridCell> neighbors = getNeighbors(fishCell);
    		if(fishCell.getMyNextState() == State.DEAD) {
    			//getMyCells()[fishCell.getMyGridLocation().getRow()][fishCell.getMyGridLocation().getCol()];
    		}
    		else {
    			fishCell.update();
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
    	List<GridCell> neighbors = getNeighbors(shark);
    	shark.update();
    	List<FishCell> edible = new ArrayList<FishCell>();
    	GridCell toMove = shark;
    	if(shark.canEat(neighbors)){
       		for(GridCell cell : neighbors) {
    			if(cell instanceof FishCell){
    				edible.add((FishCell)cell);
    			}
    		}
       		Collections.shuffle(edible);
       		
    	}
    	
    	
    	
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
    		if(cell.getMyNextState()== null){
    			cell.setMyNextState(cell.getMyCurrentState());
    		}
    }

    private void move (GridCell origin, GridCell destination) {
    	int originCol = origin.getMyGridLocation().getCol();
    	int originRow = origin.getMyGridLocation().getRow();
    	
    	int destinationCol = destination.getMyGridLocation().getCol();
    	int destinationRow = destination.getMyGridLocation().getRow();
    	
    	if(origin instanceof SharkCell) {
    		GridCell destinationCell = getMyCells()[destinationRow][destinationCol];
    		getMyGridPane().getChildren().remove(destinationCell.getMyShape());
    		destinationCell = new SharkCell((SharkCell)origin, destination.getMyGridLocation());
    		getMyCells()[destinationRow][destinationCol] = destinationCell;
    	
    		destinationCell.setMyCurrentState(State.EMPTY);
    		destinationCell.setMyNextState(State.SHARK);
    		
    		GridCell originCell = getMyCells()[originRow][originCol];
    		getMyGridPane().getChildren().remove(originCell.getMyShape());
    		originCell = new SimpleCell(State.SHARK, origin.getMyGridLocation().getRow(), origin.getMyGridLocation().getCol(), new Rectangle(getMyCellSize(), getMyCellSize()));
    		getMyCells()[originRow][originCol] = originCell;
    		originCell.setMyNextState(State.EMPTY);
    	
    		getMyGridPane().add(destinationCell.getMyShape(), destinationCol, destinationRow);
    		getMyGridPane().add(originCell.getMyShape(), originCol, originRow);

    	}
    	
    	if(origin instanceof FishCell) {
    		GridCell destinationCell = getMyCells()[destinationRow][destinationCol];
    		getMyGridPane().getChildren().remove(destinationCell.getMyShape());
    		destinationCell = new FishCell((FishCell)origin, destination.getMyGridLocation());
    		getMyCells()[destinationRow][destinationCol] = destinationCell;
    	
    		destinationCell.setMyCurrentState(State.EMPTY);
    		destinationCell.setMyNextState(State.FISH);
    		
    		GridCell originCell = getMyCells()[originRow][originCol];
    		getMyGridPane().getChildren().remove(originCell.getMyShape());
    		originCell = new SimpleCell(State.FISH, origin.getMyGridLocation().getRow(), origin.getMyGridLocation().getCol(), new Rectangle(getMyCellSize(), getMyCellSize()));
    		getMyCells()[originRow][originCol] = originCell;
    		originCell.setMyNextState(State.EMPTY);
    	
    		getMyGridPane().add(destinationCell.getMyShape(), destinationCol, destinationRow);
    		getMyGridPane().add(originCell.getMyShape(), originCol, originRow);

    	}
    	
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
            if(cellInBounds(neighborRow, neighborCol)){
                neighbors.add(getMyCells()[neighborRow][neighborCol]);
            }
        }
        
        
        return neighbors;
    }

}
