package actuator;

import com.agentfactory.logic.agent.Actuator;
import com.agentfactory.logic.lang.FOS;

public class FinishedTask extends Actuator {
    public boolean act(FOS action) {
        String agentName = action.argAt(0).toString();
        adoptBelief("ALWAYS(BELIEF(state(finished)))");
        return true;
    }
}

