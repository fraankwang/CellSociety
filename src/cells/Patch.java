/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package cells;

import states.State;


/**
 * Abstract class for a Patch cell
 *
 */
public abstract class Patch extends DataCell {
    private Agent myAgent;

    public Patch (State currentState, int row, int col) {
        super(currentState, row, col);
    }

    @Override
    public abstract void update ();

    /**
     * Initializes the Patch to contain an agent
     *
     * @param agent
     */
    public void initializeWithAgent (Agent agent) {
        setMyAgent(agent);
    }

    /**
     * Returns true if the Patch cell contains an Agent
     *
     * @return Boolean
     */
    public boolean isOccupied () {
        return myAgent != null;
    }

    // =========================================================================
    // Getters and Setters
    // =========================================================================
    public Agent getMyAgent () {
        return myAgent;
    }

    public void setMyAgent (Agent myAgent) {
        this.myAgent = myAgent;
    }

}
