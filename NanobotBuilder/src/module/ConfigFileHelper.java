package module;

import java.util.Map;
import java.util.StringTokenizer;
import java.io.*;

public class ConfigFileHelper {

    public static void read(String fileName, Map<String, Integer> map) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(file));
                try {
                    String line = null;
                    while((line = br.readLine()) != null) {
                        StringTokenizer st = new StringTokenizer(line,",");
                        String name = st.nextToken();
                        String value = st.nextToken();
                        Integer currentValue = map.get(name);
                        if (currentValue != null) {
                            map.put(name, currentValue + Integer.parseInt(value));
                        } else {
                            map.put(name, Integer.parseInt(value));
                        }
                    }
                } finally {
                    br.close();
                }
            } else {
                System.out.println("Could not find open file " + fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
