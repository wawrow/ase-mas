package world;

public abstract class AbstractObject {
    protected boolean finished = false;
    protected String name;

    public AbstractObject(String name) {
        this.name = name;
    }

    public abstract boolean isNanobot();

    public String getName() {
        return name;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void setFinished() {
        this.finished = true;
    }

    public abstract int getType();
}
