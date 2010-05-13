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
    private final String NANOBOT_WELDER = "0";
    
    private SortedMap<Integer, List<Blueprint.BlueprintStepHolder>> phases =
            new TreeMap<Integer, List<Blueprint.BlueprintStepHolder>>();
    private int currentPhase = 1;
    private boolean allPhasesDone = false;
    private boolean allPhasesDeployed = false;

    private class BlueprintStepHolder {
        public static final int STEP_UNALLOCATED = 1;
        public static final int STEP_DEPLOYED = 2;
        public static final int STEP_ALLOCATED = 3;
        public static final int STEP_DONE = 4;
        
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
                    Blueprint.BlueprintStepHolder step = null;
                    while((line = br.readLine()) != null) {
                        StringTokenizer st = new StringTokenizer(line,",");
                        String phaseId = st.nextToken();
                        String nanobotType = st.nextToken();

                        // Welders have two coordinates in the blueprint
                        if (nanobotType.equals(NANOBOT_WELDER)) {
                            step = new Blueprint.BlueprintStepHolder(new BlueprintStep(
                                stepId++, phaseId, nanobotType, st.nextToken(),
                                st.nextToken(), st.nextToken(), st.nextToken()));
                        } else {
                            step = new Blueprint.BlueprintStepHolder(new BlueprintStep(
                                stepId++, phaseId, nanobotType, st.nextToken(),
                                st.nextToken(), "0", "0"));
                        }

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

    public void finishedStep(String agentType, int x, int y) {

        List<Blueprint.BlueprintStepHolder> phase = phases.get(currentPhase);
        for (BlueprintStepHolder stepHolder : phase) {
            Point agentPosition = new Point(x,y);
            Point stepPosition = new Point(Integer.parseInt(stepHolder.step.xPosition),
                                           Integer.parseInt(stepHolder.step.yPosition));
            if (agentPosition.equals(stepPosition)) {
                stepHolder.state = BlueprintStepHolder.STEP_DONE;
                return;
            }
        }
    }

    public BlueprintStep readNextBlueprintStep() {

        boolean isCurrentPhaseComplete = true;
        boolean isCurrentPhaseDeployed = true;
        List<Blueprint.BlueprintStepHolder> phase = phases.get(currentPhase);
        if (phase == null) {
            this.allPhasesDone = true;
            return null;
        }

        // Look for an unallocated step in the current phase
        for (BlueprintStepHolder stepHolder : phase) {
            if (stepHolder.state != BlueprintStepHolder.STEP_DONE) {
                isCurrentPhaseComplete = false;
            }
            if (stepHolder.state != BlueprintStepHolder.STEP_DEPLOYED) {
                isCurrentPhaseDeployed = false;
            }
            if (stepHolder.state == BlueprintStepHolder.STEP_UNALLOCATED) {
                stepHolder.state = BlueprintStepHolder.STEP_DEPLOYED;
                return stepHolder.step;
            }
        }

        // If the current phase is all deployed peek is there any more phases
        SortedMap<Integer, List<Blueprint.BlueprintStepHolder>> laterPhases =
            phases.tailMap(currentPhase+1);
        if (isCurrentPhaseDeployed == true && laterPhases.isEmpty()) {
            this.allPhasesDeployed = true;
        }

        // If we didn't find a step yest and if this phase complete, move onto next phase
        if (isCurrentPhaseComplete == true) {
            currentPhase++;
            if (laterPhases.isEmpty()) {
                this.allPhasesDone = true;
                return null;
            } else {
                currentPhase = laterPhases.firstKey();
                return readNextBlueprintStep();
            }
        }
        return null;
    }

    public BlueprintStep allocateBlueprintStep(String agentType, int x, int y) {

        List<Blueprint.BlueprintStepHolder> phase = phases.get(currentPhase);
        if (phase == null) {
            this.allPhasesDone = true;
            return null;
        }

        // Iterate through the steps for this phase
        double minimumDistance = Double.MAX_VALUE;
        BlueprintStepHolder selectedStep = null;
        for (BlueprintStepHolder stepHolder : phase) {
            // Look for the nearest deployed step for this agent type
            if (stepHolder.state == BlueprintStepHolder.STEP_DEPLOYED &&
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
        return null;
    }

    public boolean isAllPhasesDone() {
        return this.allPhasesDone;
    }

    public boolean isAllPhasesDeployed() {
        return this.allPhasesDeployed;
    }
}
