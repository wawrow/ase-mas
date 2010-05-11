package actuator;

import com.agentfactory.logic.agent.Actuator;
import com.agentfactory.logic.lang.FOS;

public class GotTask extends Actuator {
    public boolean act(FOS action) {
        String agentName = action.argAt(0).toString();
        String x = action.argAt(1).toString();
        String y = action.argAt(2).toString();

        adoptBelief("ALWAYS(BELIEF(state(allocated)))");
        System.out.println("Got new task from " + agentName + " at x=" + x + " y=" + y);
        return true;
    }
}

