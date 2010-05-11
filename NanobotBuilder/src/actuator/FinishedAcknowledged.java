package actuator;

import com.agentfactory.logic.agent.Actuator;
import com.agentfactory.logic.lang.FOS;

public class FinishedAcknowledged extends Actuator {
    public boolean act(FOS action) {
        String agentName = action.argAt(0).toString();
        adoptBelief("ALWAYS(BELIEF(state(finishedAcknowledged)))");
        System.out.println(agent.getName() + ": received finished acknowledgement from " + agentName);
        return true;
    }
}

