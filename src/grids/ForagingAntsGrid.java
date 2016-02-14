/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package grids;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import cells.Agent;
import cells.Ant;
import cells.AntFoodSource;
import cells.AntNest;
import cells.AntPatch;
import cells.GridCell;
import cells.Patch;
import constants.Location;
import constants.NeighborOffset;
import constants.Offset;
import constants.Parameters;
import states.ForagingAntsState;


public class ForagingAntsGrid extends PatchGrid {
    private int myMaxHomePheromones;
    private int myMaxFoodPheromones;
    private int myK;
    private int myN;
    private int myFoodThresholdHigh;
    private int myFoodThresholdLow;
    private int myHomeThresholdHigh;
    private int myHomeThresholdLow;
    private int myNumFoodSources;
    private int myNumObstacles;
    private int myMaxAnts;

    private Location myNestLocation;
    private Set<Location> myFoodSources;
    private Set<Location> myObstacles;
    private Set<Location> myLocationsWithTooManyAnts;

    public ForagingAntsGrid (Parameters params) {
        super(params);
        myMaxHomePheromones = Integer.parseInt(params.getParameter("maxHomePheromones"));
        myMaxFoodPheromones = Integer.parseInt(params.getParameter("maxFoodPheromones"));
        myK = Integer.parseInt(params.getParameter("K"));
        myN = Integer.parseInt(params.getParameter("N"));
        myFoodThresholdHigh = Integer.parseInt(params.getParameter("foodThresholdHigh"));
        myFoodThresholdLow = Integer.parseInt(params.getParameter("foodThresholdLow"));
        myHomeThresholdHigh = Integer.parseInt(params.getParameter("homeThresholdHigh"));
        myHomeThresholdLow = Integer.parseInt(params.getParameter("homeThresholdLow"));
        myNumFoodSources = Integer.parseInt(params.getParameter("numFoodSources"));
        myNumObstacles = Integer.parseInt(params.getParameter("numObstacles"));
        myMaxAnts = Integer.parseInt(params.getParameter("maxAnts"));

        initializeCells();
    }

    /**
     * Initialize the patches (cells) in the grid
     */
    @Override
    protected void initializePatches () {
        pickRandomNestFoodSourceObstacleLocations();
        super.initializePatches();

    }

    /**
     * Initializes myNestLocation and myFoodSources with random locations on grid
     */
    private void pickRandomNestFoodSourceObstacleLocations () {
        myNestLocation = getMyRandomLocations().get(0);
        myFoodSources = new HashSet<Location>();
        myObstacles = new HashSet<Location>();

        for (int i = 1; i < myNumFoodSources + 1; i++) {
            Location foodSource = getMyRandomLocations().get(i);
            myFoodSources.add(foodSource);
        }

        /*
         * for (int i = myNumFoodSources + 1; i < myNumObstacles + myNumFoodSources; i++) {
         * Location obstacle = getMyRandomLocations().get(i + 1);
         * myObstacles.add(obstacle);
         * }
         */

    }

    @Override
    protected Patch initializePatch (int row, int column) {
        Location location = new Location(row, column);
        Patch patch = null;

        if (location.equals(myNestLocation)) {
            patch = initializeNest(row, column);
        }
        else if (myFoodSources.contains(location)) {
            patch = initializeFoodSource(row, column);
        }
        else if (myObstacles.contains(location)) {
            // patch = initializeObstacle(row, column);
        }
        else {
            patch =
                    new AntPatch(row, column, myMaxAnts, myFoodThresholdHigh, myFoodThresholdLow,
                                 myHomeThresholdHigh, myHomeThresholdLow);

        }

        return patch;
    }

    /**
     * Initialize a nest at the given row and column
     *
     * @param row
     * @param column
     * @return The nest
     */
    private Patch initializeNest (int row, int column) {
        AntNest patch = new AntNest(ForagingAntsState.NEST, row, column);
        return patch;
    }

    /**
     * Initialize a food source at the given row and column
     *
     * @param row
     * @param column
     * @return The nest
     */
    private Patch initializeFoodSource (int row, int column) {
        AntFoodSource patch = new AntFoodSource(ForagingAntsState.FOOD_SOURCE, row, column);
        return patch;
    }

