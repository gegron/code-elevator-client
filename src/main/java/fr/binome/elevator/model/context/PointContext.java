package fr.binome.elevator.model.context;

import fr.binome.elevator.model.ElevatorResponse;

import java.util.HashMap;
import java.util.Map;

import static fr.binome.elevator.model.ElevatorResponse.DOWN;
import static fr.binome.elevator.model.ElevatorResponse.UP;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class PointContext {

    final static Integer BASE_POINT = 22;
    private Map<Integer, InformationLevel> callPoints = new HashMap<>();

    public Integer findNearestLevelWithMaxPoint(Integer currentLevel) {
        Integer nearestLevel = null;
        Integer maxPoint = 0;

        for (Map.Entry<Integer, InformationLevel> entry : callPoints.entrySet()) {
            if ((entry.getValue().points - Math.abs(currentLevel - entry.getKey())) > maxPoint) {
                nearestLevel = entry.getKey();
                maxPoint = entry.getValue().points - Math.abs(currentLevel - entry.getKey());
            }
        }

        return nearestLevel;
    }

    void decreaseAllLevelPoints() {
        for (InformationLevel informationLevel : callPoints.values()) {
            informationLevel.decreasePoints();
        }
    }

    public void call(Integer floorToGo, ElevatorResponse way) {
        InformationLevel informationLevel = callPoints.get(floorToGo);

        if (informationLevel == null) {
            informationLevel = new InformationLevel();
        }

        callPoints.put(floorToGo, informationLevel.addUser(way));
    }

    public Integer getPoints(Integer floor) {
        InformationLevel informationLevel = callPoints.get(floor);

        return informationLevel != null ? informationLevel.points : null;
    }

    private class InformationLevel {
        private Integer points = 0;

        private Integer userCount = 0;

        private Map<ElevatorResponse, Boolean> callWays = new HashMap<ElevatorResponse, Boolean>() {{
            put(UP, FALSE);
            put(DOWN, FALSE);
        }};

        private InformationLevel addUser(ElevatorResponse way) {
            this.points += BASE_POINT;
            this.userCount++;
            this.callWays.put(way, TRUE);

            return this;
        }

        public void decreasePoints() {
            //TODO: est-ce qu'il peut y avoir un nombre de points négatifs?
            points -= userCount;
        }
    }

}
