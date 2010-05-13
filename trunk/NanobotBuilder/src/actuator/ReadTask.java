package actuator;

import com.agentfactory.logic.agent.Actuator;
import com.agentfactory.logic.lang.FOS;
import module.Blueprint;
import module.BlueprintStep;

public class ReadTask extends Actuator {
    public boolean act(FOS action) {
        String blueprintName = action.argAt(0).toString();
        Blueprint blueprint = (Blueprint)this.getModuleByName(blueprintName);        
        BlueprintStep step = blueprint.readNextBlueprintStep();
        if (step == null) {
            if (blueprint.isAllPhasesDeployed()) {
                adoptBelief("BELIEF(readTaskEndOfFile(true))");
            } else {
                adoptBelief("BELIEF(readTaskWaiting(true))");
                adoptBelief("BELIEF(state(readingBlueprint))");
            }
        } else {
            System.out.println("Read task from blueprint, agentType=" + step.agentType + " stepId=" + step.stepId);
            adoptBelief("BELIEF(readTaskSuccessful(" + step.agentType + "," + step.stepId + "))");
            adoptBelief("BELIEF(state(readingBlueprint))");
        }
        return true;
    }
}

