package service;

import com.agentfactory.platform.impl.AbstractPlatformService;
import com.agentfactory.logic.lang.FOS;
import com.agentfactory.platform.core.IAgent;
import world.NanobotWorld;

import java.util.List;

public class WorldService extends AbstractPlatformService {

    private NanobotWorld nanobotWorld;

    @Override
    public void onStart() {
        nanobotWorld = new NanobotWorld(100, 100);
    }

    @Override
    public void onBind(IAgent agent) {
        System.out.println("onbind: tostring=" + agent.toString() + " agentId=" + agent.getAgentID().toString() + " agent.class=" + agent.getClass().toString() +
                " type=" + agent.getType() + " agentId=" + agent.getAgentID().getName() + " fipaString=" + agent.getAgentID().toFIPAString() +
                " FOSString" + agent.getAgentID().toFOSString() + " serviceDescriptions=" + agent.getAgentID().getServiceDescriptionss().toString() );
        nanobotWorld.addNanobot(agent.getName());
    }

    public List<FOS> getPercepts(IAgent agent) {
        return nanobotWorld.getPercepts(agent.getName());
    }

    @Override
    public void onUnbind(IAgent agent) {
    }

    @Override
    public void onStop() {
    }
}
