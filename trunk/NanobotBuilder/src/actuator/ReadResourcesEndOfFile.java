package actuator;

import com.agentfactory.logic.agent.Actuator;
import com.agentfactory.logic.lang.FOS;

public class ReadResourcesEndOfFile extends Actuator {
    public boolean act(FOS action) {
        System.out.println("End of resources");
        adoptBelief("BELIEF(state(readingBlueprint))");
        return true;
    }
}

