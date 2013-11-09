package fr.binome.elevator.model;

import org.junit.Before;
import org.junit.Test;

import static fr.binome.elevator.model.ElevatorResponse.DOWN;
import static fr.binome.elevator.model.ElevatorResponse.UP;
import static org.fest.assertions.Assertions.assertThat;

public class BasicElevatorTest {

    private BasicElevator basicElevator;

    @Before
    public void setUp() throws Exception {
        basicElevator = new BasicElevator();
    }

    @Test
    public void should_manage_go_call() {
        // Given
        Integer floorToGo = 3;

        // When
        basicElevator.go(floorToGo);

        // Then
        assertThat(basicElevator.finalDestination).isEqualTo(3);
    }

    @Test
    public void should_go_up_level_when_elevator_way_is_up() {
        // Given
        basicElevator.way = UP;
        basicElevator.currentLevel = 2;

        // When
        assertThat(basicElevator.goNextLevel()).isEqualTo(UP);
        assertThat(basicElevator.currentLevel).isEqualTo(3);
    }

    @Test
    public void should_go_down_level_when_elevator_reach_max_level() {
        // Given
        basicElevator.way = UP;
        basicElevator.currentLevel = Elevator.MAX_LEVEL;

        // When
        assertThat(basicElevator.goNextLevel()).isEqualTo(DOWN);
        assertThat(basicElevator.way).isEqualTo(DOWN);
        assertThat(basicElevator.currentLevel).isEqualTo(Elevator.MAX_LEVEL - 1);
    }

    @Test
    public void should_go_up_level_when_elevator_reach_min_level() {
        // Given
        basicElevator.way = DOWN;
        basicElevator.currentLevel = 0;

        // When
        assertThat(basicElevator.goNextLevel()).isEqualTo(UP);
        assertThat(basicElevator.way).isEqualTo(UP);
        assertThat(basicElevator.currentLevel).isEqualTo(1);
    }

}
