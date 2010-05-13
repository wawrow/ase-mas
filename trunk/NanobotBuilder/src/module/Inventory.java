package module;

import com.agentfactory.logic.agent.Module;
import java.util.Map;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.io.*;

public class Inventory extends Module {

    private final String INVENTORY_FILENAME = "inventory.txt";
    private Map<String, Integer> inventoryMap = new HashMap<String, Integer>();

    @Override
    public void init() {
        try {
            File file = new File(INVENTORY_FILENAME);
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                try {
                    String line = null;
                    while((line = br.readLine()) != null) {
                        StringTokenizer st = new StringTokenizer(line,",");
                        String nanobotType = st.nextToken();
                        String nanobotCount = st.nextToken();
                        Integer currentCount = inventoryMap.get(nanobotType);
                        if (currentCount != null) {
                            inventoryMap.put(nanobotType, currentCount + Integer.parseInt(nanobotCount));
                        } else {
                            inventoryMap.put(nanobotType, Integer.parseInt(nanobotCount));
                        }
                    }
                } finally {
                    br.close();
                }
            } else {
                System.out.println("Could not find inventory file " + INVENTORY_FILENAME);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void decrement(String nanobotType) {
        inventoryMap.put(nanobotType, inventoryMap.get(nanobotType) - 1);
    }
}
