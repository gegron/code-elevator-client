package fr.binome.elevator.model.context;

import fr.binome.elevator.model.CleverElevator;
import fr.binome.elevator.model.Elevator;
import fr.binome.elevator.model.ElevatorResponse;

import java.util.HashMap;
import java.util.Map;

import static fr.binome.elevator.model.ElevatorResponse.DOWN;
import static fr.binome.elevator.model.ElevatorResponse.UP;

public class ElevatorContext {
    // Elevator rules
    public static int MAX_LEVEL;
    public static int MIN_LEVEL;
    public static int CABIN_COUNT;

    private Map<Integer, Elevator> elevators;

    private CallContext callsUp;
    private CallContext callsDown;

    public ElevatorContext() {
        // set default values
        reset(0, 20, 100, 2, "First context initialization.");
    }

    private void initElevatorContext() {
        elevators = new HashMap<>(CABIN_COUNT);

        for (int i = 0; i < CABIN_COUNT; i++) {
            elevators.put(i, new CleverElevator(this));
        }

        callsUp = new CallContext(MIN_LEVEL, MAX_LEVEL);
        callsDown = new CallContext(MIN_LEVEL, MAX_LEVEL);
    }

    public void go(Integer cabin, Integer floorToGo) {
        elevators.get(cabin).go(floorToGo);
    }

    public void call(Integer floorLevel, String way) {
        CallContext calls = getCalls(way);

        calls.addCall(floorLevel);
    }

    public String nextCommands() {
        StringBuilder sb = new StringBuilder();

        for (Elevator elevator : elevators.values()) {
            sb.append(elevator.nextCommand()).append("\n");
        }

        return sb.toString();
    }

    public void reset(Integer lowerFloor, Integer higherFloor, Integer cabinSize, Integer cabinCount, String cause) {
        // set default values
        MIN_LEVEL = lowerFloor;
        MAX_LEVEL = higherFloor;
        CABIN_COUNT = cabinCount;

        initElevatorContext();

        for (Elevator elevator : elevators.values()) {
            elevator.reset(lowerFloor, higherFloor, cabinSize);
        }
    }

    public void userHasEntered(Integer cabin) {
        elevators.get(cabin).userHasEntered();
    }

    public void userHasExited(Integer cabin) {
        elevators.get(cabin).userHasExited();
    }

    public CallContext getCalls(ElevatorResponse inWay) {
        return getCalls(inWay.name());
    }

    private CallContext getCalls(String way) {
        if (ElevatorResponse.valueOf(way) == UP) {
            return callsUp;
        }

        if (ElevatorResponse.valueOf(way) == DOWN) {
            return callsDown;
        }

        return null;
    }

    public Boolean higherCallExist(Integer currentLevel) {
        return callsUp.higherCallExist(currentLevel) || callsDown.higherCallExist(currentLevel);
    }

    public Boolean lowerCallExist(int currentLevel) {
        return callsUp.lowerCallExist(currentLevel) || callsDown.lowerCallExist(currentLevel);
    }

    public Boolean hasAtLeastOneCall(int currentLevel) {
        return callsUp.hasCallAtThisLevel(currentLevel) || callsDown.hasCallAtThisLevel(currentLevel);
    }

}
