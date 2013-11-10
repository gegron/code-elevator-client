package fr.binome.elevator.model;

import fr.binome.elevator.model.context.PointContext;

public class MaxPointElevator extends Elevator {

    private PointContext context = new PointContext();

    @Override
    public void go(Integer floorToGo) {
    }

    @Override
    public void call(Integer atFloor, String way) {
        context.call(atFloor, ElevatorResponse.valueOf(way));
    }

    @Override
    public ElevatorResponse nextCommand() {
        return null;
    }

}
