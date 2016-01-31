/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cellsociety_team03;

import java.util.Map;

import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.control.Cell;
import javafx.scene.layout.GridPane;

public abstract class Grid {
	Cell[][] myCells;
	int myGridWidth;
	int myGridHeight;
	Group myRoot;
	
	public Grid(Map<String,String> params){
		
	}
	
	protected abstract void step(Double elapsedTime);
	
	private void handleMouseInput(ActionEvent e){
		
	}
	
	private void handleKeyEntry(ActionEvent e){
		
	}
	
	private void initializeCells(){
		
	}
	
	private void createBoard(){
		//make GridPane based on Cell[][] myCells
		//set root
		GridPane gp = new GridPane();
		for (int i = 0; i< myCells.length; i++){
			
		}
	}

	public Cell[][] getMyCells() {
		return myCells;
	}

	public void setMyCells(Cell[][] myCells) {
		this.myCells = myCells;
	}

	public int getGridWidth() {
		return myGridWidth;
	}

	public void setGridWidth(int gridWidth) {
		this.myGridWidth = gridWidth;
	}

	public int getGridHeight() {
		return myGridHeight;
	}

	public void setGridHeight(int gridHeight) {
		this.myGridHeight = gridHeight;
	}

	public Group getRoot() {
		return myRoot;
	}

	public void setRoot(Group root) {
		this.myRoot = root;
	}
	
	
	
	
	
	
	
	
	
}
