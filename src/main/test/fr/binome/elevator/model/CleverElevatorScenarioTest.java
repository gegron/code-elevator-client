package fr.binome.elevator.model;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static fr.binome.elevator.model.ElevatorResponse.*;
import static org.fest.assertions.Assertions.assertThat;

@Ignore
public class CleverElevatorScenarioTest {
    private CleverElevator cleverElevator;

    @Before
    public void setUp() throws Exception {
        cleverElevator = new CleverElevator();
    }

    @Test
    public void should_halt_level_where_go_order() {
        // Given
        cleverElevator.call(3, "UP");

        // Init
        assertThat(cleverElevator.nextCommand()).isEqualTo(UP);

        // 1st level
        assertThat(cleverElevator.nextCommand()).isEqualTo(UP);

        // 2nd level
        assertThat(cleverElevator.nextCommand()).isEqualTo(UP);

        // 3rd level
        assertThat(cleverElevator.nextCommand()).isEqualTo(OPEN);
        assertThat(cleverElevator.nextCommand()).isEqualTo(CLOSE);
        assertThat(cleverElevator.nextCommand()).isEqualTo(UP);

        cleverElevator.go(1);

        // 4rd level
        assertThat(cleverElevator.nextCommand()).isEqualTo(UP);

        // 5rd level
        assertThat(cleverElevator.nextCommand()).isEqualTo(DOWN);

        // 4rd level
        assertThat(cleverElevator.nextCommand()).isEqualTo(DOWN);

        // 3rd level
        assertThat(cleverElevator.nextCommand()).isEqualTo(DOWN);

        // 2nd level
        assertThat(cleverElevator.nextCommand()).isEqualTo(DOWN);

        // 1st level
        assertThat(cleverElevator.nextCommand()).isEqualTo(OPEN);
        assertThat(cleverElevator.nextCommand()).isEqualTo(CLOSE);
        assertThat(cleverElevator.nextCommand()).isEqualTo(DOWN);

        // 0 level
        assertThat(cleverElevator.nextCommand()).isEqualTo(UP);
    }

    @Test
    public void should_halt_level_where_call_order_in_same_way_of_elevator() {
        // Given
        cleverElevator.call(3, "UP");

        // Init
        assertThat(cleverElevator.nextCommand()).isEqualTo(UP);

        // 1st level
        assertThat(cleverElevator.nextCommand()).isEqualTo(UP);

        // 2nd level
        assertThat(cleverElevator.nextCommand()).isEqualTo(UP);

        // 3rd level
        assertThat(cleverElevator.nextCommand()).isEqualTo(OPEN);
        assertThat(cleverElevator.nextCommand()).isEqualTo(CLOSE);
        assertThat(cleverElevator.nextCommand()).isEqualTo(UP);
    }

    @Test
    public void should_not_halt_level_where_call_order_in_different_way_of_elevator() {
        // Given
        cleverElevator.call(3, "UP");

        // Init
        assertThat(cleverElevator.nextCommand()).isEqualTo(UP);

        // 1st level
        assertThat(cleverElevator.nextCommand()).isEqualTo(UP);

        // 2nd level
        assertThat(cleverElevator.nextCommand()).isEqualTo(UP);

        // Call with different way
        cleverElevator.call(4, "DOWN");

        // 3rd level
        assertThat(cleverElevator.nextCommand()).isEqualTo(OPEN);

        cleverElevator.go(5);

        assertThat(cleverElevator.nextCommand()).isEqualTo(CLOSE);
        assertThat(cleverElevator.nextCommand()).isEqualTo(UP);

        // 4rd level
        assertThat(cleverElevator.nextCommand()).isEqualTo(UP);

        // 5rd level
        assertThat(cleverElevator.nextCommand()).isEqualTo(OPEN);
        assertThat(cleverElevator.nextCommand()).isEqualTo(CLOSE);
        assertThat(cleverElevator.nextCommand()).isEqualTo(DOWN);

        // 4rd level
        assertThat(cleverElevator.nextCommand()).isEqualTo(OPEN);

        cleverElevator.go(3);

        assertThat(cleverElevator.nextCommand()).isEqualTo(CLOSE);
        assertThat(cleverElevator.nextCommand()).isEqualTo(DOWN);

        // 3rd level
        assertThat(cleverElevator.nextCommand()).isEqualTo(OPEN);
        assertThat(cleverElevator.nextCommand()).isEqualTo(CLOSE);
    }

    @Test
    public void should_halt_level0_when_call_for_UP() {
        // Given
        cleverElevator.currentLevel = 1;
        cleverElevator.way = DOWN;

        cleverElevator.call(0, "UP");

        // 1st level
        assertThat(cleverElevator.nextCommand()).isEqualTo(DOWN);

        // Level 0
        assertThat(cleverElevator.nextCommand()).isEqualTo(OPEN);
        assertThat(cleverElevator.nextCommand()).isEqualTo(CLOSE);
        assertThat(cleverElevator.nextCommand()).isEqualTo(UP);
    }

    @Test
    public void should_halt_level5_when_call_for_DOWN() {
        // Given
        cleverElevator.currentLevel = 4;

        cleverElevator.call(5, "DOWN");

        // 4th level
        assertThat(cleverElevator.nextCommand()).isEqualTo(UP);

        // 5th level
        assertThat(cleverElevator.nextCommand()).isEqualTo(OPEN);
        assertThat(cleverElevator.nextCommand()).isEqualTo(CLOSE);
        assertThat(cleverElevator.nextCommand()).isEqualTo(DOWN);
    }

}