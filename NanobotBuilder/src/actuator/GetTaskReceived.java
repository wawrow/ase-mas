package actuator;

import com.agentfactory.logic.agent.Actuator;
import com.agentfactory.logic.lang.FOS;
import module.Blueprint;
import module.BlueprintStep;
 
public class GetTaskReceived extends Actuator {
    public boolean act(FOS action) {
        String blueprintName = action.argAt(0).toString();
        String agentName = action.argAt(1).toString();
        String agentType = action.argAt(2).toString();
        String x = action.argAt(3).toString();
        String y = action.argAt(4).toString();

        System.out.println(agent.getName() + ": received getTask request from " + agentName + " currently at (" + x + "," + y + ")");

        Blueprint blueprint = (Blueprint)this.getModuleByName(blueprintName);
        BlueprintStep step = blueprint.allocateBlueprintStep(agentType,
                Integer.parseInt(x), Integer.parseInt(y));
        if (step == null) {
            if (blueprint.isAllPhasesDone()) {
            System.out.println("step is null, allTasksComplete");
                adoptBelief("BELIEF(allTasksComplete(" + agentName + "))");
            } else {
            System.out.println("step is null, taskUnavailable");
                adoptBelief("BELIEF(taskUnavailable(" + agentName + "))");
            }
        } else {
            if (step.xPosition2 != null) {
                System.out.println("sending a welder task");
                adoptBelief("BELIEF(gotTask(" + agentName + "," + step.xPosition + "," + step.yPosition +
                        "," + step.xPosition2 + "," + step.yPosition2 + "))");
            } else {
                System.out.println("sending a nanobot task");
                adoptBelief("BELIEF(gotTask(" + agentName + "," + step.xPosition + "," + step.yPosition + "))");
            }
        }
        return true;
     }
 }

