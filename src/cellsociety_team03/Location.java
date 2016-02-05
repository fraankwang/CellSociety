package cellsociety_team03;

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
        this.myRow = r;
    }

    public int getCol () {
        return myCol;
    }

    public void setCol (int c) {
        this.myCol = c;
    }

}
