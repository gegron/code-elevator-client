package fr.binome.elevator.model;

import com.google.common.annotations.VisibleForTesting;

import static fr.binome.elevator.model.ElevatorResponse.*;

public class BasicElevator extends Elevator {
    // public field
    public Integer finalDestination;

    // private field
    private boolean doorsAlreadyOpenAtThisLevel = false;

    @Override
    public void go(Integer floorToGo) {
        finalDestination = floorToGo;
    }

    @Override
    public void call(Integer atFloor, String way) {
        //To change body of created methods use File | Settings | File Templates.
    }

    @Override
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

    @Override
    public void reset(Integer lowerFloor, Integer higherFloor, Integer cabinSize, String cause) {
        super.reset(lowerFloor, higherFloor, cabinSize, cause);

        doorsAlreadyOpenAtThisLevel = false;
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
