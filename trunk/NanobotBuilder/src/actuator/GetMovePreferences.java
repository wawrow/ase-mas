package actuator;

import com.agentfactory.logic.agent.Actuator;
import com.agentfactory.logic.lang.FOS;
import java.util.ArrayList;
import java.util.Random;

public class GetMovePreferences extends Actuator {

    private final String TOP = "top";
    private final String BOTTOM = "bottom";
    private final String LEFT = "left";
    private final String RIGHT = "right";
    private Random random = new Random();

    public boolean act(FOS action) {

        int x = Integer.parseInt(action.argAt(0).toString());
        int y = Integer.parseInt(action.argAt(1).toString());
        int targetX = Integer.parseInt(action.argAt(2).toString());
        int targetY = Integer.parseInt(action.argAt(3).toString());

        System.out.println(agent.getName() + ": calculating move preferences from (" + x + "," + y + ") to target (" + targetX + "," + targetY + ")");

        ArrayList<String> preferences = new ArrayList<String>();
        if (Math.abs(x - targetX) <= 1 &&
            Math.abs(y - targetY) <= 1 ) {
            adoptBelief("BELIEF(state(targetWithinOne)))");
        }
        
        if (targetX == x && targetY == y) {
            return true;
        } else if (targetX == x) {
            if (targetY > y) {
                preferences.add(TOP);
                addRandom(LEFT, RIGHT, preferences);
                preferences.add(BOTTOM);
            } else {
                preferences.add(BOTTOM);
                addRandom(LEFT, RIGHT, preferences);
                preferences.add(TOP);
            }
        } else if (targetY == y) {
            if (targetX > x) {
                preferences.add(RIGHT);
                addRandom(TOP, BOTTOM, preferences);
                preferences.add(LEFT);
            } else {
                preferences.add(LEFT);
                addRandom(TOP, BOTTOM, preferences);
                preferences.add(RIGHT);
            }            
        } else if (targetY > y & targetX > x) {
            addRandom(TOP, RIGHT, preferences);
            addRandom(BOTTOM, LEFT, preferences);
        } else if (targetY > y & targetX < x) {
            addRandom(TOP, LEFT, preferences);
            addRandom(BOTTOM, RIGHT, preferences);
        } else if (targetY < y & targetX < x) {
            addRandom(BOTTOM, LEFT, preferences);
            addRandom(TOP, RIGHT, preferences);
        } else {
            addRandom(BOTTOM, RIGHT, preferences);
            addRandom(TOP, LEFT, preferences);
        }

        adoptBelief("BELIEF(movePreferences(" + preferences.get(0) + "," + preferences.get(1) + "," +
                preferences.get(2) + "," + preferences.get(3) + "))");
        return true;
    }

    private void addRandom(String string1, String string2, ArrayList<String> preferences) {
        // Add 2 strings in random order to a list
        if (random.nextBoolean()) {
            preferences.add(string1);
            preferences.add(string2);
        } else {
            preferences.add(string2);
            preferences.add(string1);
        }
    }
}
