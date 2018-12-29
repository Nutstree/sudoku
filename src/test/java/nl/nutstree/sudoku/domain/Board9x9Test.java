package nl.nutstree.sudoku.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Board9x9Test {

    private Board9x9 board;

    @BeforeEach
    public void setUp() {
        board = new Board9x9();
    }

    @Test
    public void emptyBoard() {
        assertBoardEmpty(board);
    }

    private void assertBoardEmpty(Board9x9 board) {
        for (int row = 0; row < board.getSize(); row++) {
            for (int column = 1; column < board.getSize(); column++) {
                Location location = Location.of(row, column);

                assertThatLocationIsUntouched(location);
            }
        }
    }

    private void assertThatLocationIsUntouched(Location location) {
        assertThat(board.getValue(location)).isEqualTo(0);
        assertThat(board.getPossibilities(location)).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    public void constructBoardWithSudokuPuzzle_nonZeroFieldsCorrectlyFilled() {
        board = new Board9x9("010020300004005060070000008006900070000100002030048000500006040000800106008000000");

        // 010020300
        assertThat(board.getValue(Location.of(1, 0))).isEqualTo(1);
        assertThat(board.getValue(Location.of(4, 0))).isEqualTo(2);
        assertThat(board.getValue(Location.of(6, 0))).isEqualTo(3);
        assertThat(board.getValue(Location.of(0, 0))).isEqualTo(0);

        // 004005060
        assertThat(board.getValue(Location.of(2, 1))).isEqualTo(4);
        assertThat(board.getValue(Location.of(5, 1))).isEqualTo(5);
        assertThat(board.getValue(Location.of(7, 1))).isEqualTo(6);

        // 070000008
        assertThat(board.getValue(Location.of(1, 2))).isEqualTo(7);
        assertThat(board.getValue(Location.of(8, 2))).isEqualTo(8);

        // 006900070
        assertThat(board.getValue(Location.of(2, 3))).isEqualTo(6);
        assertThat(board.getValue(Location.of(3, 3))).isEqualTo(9);
        assertThat(board.getValue(Location.of(7, 3))).isEqualTo(7);

        // 000100002
        assertThat(board.getValue(Location.of(3, 4))).isEqualTo(1);
        assertThat(board.getValue(Location.of(8, 4))).isEqualTo(2);

        // 030048000
        assertThat(board.getValue(Location.of(1, 5))).isEqualTo(3);
        assertThat(board.getValue(Location.of(4, 5))).isEqualTo(4);
        assertThat(board.getValue(Location.of(5, 5))).isEqualTo(8);

        // 500006040
        assertThat(board.getValue(Location.of(0, 6))).isEqualTo(5);
        assertThat(board.getValue(Location.of(5, 6))).isEqualTo(6);
        assertThat(board.getValue(Location.of(7, 6))).isEqualTo(4);

        // 000800106
        assertThat(board.getValue(Location.of(3, 7))).isEqualTo(8);
        assertThat(board.getValue(Location.of(6, 7))).isEqualTo(1);
        assertThat(board.getValue(Location.of(8, 7))).isEqualTo(6);

        // 008000000
        assertThat(board.getValue(Location.of(2, 8))).isEqualTo(8);
    }

    @Test
    public void constructBoardWithSudokuPuzzle_zeroFieldsStillEmpty() {
        board = new Board9x9("010020300004005060070000008006900070000100002030048000500006040000800106008000000");

        // random pick to check if fields are left empty
        assertThat(board.getValue(Location.of(0, 0))).isEqualTo(0);
        assertThat(board.getValue(Location.of(6, 1))).isEqualTo(0);
        assertThat(board.getValue(Location.of(3, 2))).isEqualTo(0);
        assertThat(board.getValue(Location.of(8, 3))).isEqualTo(0);
        assertThat(board.getValue(Location.of(2, 4))).isEqualTo(0);
        assertThat(board.getValue(Location.of(7, 5))).isEqualTo(0);
        assertThat(board.getValue(Location.of(1, 6))).isEqualTo(0);
        assertThat(board.getValue(Location.of(4, 7))).isEqualTo(0);
        assertThat(board.getValue(Location.of(5, 8))).isEqualTo(0);
    }

    @Test
    public void toStringTest() {
        board = new Board9x9("010020300004005060070000008006900070000100002030048000500006040000800106008000000");

        System.out.println(board);
        assertThat(board.toString()).contains(Board9x9.BOARD_NAME);
    }

    @Test
    public void setIllegalValues_throwsExeption() {
        assertThatThrownBy(() -> board.setValue(0, Location.of(0, 0)))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> board.setValue(-1, Location.of(2, 8)))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> board.setValue(10, Location.of(4, 1)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void setLegalValues_containsValueAfterSetting() {
        IntStream.rangeClosed(1, 9)
        .forEach(this::assertSetValueCorrect);
    }

    private void assertSetValueCorrect(int value) {
        Location location = Location.of(6, 6);
        board.setValue(value, location);

        assertThat(board.getValue(location)).isEqualTo(value);
        assertThat(board.getPossibilities(location)).isEmpty();
    }

    @Test
    public void afterSettingValueOnlocation_relatedCellsPossibilityAdjusted() {
        Location location = Location.of(7, 7);
        Collection<Location> relatedLocations = board.getRelatedLocations(location);
        Collection<Location> unrelatedLocations = getUnrelatedLocations(relatedLocations);

        board.setValue(5, location);

        assertLocationsDoesNotContainPossibility(relatedLocations, 5);
        assertOtherLocationsNotChanged(unrelatedLocations);
    }

    private void assertLocationsDoesNotContainPossibility(Collection<Location> locations, int possibility) {
        locations.stream()
                .forEach(location -> assertLocationDoesNotContainPossibility(location, possibility));
    }

    private void assertLocationDoesNotContainPossibility(Location location, int possibility) {
        Collection<Integer> possibilities = board.getPossibilities(location);

        assertThat(possibilities).doesNotContain(possibility);
    }

    private void assertOtherLocationsNotChanged(Collection<Location> unrelatedLocations) {
        unrelatedLocations.stream()
                .forEach(this::assertThatLocationIsUntouched);
    }

    private Collection<Location> getUnrelatedLocations(Collection<Location> relatedLocations) {
        Collection<Location> locations = new HashSet<Location>(board.getAllLocations());
        locations.removeAll(relatedLocations);

        return locations;
    }

    @Test
    public void getLocationsInRow() {
        board.getLocationsInRow(Location.of(0, 0)).stream()
                .forEach(location -> assertThat(location.getRow()).isEqualTo(0));
        board.getLocationsInRow(Location.of(5, 0)).stream()
                .forEach(location -> assertThat(location.getRow()).isEqualTo(5));
        board.getLocationsInRow(Location.of(8, 0)).stream()
                .forEach(location -> assertThat(location.getRow()).isEqualTo(8));
    }

    @Test
    public void getLocationsInColumn() {
        board.getLocationsInColumn(Location.of(0, 1)).stream()
                .forEach(location -> assertThat(location.getColumn()).isEqualTo(1));
        board.getLocationsInColumn(Location.of(0, 4)).stream()
                .forEach(location -> assertThat(location.getColumn()).isEqualTo(4));
        board.getLocationsInColumn(Location.of(0, 7)).stream()
                .forEach(location -> assertThat(location.getColumn()).isEqualTo(7));
    }

    @Test
    public void getLocationsInQuadrant() {
        assertgetCellsInQuadrant(Location.of(4, 5));
        assertgetCellsInQuadrant(Location.of(6, 1));
        assertgetCellsInQuadrant(Location.of(0, 2));
    }

    private void assertgetCellsInQuadrant(Location location) {
        int expectedQuadrant = board.getQuadrantOf(location);

        board.getLocationsInQuadrant(location).stream()
                .map(Board9x9::getQuadrantOf)
                .forEach(result -> assertThat(result).isEqualTo(expectedQuadrant));
    }

    @Test
    public void getQuadrantOf() {
        assertThat(Board9x9.getQuadrantOf(Location.of(0, 0))).isEqualTo(0);
        assertThat(Board9x9.getQuadrantOf(Location.of(1, 0))).isEqualTo(0);
        assertThat(Board9x9.getQuadrantOf(Location.of(2, 0))).isEqualTo(0);
        assertThat(Board9x9.getQuadrantOf(Location.of(3, 0))).isEqualTo(1);
        assertThat(Board9x9.getQuadrantOf(Location.of(4, 0))).isEqualTo(1);
        assertThat(Board9x9.getQuadrantOf(Location.of(5, 0))).isEqualTo(1);
        assertThat(Board9x9.getQuadrantOf(Location.of(6, 0))).isEqualTo(2);
        assertThat(Board9x9.getQuadrantOf(Location.of(7, 0))).isEqualTo(2);
        assertThat(Board9x9.getQuadrantOf(Location.of(8, 0))).isEqualTo(2);

        assertThat(Board9x9.getQuadrantOf(Location.of(0, 1))).isEqualTo(0);
        assertThat(Board9x9.getQuadrantOf(Location.of(0, 2))).isEqualTo(0);
        assertThat(Board9x9.getQuadrantOf(Location.of(0, 3))).isEqualTo(3);
        assertThat(Board9x9.getQuadrantOf(Location.of(0, 4))).isEqualTo(3);
        assertThat(Board9x9.getQuadrantOf(Location.of(0, 5))).isEqualTo(3);
        assertThat(Board9x9.getQuadrantOf(Location.of(0, 6))).isEqualTo(6);
        assertThat(Board9x9.getQuadrantOf(Location.of(0, 7))).isEqualTo(6);
        assertThat(Board9x9.getQuadrantOf(Location.of(0, 8))).isEqualTo(6);

        assertThat(Board9x9.getQuadrantOf(Location.of(1, 1))).isEqualTo(0);
        assertThat(Board9x9.getQuadrantOf(Location.of(2, 2))).isEqualTo(0);
        assertThat(Board9x9.getQuadrantOf(Location.of(3, 3))).isEqualTo(4);
        assertThat(Board9x9.getQuadrantOf(Location.of(4, 4))).isEqualTo(4);
        assertThat(Board9x9.getQuadrantOf(Location.of(5, 5))).isEqualTo(4);
        assertThat(Board9x9.getQuadrantOf(Location.of(6, 6))).isEqualTo(8);
        assertThat(Board9x9.getQuadrantOf(Location.of(7, 7))).isEqualTo(8);
        assertThat(Board9x9.getQuadrantOf(Location.of(8, 8))).isEqualTo(8);

        assertThat(Board9x9.getQuadrantOf(Location.of(4, 1))).isEqualTo(1);
        assertThat(Board9x9.getQuadrantOf(Location.of(5, 2))).isEqualTo(1);
        assertThat(Board9x9.getQuadrantOf(Location.of(6, 3))).isEqualTo(5);
        assertThat(Board9x9.getQuadrantOf(Location.of(7, 4))).isEqualTo(5);
        assertThat(Board9x9.getQuadrantOf(Location.of(8, 5))).isEqualTo(5);
        assertThat(Board9x9.getQuadrantOf(Location.of(1, 6))).isEqualTo(6);
        assertThat(Board9x9.getQuadrantOf(Location.of(2, 7))).isEqualTo(6);
        assertThat(Board9x9.getQuadrantOf(Location.of(3, 8))).isEqualTo(7);

        assertThat(Board9x9.getQuadrantOf(Location.of(7, 1))).isEqualTo(2);
        assertThat(Board9x9.getQuadrantOf(Location.of(8, 2))).isEqualTo(2);
        assertThat(Board9x9.getQuadrantOf(Location.of(1, 3))).isEqualTo(3);
        assertThat(Board9x9.getQuadrantOf(Location.of(2, 4))).isEqualTo(3);
        assertThat(Board9x9.getQuadrantOf(Location.of(3, 5))).isEqualTo(4);
        assertThat(Board9x9.getQuadrantOf(Location.of(4, 6))).isEqualTo(7);
        assertThat(Board9x9.getQuadrantOf(Location.of(5, 7))).isEqualTo(7);
        assertThat(Board9x9.getQuadrantOf(Location.of(6, 8))).isEqualTo(8);
    }

}