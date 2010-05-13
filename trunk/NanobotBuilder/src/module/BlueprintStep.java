package module;

public class BlueprintStep {
    public int stepId;
    public String phaseId;
    public String agentType;
    public String xPosition;
    public String yPosition;
    public String xPosition2;
    public String yPosition2;

    public BlueprintStep(int stepId, String phaseId, String agentType, String xPosition, String yPosition,
            String xPosition2, String yPosition2) {
        this.stepId = stepId;
        this.phaseId = phaseId;
        this.agentType = agentType;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xPosition2 = xPosition2;
        this.yPosition2 = yPosition2;

        System.out.println("step=" + stepId + " phaseId=" + phaseId + 
               " agentType=" + agentType + " x=" + xPosition + " y=" + yPosition +
               " x2=" + xPosition2 + " y2=" + yPosition2);
    }
}

