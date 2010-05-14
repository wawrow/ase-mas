package actuator;

import com.agentfactory.logic.agent.Actuator;
import com.agentfactory.logic.lang.FOS;
import module.Resources;
import module.ResourcesStep;

public class ReadResources extends Actuator {
    public boolean act(FOS action) {
        String resourcesName = action.argAt(0).toString();
        Resources resources = (Resources)this.getModuleByName(resourcesName);

        ResourcesStep resource = resources.read();
        if (resource != null) {
            System.out.println("Resources read: agentType=" + resource.agentType + " agentCount=" + resource.agentCount);
            adoptBelief("BELIEF(readResourcesSuccessful(" + resource.agentType + "," + resource.agentCount + "))");
            adoptBelief("BELIEF(state(readingBlueprint))");
        } else {
            adoptBelief("BELIEF(readResourcesEndOfFile(true))");
        }
        return true;
    }
}

