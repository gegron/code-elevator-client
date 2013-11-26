package fr.binome.elevator.model;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import fr.binome.elevator.model.context.CallContext;
import fr.binome.elevator.model.context.ElevatorContext;

import java.util.List;

import static fr.binome.elevator.model.ElevatorResponse.*;

public class CleverElevator extends Elevator {

    // TODO: Rethink because of elevator empty + person calls at the current floor
    private boolean doorsAlreadyOpenAtThisLevel = false;

    private final ElevatorContext elevatorContext;

    private CallContext destinations;

    public CleverElevator(ElevatorContext elevatorContext) {
        this.elevatorContext = elevatorContext;

        destinations = new CallContext(elevatorContext.MIN_LEVEL, elevatorContext.MAX_LEVEL);
    }

    @Override
    public void reset(Integer lowerFloor, Integer higherFloor, Integer cabinSize) {
        super.reset(lowerFloor, higherFloor, cabinSize);

        doorsAlreadyOpenAtThisLevel = false;
        destinations = new CallContext(elevatorContext.MIN_LEVEL, elevatorContext.MAX_LEVEL);
    }

    @Override
    public void go(Integer floorToGo) {
        destinations.addCall(floorToGo);
    }

    @Override
    public ElevatorResponse nextCommand() {
        if (doorIsOpen()) {
            return closeDoor();
        }

        if (doorsMustOpenAtThisLevel(elevatorContext) && doorsCanOpenAtThisLevel() && !doorsAlreadyOpenAtThisLevel) {
            return openDoor();
        }
        else {
            return nextWay();
        }
    }

    @VisibleForTesting
    boolean doorsCanOpenAtThisLevel() {
        return destinations.hasCallAtThisLevel(currentLevel) || cabinPersonCount < CABIN_SIZE;
    }

    @VisibleForTesting
    boolean doorsMustOpenAtThisLevel(ElevatorContext elevatorContext) {
        CallContext calls = elevatorContext.getCalls(way);

        boolean mustOpen;

        // Special case if we are at the maximum level or minimum level:
        //  in that case we open the doors to passengers going in the other way (they can only go one way)
        if ((way == DOWN && currentLevel == getLowestCallLevel()) || (way == UP && currentLevel == getHighestCallLevel())) {
            mustOpen = hasAtLeastOneCallNoMatterWay(currentLevel);
        }
        else {
            mustOpen = (calls != null && calls.hasCallAtThisLevel(currentLevel));
        }

        return (destinations.hasCallAtThisLevel(currentLevel) || mustOpen);
    }

    @VisibleForTesting
    ElevatorResponse nextWay() {
        doorsAlreadyOpenAtThisLevel = false;

        if (way == UP && !needToGoUp()) {
            if (needToGoDown()) {
                way = DOWN;
            }
            else {
                way = NOTHING;
            }
        }

        if (way == DOWN && !needToGoDown()) {
            if (needToGoUp()) {
                way = UP;
            }
            else {
                way = NOTHING;
            }
        }

        if (way == NOTHING) {
            if (needToGoUp()) {
                way = UP;
            }
            else if (needToGoDown()) {
                way = DOWN;
            }
        }

        adjustLevel();

        return way;
    }

    @VisibleForTesting
    Integer getHighestCallLevel() {
        List<Integer> allCalls = Lists.newArrayList();

        allCalls.addAll(destinations.getCallLevels());
        allCalls.addAll(elevatorContext.getCalls(ElevatorResponse.UP).getCallLevels());
        allCalls.addAll(elevatorContext.getCalls(ElevatorResponse.DOWN).getCallLevels());

        return allCalls.isEmpty() ? MAX_LEVEL : Ordering.natural().max(allCalls);
    }

    @VisibleForTesting
    Integer getLowestCallLevel() {
        List<Integer> allCalls = Lists.newArrayList();

        allCalls.addAll(destinations.getCallLevels());
        allCalls.addAll(elevatorContext.getCalls(UP).getCallLevels());
        allCalls.addAll(elevatorContext.getCalls(DOWN).getCallLevels());

        return allCalls.isEmpty() ? MIN_LEVEL : Ordering.natural().min(allCalls);
    }

    private void adjustLevel() {
        if (way == UP) {
            currentLevel++;
        }
        else if (way == DOWN) {
            currentLevel--;
        }
    }

    private boolean hasAtLeastOneCallNoMatterWay(int level) {
        return (elevatorContext.getCalls(UP).hasCallAtThisLevel(level) || elevatorContext.getCalls(DOWN).hasCallAtThisLevel(level));
    }

    private boolean needToGoUp() {
        return (atLeastOneHeadedUp() || atLeastOneCallHigher());
    }

    private boolean needToGoDown() {
        return (atLeastOneHeadedDown() || atLeastOneCallLower());
    }

    private boolean atLeastOneHeadedUp() {
        boolean res = false;

        for (int i = currentLevel + 1; i <= MAX_LEVEL; i++) {
            res = res || destinations.hasCallAtThisLevel(i);
        }

        return res;
    }

    private boolean atLeastOneHeadedDown() {
        boolean res = false;

        for (int i = currentLevel - 1; i >= MIN_LEVEL; i--) {
            res = res || destinations.hasCallAtThisLevel(i);
        }

        return res;
    }

    private boolean atLeastOneCallHigher() {
        boolean res = false;

        for (int i = currentLevel + 1; i <= MAX_LEVEL; i++) {
            res = res || hasAtLeastOneCallNoMatterWay(i);
        }

        return res;
    }

    private boolean atLeastOneCallLower() {
        boolean res = false;

        for (int i = currentLevel - 1; i >= MIN_LEVEL; i--) {
            res = res || hasAtLeastOneCallNoMatterWay(i);
        }

        return res;
    }

    private ElevatorResponse openDoor() {
        doorsAlreadyOpenAtThisLevel = true;


        destinations.resetCall(currentLevel);
        elevatorContext.getCalls(UP).resetCall(currentLevel);
        elevatorContext.getCalls(DOWN).resetCall(currentLevel);

        return stateDoors = OPEN;
    }

    private ElevatorResponse closeDoor() {
        return stateDoors = CLOSE;
    }

    private boolean doorIsOpen() {
        return OPEN == stateDoors;
    }

}
