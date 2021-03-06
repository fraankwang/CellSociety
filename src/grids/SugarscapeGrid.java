package grids;

import java.util.Map;
import java.util.Random;
import cells.Agent;
import cells.GridCell;
import cells.Patch;
import cells.SugarAgent;
import cells.SugarPatch;
import constants.Location;
import constants.Offset;
import constants.Parameters;
import javafx.scene.chart.XYChart;
import states.SugarscapeState;
import uiviews.SugarscapeUIView;


/**
 * Grid subclass for Sugarscape simulation
 *
 */
public class SugarscapeGrid extends PatchGrid {
    private int myAgentSugarMin;
    private int myAgentSugarMax;
    private int myAgentMetabolismMin;
    private int myAgentMetabolismMax;
    private int myAgentVisionMin;
    private int myAgentVisionMax;
    private int mySugarGrowBackRate;
    private int mySugarGrowBackInterval;
    private int mySugarThresholdLow;
    private int mySugarThresholdMedium;
    private int mySugarThresholdHigh;
    private int mySugarThresholdStrong;
    private int myPatchSugarMax;

	private int agentCount = 0;

    /**
     * Constructor - sets up parameters and initializes cells
     *
     * @param params The XML parameters
     */
    public SugarscapeGrid (Parameters params) {
        super(params);
        myAgentSugarMin = Integer.parseInt(params.getParameter("agentSugarMin"));
        myAgentSugarMax = Integer.parseInt(params.getParameter("agentSugarMax"));
        myAgentMetabolismMin = Integer.parseInt(params.getParameter("agentMetabolismMin"));
        myAgentMetabolismMax = Integer.parseInt(params.getParameter("agentMetabolismMax"));
        myAgentVisionMin = Integer.parseInt(params.getParameter("agentVisionMin"));
        myAgentVisionMax = Integer.parseInt(params.getParameter("agentVisionMax"));
        mySugarGrowBackRate = Integer.parseInt(params.getParameter("sugarGrowBackRate"));
        mySugarGrowBackInterval = Integer.parseInt(params.getParameter("sugarGrowBackInterval"));
        mySugarThresholdLow = Integer.parseInt(params.getParameter("sugarThresholdLow"));
        mySugarThresholdMedium = Integer.parseInt(params.getParameter("sugarThresholdMedium"));
        mySugarThresholdHigh = Integer.parseInt(params.getParameter("sugarThresholdHigh"));
        mySugarThresholdStrong = Integer.parseInt(params.getParameter("sugarThresholdStrong"));
        myPatchSugarMax = Integer.parseInt(params.getParameter("patchSugarMax"));

        initializeCells();

    }

    @Override
    protected Agent initializeAgent (int row, int column) {

        Random r = new Random();
        int sugar = r.nextInt(myAgentSugarMax - myAgentSugarMin + 1) + myAgentSugarMin;
        int sugarMetabolism =
                r.nextInt(myAgentMetabolismMax - myAgentMetabolismMin + 1) + myAgentMetabolismMin;
        int vision = r.nextInt(myAgentVisionMax - myAgentVisionMin + 1) + myAgentVisionMin;

        Agent agent =
                new SugarAgent(SugarscapeState.AGENT, row, column, sugar, sugarMetabolism, vision);
        agentCount++;		
        return agent;
    }

    @Override
    protected Patch initializePatch (int row, int column) {

        Random r = new Random();
        int sugar = r.nextInt(myPatchSugarMax - 0 + 1) + 0;

        return new SugarPatch(row, column, mySugarGrowBackRate, sugar, myPatchSugarMax,
                              mySugarGrowBackInterval, mySugarThresholdLow, mySugarThresholdMedium,
                              mySugarThresholdHigh, mySugarThresholdStrong);
    }

    @Override
    protected void toggleState (GridCell cell) {
        boolean currentState = false;
        for (SugarscapeState state : SugarscapeState.values()) {

            if (currentState == true) {
                SugarscapeState nextState = state;
                toggleState((SugarPatch) cell, nextState);
                return;

            }
            if (state == cell.getMyCurrentState()) {
                currentState = true;
                if (state == SugarscapeState.STRONG) {
                    toggleState((SugarPatch) cell, SugarscapeState.AGENT);
                }
            }
        }

    }

