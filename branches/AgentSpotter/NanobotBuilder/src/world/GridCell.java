package world;

public class GridCell {
    private int x;
    private int y;
    private AbstractObject object = null;
    private AbstractObject weld = null;    

    public GridCell(int x, int y, AbstractObject object) {
        this.x = x;
        this.y = y;
        this.object = object;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public boolean isOccupied() {
        return this.object != null || this.weld != null;
    }

    public boolean isWeld() {
        return this.weld != null;
    }

    public void setOccupant(AbstractObject object, Grid grid) {
        if (object != null) {
            System.out.println("Object " + object.getName() + " in cell (" + x + "," + y + ")");
            this.object = object;
            grid.setObjectLocation(object, this);
        } else {
            System.out.println("cell (" + x + "," + y + ") is now empty");
            this.object = null;
        }
    }
    public void setWeld(AbstractObject object) {
        if (object != null) {
            System.out.println("Object " + object.getName() + " in cell (" + x + "," + y + ")");
            this.weld = object;
        } 
    }

    public AbstractObject getOccupant() {
        return this.object;
    }
}
