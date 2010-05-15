package module;

import com.agentfactory.logic.agent.Module;
import java.util.Map;
import java.util.HashMap;

public class Resources extends Module {

    private final String RESOURCES_FILENAME = "resources.txt";
    private Map<String, Integer> map = new HashMap<String, Integer>();
    private int index = 0;

    @Override
    public void init() {
        System.out.println("Loading resources");
        ConfigFileHelper.read(RESOURCES_FILENAME, map);
    }
    
    public ResourcesStep read() {
        if (index == 0) {
            String agentType = "0";
            Integer agentCount = map.get(agentType);
            index ++;
            return new ResourcesStep("0", agentCount);
        }
        return null;
    }
}
