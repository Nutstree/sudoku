package nl.nutstree.sudoku.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
    }

    @Test
    public void emptyBoard() {
        assertBoardEmpty(board);
    }

    private void assertBoardEmpty(Board board) {
        for (int row = 0; row < board.getSize(); row++) {
            for (int column = 1; column < board.getSize(); column++) {
                Location location = Location.of(row, column);

                assertThat(board.getValue(location)).isEmpty();
                assertThat(board.getPossibilities(location)).containsExactly(1, 2, 3, 4, 5, 6, 7, 8, 9);
            }
        }
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

        assertThat(board.getValue(location)).contains(value);
        assertThat(board.getPossibilities(location)).isEmpty();
    }

    @Test
    public void afterSettingValueOnlocation_relatedCellsPossibilityAdjusted() {
        Location location = Location.of(7, 7);
        board.setValue(5, location);

        assertRowDoesNotContainPossibility(5, location);
        assertColumnDoesNotContainPossibility(5, location);
        assertQuadrantDoesNotContainPossibility(5, location);
    }

    private void assertRowDoesNotContainPossibility(int possibility, Location location) {
        assertCellsDoNotContainPossibility(board.getCellsInRow(location.getX()), possibility);
    }

    private void assertColumnDoesNotContainPossibility(int possibility, Location location) {
        assertCellsDoNotContainPossibility(board.getCellsInColumn(location.getY()), possibility);

    }

    private void assertQuadrantDoesNotContainPossibility(int possibility, Location location) {
        assertCellsDoNotContainPossibility(board.getCellsInQuadrant(location), possibility);

    }

    private void assertCellsDoNotContainPossibility(Collection<Cell> cells, int possibility) {
        cells.stream()
                .forEach(cell -> assertPossiblityNotInCell(cell, possibility));
    }

    private void assertPossiblityNotInCell(Cell cell, int possibility) {
        assertThat(cell.getPossibilities()).doesNotContain(possibility);
    }

    @Test
    public void getCellsInRow() {
        board.getCellsInRow(0).stream()
                .forEach(cell -> assertThat(cell.getLocation().getX()).isEqualTo(0));
        board.getCellsInRow(5).stream()
                .forEach(cell -> assertThat(cell.getLocation().getX()).isEqualTo(5));
        board.getCellsInRow(8).stream()
                .forEach(cell -> assertThat(cell.getLocation().getX()).isEqualTo(8));
    }

    @Test
    public void getCellsInColumn() {
        board.getCellsInColumn(1).stream()
                .forEach(cell -> assertThat(cell.getLocation().getY()).isEqualTo(1));
        board.getCellsInColumn(4).stream()
                .forEach(cell -> assertThat(cell.getLocation().getY()).isEqualTo(4));
        board.getCellsInColumn(7).stream()
                .forEach(cell -> assertThat(cell.getLocation().getY()).isEqualTo(7));
    }

    @Test
    public void getCellsInQuadrant() {
        assertgetCellsInQuadrant(Location.of(4, 5));
        assertgetCellsInQuadrant(Location.of(6, 1));
        assertgetCellsInQuadrant(Location.of(0, 2));
    }

    private void assertgetCellsInQuadrant(Location location) {
        int expectedQuadrant = location.getQuadrant();

        board.getCellsInQuadrant(location).stream()
                .map(Cell::getLocation)
                .map(Location::getQuadrant)
                .forEach(result -> assertThat(result).isEqualTo(expectedQuadrant));
    }

}