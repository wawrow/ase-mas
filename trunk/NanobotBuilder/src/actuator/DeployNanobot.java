package actuator;

import com.agentfactory.logic.agent.Actuator;
import com.agentfactory.logic.lang.FOS;
import com.agentfactory.service.ams.AgentManagementService;
import com.agentfactory.platform.core.IAgent;
import com.agentfactory.platform.core.NoSuchArchitectureException;
import com.agentfactory.platform.core.DuplicateAgentNameException;

public class DeployNanobot extends Actuator {
    public boolean act(FOS action) {
        String agentType = action.argAt(0).toString();
        String taskId = action.argAt(1).toString();
        String supervisorName = action.argAt(2).toString();
        String supervisorAddress = action.argAt(3).toString();

        System.out.println("Deploying nanobot agentType=" + agentType + " taskId=" +
                taskId + " supervisorName=" + supervisorName + " supervisorAddress=" + supervisorAddress);
        try {
            AgentManagementService ams = (AgentManagementService)this.agent.getPlatformService(AgentManagementService.NAME);
            IAgent newAgent = ams.createAgent(agentType + "#nanobot" + taskId, "nanobotbuilder/Nanobot.agent");
            newAgent.initialise("ALWAYS(BELIEF(supervisor(" + supervisorName + ", " + supervisorAddress + ")))");
            newAgent.initialise("ALWAYS(BELIEF(agentType(" + agentType + ")))");
            newAgent.execute();

        } catch (NoSuchArchitectureException ex) {
        } catch (DuplicateAgentNameException ex) {
        }
        return true;
    }
}

