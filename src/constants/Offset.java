/**
 * Authors: Frank Wang, Jeremy Schreck, Madhav Kumar
 */

package constants;

/**
 * Convenience class to represent an offset of x rows and y columns in a grid
 *
 */
public class Offset {
    private int myRow;
    private int myCol;

    /**
     * Constructor
     *
     * @param row The row offset
     * @param col The column offset
     */
    public Offset (int row, int col) {
        myRow = row;
        myCol = col;
    }

    public int getRow () {
        return myRow;
    }

    public void setRow (int row) {
        myRow = row;
    }

    public int getCol () {
        return myCol;
    }

    public void setCol (int col) {
        myCol = col;
    }

}
