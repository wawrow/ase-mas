package perceptor;

import com.agentfactory.logic.agent.Perceptor;
import com.agentfactory.logic.lang.FOS;
import service.WorldService;

public class WorldPerceptor extends Perceptor {

    public void perceive() {
        WorldService world = (WorldService)this.getService("world");

        if (world == null) {
            adoptBelief("BELIEF(unavailable(world))");
            return;
        }
        for (FOS fos: world.getPercepts(agent)) {
            adoptBelief("BELIEF(" + fos.toString() + ")");
        }
    }
}