/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package constants;

/**
 * Convenience class for "location" of a cell with respect to its grid
 *
 */
public class Location {
    private int myRow;
    private int myCol;

    /**
     * Constructor
     *
     * @param row The row location
     * @param col The column location
     */
    public Location (int row, int col) {
        myRow = row;
        myCol = col;

    }

    public int getRow () {
        return myRow;
    }

    public void setRow (int r) {
        myRow = r;
    }

    public int getCol () {
        return myCol;
    }

    public void setCol (int c) {
        myCol = c;
    }

}
