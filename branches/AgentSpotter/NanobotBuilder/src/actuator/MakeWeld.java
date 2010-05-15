package actuator;

import com.agentfactory.logic.agent.Actuator;
import com.agentfactory.logic.lang.FOS;
import service.WorldService;

public class MakeWeld extends Actuator {
    public boolean act(FOS action) {
        String targetX = action.argAt(0).toString();
        String targetY = action.argAt(1).toString();
        String x1 = action.argAt(2).toString();
        String y1 = action.argAt(3).toString();
        String x2 = action.argAt(4).toString();
        String y2 = action.argAt(5).toString();

        System.out.println(agent.getName() + ": Making weld between (" + x1 + "," + y1 + ") (" + x2 + "," + y2 + ")");

        WorldService world = (WorldService)this.getService("world");
        if (world == null) {
            return false;
        }

        world.addWeld(agent, Integer.parseInt(targetX), Integer.parseInt(targetY), Integer.parseInt(x1),
            Integer.parseInt(y1), Integer.parseInt(x2), Integer.parseInt(y2));

        retractBelief("ALWAYS(BELIEF(state(target(" + targetX + "," + targetY + "))))");
        retractBelief("ALWAYS(BELIEF(state(weldEndpoints(" + x1 + "," + y1 + "," + x2 + "," + y2 + "))))");
        adoptBelief("ALWAYS(BELIEF(state(target(0,30))))");
        adoptBelief("BELIEF(state(needTask))");
        return true;
    }
}

