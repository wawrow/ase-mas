package world;

public abstract class AbstractNanobot {
    protected String name;
    protected boolean finished = false;

    public AbstractNanobot(String name) {
        this.name = name;
    }

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
