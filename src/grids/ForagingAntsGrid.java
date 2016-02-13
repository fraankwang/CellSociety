package grids;

import java.util.HashSet;
import java.util.Set;
import cells.Agent;
import cells.Ant;
import cells.AntPatch;
import cells.GridCell;
import cells.Patch;
import constants.Location;
import constants.NeighborOffset;
import constants.Offset;
import constants.Parameters;

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
        Ant ant = (Ant) agent;
        if(ant.hasFoodItem()){
            antReturnToNest(ant);
        }else{
            antFindFoodSource(ant);
        }
    }

    private void antReturnToNest(Ant ant){
        if (ant.isLocatedAtFoodSource()){
            ant.setOrientation(neighborLocationWithMaxHomePheromones(ant));
        }
        
        NeighborOffset nOffset = forwardLocationWithMaxHomePheromones(ant);
        if(nOffset == null){
            nOffset = neighborLocationWithMaxHomePheromones(ant);
        }
        if(nOffset != null){
            dropFoodPheromones(ant);
            ant.setOrientation(nOffset);
            //move(ant);
            if (ant.isLocatedAtNest()){
                dropFoodItem(ant);
            }
        }
    }
    
    private void antFindFoodSource(Ant ant){
        if (ant.isLocatedAtNest()){
            ant.setOrientation(neighborLocationWithMaxHomePheromones(ant));
        }
        
        Set<Location> forwardLocations = new HashSet<Location>();
        Location nextLocation  = selectLocation(forwardLocations);
        if(nextLocation == null){
            Set<Location> neighborLocations = new HashSet<Location>();
            nextLocation = selectLocation(neighborLocations);
        }
        if(nextLocation != null){
            dropHomePheromones(ant);
            //ant.setOrientation(nOffset);
            //move(ant);
            if (ant.isLocatedAtFoodSource()){
                pickupFoodItem(ant);
            }
        }
    }
    
    private NeighborOffset neighborLocationWithMaxHomePheromones(Ant ant){
        return null;
    }
    
    private NeighborOffset forwardLocationWithMaxHomePheromones(Ant ant){
        return null;
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
        Set<Location> locationsToRemove = new HashSet<Location>();
        for(Location location : locations){
            if (myObstacles.contains(location)){
                locationsToRemove.add(location);
            }else{
                AntPatch patch = (AntPatch) getMyCells()[location.getRow()][location.getCol()];
                if(patch.hasTooManyAnts()){
                    locationsToRemove.add(location);
                }
            }
            
         
        }
        for(Location location : locationsToRemove){
            locations.remove(location);
        }
        
        if(locations == null || locations.size() == 0){
            return null;
        }else{
            //Select location from locations
            return selectLocationUsingProbability(locations);
        }
        
    }
    
    
    private Location selectLocationUsingProbability(Set<Location> locations){
        // for now just pick one with max food pheromones
        // should change to (K + foodPheromonesAtLocation)^N
        
        int maxFoodPheromonesAtLocation = 0;
        Location chosenLocation = null;
        for(Location location : locations){
            AntPatch patch = (AntPatch) getMyCells()[location.getRow()][location.getCol()];
            if(patch.getMyNumFoodPheromones() > maxFoodPheromonesAtLocation){
                maxFoodPheromonesAtLocation = patch.getMyNumFoodPheromones();
                chosenLocation = patch.getMyGridLocation();
            }
        }
        return chosenLocation;
    }
    private Set<Offset> forwardLocationOffsets(NeighborOffset orientation){
        return null;
    }
    
    private void dropHomePheromones (Ant ant) {
        
    }
    
    private void dropFoodPheromones (Ant ant){
        
    }
    
    private void dropFoodItem(Ant ant){
        ant.setHasFoodItem(false);
        // Drop food item at ant location (nest)
    }
    private void pickupFoodItem(Ant ant){
        ant.setHasFoodItem(true);
        // Pick up food item from ant location (food source)
    }
    
    
    
    

}
