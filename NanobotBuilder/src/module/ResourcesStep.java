package module;

public class ResourcesStep {
    public String agentType;
    public Integer agentCount;

    public ResourcesStep(String agentType, Integer agentCount){
        this.agentType = agentType;
        this.agentCount = agentCount;

        System.out.println("agent type=" + agentType + " agentCount=" + agentCount); 
    }
}

