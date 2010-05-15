package actuator;

import com.agentfactory.logic.agent.Actuator;
import com.agentfactory.logic.lang.FOS;

public class Revisited extends Actuator {
    public boolean act(FOS action) {
        int currentRevisitedCount = Integer.parseInt(action.argAt(0).toString());
        int maxRevisitedCount = Integer.parseInt(action.argAt(1).toString());
        currentRevisitedCount++;
        adoptBelief("BELIEF(revisitedCount(" + currentRevisitedCount + "))");

        if (currentRevisitedCount >= maxRevisitedCount) {
            System.out.println("Warning: Target appears to be unreachable for " + this.getAgentName() + " " + currentRevisitedCount);
        }
        return true;
    }
}

