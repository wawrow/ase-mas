package actuator;

import com.agentfactory.logic.agent.Actuator;
import com.agentfactory.logic.lang.FOS;
import module.Blueprint;
import module.BlueprintStep;
 
public class GetTask extends Actuator {
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
                adoptBelief("BELIEF(allTasksComplete(" + agentName + "))");
            } else {
                adoptBelief("BELIEF(taskUnavailable(" + agentName + "))");
            }
        } else {
            adoptBelief("BELIEF(gotTask(" + agentName + "," + step.xPosition + "," + step.yPosition + "))");
        }
        return true;
     }
 }

