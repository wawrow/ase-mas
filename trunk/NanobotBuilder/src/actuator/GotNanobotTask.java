package actuator;

import com.agentfactory.logic.agent.Actuator;
import com.agentfactory.logic.lang.FOS;

public class GotNanobotTask extends Actuator {
    public boolean act(FOS action) {
        String agentName = action.argAt(0).toString();
        String x = action.argAt(1).toString();
        String y = action.argAt(2).toString();

        adoptBelief("ALWAYS(BELIEF(state(target(" + x + "," + y + "))))");
        System.out.println(agent.getName() + ": got new task from " + agentName + " target=(" + x + "," + y + ")");
        return true;
    }
}

