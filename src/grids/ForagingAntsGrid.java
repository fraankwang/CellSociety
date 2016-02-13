package grids;

import java.util.Set;
import cells.Agent;
import cells.GridCell;
import cells.Patch;
import constants.Location;
import constants.NeighborOffset;
import constants.Offset;
import constants.Parameters;
import javafx.scene.layout.VBox;

public class ForagingAntsGrid extends PatchGrid {
    private int myMaxHomePheromones;
    private int myK;
    private int myN;
    private int my2;
    
    private Location myNestLocation;
    private Set<Location> myFoodSources;
    private Set<Location> myObstacles;
    private Set<Location> myLocationsWithTooManyAnds;
    

    public ForagingAntsGrid (Parameters params) {
        super(params);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected Patch initializePatch (int row, int column) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected Agent initializeAgent (int row, int column) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void setPatchState (Patch patch) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void setAgentState (Agent agent) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void moveAgent (Agent origin, Patch destination) {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void toggleState (GridCell cell) {
        // TODO Auto-generated method stub
        
    }
    
    private Location selectLocation(Set<Location> locations){
        return null;
    }
    
    private Set<Offset> forwardLocationOffsets(NeighborOffset orientation){
        return null;
    }
    
    private void dropHomePheromones (Location location){
        
    }
    
    private void dropFoodPheromones (Location location){
        
    }
    
    private void dropFoodItem(Location location){
        
    }

	@Override
	public VBox createParameterButtons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateParameters(Parameters params) {
		// TODO Auto-generated method stub
		
	}
    
    
    
    

}
