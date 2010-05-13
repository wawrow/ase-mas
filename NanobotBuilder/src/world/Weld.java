package world;

public class Weld extends AbstractObject {

    public int endPointX1;
    public int endPointY1;
    public int endPointX2;
    public int endPointY2;

    public Weld(String name) {
        super(name);
    }

    public Weld(String name, int endPointX1, int endPointY1, int endPointX2, int endPointY2) {
        super(name);
        this.endPointX1 = endPointX1;
        this.endPointY1 = endPointY1;
        this.endPointX2 = endPointX2;
        this.endPointY2 = endPointY2;
    }

    public boolean isNanobot() {
        return false;
    }

    public int getType() {
        return 0;
    }
}
