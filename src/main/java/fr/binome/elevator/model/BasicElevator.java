package fr.binome.elevator.model;

public class BasicElevator {

    public Integer finalDestination;

    public void go(Integer floorToGo) {
        finalDestination = floorToGo;
    }

}
