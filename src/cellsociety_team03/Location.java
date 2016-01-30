package cellsociety_team03;

public class Location {
    private int myX;
    private int myY;
    
    public Location (int x, int y) {
        myX = x;
        myY = y;
    }

    public int getX () {
        return myX;
    }

    public void setX (int x) {
        this.myX = x;
    }

    public int getY () {
        return myY;
    }

    public void setY (int y) {
        this.myY = y;
    }

}
