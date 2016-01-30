/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cellsociety_team03;

import java.util.Map;

import javafx.event.ActionEvent;
import javafx.scene.Group;

public abstract class Grid {
	Cell[][] myCells;
	int gridWidth;
	int gridHeight;
	Group root;
	
	public Grid(Map<String,String> params){
		
	}
	
	protected abstract void step();
	
	private void handleMouseInput(ActionEvent e){
		
	}
	
	private void handleKeyEntry(ActionEvent e){
		
	}
	
	private void initializeCells(){
		
	}
	
	private void createBoard(){
		//make GridPane based on Cell[][] myCells
		//set root
	}

	public Cell[][] getMyCells() {
		return myCells;
	}

	public void setMyCells(Cell[][] myCells) {
		this.myCells = myCells;
	}

	public int getGridWidth() {
		return gridWidth;
	}

	public void setGridWidth(int gridWidth) {
		this.gridWidth = gridWidth;
	}

	public int getGridHeight() {
		return gridHeight;
	}

	public void setGridHeight(int gridHeight) {
		this.gridHeight = gridHeight;
	}

	public Group getRoot() {
		return root;
	}

	public void setRoot(Group root) {
		this.root = root;
	}
	
	
	
	
	
	
	
	
	
}
