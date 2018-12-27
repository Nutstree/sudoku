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
    public void constructBoardWithSudokuPuzzle_nonZeroFieldsCorrectlyFilled() {
        board = new Board("010020300004005060070000008006900070000100002030048000500006040000800106008000000");

        // 010020300
        assertThat(board.getValue(Location.of(1, 0))).contains(1);
        assertThat(board.getValue(Location.of(4, 0))).contains(2);
        assertThat(board.getValue(Location.of(6, 0))).contains(3);
        assertThat(board.getValue(Location.of(0, 0))).isEmpty();

        // 004005060
        assertThat(board.getValue(Location.of(2, 1))).contains(4);
        assertThat(board.getValue(Location.of(5, 1))).contains(5);
        assertThat(board.getValue(Location.of(7, 1))).contains(6);

        // 070000008
        assertThat(board.getValue(Location.of(1, 2))).contains(7);
        assertThat(board.getValue(Location.of(8, 2))).contains(8);

        // 006900070
        assertThat(board.getValue(Location.of(2, 3))).contains(6);
        assertThat(board.getValue(Location.of(3, 3))).contains(9);
        assertThat(board.getValue(Location.of(7, 3))).contains(7);

        // 000100002
        assertThat(board.getValue(Location.of(3, 4))).contains(1);
        assertThat(board.getValue(Location.of(8, 4))).contains(2);

        // 030048000
        assertThat(board.getValue(Location.of(1, 5))).contains(3);
        assertThat(board.getValue(Location.of(4, 5))).contains(4);
        assertThat(board.getValue(Location.of(5, 5))).contains(8);

        // 500006040
        assertThat(board.getValue(Location.of(0, 6))).contains(5);
        assertThat(board.getValue(Location.of(5, 6))).contains(6);
        assertThat(board.getValue(Location.of(7, 6))).contains(4);

        // 000800106
        assertThat(board.getValue(Location.of(3, 7))).contains(8);
        assertThat(board.getValue(Location.of(6, 7))).contains(1);
        assertThat(board.getValue(Location.of(8, 7))).contains(6);

        // 008000000
        assertThat(board.getValue(Location.of(2, 8))).contains(8);
    }

    @Test
    public void constructBoardWithSudokuPuzzle_zeroFieldsStillEmpty() {
        board = new Board("010020300004005060070000008006900070000100002030048000500006040000800106008000000");

        // random pick to check if fields are left empty
        assertThat(board.getValue(Location.of(0, 0))).isEmpty();
        assertThat(board.getValue(Location.of(6, 1))).isEmpty();
        assertThat(board.getValue(Location.of(3, 2))).isEmpty();
        assertThat(board.getValue(Location.of(8, 3))).isEmpty();
        assertThat(board.getValue(Location.of(2, 4))).isEmpty();
        assertThat(board.getValue(Location.of(7, 5))).isEmpty();
        assertThat(board.getValue(Location.of(1, 6))).isEmpty();
        assertThat(board.getValue(Location.of(4, 7))).isEmpty();
        assertThat(board.getValue(Location.of(5, 8))).isEmpty();
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