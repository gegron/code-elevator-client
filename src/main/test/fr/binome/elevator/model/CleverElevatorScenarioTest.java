package fr.binome.elevator.model;

import org.junit.Before;
import org.junit.Test;

import static fr.binome.elevator.model.ElevatorResponse.*;
import static org.fest.assertions.Assertions.assertThat;

public class CleverElevatorScenarioTest {
    private CleverElevator cleverElevator;

    @Before
    public void setUp() throws Exception {
        cleverElevator = new CleverElevator();
    }

    @Test
    public void should_halt_level_where_go_or_call_order() {
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
    
}
