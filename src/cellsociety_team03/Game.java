/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cellsociety_team03;

import java.io.File;
import java.util.Map;

import javafx.animation.Timeline;
import javafx.scene.Scene;

public class Game {
	private String gameType;
	private Grid myGrid;
	private Map<String,String> myParameters;
	private Scene primaryScene;
	private Timeline gameLoop;
	
	public Game(String type, Map<String,String> params){
		gameType = type;
		myParameters = params;
	}
	
	private void parseXML(File file){
		
	}
	
	private void initializeGrid(){
		
	}
	
	private void initializeGameLoop(){
		
	}
	
	private void beginGame(){
	
	}
	private void endGame(){
		
	}
	
}
