package actuator;

import com.agentfactory.logic.agent.Actuator;
import com.agentfactory.logic.lang.FOS;

public class NeedTask extends Actuator {
    public boolean act(FOS action) {
        adoptBelief("BELIEF(state(needTask))");
        System.out.println("Agent needs task");
        return true;
    }
}

