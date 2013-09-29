package fr.binome.elevator.model;

import org.junit.Before;
import org.junit.Test;

import static fr.binome.elevator.model.ElevatorResponse.*;
import static org.fest.assertions.Assertions.assertThat;

public class BasicElevatorScenarioTest {

    private BasicElevator basicElevator;

    @Before
    public void setUp() throws Exception {
        basicElevator = new BasicElevator();
    }

    @Test
    public void should_halt_each_level_until_reach_finale_destination() {
        // Init
        assertThat(basicElevator.nextCommand()).isEqualTo(OPEN);
        assertThat(basicElevator.nextCommand()).isEqualTo(CLOSE);
        assertThat(basicElevator.nextCommand()).isEqualTo(UP);

        // 1st level
        assertThat(basicElevator.nextCommand()).isEqualTo(OPEN);
        assertThat(basicElevator.nextCommand()).isEqualTo(CLOSE);
        assertThat(basicElevator.nextCommand()).isEqualTo(UP);

        // 2nd level
        assertThat(basicElevator.nextCommand()).isEqualTo(OPEN);
        assertThat(basicElevator.nextCommand()).isEqualTo(CLOSE);
        assertThat(basicElevator.nextCommand()).isEqualTo(UP);

        // 3rd level
        assertThat(basicElevator.nextCommand()).isEqualTo(OPEN);
        assertThat(basicElevator.nextCommand()).isEqualTo(CLOSE);
        assertThat(basicElevator.nextCommand()).isEqualTo(UP);

        // 4rd level
        assertThat(basicElevator.nextCommand()).isEqualTo(OPEN);
        assertThat(basicElevator.nextCommand()).isEqualTo(CLOSE);
        assertThat(basicElevator.nextCommand()).isEqualTo(UP);

        // 5rd level
        assertThat(basicElevator.nextCommand()).isEqualTo(OPEN);
        assertThat(basicElevator.nextCommand()).isEqualTo(CLOSE);
        assertThat(basicElevator.nextCommand()).isEqualTo(DOWN);

        // 4rd level
        assertThat(basicElevator.nextCommand()).isEqualTo(OPEN);
        assertThat(basicElevator.nextCommand()).isEqualTo(CLOSE);
        assertThat(basicElevator.nextCommand()).isEqualTo(DOWN);

        // 3rd level
        assertThat(basicElevator.nextCommand()).isEqualTo(OPEN);
        assertThat(basicElevator.nextCommand()).isEqualTo(CLOSE);
        assertThat(basicElevator.nextCommand()).isEqualTo(DOWN);

        // 2nd level
        assertThat(basicElevator.nextCommand()).isEqualTo(OPEN);
        assertThat(basicElevator.nextCommand()).isEqualTo(CLOSE);
        assertThat(basicElevator.nextCommand()).isEqualTo(DOWN);

        // 1st level
        assertThat(basicElevator.nextCommand()).isEqualTo(OPEN);
        assertThat(basicElevator.nextCommand()).isEqualTo(CLOSE);
        assertThat(basicElevator.nextCommand()).isEqualTo(DOWN);

        // 0 level
        assertThat(basicElevator.nextCommand()).isEqualTo(OPEN);
        assertThat(basicElevator.nextCommand()).isEqualTo(CLOSE);
        assertThat(basicElevator.nextCommand()).isEqualTo(UP);
    }

}
