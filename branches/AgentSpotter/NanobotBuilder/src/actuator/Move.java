package actuator;

import com.agentfactory.logic.agent.Actuator;
import com.agentfactory.logic.lang.FOS;
import service.WorldService;

public class Move extends Actuator {
    public boolean act(FOS action) {
        WorldService world = (WorldService)this.getService("world");
        if (world == null) {
            return false;
        }
        
        String target = action.argAt(0).toString();
        return world.move(agent, target);
    }
}
