package constants;

/**
 * Convenience method to represent an offset of x rows and y columns in a grid
 *
 */
public class Offset {
    private int myRow;
    private int myCol;

    public Offset (int r, int c) {
        myRow = r;
        myCol = c;
    }

    public int getRow () {
        return myRow;
    }

    public void setRow (int row) {
        this.myRow = row;
    }

    public int getCol () {
        return myCol;
    }

    public void setCol (int col) {
        this.myCol = col;
    }

}
