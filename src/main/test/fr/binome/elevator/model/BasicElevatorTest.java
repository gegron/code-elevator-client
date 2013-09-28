package fr.binome.elevator.model;

import org.junit.Before;
import org.junit.Test;

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

}
