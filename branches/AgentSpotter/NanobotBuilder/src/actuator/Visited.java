package actuator;

import com.agentfactory.logic.agent.Actuator;
import com.agentfactory.logic.lang.FOS;

public class Visited extends Actuator {
    public boolean act(FOS action) {
        String x = action.argAt(0).toString();
        String y = action.argAt(1).toString();

        adoptBelief("ALWAYS(BELIEF(visited(" + x + "," + y + ")))");
        adoptBelief("BELIEF(revisitedCount(0))");
        return true;
    }
}

