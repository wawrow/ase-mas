package actuator;

import com.agentfactory.logic.agent.Actuator;
import com.agentfactory.logic.lang.FOS;

public class MakeWeldAcknowledged extends Actuator {
    public boolean act(FOS action) {
        adoptBelief("BELIEF(state(needTask))");
        return true;
    }
}

