/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package constants;

import java.awt.Dimension;
import java.util.Map;


public class Parameters {
    private Map<String, String> myParameters;
    int[][] initialStates;

    public Parameters (Map<String, String> params, int[][] states) {
        myParameters = params;
        initialStates = states;
    }

    public String getGameType () {
        return myParameters.get("gameType");
    }

    public Double getDelay () {
        return Double.parseDouble(myParameters.get("delay"));
    }

    public String getParameter (String param) {
        return myParameters.get(param);
    }

    public Dimension getDimension () {
        return new Dimension(Integer.parseInt(myParameters.get("width")),
                             Integer.parseInt(myParameters.get("height")));
    }

    public int getRows () {
        return Integer.parseInt(myParameters.get("rows"));
    }

    public int getColumns () {
        return Integer.parseInt(myParameters.get("columns"));
    }

    public int getCellSize () {
        return Integer.parseInt(myParameters.get("cellSize"));
    }

    public void setCellSize (int cellSize) {
        myParameters.replace("cellSize", Integer.toString(cellSize));
    }

    public int[][] getInitialStates () {
        return initialStates;
    }

}
