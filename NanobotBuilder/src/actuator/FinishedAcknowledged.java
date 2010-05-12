package actuator;

import com.agentfactory.logic.agent.Actuator;
import com.agentfactory.logic.lang.FOS;
import service.WorldService;

public class FinishedAcknowledged extends Actuator {
    public boolean act(FOS action) {

        String agentName = action.argAt(0).toString();
        adoptBelief("ALWAYS(BELIEF(state(finishedAcknowledged)))");
        System.out.println(agent.getName() + ": received finished acknowledgement from " + agentName);

        // Update the world view so that this agent is finished its task
        WorldService world = (WorldService)this.getService("world");
        if (world == null) {
            return false;
        }
        world.setFinished(agent);                       
        return true;
    }
}

