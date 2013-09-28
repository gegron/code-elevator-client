package fr.binome.elevator.model;

import static fr.binome.elevator.model.ElevatorResponse.NOTHING;

public class BasicElevator {

    public Integer finalDestination;

    public void go(Integer floorToGo) {
        finalDestination = floorToGo;
    }

    public void call(Integer atFloor, String to) {
        //To change body of created methods use File | Settings | File Templates.
    }

    public ElevatorResponse nextCommand() {
        return NOTHING;
    }
}
