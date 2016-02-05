package constants;
import javafx.scene.paint.Color;

public enum State {
    EMPTY	(Color.WHITE), 
    DEAD	(Color.WHITE), 
    ALIVE	(Color.BLACK), 
    BURNING     (Color.ORANGE), 
    TREE 	(Color.GREEN), 
    BURNED	(Color.BROWN),
    RED		(Color.RED),
    BLUE	(Color.BLUE),
    SHARK	(Color.GRAY),
	FISH	(Color.YELLOW);
	
    Color myColor;
    State(Color color){
    	myColor = color;
    }
    
    public Color getColor(){
    	return myColor;
    }
}