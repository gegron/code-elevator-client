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
    private Map<Integer, InformationLevel> goPoints = new HashMap<>();

    public void call(Integer floorToGo, ElevatorResponse way) {
        addPoints(callPoints, floorToGo, way);
    }

    public void go(Integer floorToGo) {
        addPoints(goPoints, floorToGo, null);
    }

    public Integer findCallLevelWithMaxPoints(Integer currentLevel) {
        return findNearestLevelWithMaxPoints(callPoints, currentLevel);
    }

    public Integer findGoLevelWithMaxPoints(Integer currentLevel) {
        return findNearestLevelWithMaxPoints(goPoints, currentLevel);
    }

    public Integer getPoints(Integer floor) {
        InformationLevel informationLevel = callPoints.get(floor);

        return informationLevel != null ? informationLevel.points : null;
    }

    Integer findNearestLevelWithMaxPoints(Map<Integer, InformationLevel> points, Integer currentLevel) {
        Integer nearestLevel = null;
        Integer maxPoint = 0;

        for (Map.Entry<Integer, InformationLevel> entry : points.entrySet()) {
            if ((entry.getValue().points - Math.abs(currentLevel - entry.getKey())) > maxPoint) {
                nearestLevel = entry.getKey();
                maxPoint = entry.getValue().points - Math.abs(currentLevel - entry.getKey());
            }
        }

        return nearestLevel;
    }

    void addPoints(Map<Integer, InformationLevel> points, Integer floorToGo, ElevatorResponse way) {
        InformationLevel informationLevel = points.get(floorToGo);

        if (informationLevel == null) {
            informationLevel = new InformationLevel();
        }

        points.put(floorToGo, informationLevel.addUser(way));
    }

    void decreaseAllLevelPoints() {
        for (InformationLevel informationLevel : callPoints.values()) {
            informationLevel.decreasePoints();
        }
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
