package fr.binome.elevator.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fr.binome.elevator.model.ElevatorResponse.*;

public class CleverElevator extends Elevator {

    private int capacityMax = 3;

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

        if (doorsMustOpenAtThisLevel()) {
            return openDoor();
        }
        else {
            return goNextLevel();
        }
    }

    private boolean elevatorCapacityAccept() {
        return userCount <= capacityMax || exceptionLevel();
    }

    private boolean exceptionLevel() {
        return (1 == currentLevel && callsDown.get(1)) || (4 == currentLevel && callsUp.get(4));
    }

    @VisibleForTesting
    boolean doorsMustOpenAtThisLevel() {
        return (destinations.get(currentLevel) || (getCalls(way).get(currentLevel) && elevatorCapacityAccept()));
    }

    @VisibleForTesting
    ElevatorResponse goNextLevel() {

        if (way == UP && currentLevel < getHighestLevelToGo()) {
            currentLevel++;
        }

        if (way == DOWN && currentLevel > getLowestLevelToGo()) {
            currentLevel--;
        }

        ElevatorResponse result = way;

        initWayForNextTick();

        return result;
    }

    @VisibleForTesting
    int getLowestLevelToGo() {
        List<Integer> levelToHalt = new ArrayList<Integer>();

        Iterables.addAll(levelToHalt, filterTrueValue(destinations));
        Iterables.addAll(levelToHalt, filterTrueValue(callsUp));
        Iterables.addAll(levelToHalt, filterTrueValue(callsDown));

        return Ordering.<Integer>natural().min(levelToHalt);
    }

    @VisibleForTesting
    int getHighestLevelToGo() {
        List<Integer> levelToHalt = new ArrayList<Integer>();

        Iterables.addAll(levelToHalt, filterTrueValue(destinations));
        Iterables.addAll(levelToHalt, filterTrueValue(callsUp));
        Iterables.addAll(levelToHalt, filterTrueValue(callsDown));

        return Ordering.<Integer>natural().max(levelToHalt);
    }

    private Iterable<Integer> filterTrueValue(final Map<Integer, Boolean> map) {
        return Iterables.filter(map.keySet(), new Predicate<Integer>() {
            @Override
            public boolean apply(@Nullable Integer level) {
                return map.get(level);
            }
        });
    }

    private void initWayForNextTick() {
        if (way == UP && currentLevel == getHighestLevelToGo()) {
            way = DOWN;
        }
        else if (way == DOWN && currentLevel == getLowestLevelToGo()) {
            way = UP;
        }
    }

    private ElevatorResponse openDoor() {

        destinations.put(currentLevel, false);
        callsUp.put(currentLevel, false);
        callsDown.put(currentLevel, false);

        return stateDoors = OPEN;
    }

    private ElevatorResponse closeDoor() {
        return stateDoors = CLOSE;
    }

    private boolean doorIsOpen() {
        return OPEN == stateDoors;
    }

}
