package cellsociety_team03;
import javafx.scene.paint.Color;

public enum State {
    EMPTY	(Color.WHITE), 
    DEAD	(Color.WHITE), 
    ALIVE	(Color.BLACK), 
    BURNING     (Color.ORANGE), 
    TREE 	(Color.GREEN), 
    BURNED	(Color.BROWN),
    RED		(Color.RED),
    BLUE	(Color.BLUE);
    
    Color myColor;
    State(Color color){
    	myColor = color;
    }
    
    public Color getColor(){
    	return myColor;
    }
}
