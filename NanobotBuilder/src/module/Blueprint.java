package module;

import com.agentfactory.logic.agent.Module;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.awt.Point;
import java.io.*;

public class Blueprint extends Module {

    private final String BLUEPRINT_FILENAME = "blueprint.txt";
    private SortedMap<Integer, List<Blueprint.BlueprintStepHolder>> phases =
            new TreeMap<Integer, List<Blueprint.BlueprintStepHolder>>();
    private int currentPhase = 1;
    private boolean allStepsDone = false;

    private class BlueprintStepHolder {
        public static final int STEP_UNALLOCATED = 1;
        public static final int STEP_ALLOCATED = 2;
        public static final int STEP_DONE = 3;
        
        public BlueprintStep step;
        public int state;
        public BlueprintStepHolder(BlueprintStep step) {
            this.step = step;
            this.state = STEP_UNALLOCATED;
        }
    }
    @Override
    public void init() {
        try {
            File file = new File(BLUEPRINT_FILENAME);
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                try {
                    String line = null;
                    int stepId = 0;
                    while((line = br.readLine()) != null) {
                        StringTokenizer st = new StringTokenizer(line,",");
                        String phaseId = st.nextToken();
                        Blueprint.BlueprintStepHolder step = new Blueprint.BlueprintStepHolder(
                                new BlueprintStep(stepId++, phaseId, st.nextToken(), st.nextToken(), st.nextToken()));

                        List<Blueprint.BlueprintStepHolder> phase = phases.get(Integer.parseInt(phaseId));
                        if (phase == null) {
                            phase = new ArrayList<Blueprint.BlueprintStepHolder>();
                            phases.put(Integer.parseInt(phaseId), phase);
                        }
                        phase.add(step);
                    }
                } finally {
                    br.close();
                }
            } else {
                System.out.println("Could not find blueprint file " + BLUEPRINT_FILENAME);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BlueprintStep getBlueprintStep(String agentType, int x, int y) {

        boolean isCurrentPhaseComplete = true;
        List<Blueprint.BlueprintStepHolder> phase = phases.get(currentPhase);
        if (phase ==  null) {
            this.allStepsDone = true;
            return null;
        }

        // Iterate through the steps for this phase
        double minimumDistance = Double.MAX_VALUE;
        BlueprintStepHolder selectedStep = null;
        for (BlueprintStepHolder stepHolder : phase) {
            if (stepHolder.state != BlueprintStepHolder.STEP_DONE) {
                isCurrentPhaseComplete = false;
            }
            // Look for the nearest unallocated step for this agent type
            if (stepHolder.state == BlueprintStepHolder.STEP_UNALLOCATED &&
                stepHolder.step.agentType.equals(agentType)) {
                Point agentPosition = new Point(x,y);
                Point stepPosition = new Point(Integer.parseInt(stepHolder.step.xPosition),
                                               Integer.parseInt(stepHolder.step.yPosition));
                double distance = Math.abs(agentPosition.distance(stepPosition));
                if (distance < minimumDistance) {
                    minimumDistance = distance;
                    selectedStep = stepHolder;
                }
            }
        }
        // If we found one, return it
        if (selectedStep != null) {
            selectedStep.state = BlueprintStepHolder.STEP_ALLOCATED;
            return selectedStep.step;
        }
        
        // Otherwise, if this phase complete, move onto next phase and look there
        if (isCurrentPhaseComplete == true) {
            currentPhase++;
            SortedMap<Integer, List<Blueprint.BlueprintStepHolder>> laterPhases =
                    phases.tailMap(currentPhase);
            if (laterPhases.isEmpty()) {
                this.allStepsDone = true;
                return null;
            } else {
                currentPhase = laterPhases.firstKey();
                return getBlueprintStep(agentType, x, y);
            }
        }
        return null;
    }

    public boolean isAllStepsDone() {
        return this.allStepsDone;
    }
}
