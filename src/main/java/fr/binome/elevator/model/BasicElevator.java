package fr.binome.elevator.model;

import com.google.common.annotations.VisibleForTesting;

import static fr.binome.elevator.model.ElevatorResponse.*;

public class BasicElevator {

    // public field
    public Integer finalDestination;

    // package protected field
    ElevatorResponse way = UP;

    int currentLevel = 0;

    ElevatorResponse stateDoors = CLOSE;

    // private field
    private boolean doorsAlreadyOpenAtThisLevel = false;

    // Elevator rules
    private final int MAX_LEVEL = 5;

    private final int MIN_LEVEL = 0;

    public void go(Integer floorToGo) {
        finalDestination = floorToGo;
    }

    public void call(Integer atFloor, String to) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public ElevatorResponse nextCommand() {
        if (doorIsOpen()) {
            return closeDoor();
        }

        if (doorsAlreadyOpenAtThisLevel) {
            return goNextLevel();
        }
        else {
            return openDoor();
        }
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

        return stateDoors = OPEN;
    }

    private ElevatorResponse closeDoor() {
        return stateDoors = CLOSE;
    }

    private boolean doorIsOpen() {
        return OPEN == stateDoors;
    }

}