    /**
     * Helper method used in toggleState(cell) to copy persistent data in addition
     * to state
     *
     * @param patch The patch to toggle
     * @param nextState The state to toggle to
     */
    private void toggleState (SugarPatch patch, SugarscapeState nextState) {

        int newSugarCount = patch.sugarCountFromState(nextState);
        if (patch.getMyCurrentState() == SugarscapeState.AGENT) {
            Agent agent = patch.getMyAgent();
            addAgentToRemove(agent);
            patch.setMyAgent(null);
            agentCount--;
        }
        else if (nextState == SugarscapeState.AGENT) {
            Location location = patch.getMyGridLocation();
            Agent agent = initializeAgent(location.getRow(), location.getCol());
            addAgent(agent);
            patch.setMyAgent(agent);
            newSugarCount = patch.getMySugar();
        }

        patch.setMySugar(newSugarCount);
        patch.setMyCurrentState(nextState);

    }

    @Override
    protected void setAgentState (Agent agent) {

        SugarAgent sugarAgent = (SugarAgent) agent;
        // Set to -1 to guarantee that a patchToOccupy gets initialized (unless vision is 0)
        int maxSugarValue = -1;
        SugarPatch patchToOccupy = null;
        for (Offset baseOffset : getMyNeighborOffsets()) {
            for (int i = 1; i <= sugarAgent.getMyVision(); i++) {
                // Only cardinal directions for now
                // Expand closest patches first so choose closest on ties
                Offset offset = new Offset(baseOffset.getRow() * i, baseOffset.getCol() * i);
                Location location =
                        new Location(sugarAgent.getMyGridLocation().getRow() + offset.getRow(),
                                     sugarAgent.getMyGridLocation().getCol() + offset.getCol());
                if (cellInBounds(location)) {
                    SugarPatch patch =
                            (SugarPatch) getMyCells()[location.getRow()][location.getCol()];
                    if (!patch.isOccupied() && patch.getMySugar() > maxSugarValue) {
                        maxSugarValue = patch.getMySugar();
                        patchToOccupy = patch;
                    }
                }
            }
        }

        if (patchToOccupy != null) {
            moveAgent(agent, patchToOccupy);

        }
        else {
            System.out.println("Error");

        }

    }

    @Override
    protected void moveAgent (Agent origin, Patch destination) {
        super.moveAgent(origin, destination);

        SugarAgent agent = (SugarAgent) origin;
        SugarPatch newPatch = (SugarPatch) destination;

        agent.eat(newPatch.getMySugar());

        newPatch.didGetEaten(agent);

        if (agent.getMySugar() <= 0) {
        	agentCount--;
            addAgentToRemove(agent);
        }

    }

    @Override
    protected void addAgentToRemove (Agent agent) {
        SugarPatch patch =
                (SugarPatch) getMyCells()[agent.getMyGridLocation().getRow()][agent
                        .getMyGridLocation().getCol()];
        patch.setMyAgent(null);
        super.addAgentToRemove(agent);
    }

    @Override
    protected void setPatchState (Patch patch) {
        patch.update();

    }

