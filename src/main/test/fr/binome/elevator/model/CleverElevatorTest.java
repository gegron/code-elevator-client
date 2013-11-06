package fr.binome.elevator.model;

import org.junit.Before;
import org.junit.Test;

import static fr.binome.elevator.model.ElevatorResponse.DOWN;
import static fr.binome.elevator.model.ElevatorResponse.UP;
import static org.fest.assertions.Assertions.assertThat;

public class CleverElevatorTest {
    private CleverElevator cleverElevator;

    @Before
    public void setUp() throws Exception {
        cleverElevator = new CleverElevator();
    }

    @Test
    public void should_go_up_level_when_elevator_way_is_up_and_call_higer() {
        // Given
        cleverElevator.way = UP;
        cleverElevator.currentLevel = 2;
        cleverElevator.call(4, "UP");

        // When
        assertThat(cleverElevator.nextWay()).isEqualTo(UP);
        assertThat(cleverElevator.currentLevel).isEqualTo(3);
    }

    @Test
    public void should_go_down_level_when_elevator_reach_max_level_and_call_lower() {
        // Given
        cleverElevator.way = UP;
        cleverElevator.currentLevel = 5;
        cleverElevator.call(4, "DOWN");

        // When
        assertThat(cleverElevator.nextWay()).isEqualTo(DOWN);
        assertThat(cleverElevator.way).isEqualTo(DOWN);
        assertThat(cleverElevator.currentLevel).isEqualTo(4);
    }

    @Test
    public void should_go_up_level_when_elevator_reach_min_level_and_call_higher() {
        // Given
        cleverElevator.way = DOWN;
        cleverElevator.currentLevel = 0;
        cleverElevator.call(3, "UP");

        // When
        assertThat(cleverElevator.nextWay()).isEqualTo(UP);
        assertThat(cleverElevator.way).isEqualTo(UP);
        assertThat(cleverElevator.currentLevel).isEqualTo(1);
    }

    @Test
    public void should_open_doors_at_level4_when_call_at_level4() {
        // Given
        cleverElevator.call(4, "UP");

        cleverElevator.currentLevel = 4;

        // When
        boolean result = cleverElevator.doorsMustOpenAtThisLevel();

        // Then
        assertThat(result).isTrue();
    }

    @Test
    public void should_not_open_doors_at_level4_when_call_at_level4_but_elevator_is_level3() {
        // Given
        cleverElevator.call(4, "UP");

        cleverElevator.currentLevel = 3;

        // When
        boolean result = cleverElevator.doorsMustOpenAtThisLevel();

        // Then
        assertThat(result).isFalse();
    }

    @Test
    public void should_open_doors_at_level4_when_user_want_to_go_level4() {
        // Given
        cleverElevator.go(4);

        cleverElevator.currentLevel = 4;

        // When
        boolean result = cleverElevator.doorsMustOpenAtThisLevel();

        // Then
        assertThat(result).isTrue();
    }

    @Test
    public void should_not_open_doors_at_level4_when_user_want_to_go_level5() {
        // Given
        cleverElevator.go(5);

        cleverElevator.currentLevel = 4;

        // When
        boolean result = cleverElevator.doorsMustOpenAtThisLevel();

        // Then
        assertThat(result).isFalse();
    }

    @Test
    public void should_return_highest_level_with_call() {
        assertThat(cleverElevator.getHighestCallLevel()).isEqualTo(Elevator.MAX_LEVEL);

        cleverElevator.call(1, "DOWN");
        assertThat(cleverElevator.getHighestCallLevel()).isEqualTo(1);

        cleverElevator.call(0, "DOWN");
        assertThat(cleverElevator.getHighestCallLevel()).isEqualTo(1);

        cleverElevator.call(4, "DOWN");
        assertThat(cleverElevator.getHighestCallLevel()).isEqualTo(4);
    }

    @Test
    public void should_return_lowest_level_with_call() {
        assertThat(cleverElevator.getLowestCallLevel()).isEqualTo(Elevator.MIN_LEVEL);

        cleverElevator.call(4, "DOWN");
        assertThat(cleverElevator.getLowestCallLevel()).isEqualTo(4);

        cleverElevator.call(5, "DOWN");
        assertThat(cleverElevator.getLowestCallLevel()).isEqualTo(4);

        cleverElevator.call(2, "DOWN");
        assertThat(cleverElevator.getLowestCallLevel()).isEqualTo(2);

        cleverElevator.call(0, "DOWN");
        assertThat(cleverElevator.getLowestCallLevel()).isEqualTo(0);
    }

}
