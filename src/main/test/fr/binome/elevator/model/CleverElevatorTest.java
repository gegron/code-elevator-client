package fr.binome.elevator.model;

import org.junit.Before;
import org.junit.Ignore;
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

    @Ignore
    @Test
    public void should_go_up_level_when_elevator_way_is_up() {
        // Given
        cleverElevator.way = UP;
        cleverElevator.currentLevel = 2;

        // When
        assertThat(cleverElevator.goNextLevel()).isEqualTo(UP);
        assertThat(cleverElevator.currentLevel).isEqualTo(3);
    }

    @Ignore
    @Test
    public void should_go_down_level_when_elevator_reach_max_level() {
        // Given
        cleverElevator.way = UP;
        cleverElevator.currentLevel = 5;

        // When
        assertThat(cleverElevator.goNextLevel()).isEqualTo(DOWN);
        assertThat(cleverElevator.way).isEqualTo(DOWN);
        assertThat(cleverElevator.currentLevel).isEqualTo(4);
    }

    @Ignore
    @Test
    public void should_go_up_level_when_elevator_reach_min_level() {
        // Given
        cleverElevator.way = DOWN;
        cleverElevator.currentLevel = 0;

        // When
        assertThat(cleverElevator.goNextLevel()).isEqualTo(UP);
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
    public void should_return_highest_level_in_up_map() {
        // Given
        cleverElevator.call(4, "UP");
        cleverElevator.call(3, "DOWN");
        cleverElevator.go(3);

        // When
        int highestLevelToGo = cleverElevator.getHighestLevelToGo();

        // Then
        assertThat(highestLevelToGo).isEqualTo(4);
    }

    @Test
    public void should_return_highest_level_in_down_map() {
        // Given
        cleverElevator.call(3, "UP");
        cleverElevator.call(5, "DOWN");
        cleverElevator.go(3);

        // When
        int highestLevelToGo = cleverElevator.getHighestLevelToGo();

        // Then
        assertThat(highestLevelToGo).isEqualTo(5);
    }

    @Test
    public void should_return_highest_level_in_go_map() {
        // Given
        cleverElevator.call(2, "UP");
        cleverElevator.call(3, "DOWN");
        cleverElevator.go(3);

        // When
        int highestLevelToGo = cleverElevator.getHighestLevelToGo();

        // Then
        assertThat(highestLevelToGo).isEqualTo(3);
    }

    @Test
    public void should_return_lowest_level_in_up_map() {
        // Given
        cleverElevator.call(2, "UP");
        cleverElevator.call(3, "DOWN");
        cleverElevator.go(3);

        // When
        int lowestLevelToGo = cleverElevator.getLowestLevelToGo();

        // Then
        assertThat(lowestLevelToGo).isEqualTo(2);
    }

    @Test
    public void should_return_lowest_level_in_down_map() {
        // Given
        cleverElevator.call(2, "UP");
        cleverElevator.call(1, "DOWN");
        cleverElevator.go(3);

        // When
        int lowestLevelToGo = cleverElevator.getLowestLevelToGo();

        // Then
        assertThat(lowestLevelToGo).isEqualTo(1);
    }

    @Test
    public void should_return_lowest_level_in_go_map() {
        // Given
        cleverElevator.call(5, "UP");
        cleverElevator.call(3, "DOWN");
        cleverElevator.go(2);

        // When
        int lowestLevelToGo = cleverElevator.getLowestLevelToGo();

        // Then
        assertThat(lowestLevelToGo).isEqualTo(2);
    }

}
