package actuator;

import com.agentfactory.logic.agent.Actuator;
import com.agentfactory.logic.lang.FOS;
import com.agentfactory.platform.core.AgentCreationException;
import com.agentfactory.service.ams.AgentManagementService;
import com.agentfactory.platform.core.IAgent;
import com.agentfactory.platform.core.NoSuchArchitectureException;
import com.agentfactory.platform.core.DuplicateAgentNameException;
import module.Inventory;

public class DeployNanobot extends Actuator {

    public boolean act(FOS action) {
        String inventoryName = action.argAt(0).toString();
        String agentType = action.argAt(1).toString();
        String agentCount = action.argAt(2).toString();
        String taskId = action.argAt(3).toString();
        String supervisorName = action.argAt(4).toString();
        String supervisorAddress = action.argAt(5).toString();
        Inventory inventory = (Inventory)this.getModuleByName(inventoryName);

        for(int i=0; i<Integer.parseInt(agentCount); i++) {
            boolean success = inventory.decrement(agentType);
            if (success == false) {
                System.out.println("Failed to deploy agent of type=" + agentType);
                return true;
            }

            System.out.println("Deploying count= " + agentCount + " agentType=" + agentType + " taskId=" +
                    taskId + " supervisorName=" + supervisorName + " supervisorAddress=" + supervisorAddress);
            try {
                AgentManagementService ams = (AgentManagementService)this.agent.getPlatformService(AgentManagementService.NAME);
                String agentName = generateAgentName(agentType, taskId, i);
                String agentDescriptor = generateAgentDescriptor(agentType);
                IAgent newAgent = ams.createAgent(agentName, agentDescriptor);
                newAgent.initialise("ALWAYS(BELIEF(supervisor(" + supervisorName + ", " + supervisorAddress + ")))");
                newAgent.initialise("ALWAYS(BELIEF(agentType(" + agentType + ")))");
                newAgent.execute();
            } catch (NoSuchArchitectureException ex) {
            } catch (DuplicateAgentNameException ex) {
            } catch (AgentCreationException ex){
            }
        }
        adoptBelief("BELIEF(nanobotAllocated(true))");
        return true;
    }

    public String generateAgentName(String agentType, String taskId, int index) {
        if (agentType.equals("0")) {
            return new String(agentType + "#welder" + index);
        } else {
            return new String(agentType + "#nanobot" + taskId);
        }
    }

    public String generateAgentDescriptor(String agentType) {
        if (agentType.equals("0")) {
            return "nanobotbuilder/Welder.agent";
        } else {
            return "nanobotbuilder/Nanobot.agent";
        }
    }
}

