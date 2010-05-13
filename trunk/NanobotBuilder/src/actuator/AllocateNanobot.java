package actuator;

import com.agentfactory.logic.agent.Actuator;
import com.agentfactory.logic.lang.FOS;
import module.Inventory;

public class AllocateNanobot extends Actuator {
    public boolean act(FOS action) {

        String inventoryName = action.argAt(0).toString();
        String nanobotType = action.argAt(1).toString();
        Inventory inventory = (Inventory)this.getModuleByName(inventoryName);
        inventory.decrement(nanobotType);
        adoptBelief("BELIEF(nanobotAllocated(true))");
        return true;
    }
}

