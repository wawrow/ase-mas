package actuator;

import com.agentfactory.logic.agent.Actuator;
import com.agentfactory.logic.lang.FOS;
import module.Blueprint;
 
public class FinishedTask extends Actuator {
    public boolean act(FOS action) {
        String blueprintName = action.argAt(0).toString();
        String agentName = action.argAt(1).toString();
        String agentType = action.argAt(2).toString();
        String x = action.argAt(3).toString();
        String y = action.argAt(4).toString();

        System.out.println(agent.getName() + ": received finishedTask notification from " + agentName + " at (" + x + "," + y + ")");

        Blueprint blueprint = (Blueprint)this.getModuleByName(blueprintName);
        blueprint.finishedStep(agentType, Integer.parseInt(x), Integer.parseInt(y));
        return true;
     }
 }

