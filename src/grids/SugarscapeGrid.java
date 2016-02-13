package grids;

import cells.Agent;
import cells.GridCell;
import cells.Patch;
import cells.SugarAgent;
import cells.SugarPatch;
import constants.Location;
import constants.Offset;
import constants.Parameters;
import states.SugarscapeState;

public class SugarscapeGrid extends PatchGrid {
   
    public SugarscapeGrid (Parameters params) {
        super(params);
        // TODO Auto-generated constructor stub
    }

    private int sugarGrowBackInterval;
    private int stepCount;
    
   

    @Override
    public void step () {
        super.step();
        stepCount++;
    }

   
    @Override
    protected Agent initializeAgent (int row, int column) {
        // TODO Auto-generated method stub
        int sugar = 1;
        int sugarMetabolism = 5;
        int vision = 2;
        
        Agent agent = new SugarAgent(SugarscapeState.AGENT, row, column, sugar, sugarMetabolism, vision);
        return agent;
    }

    @Override
    protected Patch initializePatch (int row, int column) {
        // TODO Auto-generated method stub
        
        return null;
    }
    
    @Override
    protected void toggleState (GridCell cell) {
        // TODO Auto-generated method stub
        
    }



    @Override
    protected void setAgentState (Agent agent) {
        SugarAgent sugarAgent = (SugarAgent) agent;
        // Set to -1 to guarantee that a patchToOccupy gets initialized (unless vision is 0)
        int maxSugarValue = -1;
        SugarPatch patchToOccupy = null;
        for (Offset baseOffset : getMyNeighborOffsets()){
            for (int i = 1; i<=sugarAgent.getMyVision(); i++){
                // Only cardinal directions for now
                // Expand closest patches first so choose closest on ties
                Offset offset = new Offset(baseOffset.getRow()*i, baseOffset.getCol() * i);
                Location location = new Location(sugarAgent.getMyGridLocation().getRow() + offset.getRow(), sugarAgent.getMyGridLocation().getCol() + offset.getCol());
                SugarPatch patch = (SugarPatch) getMyCells()[location.getRow()][location.getCol()];
                if (!patch.isOccupied() && patch.getMySugar() > maxSugarValue){
                   maxSugarValue = patch.getMySugar();
                   patchToOccupy = patch;
                }
            }
        }
        
        moveAgent(agent, patchToOccupy);
        
    }

    
    protected void moveAgent (Agent origin, Patch destination){
        SugarPatch oldPatch = (SugarPatch) getMyCells()[origin.getMyGridLocation().getRow()][origin.getMyGridLocation().getCol()];
        oldPatch.setOccupied(false);
        
        SugarAgent agent = (SugarAgent) origin;
        SugarPatch newPatch = (SugarPatch) destination;
        newPatch.didGetEaten();

        agent.addSugar(newPatch.getMySugar());
        
        if(agent.getMySugar() <=0) {
            removeAgent(agent);
        }
        
    }
    
    protected void removeAgent (Agent agent){
        SugarPatch patch = (SugarPatch) getMyCells()[agent.getMyGridLocation().getRow()][agent.getMyGridLocation().getCol()];
        patch.setOccupied(false);
        super.removeAgent(agent);
    }



    @Override
    protected void setPatchState (Patch patch) {
        if (stepCount >= sugarGrowBackInterval){
            stepCount = 0;
            patch.update();
        }
        
    }
}
