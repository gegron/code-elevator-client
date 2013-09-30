package fr.binome.elevator.model;

import com.google.common.annotations.VisibleForTesting;

import java.util.HashMap;
import java.util.Map;

import static fr.binome.elevator.model.ElevatorResponse.*;

public class CleverElevator extends Elevator {

    /** TODO: Rethink because of elevator empty + person calls at the current floor **/
    private boolean doorsAlreadyOpenAtThisLevel = false;

    private Map<Integer, Boolean> destinations = new HashMap<Integer, Boolean>() {{
        for (int i = MIN_LEVEL; i <= MAX_LEVEL; i++) {
            put(i, false);
        }
    }};

    private Map<Integer, Boolean> callsUp = new HashMap<Integer, Boolean>() {{
        for (int i = MIN_LEVEL; i <= MAX_LEVEL; i++) {
            put(i, false);
        }
    }};

    private Map<Integer, Boolean> callsDown = new HashMap<Integer, Boolean>() {{
        for (int i = MIN_LEVEL; i <= MAX_LEVEL; i++) {
            put(i, false);
        }
    }};

    private Map<Integer, Boolean> getCalls(String inWay) {
        if (ElevatorResponse.valueOf(inWay) == UP) {
            return callsUp;
        }
        if (ElevatorResponse.valueOf(inWay) == DOWN) {
            return callsDown;
        }

        return null;
    }

    private Map<Integer, Boolean> getCalls(ElevatorResponse inWay) {
        return getCalls(inWay.name());
    }

    @Override
    public void go(Integer floorToGo) {
        destinations.put(floorToGo, true);
    }

    @Override
    public void call(Integer atFloor, String inWay) {
        getCalls(inWay).put(atFloor, true);
    }

    @Override
    public ElevatorResponse nextCommand() {
        if (doorIsOpen()) {
            return closeDoor();
        }

        if (doorsMustOpenAtThisLevel() && !doorsAlreadyOpenAtThisLevel) {
            return openDoor();
        }
        else {
            return goNextLevel();
        }
    }

    @VisibleForTesting
    boolean doorsMustOpenAtThisLevel() {
        return (destinations.get(currentLevel) || getCalls(way).get(currentLevel));
    }

    @VisibleForTesting
    ElevatorResponse goNextLevel() {
        doorsAlreadyOpenAtThisLevel = false;

        if (way == UP && currentLevel == MAX_LEVEL) {
            way = DOWN;
        }
        else if (way == DOWN && currentLevel == MIN_LEVEL) {
            way = UP;
        }

        if (way == UP) {
            currentLevel++;
        }
        else {
            currentLevel--;
        }

        return way;
    }

    private ElevatorResponse openDoor() {
        doorsAlreadyOpenAtThisLevel = true;

        destinations.put(currentLevel, false);
        getCalls(way).put(currentLevel, false);

        return stateDoors = OPEN;
    }

    private ElevatorResponse closeDoor() {
        return stateDoors = CLOSE;
    }

    private boolean doorIsOpen() {
        return OPEN == stateDoors;
    }

}
