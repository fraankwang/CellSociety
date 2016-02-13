package grids;

import java.util.ArrayList;
import java.util.List;
import cells.Agent;
import cells.GridCell;
import cells.Patch;
import constants.Parameters;
import states.SugarscapeState;

public abstract class PatchGrid extends Grid {

    private List<Agent> myInitialAgentData;
    private List<Agent> myAgents;
    
    public PatchGrid (Parameters params) {
        super(params);
        
        
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
        return initializePatch (row, column);
    }

    protected abstract Patch initializePatch(int row, int column);
    
   
    protected void initializeAgents() {
        myAgents = new ArrayList<Agent>();
        for (int row = 0; row < getRows(); row++) {
            for (int col = 0; col < getColumns(); col++) {
                Agent cell = initializeAgent(row, col);
                myAgents.add(cell);
            }
        }
    }
    
    protected abstract Agent initializeAgent(int row, int column);

    
    /**
     * Updates each cell's next state by calling setCellState for each cell
     */
    protected void setCellStates () {
        setAgentStates();
        setPatchStates();

    }
    protected void setPatchStates() {
        super.setCellStates();

    }

    @Override
    protected void setCellState (GridCell cell) {
        setPatchState((Patch) cell);
    }
    
    protected abstract void setPatchState(Patch patch);
   
    protected void setAgentStates() {
        for (Agent agent : myAgents){
            setAgentState(agent);
        }
    }
    
    protected abstract void setAgentState(Agent agent);
    
    protected void removeAgent (Agent agent){
        myAgents.remove(agent);
    }

    protected abstract void moveAgent (Agent origin, Patch destination);

    
  
    @Override
    protected void toggleState (GridCell cell) {
        // TODO Auto-generated method stub

    }

   

}
