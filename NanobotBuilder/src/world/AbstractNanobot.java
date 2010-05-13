package world;

public abstract class AbstractNanobot extends AbstractObject {

    public AbstractNanobot(String name) {
        super(name);
    }

    public boolean isNanobot() {
        return true;
    }

    public abstract int getType();
}