    @Override
    protected Agent initializeAgent (int row, int column) {
        return new Ant(ForagingAntsState.ANT, row, column);
    }

    @Override
    protected void setPatchState (Patch patch) {
        patch.update();

    }

    @Override
    protected void setAgentState (Agent agent) {
        // TODO Auto-generated method stub
        Ant ant = (Ant) agent;
        if (ant.hasFoodItem()) {
            antReturnToNest(ant);
        }
        else {
            antFindFoodSource(ant);
        }
    }

    private void antReturnToNest (Ant ant) {
        if (ant.isLocatedAtFoodSource()) {
            ant.setOrientation(neighborLocationWithMaxHomePheromones(ant));
        }

        NeighborOffset nOffset = forwardLocationWithMaxHomePheromones(ant);
        if (nOffset == null) {
            nOffset = neighborLocationWithMaxHomePheromones(ant);
        }
        if (nOffset != null) {
            dropFoodPheromones(ant);
            ant.setOrientation(nOffset);
            // move(ant);
            if (ant.isLocatedAtNest()) {
                dropFoodItem(ant);
            }
        }
    }

    private void antFindFoodSource (Ant ant) {
        if (ant.isLocatedAtNest()) {
            ant.setOrientation(neighborLocationWithMaxHomePheromones(ant));
        }

        Set<Location> forwardLocations = new HashSet<Location>();
        Location nextLocation = selectLocation(forwardLocations);
        if (nextLocation == null) {
            Set<Location> neighborLocations = new HashSet<Location>();
            nextLocation = selectLocation(neighborLocations);
        }
        if (nextLocation != null) {
            dropHomePheromones(ant);
            // ant.setOrientation(nOffset);
            // move(ant);
            if (ant.isLocatedAtFoodSource()) {
                pickupFoodItem(ant);
            }
        }
    }

    private NeighborOffset neighborLocationWithMaxHomePheromones (Ant ant) {
        return null;
    }

    private NeighborOffset forwardLocationWithMaxHomePheromones (Ant ant) {
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

    private Location selectLocation (Set<Location> locations) {
        Set<Location> locationsToRemove = new HashSet<Location>();
        for (Location location : locations) {
            if (myObstacles.contains(location)) {
                locationsToRemove.add(location);
            }
            else {
                AntPatch patch = (AntPatch) getMyCells()[location.getRow()][location.getCol()];
                if (patch.hasTooManyAnts()) {
                    locationsToRemove.add(location);
                }
            }

        }
        for (Location location : locationsToRemove) {
            locations.remove(location);
        }

        if (locations == null || locations.size() == 0) {
            return null;
        }
        else {
            // Select location from locations
            return selectLocationUsingProbability(locations);
        }

    }

    private Location selectLocationUsingProbability (Set<Location> locations) {
        // for now just pick one with max food pheromones
        // should change to (K + foodPheromonesAtLocation)^N

        int maxFoodPheromonesAtLocation = 0;
        Location chosenLocation = null;
        for (Location location : locations) {

            AntPatch patch = (AntPatch) getMyCells()[location.getRow()][location.getCol()];
            patch.getMyNumFoodPheromones();

            if (patch.getMyNumFoodPheromones() > maxFoodPheromonesAtLocation) {
                maxFoodPheromonesAtLocation = patch.getMyNumFoodPheromones();
                chosenLocation = patch.getMyGridLocation();
            }
        }
        return chosenLocation;
    }

    private Set<Offset> forwardLocationOffsets (NeighborOffset orientation) {
        return null;
    }

    private void dropHomePheromones (Ant ant) {

    }

    private void dropFoodPheromones (Ant ant) {

    }

    private void dropFoodItem (Ant ant) {
        ant.setHasFoodItem(false);
        // Drop food item at ant location (nest)
    }

    private void pickupFoodItem (Ant ant) {
        ant.setHasFoodItem(true);
        // Pick up food item from ant location (food source)
    }

    @Override
    public void updateParams (Map<String, Double> map) {
        // TODO Auto-generated method stub

    }

	@Override
	protected void updateUIView() {
		// TODO Auto-generated method stub
		
	}

}
