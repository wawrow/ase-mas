package actuator;

import com.agentfactory.logic.agent.Actuator;
import com.agentfactory.logic.lang.FOS;

public class GotWelderTask extends Actuator {
    public boolean act(FOS action) {
        String agentName = action.argAt(0).toString();
        String x1 = action.argAt(1).toString();
        String y1 = action.argAt(2).toString();
        String x2 = action.argAt(3).toString();
        String y2 = action.argAt(4).toString();
        System.out.println(agent.getName() + ": got new task from " + agentName + " target weld endpoints=(" + x1 + "," + y1 + ") (" + x2 + "," + y2 + ")");        

        // Target for a weld is in the middle of the two endpoints
        Integer targetX = (Integer.parseInt(x1) + Integer.parseInt(x2)) / 2;
        Integer targetY = (Integer.parseInt(y1) + Integer.parseInt(y2)) / 2;

        retractBelief("ALWAYS(BELIEF(state(target(?x, ?y))))");
        retractBelief("ALWAYS(BELIEF(state(weldEndpoints(?x1, ?y1, ?x2, ?y2))))");
        adoptBelief("ALWAYS(BELIEF(state(target(" + targetX + "," + targetY + "))))");
        adoptBelief("ALWAYS(BELIEF(state(weldEndpoints(" + x1 + "," + y1 + "," + x2 + "," + y2 + "))))");
        return true;
    }
}

