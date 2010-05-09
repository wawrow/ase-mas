package world;

public class GridCell {
    private int x;
    private int y;
    private AbstractNanobot nanobot = null;

    public GridCell(int x, int y, AbstractNanobot nanobot) {
        this.x = x;
        this.y = y;
        this.nanobot = nanobot;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public boolean isOccupied() {
        return this.nanobot != null;
    }

    public void setOccupant(AbstractNanobot nanobot, Grid grid) {
        if (nanobot != null) {
            System.out.println("Nanobot " + nanobot.getName() + " in cell (" + x + "," + y + ")");
        } else {
            System.out.println("cell (" + x + "," + y + ") is now empty");
        }
        this.nanobot = nanobot;
        grid.setNanobotLocation(nanobot, this);
    }

    public AbstractNanobot getOccupant() {
        return this.nanobot;
    }
}
