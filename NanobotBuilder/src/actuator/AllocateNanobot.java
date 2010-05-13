package actuator;

import com.agentfactory.logic.agent.Actuator;
import com.agentfactory.logic.lang.FOS;

public class AllocateNanobot extends Actuator {
    public boolean act(FOS action) {
        adoptBelief("BELIEF(nanobotAllocated(true))");
        return true;
    }
}

