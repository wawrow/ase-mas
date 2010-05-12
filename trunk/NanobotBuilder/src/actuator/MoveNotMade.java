package actuator;

import com.agentfactory.logic.agent.Actuator;
import com.agentfactory.logic.lang.FOS;

public class MoveNotMade extends Actuator {
    public boolean act(FOS action) {
        adoptBelief("BELIEF(revisitedCount(0))");
        return true;
    }
}