    @Override
    public Map<String, String> getMyGameState () {
        Map<String, String> currentGameState = super.getMyGameState();

        currentGameState.put("numAgents", Integer.toString(getMyNumAgents()));
        currentGameState.put("agentSugarMin", Integer.toString(getMyAgentSugarMin()));
        currentGameState.put("agentSugarMax", Integer.toString(getMyAgentSugarMax()));
        currentGameState.put("agentMetabolismMin", Integer.toString(getMyAgentMetabolismMin()));
        currentGameState.put("agentMetabolismMax", Integer.toString(getMyAgentMetabolismMax()));
        currentGameState.put("agentVisionMin", Integer.toString(getMyAgentVisionMin()));
        currentGameState.put("agentVisionMax", Integer.toString(getMyAgentVisionMax()));
        currentGameState.put("sugarGrowBackRate", Integer.toString(getMySugarGrowBackRate()));
        currentGameState.put("sugarGrowBackInterval",
                             Integer.toString(getMySugarGrowBackInterval()));

        return currentGameState;

    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================
    public int getMyAgentSugarMin () {
        return myAgentSugarMin;
    }

    public void setMyAgentSugarMin (int myAgentSugarMin) {
        this.myAgentSugarMin = myAgentSugarMin;
    }

    public int getMyAgentSugarMax () {
        return myAgentSugarMax;
    }

    public void setMyAgentSugarMax (int myAgentSugarMax) {
        this.myAgentSugarMax = myAgentSugarMax;
    }

    public int getMyAgentMetabolismMin () {
        return myAgentMetabolismMin;
    }

    public void setMyAgentMetabolismMin (int myAgentMetabolismMin) {
        this.myAgentMetabolismMin = myAgentMetabolismMin;
    }

    public int getMyAgentMetabolismMax () {
        return myAgentMetabolismMax;
    }

    public void setMyAgentMetabolismMax (int myAgentMetabolismMax) {
        this.myAgentMetabolismMax = myAgentMetabolismMax;
    }

    public int getMyAgentVisionMin () {
        return myAgentVisionMin;
    }

    public void setMyAgentVisionMin (int myAgentVisionMin) {
        this.myAgentVisionMin = myAgentVisionMin;
    }

    public int getMyAgentVisionMax () {
        return myAgentVisionMax;
    }

    public void setMyAgentVisionMax (int myAgentVisionMax) {
        this.myAgentVisionMax = myAgentVisionMax;
    }

    public int getMySugarGrowBackRate () {
        return mySugarGrowBackRate;
    }

    public void setMySugarGrowBackRate (int mySugarGrowBackRate) {
        this.mySugarGrowBackRate = mySugarGrowBackRate;
    }

    public int getMySugarGrowBackInterval () {
        return mySugarGrowBackInterval;
    }

    public void setMySugarGrowBackInterval (int mySugarGrowBackInterval) {
        this.mySugarGrowBackInterval = mySugarGrowBackInterval;
    }

	@Override
	public void updateParams(Map<String, Double> map) {
		myAgentSugarMin = map.get("agentSugarMin").intValue();
		myAgentSugarMax = map.get("agentSugarMax").intValue();
		myAgentMetabolismMin = map.get("agentMetabolismMin").intValue();
		myAgentMetabolismMax = map.get("agentMetabolismMax").intValue();
		myAgentVisionMin = map.get("agentVisionMin").intValue();
		myAgentVisionMax = map.get("agentVisionMax").intValue();
		mySugarGrowBackRate = map.get("sugarGrowBackRate").intValue();
		mySugarGrowBackInterval =  map.get("sugarGrowBackInterval").intValue();

	}

	@Override
	protected void updateUIView() {
		SugarscapeUIView SugarscapeUIView = (SugarscapeUIView) getMyUIView();
		XYChart.Series<Number, Number> agentSeries = SugarscapeUIView.getAgentPopulationSeries();
		XYChart.Series<Number, Number> sugarSeries = SugarscapeUIView.getSugarCountSeries();
		    
		SugarscapeUIView.addDataPoint(agentSeries, getElapsedTime(), agentCount());
		SugarscapeUIView.addDataPoint(sugarSeries, getElapsedTime(), sugarCount());
		
	}
	
	/**
	 * Number of Agents
	 * @return
	 */
	private int agentCount () {
		return agentCount;
		
	}
	
	/**
	 * Total Sugar Count
	 * @return
	 */
	private double sugarCount () {
		long count = 0;
		
		GridCell[][] myCells = getMyCells();
		for (int row = 0; row < getRows(); row++) {
			for (int col = 0; col < getColumns(); col++) {
				SugarscapeState state = (SugarscapeState) myCells[row][col].getMyCurrentState();
				count += state.getStateValue();
			}
		}
		return count;
		
	}
   

}
