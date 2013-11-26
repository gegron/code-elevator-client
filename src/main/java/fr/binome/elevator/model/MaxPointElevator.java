package fr.binome.elevator.model;

import fr.binome.elevator.model.context.PointContext;

public class MaxPointElevator extends Elevator {

    private PointContext context = new PointContext();

    @Override
    public void go(Integer floorToGo) {
        context.go(floorToGo);
    }

    @Override
    public ElevatorResponse nextCommand() {
        return null;
    }

}
