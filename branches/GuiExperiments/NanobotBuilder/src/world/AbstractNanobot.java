package world;

public abstract class AbstractNanobot {
    protected String name;

    public AbstractNanobot(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
