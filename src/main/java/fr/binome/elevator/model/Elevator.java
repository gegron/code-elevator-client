package fr.binome.elevator.model;

import static fr.binome.elevator.model.ElevatorResponse.CLOSE;
import static fr.binome.elevator.model.ElevatorResponse.UP;

public abstract class Elevator {
    // Elevator rules
    public static int MAX_LEVEL = 20;
    public static int MIN_LEVEL = 0;
    public static int CABIN_SIZE = 100;

    // package protected field
    ElevatorResponse way = UP;
    ElevatorResponse stateDoors = CLOSE;
    int currentLevel = 0;
    int cabinPersonCount = 0;

    public abstract void go(Integer floorToGo);

    public abstract ElevatorResponse nextCommand();

    public void reset(Integer lowerFloor, Integer higherFloor, Integer cabinSize) {
        MIN_LEVEL = lowerFloor;
        MAX_LEVEL = higherFloor;
        CABIN_SIZE = cabinSize;

        way = UP;
        currentLevel = 0;
        stateDoors = CLOSE;
        cabinPersonCount = 0;
    }

    public void userHasEntered() {
        cabinPersonCount++;
    }

    public void userHasExited() {
        cabinPersonCount--;
    }

}
