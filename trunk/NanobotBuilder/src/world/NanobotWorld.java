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

    private Grid grid;
    private WorldObserverSwing worldObserver;
    private Map<String, AbstractNanobot> nanobotMap = new HashMap<String, AbstractNanobot>();
    
    public NanobotWorld(int x, int y) {
        grid = new Grid(x, y);
        //TODO this should be refactored to main
        worldObserver = new WorldObserverSwing();
        try{
            worldObserver.Start();
            grid.addObserver(worldObserver);
            //worldObserver.update(grid, this);
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

    public boolean move(String name, String target) {
        return grid.moveNanobot(nanobotMap.get(name), target);
    }

}
