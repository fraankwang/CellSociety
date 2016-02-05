package constants;

public enum NeighborOffset {
    TOP_LEFT            (new Offset(-1, -1)), 
    TOP                 (new Offset(-1,  0)), 
    TOP_RIGHT           (new Offset(-1,  1)), 
    LEFT                (new Offset( 0, -1)), 
    RIGHT               (new Offset( 0,  1)), 
    BOTTOM_LEFT         (new Offset( 1, -1)), 
    BOTTOM              (new Offset( 1,  0)),
    BOTTOM_RIGHT        (new Offset( 1,  1));

    
    private Offset myOffset;
    
    NeighborOffset(Offset o){
        myOffset = o;
    }
    
    public Offset getOffset(){
        return myOffset;
    }
}
