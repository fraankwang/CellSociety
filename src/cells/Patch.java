package cells;

import states.State;

public abstract class Patch extends DataCell {
    private Agent myAgent;

    public Patch (State currentState, int row, int col) {
        super(currentState, row, col);
        // TODO Auto-generated constructor stub
    }
    
    public void initializeWithAgent(Agent agent){
        setMyAgent(agent);
    }

    @Override
    public abstract void update ();

    public boolean isOccupied(){
        return myAgent != null;
    }
    
    public Agent getMyAgent () {
        return myAgent;
    }

    public void setMyAgent (Agent myAgent) {
        this.myAgent = myAgent;
    }

}
