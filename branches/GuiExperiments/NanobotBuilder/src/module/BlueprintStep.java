package module;

public class BlueprintStep {
    public int stepId;
    public String phaseId;
    public String agentType;
    public String xPosition;
    public String yPosition;

    public BlueprintStep(int stepId, String phaseId, String agentType, String xPosition, String yPosition) {
        this.stepId = stepId;
        this.phaseId = phaseId;
        this.agentType = agentType;
        this.xPosition = xPosition;
        this.yPosition = yPosition;

        System.out.println("step=" + stepId + " phaseId=" + phaseId + 
                " agentType=" + agentType + " x=" + xPosition + " y=" + yPosition);
    }
}

