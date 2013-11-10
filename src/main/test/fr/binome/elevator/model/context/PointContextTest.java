package fr.binome.elevator.model.context;

import org.junit.Test;

import static fr.binome.elevator.model.ElevatorResponse.UP;
import static org.fest.assertions.Assertions.assertThat;

public class PointContextTest {

    @Test
    public void should_decrease_call_points_with_two_personn_on_same_level() {
        // Given
        PointContext pointContext = new PointContext();

        pointContext.call(2, UP);
        pointContext.call(2, UP);

        // When
        pointContext.decreaseAllLevelPoints();

        // Then
        assertThat(pointContext.getPoints(2)).isEqualTo((2 * PointContext.BASE_POINT) - 2);
    }

    @Test
    public void should_find_nearest_level_return_null_when_no_call() {
        // Given
        PointContext pointContext = new PointContext();

        // When
        Integer result = pointContext.findCallLevelWithMaxPoints(3);

        // Then
        assertThat(result).isNull();
    }


    @Test
    public void should_find_nearest_level_when_level_has_same_point() {
        // Given
        PointContext pointContext = new PointContext();

        pointContext.call(2, UP);
        pointContext.call(4, UP);

        // When
        Integer result = pointContext.findCallLevelWithMaxPoints(1);

        // Then
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void should_find_level_with_max_point_even_if_not_nearest_level() {
        // Given
        PointContext pointContext = new PointContext();

        pointContext.call(2, UP);
        pointContext.call(4, UP);
        pointContext.call(4, UP);

        // When
        Integer result = pointContext.findCallLevelWithMaxPoints(1);

        // Then
        assertThat(result).isEqualTo(4);
    }

    @Test
    public void should_find_level_nearest_because_cost_to_go() {
        // Given
        PointContext pointContext = new PointContext();

        pointContext.call(2, UP);
        pointContext.decreaseAllLevelPoints();

        pointContext.call(4, UP);

        // When
        Integer result = pointContext.findCallLevelWithMaxPoints(1);

        // Then
        assertThat(result).isEqualTo(2);
    }

}
