package nl.nutstree.sudoku.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    private Board board;
    private Location location;

    @BeforeEach
    public void setUp() {
        board = new Board();
        location = Location.of(0, 0);

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
        assertThatThrownBy(() -> board.setValue(0, location))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> board.setValue(-1, location))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> board.setValue(10, location))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void setLegalValues_containsValueAfterSetting() {
        IntStream.rangeClosed(1, 9)
        .forEach(this::assertSetValueCorrect);
    }

    private void assertSetValueCorrect(int value) {
        board.setValue(value, location);

        assertThat(board.getValue(location)).contains(value);
        assertThat(board.getPossibilities(location)).isEmpty();
    }

    @Test
    public void afterSettingValueOnlocation_relatedCellsPossibilityAdjusted() {
        board.setValue(5, location);

        assertRowDoesNotContainPossibility(5, location);
        assertColumnDoesNotContainPossibility(5, location);
        assertQuadrantDoesNotContainPossibility(5, location);
    }

    private void assertRowDoesNotContainPossibility(int possibility, Location location) {
        board.getCellsInRow(location.getX()).stream()
                .forEach(cell -> assertPossiblityNotInCell(cell, possibility));
    }

    private void assertColumnDoesNotContainPossibility(int possibility, Location location) {
        board.getCellsInColumn(location.getY()).stream()
                .forEach(cell -> assertPossiblityNotInCell(cell, possibility));
    }

    private void assertQuadrantDoesNotContainPossibility(int possibility, Location location) {
        board.getCellsInQuadrant(location).stream()
                .forEach(cell -> assertPossiblityNotInCell(cell, possibility));
    }

    private void assertPossiblityNotInCell(Cell cell, int possibility) {
        assertThat(cell.getPossibilities()).doesNotContain(possibility);
    }

}