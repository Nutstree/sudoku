package nl.nutstree.sudoku.domain.autovalue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LocationsTest {

    private Locations locations;

    @BeforeEach
    public void setUp() {
        locations = SquareBoard.Factory.empty(Type.SQUARE_9X9)
                .getLocations();
    }

    @Test
    public void getLocationsInRow() {
        locations.getLocationsInSameRow(ImmutableLocation.of(0, 0)).stream()
                .forEach(location -> assertThat(location.getRow()).isEqualTo(0));
        locations.getLocationsInSameRow(ImmutableLocation.of(5, 0)).stream()
                .forEach(location -> assertThat(location.getRow()).isEqualTo(5));
        locations.getLocationsInSameRow(ImmutableLocation.of(8, 0)).stream()
                .forEach(location -> assertThat(location.getRow()).isEqualTo(8));
    }

    @Test
    public void getLocationsInColumn() {
        locations.getLocationsInSameColumn(ImmutableLocation.of(0, 1)).stream()
                .forEach(location -> assertThat(location.getColumn()).isEqualTo(1));
        locations.getLocationsInSameColumn(ImmutableLocation.of(0, 4)).stream()
                .forEach(location -> assertThat(location.getColumn()).isEqualTo(4));
        locations.getLocationsInSameColumn(ImmutableLocation.of(0, 7)).stream()
                .forEach(location -> assertThat(location.getColumn()).isEqualTo(7));
    }

    @Test
    public void getLocationsInQuadrant() {
        assertgetCellsInQuadrant(ImmutableLocation.of(4, 5));
        assertgetCellsInQuadrant(ImmutableLocation.of(6, 1));
        assertgetCellsInQuadrant(ImmutableLocation.of(0, 2));
    }

    private void assertgetCellsInQuadrant(Location location) {
        int expectedQuadrant = location.getQuadrant();

        locations.getLocationsInSameQuadrant(location).stream()
                .map(Location::getQuadrant)
                .forEach(result -> assertThat(result).isEqualTo(expectedQuadrant));
    }
}