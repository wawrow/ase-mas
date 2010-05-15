package module;

import com.agentfactory.logic.agent.Module;
import java.util.Map;
import java.util.HashMap;

public class Inventory extends Module {

    private final String INVENTORY_FILENAME = "inventory.txt";
    private Map<String, Integer> inventoryMap = new HashMap<String, Integer>();

    @Override
    public void init() {
        System.out.println("Loading inventory");
        ConfigFileHelper.read(INVENTORY_FILENAME, inventoryMap);
    }
    
    public boolean decrement(String nanobotType) {
        Integer count = inventoryMap.get(nanobotType);
        if (count != null) {
            if (count > 0 ) {
                inventoryMap.put(nanobotType, count - 1);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
