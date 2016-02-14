package grids;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import cells.Agent;
import cells.GridCell;
import cells.Patch;
import cells.SugarPatch;
import constants.Location;
import constants.Parameters;


/**
 * Abstract class for a Grid for a simulation that has a concept of Patches and Agents
 *
 */
public abstract class PatchGrid extends Grid {

    private List<Agent> myAgents;
    private List<Agent> myAgentsToRemove;
    private int myNumAgents;

    private List<Location> myRandomLocations;
    
    /**
     * Constructor
     * 
     * @param params XML paramters for initial configuration
     */
    public PatchGrid (Parameters params) {
        super(params);
        myNumAgents = Integer.parseInt(params.getParameter("numAgents"));
        myRandomLocations = createRandomLocationsList();

    }

    @Override
    protected void initializeCells () {
        initializePatches();
        initializeAgents();

    }

    /**
     * Initialize the patches (cells) in the grid
     */
    protected void initializePatches () {
        super.initializeCells();

    }

    @Override
    protected GridCell initializeCell (int row, int column) {
        return initializePatch(row, column);
    }

    /**
     * Abstract method for initializing a patch (cell) in the grid
     * 
     * @param row The row of the patch
     * @param column The column of the patch
     * @return An initialized Patch to put at the given location in the grid
     */
    protected abstract Patch initializePatch (int row, int column);

    /**
     * Initializes the Agents in the grid by randomly selecting x locations,
     * where x = numAgents, and calling initializeAgent for each lcoation
     */
    protected void initializeAgents () {
        myAgents = new ArrayList<Agent>();
        myAgentsToRemove = new ArrayList<Agent>();

        for (int i = 0; i < myNumAgents; i++) {
            Location location = getMyRandomLocations().get(i);
            insertNewAgent(location.getRow(), location.getCol());

        }
    }

    /**
     * Creates random pairings of all possible x coordinates and y coordinates in the grid
     * 
     * @return Lists of <xCoords, yCoords>
     */
    protected List<Location> createRandomLocationsList(){
      
        List<Location> locations = new ArrayList<Location>();
        
        for (int r = 0; r < getRows(); r++) {
            for (int c = 0; c < getColumns(); c++) {
               
                locations.add(new Location(r, c));
            }
        }

        
        Collections.shuffle(locations);
       
        return locations;
    }
    /**
     * Inserts a new agent on the grid by calling initializeAgent, adding it
     * to a patch, and storing it in myAgents
     * 
     * @param row The row to place to newly initialized Agent
     * @param column The column to place to newly initialized Agent
     */
    private void insertNewAgent (int row, int column) {
        Agent agent = initializeAgent(row, column);
        Patch patch = (Patch) getMyCells()[row][column];
        patch.initializeWithAgent(agent);

        myAgents.add(agent);
    }

    /**
     * Abstract method that initializes an Agent at a specific location in the grid
     * 
     * @param row The row of the initialized Agent
     * @param column The column of the initialized Agent
     * @return The Agent to insert in the grid
     */
    protected abstract Agent initializeAgent (int row, int column);

    /**
     * Called every step of the game loop - updates each cell's next state
     * - sets Agent states, sets Patch states, and removes dead agents
     */
    protected void setCellStates () {
        setAgentStates();
        setPatchStates();
        removeAgents();

    }

    /**
     * Determines the next state of each patch by calling setPatchState on each
     */
    protected void setPatchStates () {
        super.setCellStates();

    }

    @Override
    protected void setCellState (GridCell cell) {
        setPatchState((Patch) cell);
    }

    /**
     * Determines the next state of the given patch
     * 
     * @param patch The patch to determine next state
     */
    protected abstract void setPatchState (Patch patch);

    /**
     * Determines the next state of each agent by calling setAgentState on each
     */
    protected void setAgentStates () {
        System.out.println(myAgents.size());
        for (Agent agent : myAgents) {
            setAgentState(agent);
        }
    }

    /**
     * Determines the next state of the given agent
     * 
     * @param agent The agent to determine next state
     */
    protected abstract void setAgentState (Agent agent);

    /**
     * Adds an agent to the collection of stored agents
     * 
     * @param agent
     */
    protected void addAgent (Agent agent) {
        myAgents.add(agent);
    }

    /**
     * Removes all agents in myAgentsToRemove from myAgents
     */
    private void removeAgents () {
        for (Agent agent : myAgentsToRemove) {
            myAgents.remove(agent);
        }
        myAgentsToRemove = new ArrayList<Agent>();
    }

    /**
     * Adds an agent to the list of agentsToBeRemoved that will get removed at
     * the end of each game cycle
     * 
     * Note: Subclasses can override this method to add further necessary
     * logic for removing an agent
     * 
     * @param agent The agent to remove
     */
    protected void addAgentToRemove (Agent agent) {
        myAgentsToRemove.add(agent);
    }

    /**
     * Moves an agent to a new Patch
     * 
     * Note: Subclasses can override this method to add further necessary
     * logic for moving an agent
     * 
     * @param origin The agent to move
     * @param destination The patch where the agent should move
     */
    protected void moveAgent (Agent agent, Patch destination) {
        SugarPatch oldPatch =
                (SugarPatch) getMyCells()[agent.getMyGridLocation().getRow()][agent
                        .getMyGridLocation().getCol()];
        oldPatch.setMyAgent(null);

        agent.setMyGridLocation(new Location(destination.getMyGridLocation().getRow(),
                                             destination.getMyGridLocation().getCol()));

    }

    @Override
    public void toggleStateAndUpdateUI (GridCell cell) {
        super.toggleStateAndUpdateUI(cell);
        removeAgents();
    }

    @Override
    protected abstract void toggleState (GridCell cell);

    // =========================================================================
    // Getters and Setters
    // =========================================================================
    public int getMyNumAgents () {
        return myNumAgents;
    }

    protected List<Location> getMyRandomLocations () {
        return myRandomLocations;
    }

    protected void shuffleRandomLocations() {
        Collections.shuffle(myRandomLocations);
    }
}
