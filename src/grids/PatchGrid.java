package grids;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import cells.Agent;
import cells.GridCell;
import cells.Patch;
import constants.Parameters;


public abstract class PatchGrid extends Grid {

    private List<Agent> myAgents;
    private List<Agent> myAgentsToRemove;

    private int myNumAgents;

    public PatchGrid (Parameters params) {
        super(params);
        myNumAgents = Integer.parseInt(params.getParameter("numAgents"));

    }
    
    

    @Override
    protected void initializeCells () {
        initializePatches();
        initializeAgents();

    }

    protected void initializePatches () {
        super.initializeCells();

    }

    @Override
    protected GridCell initializeCell (int row, int column) {
        return initializePatch(row, column);
    }

    protected abstract Patch initializePatch (int row, int column);

    protected void initializeAgents () {
        myAgents = new ArrayList<Agent>();
        myAgentsToRemove = new ArrayList<Agent>();

        List<Integer> xCoords = new ArrayList<Integer>();
        List<Integer> yCoords = new ArrayList<Integer>();
        for (int i = 0; i < getRows(); i++) {
            xCoords.add(i);
        }
        for (int i = 0; i < getColumns(); i++) {
            yCoords.add(i);
        }

        Collections.shuffle(xCoords);
        Collections.shuffle(yCoords);

        Random r = new Random();
        for (int i = 0; i < myNumAgents; i++) {
            int randomRow = r.nextInt(getRows());
            int randomCol = r.nextInt(getColumns());
            Agent cell = initializeAgent(xCoords.get(randomRow), yCoords.get(randomCol));
            myAgents.add(cell);
        }
    }

    protected abstract Agent initializeAgent (int row, int column);

    /**
     * Updates each cell's next state by calling setCellState for each cell
     */
    protected void setCellStates () {
        setAgentStates();
        setPatchStates();
        removeAgents();
        

    }

    protected void setPatchStates () {
        super.setCellStates();

    }

    @Override
    protected void setCellState (GridCell cell) {
        setPatchState((Patch) cell);
    }

    protected abstract void setPatchState (Patch patch);

    protected void setAgentStates () {
        for (Agent agent : myAgents) {
            setAgentState(agent);
        }
    }

    protected abstract void setAgentState (Agent agent);
    
    protected void addAgent (Agent agent) {
        myAgents.add(agent);
    }
    
    private void removeAgents () {
        for(Agent agent : myAgentsToRemove){
            removeAgent(agent);
        }
        myAgentsToRemove = new ArrayList<Agent>();
    }
    
    private void removeAgent (Agent agent) {
        myAgents.remove(agent);
    }
    
    protected void addAgentToRemove(Agent agent){
        myAgentsToRemove.add(agent);
    }

    protected abstract void moveAgent (Agent origin, Patch destination);

    @Override
    protected abstract void toggleState (GridCell cell);

}
