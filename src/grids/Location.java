package grids;

/**
 * Convenience class for "location" of a cell with respect to its grid
 *
 */
public class Location {
    private int myRow;
    private int myCol;

    public Location (int r, int c) {
        myRow = r;
        myCol = c;
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
