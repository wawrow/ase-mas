package world;

import com.agentfactory.logic.lang.FOS;
import gui.WorldObserverSwing;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class NanobotWorld {

    public static final String NANOBOT_TYPE1 = "1";
    public static final String NANOBOT_TYPE2 = "2";
    public static final String NANOBOT_TYPE3 = "3";
    private final String WELD_NAME = "weld";

    private Grid grid;
    private WorldObserverSwing worldObserver;
    private Map<String, AbstractNanobot> nanobotMap = new HashMap<String, AbstractNanobot>();
    
    public NanobotWorld(int x, int y) {
        grid = new Grid(x, y);
        //TODO this should be refactored to main
        worldObserver = new WorldObserverSwing();
        try {
            worldObserver.Start();
            grid.addObserver(worldObserver);
        } catch(Exception ex){
            System.err.println("Failed to initialize gui: " + ex.toString());
        }
    }

    public void addNanobot(String name) {
        System.out.println("World, adding nanobot name=" + name);
        AbstractNanobot nanobot = createNanobot(name);
        nanobotMap.put(name, nanobot);
        grid.addNanobot(nanobot);
    }

    public void addWeld(int x, int y, int endpointX1, int endpointY1, int endpointX2, int endpointY2) {
        System.out.println("World, putting weld in (" + x + "," + y + ") between (" +
                endpointX1 + "," + endpointY1 + ") and (" + endpointX2 + "," + endpointY2 + ")");
        Weld weld = new Weld(WELD_NAME, endpointX1, endpointY1, endpointX2, endpointY2);
        grid.addWeld(weld, x, y, endpointX1, endpointY1, endpointX2, endpointY2);
    }

    private AbstractNanobot createNanobot(String name) {
        StringTokenizer st = new StringTokenizer(name, "#");
        String type = st.nextToken();

        if (type.equals(NANOBOT_TYPE1)) {
            return new NanobotType1(name);
        } else if (type.equals(NANOBOT_TYPE2)) {
            return new NanobotType2(name);
        } else if (type.equals(NANOBOT_TYPE3)) {
            return new NanobotType3(name);
        } else {
            // Just default to one type of nanobot
            return new NanobotType1(name);
        }
    }

    public List<FOS> getPercepts(String name) {
        return grid.getPercepts(nanobotMap.get(name));
    }

    public void setFinished(String name) {
        nanobotMap.get(name).setFinished();
    }

    public boolean move(String name, String target) {
        return grid.moveNanobot(nanobotMap.get(name), target);
    }
}
