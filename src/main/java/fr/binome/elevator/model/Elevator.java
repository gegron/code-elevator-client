package fr.binome.elevator.model;

import static fr.binome.elevator.model.ElevatorResponse.CLOSE;
import static fr.binome.elevator.model.ElevatorResponse.UP;

public abstract class Elevator {
    // Elevator rules
    protected final int MAX_LEVEL = 20;
    protected final int MIN_LEVEL = 0;

    // package protected field
    ElevatorResponse way = UP;
    int currentLevel = 0;
    ElevatorResponse stateDoors = CLOSE;

    public abstract void go(Integer floorToGo);

    public abstract void call(Integer atFloor, String way);

    public abstract ElevatorResponse nextCommand();

    public void reset(String cause) {
        way = UP;
        currentLevel = 0;
        stateDoors = CLOSE;
    }

}
